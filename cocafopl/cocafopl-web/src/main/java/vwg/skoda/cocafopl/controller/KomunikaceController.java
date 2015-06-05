package vwg.skoda.cocafopl.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafopl.entity.MtKalkulace;
import vwg.skoda.cocafopl.entity.PredstavitelKalkulace;
import vwg.skoda.cocafopl.entity.PredstavitelMessage;
import vwg.skoda.cocafopl.entity.PredstavitelPr;
import vwg.skoda.cocafopl.service.MtKalkulaceService;
import vwg.skoda.cocafopl.service.PredstavitelKalkulaceService;
import vwg.skoda.cocafopl.service.PredstavitelMessageService;
import vwg.skoda.cocafopl.service.PredstavitelPrService;
import vwg.skoda.favas.mbv.Equipment;
import vwg.skoda.favas.mbv.Message;
import vwg.skoda.favas.mbv.VehicleOrder;
import vwg.skoda.favas.mbv.VehicleOrder.Favas;

@Controller
public class KomunikaceController {

	static Logger log = Logger.getLogger(PredstavitelController.class);

	@Autowired
	private PredstavitelPrService servicePredstavitelPr;

	@Autowired
	private PredstavitelMessageService servicePredstavitelMessage;

	@Autowired
	private PredstavitelKalkulaceService servicePredstavitelKalkulace;

	@Autowired
	private MtKalkulaceService serviceMtKalkulace;

	@RequestMapping("/komunikaceFavas/{idMtKalk}/{idPredKalk}/{vseNeboZbytek}")
	public String komunikaceFavas(@PathVariable long idMtKalk, @PathVariable long idPredKalk, @PathVariable String vseNeboZbytek, Model model, HttpServletRequest req, HttpSession session)
			throws ParseException, InterruptedException {

		session.setAttribute("errorMesage", null);

		List<PredstavitelKalkulace> pk_ke_zpracovani = new ArrayList<PredstavitelKalkulace>();
		MtKalkulace mtk = new MtKalkulace();

		if (idMtKalk == 0 && idPredKalk > 0) {
			log.debug("###\t komunikaceFavas(" + idMtKalk + ", " + idPredKalk + ") ... komunikace jednoho predstavitele z obrazovky DETAIL PREDSTAVITELE");
			pk_ke_zpracovani.add(servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk));
		} else {
			log.debug("###\t detailKomunikaceFavas(" + idMtKalk + ", " + idPredKalk + ") ... hromadna komunikace predstavitelu z obrazovky SEZNAM PREDSTAVITELU (" + vseNeboZbytek + ")");
			mtk = serviceMtKalkulace.getMtKalkulaceId(idMtKalk);
			List<PredstavitelKalkulace> pkxxx = servicePredstavitelKalkulace.getPredstaviteleKalkulace(mtk.getGz39tMt().getModelTr(), mtk.getGz39tMt().getZavod(), mtk.getGz39tKalkulace()
					.getKalkulace());
			if (vseNeboZbytek.startsWith("vse")) {
				for (PredstavitelKalkulace predstavitelKalkulace : pkxxx) {
					pk_ke_zpracovani.add(predstavitelKalkulace);
				}
			} else {
				for (PredstavitelKalkulace predstavitelKalkulace : pkxxx) {
					if (predstavitelKalkulace.getExistsPr() == null)
						pk_ke_zpracovani.add(predstavitelKalkulace);
				}
			}
		}

		int pocet_komunikovanych_predstavitelu = pk_ke_zpracovani.size();
		int i = 1;
		Boolean probehlaAlesponJednaKomunikace = false;
		for (PredstavitelKalkulace pk : pk_ke_zpracovani) {

			log.debug("###\t\t " + i++ + "/" + pocet_komunikovanych_predstavitelu + " komunikace pro: " + pk.getGz39tPredstavitel().getCisloPred() + " - "
					+ pk.getGz39tPredstavitel().getModelovyKlic() + ", " + pk.getGz39tMtKalkulace().getMrok() + ", " + pk.getGz39tPredstavitel().getKodZeme() + ", "
					+ pk.getGz39tMtKalkulace().getGz39tKalkulace().getKalkulacniDatum() + ", " + (pk.getVybavyEdit() != null ? pk.getVybavyEdit() : pk.getGz39tPredstavitel().getVybavy()));

			VehicleOrder o = new VehicleOrder();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date datum = sdf.parse(pk.getGz39tMtKalkulace().getGz39tKalkulace().getKalkulacniDatum());
			Calendar cal = Calendar.getInstance();
			cal.setTime(datum);
			o.setDate(cal);
			o.setNationalSalesProgramme(pk.getGz39tPredstavitel().getKodZeme());
			o.setModelCode(pk.getGz39tPredstavitel().getModelovyKlic());
			o.setModelYear(pk.getGz39tMtKalkulace().getMrok());
			// o.setEquipment("PK22B1");

			if (pk.getVybavyEdit() != null && pk.getVybavyEdit().startsWith("+")) {
				// smysluplna korekce vybavy (př.: +AAA+BBB+CCC)
				// jeste je tam varianta, ze kor.vybava == "Původní výbava potlačena!" ... v tomto pripade se nenaSETuje zadna vybava
				o.setEquipment(pk.getVybavyEdit().replace("+", "").trim());
			} else if (pk.getVybavyEdit() == null && pk.getGz39tPredstavitel().getVybavy() != null) {
				// bere se puvodni nadefinovana vybava, protoze korigovana vybava neexistuje (vyjimka vyse)!
				o.setEquipment(pk.getGz39tPredstavitel().getVybavy().replace("+", "").trim());
			}

			// FAVAS (konkretne metoda o.getFavas()) obcas vyhodi chybu: Server returned HTTP response code: 501 for URL: https://smbdpp1c.fw.skoda.vwg:30700 ... ale jen ji vypise
			// a metoda v tomto pripade vraci NULL.
			// V 99% pripadech se po okamzitem opetovnem zavolani teto metody chyba jiz nevyskytne. Proto kdyz mi metoda vrati null, tak to zkusim jeste 2x a az potom vyhodim
			// chybu. V tom pripade ale uz padne aplikace!
			Favas favas = null;
			for (int f = 1; f <= 3; f++) {
				if (o.getFavas() == null) {
					log.error("###\t BACHA ... " + f + ". neuspesny pokus komunikace s FAVASem {getFavas() vratil NULL}, zkusim znova za 5 sekund (max 3 pokusy).");
					if (f == 3) {
						session.setAttribute("errorMesage", "Chyba komunikace MBV/Favas! Obrattě se prosím na EO partnera aplikace.");
						break;
					}
					Thread.sleep(5000);
					continue;
				} else {
					favas = o.getFavas();

					servicePredstavitelPr.removePredstavitelPrAll(pk.getId());
					servicePredstavitelMessage.removePredstavitelMessageAll(pk.getId());

					Set<Equipment> equipment = favas.getEquipment();
					if (equipment.size() > 0) {
						// log.debug("###\t\t equipment: " + equipment.size());
						for (Equipment eq : equipment) {
							log.trace(eq.getFamily() + "-" + eq.getType() + "-" + eq.getValue() + ", ");
							PredstavitelPr pp = new PredstavitelPr();
							pp.setRodina(eq.getFamily());
							pp.setTyp(eq.getType());
							pp.setPr(eq.getValue());
							pp.setUuser(req.getUserPrincipal().getName().toUpperCase());
							pp.setUtime(new Date());
							pp.setGz39tPredstavitelKalkulace(pk);
							servicePredstavitelPr.addPredstavitelPr(pp);
						}
						pk.setUuser(req.getUserPrincipal().getName().toUpperCase());
						pk.setUtime(new Date());
						pk.setExistsPr(equipment.size());
						servicePredstavitelKalkulace.setPredstavitelKalkulace(pk);
					}

					Set<vwg.skoda.favas.mbv.Message> message = favas.getMessage();
					if (message.size() > 0) {
						for (Message me : message) {
							log.trace("###\t\t message\t" + me.getCode() + "\t" + me.getText());
							PredstavitelMessage pm = new PredstavitelMessage();
							pm.setKod(me.getCode());
							pm.setText(me.getText());
							pm.setUuser(req.getUserPrincipal().getName().toUpperCase());
							pm.setUtime(new Date());
							pm.setGz39tPredstavitelKalkulace(pk);
							servicePredstavitelMessage.addPredstavitelMessage(pm);
						}
					}

					Set<vwg.skoda.favas.mbv.Error> errorFavas = favas.getError();
					if (errorFavas.size() > 0) {
						for (vwg.skoda.favas.mbv.Error er : errorFavas) {
							log.error("###\t\t errorFavas\t" + er.getErrNo() + ", " + er.getSource() + ", " + er.getText() + ", " + er.getWeight() + ", " + er.getType());
						}
					}

					// for (Entry<String, String[]> e : favas.getPacketInfo().entrySet()) {
					// for (String pr : e.getValue()) {
					// System.out.println("### packetInfo\t" + e.getKey() + " - " + pr);
					// }
					// }
					// String[] packStruk = favas.getPacketStruc("PK4");
					// for (int i = 0; i < packStruk.length; i++) {
					// System.out.println("### packet struk\t" + packStruk[i]);
					// }
					log.debug("###\t\t ... konec komunikace.");
					probehlaAlesponJednaKomunikace = true;
					break;
				}
			}

			if (probehlaAlesponJednaKomunikace) {
				log.trace("###\t\t Komunikace MBV/Favas - ulozeni posledni editace do MT_KALKULACE.");
				MtKalkulace mtKalkulacePoslEdit = serviceMtKalkulace.getMtKalkulace(pk.getGz39tMtKalkulace().getGz39tKalkulace().getKalkulace(), pk.getGz39tMtKalkulace().getGz39tMt().getModelTr(), pk
						.getGz39tMtKalkulace().getGz39tMt().getZavod());
				mtKalkulacePoslEdit.setPosledniEditace(new Date());
				mtKalkulacePoslEdit.setPosledniEditaceDuvod("Komunikace MBV/Favas: " + pk.getGz39tPredstavitel().getCisloPred() + ", " + pk.getGz39tPredstavitel().getModelovyKlic().toUpperCase()
						+ " - " + pk.getGz39tMtKalkulace().getGz39tMt().getZavod());
				serviceMtKalkulace.setMtKalkulace(mtKalkulacePoslEdit);
			}
		}

		if (idMtKalk == 0 && idPredKalk > 0) {
			// probehla komunikace jednoho predstavitele z obrazovky DETAIL PREDSTAVITELE
			return "redirect:/srv/predstavitel/detail/" + idPredKalk;
		} else {
			// probehla hromadna komunikace predstavitelu z obrazovky SEZNAM PREDSTAVITELU
			return "redirect:/srv/predstavitel/seznam/" + mtk.getGz39tKalkulace().getKalkulace();
		}

	}

}
