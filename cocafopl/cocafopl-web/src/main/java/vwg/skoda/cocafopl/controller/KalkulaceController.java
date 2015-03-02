package vwg.skoda.cocafopl.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vwg.skoda.cocafopl.entity.Kalkulace;
import vwg.skoda.cocafopl.entity.KalkulaceMtZavod;
import vwg.skoda.cocafopl.entity.Mt;
import vwg.skoda.cocafopl.entity.MtKalkulace;
import vwg.skoda.cocafopl.entity.MtProd;
import vwg.skoda.cocafopl.entity.Protokol;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.obj.UniObj;
import vwg.skoda.cocafopl.service.KalkulaceMtZavodService;
import vwg.skoda.cocafopl.service.KalkulaceService;
import vwg.skoda.cocafopl.service.MtKalkulaceService;
import vwg.skoda.cocafopl.service.MtProdService;
import vwg.skoda.cocafopl.service.MtService;
import vwg.skoda.cocafopl.service.ProtokolService;
import vwg.skoda.cocafopl.service.UserService;

@Controller
@RequestMapping("/kalkulace")
public class KalkulaceController {

	static Logger log = Logger.getLogger(KalkulaceController.class);

	@Autowired
	private ProtokolService serviceProtokol;

	@Autowired
	private UserService serviceUser;

	@Autowired
	private MtService serviceMt;

	@Autowired
	private MtProdService serviceMtProd;

	@Autowired
	private KalkulaceService serviceKalkulace;

	@Autowired
	private MtKalkulaceService serviceMtKalkulace;

	@Autowired
	private KalkulaceMtZavodService serviceKalkulaceMtZavod;

	@RequestMapping("/mtDefinice")
	public String mtDefinice(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t MtDefinice()");

		session.setAttribute("pageTitle", "Modelová třída");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		model.addAttribute("aktualniRRRRMM", sdf.format(new Date()));

		List<Mt> mtList = serviceMt.getMtActual();
		model.addAttribute("mtList", mtList);

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().contains("APPROVERS"))
			model.addAttribute("moznoEditovat", true);
		else
			model.addAttribute("moznoEditovat", false);

		return "/mtDefinice";
	}

	@RequestMapping("/mtDefiniceZobrazitVse")
	public String mtDefiniceZobrazitVse(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t mtDefiniceZobrazitVse()");
		session.setAttribute("pageTitle", "Modelová třída");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date ted = new Date();
		model.addAttribute("aktualniRRRRMM", sdf.format(ted));

		List<Mt> mtList = serviceMt.getMtAll();
		model.addAttribute("mtList", mtList);

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().contains("APPROVERS"))
			model.addAttribute("moznoEditovat", true);
		else
			model.addAttribute("moznoEditovat", false);

		return "/mtDefinice";
	}

	@RequestMapping(headers = "Content-Type=application/json", value = "/autocompleteMtProd")
	@ResponseBody
	public Iterable<String> autocompleteMtProd(@RequestParam String string) {
		log.debug("\t### autocompleteMtProd:" + string);
		return serviceMtProd.findMtByString(string);
	}

	@RequestMapping(headers = "Content-Type=application/json", value = "/getMt")
	@ResponseBody
	public Mt getMt(@RequestParam long id) {
		log.debug("\t### getMt:" + id);
		return serviceMt.getMt(id);
	}

	@RequestMapping("/mtDefiniceNovaMt")
	public String mtDefiniceNovaMt(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t mtDefiniceNovaMt(" + mt.getModelTr() + "," + mt.getZavod() + "," + mt.getKodZeme() + "," + mt.getPlatnostOd() + "-" + mt.getPlatnostDo() + ", " + mt.getPopis() + ")");

		MtProd mtProd = serviceMtProd.getMtProd(mt.getModelTr());
		log.debug("###\t\t Dotazeni produktu k modelove tride: " + mt.getModelTr() + " -> " + mtProd.getProdukt());

		// System.out.println("|"+serviceMt.getMt(mt.getModelTr())+"|"+serviceMt.getMt(mt.getModelTr()).getModelTr().length());
		if (serviceMt.getMt(mt.getModelTr()) == null) {
			Mt newMt = new Mt();
			newMt.setModelTr(mt.getModelTr().toUpperCase());
			newMt.setProdukt(mtProd.getProdukt());
			newMt.setZavod(mt.getZavod().toUpperCase());
			newMt.setKodZeme(mt.getKodZeme().toUpperCase());
			newMt.setPopis(mt.getPopis());
			newMt.setPlatnostOd(mt.getPlatnostOd());
			newMt.setPlatnostDo(mt.getPlatnostDo());
			newMt.setUuser(req.getUserPrincipal().getName().toUpperCase());
			newMt.setUtime(new Date());
			serviceMt.addMt(newMt);
		}
		
		// Spoji entity MT a KALKULACE (pres platnost) a vrati IDcka, ktere jeste nejsou v entity MtKalkulace, aby se mohly nasledne vytvorit zatim neexistujici "radky" v entite MtKalkulace :) 
		// Duvodem tohoto kroku je to, ze MT a kalkulace mohou vznikat nezavisle na sobe a v entite MtKalkulace jsou spojeny.
		List<String> idKalkMt = serviceKalkulaceMtZavod.getIdKalkulacaAndIdMt();
		if (!idKalkMt.isEmpty()) {
			for (String s : idKalkMt) {
				long idKalkulace = Long.valueOf((s.substring(0, s.indexOf(";"))));
				long idMt = Long.valueOf(s.substring(s.indexOf(";")+1));
				//System.out.println("id k a id mt: "+s+"\t"+idKalkulace+" ... "+idMt+"\t"+s.indexOf(";"));
				MtKalkulace mtk = new MtKalkulace();
				mtk.setGz39tKalkulace(serviceKalkulace.getKalkulace(idKalkulace));
				mtk.setGz39tMt(serviceMt.getMt(idMt));
				mtk.setUtime(new Date());
				mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
				serviceMtKalkulace.setMtKalkulace(mtk);
			}
		}
		return "redirect:/srv/kalkulace/mtDefinice";
	}

	@RequestMapping(value = "/editMtForm/{idMt}")
	public String editMtForm(@PathVariable long idMt, Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t editMtForm(" + idMt + ");");
		session.setAttribute("pageTitle", "Editace modelové třídy");

		model.addAttribute("mtInput", serviceMt.getMt(idMt));

		return "/mtEditaceMt";
	}

	@RequestMapping(value = "/editMtFormSubmit")
	public String editMtFormSubmit(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t editMtFormSubmit(" + mt.getModelTr() + "," + mt.getZavod() + "," + mt.getKodZeme() + "," + mt.getPlatnostOd() + "-" + mt.getPlatnostDo() + ", " + mt.getPopis() + ")");

		Mt mtEdit = serviceMt.getMt(mt.getId());
		mtEdit.setModelTr(mt.getModelTr());
		mtEdit.setZavod(mt.getZavod());
		mtEdit.setKodZeme(mt.getKodZeme());
		mtEdit.setPopis(mt.getPopis());
		mtEdit.setPlatnostOd(mt.getPlatnostOd());
		mtEdit.setPlatnostDo(mt.getPlatnostDo());
		mtEdit.setUtime(new Date());
		mtEdit.setUuser(req.getUserPrincipal().getName().toUpperCase());
		serviceMt.setMt(mtEdit);
		
		// Spoji entity MT a KALKULACE (pres platnost) a vrati IDcka, ktere jeste nejsou v entity MtKalkulace, aby se mohly nasledne vytvorit zatim neexistujici "radky" v entite MtKalkulace :) 
		// Duvodem tohoto kroku je to, ze MT a kalkulace mohou vznikat nezavisle na sobe a v entite MtKalkulace jsou spojeny.
		List<String> idKalkMt = serviceKalkulaceMtZavod.getIdKalkulacaAndIdMt();
		if (!idKalkMt.isEmpty()) {
			for (String s : idKalkMt) {
				long idKalkulace = Long.valueOf((s.substring(0, s.indexOf(";"))));
				long idMt = Long.valueOf(s.substring(s.indexOf(";")+1));
				//System.out.println("id k a id mt: "+s+"\t"+idKalkulace+" ... "+idMt+"\t"+s.indexOf(";"));
				MtKalkulace mtk = new MtKalkulace();
				mtk.setGz39tKalkulace(serviceKalkulace.getKalkulace(idKalkulace));
				mtk.setGz39tMt(serviceMt.getMt(idMt));
				mtk.setUtime(new Date());
				mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
				serviceMtKalkulace.setMtKalkulace(mtk);
			}
		}

		return "redirect:/srv/kalkulace/mtDefinice";
	}

	@RequestMapping(value = "/smazatMt/{idMt}")
	public String smazatMt(@PathVariable long idMt, Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t smazatMt(" + idMt + ")");

		Mt mtRemove = serviceMt.getMt(idMt);
		String message = mtRemove.getModelTr() + ", " + mtRemove.getZavod() + ", " + mtRemove.getProdukt() + ", " + mtRemove.getKodZeme() + ", " + mtRemove.getPopis() + ", "
				+ mtRemove.getPlatnostOd() + "-" + mtRemove.getPlatnostDo();
		serviceMt.removeMt(mtRemove);

		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Smazani MT");
		newProtokol.setInfo(message);
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);
		return "redirect:/srv/kalkulace/mtDefinice";
		
		// TODO: pokud neudelam vazbu z entity Mt do entity Predstavitel, tak musim zajistit v pripade smazani cele Mt i zmazani postihnutych Predstavitelu. 
	}

	/* ============================================================================================================= */
	@RequestMapping("/seznam")
	public String kalkulaceSeznam(UniObj uniObj, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t kalkulaceSeznam(" + uniObj.getRok() + ")");
		session.setAttribute("pageTitle", "Seznam kalkulací");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().contains("APPROVERS"))
			model.addAttribute("moznoEditovat", true);
		else
			model.addAttribute("moznoEditovat", false);

		List<Integer> listRoku = serviceKalkulace.getKalkulaceRoky();
		model.addAttribute("listRoku", listRoku);

		if (uniObj.getRok() == null || uniObj.getRok()=="0") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String aktualRok = sdf.format(new Date());
			uniObj.setRok(aktualRok);
		}

		// Spoji entity MT a KALKULACE (pres platnost) a vrati IDcka, ktere jeste nejsou v entity MtKalkulace, aby se mohly nasledne vytvorit zatim neexistujici "radky" v entite MtKalkulace :) 
		// Duvodem tohoto kroku je to, ze MT a kalkulace mohou vznikat nezavisle na sobe a v entite MtKalkulace jsou spojeny.
		List<String> idKalkMt = serviceKalkulaceMtZavod.getIdKalkulacaAndIdMt();
		if (!idKalkMt.isEmpty()) {
			for (String s : idKalkMt) {
				long idKalkulace = Long.valueOf((s.substring(0, s.indexOf(";"))));
				long idMt = Long.valueOf(s.substring(s.indexOf(";")+1));
				//System.out.println("id k a id mt: "+s+"\t"+idKalkulace+" ... "+idMt+"\t"+s.indexOf(";"));
				MtKalkulace mtk = new MtKalkulace();
				mtk.setGz39tKalkulace(serviceKalkulace.getKalkulace(idKalkulace));
				mtk.setGz39tMt(serviceMt.getMt(idMt));
				mtk.setUtime(new Date());
				mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
				serviceMtKalkulace.setMtKalkulace(mtk);
			}
		}

		List<KalkulaceMtZavod> kalkulaceProRok = serviceKalkulaceMtZavod.getKalkulaceMtZavod(Integer.valueOf(uniObj.getRok()));
		model.addAttribute("kalkulaceProRok", kalkulaceProRok);

		return "/kalkulaceSeznam";
	}

	@RequestMapping("/kalkulaceNova")
	public String kalkulaceNova(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t kalkulaceNova()");
		session.setAttribute("pageTitle", "Seznam kalkulací");

		Integer lastRok = serviceKalkulace.getKalkulacePosledniRok();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String aktualRok = sdf.format(new Date());
		//model.addAttribute("aktualniRRRRMM", sdf.format(new Date()));
		

		if (lastRok == null) {
			log.debug("###\t Neexistuji zadne kalkulace. Zakladam nove kalkulace dle aktualniho roku: " + aktualRok);
			for (int i = 1; i <= 12; i++) {
				Calendar monday = Calendar.getInstance();
				monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				monday.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
				monday.set(Calendar.YEAR, Integer.valueOf(aktualRok));
				monday.set(Calendar.MONTH, (i - 1)); // mesice v CALENDAR jsou 0 do 11 !!!
				// System.out.println(monday.get(Calendar.YEAR) + " - " + (monday.get(Calendar.MONTH)) + " - " + monday.get(Calendar.DAY_OF_MONTH));

				String mesice = (monday.get(Calendar.MONTH) + 1) < 10 ? "0" + Integer.toString((monday.get(Calendar.MONTH) + 1)) : Integer.toString((monday.get(Calendar.MONTH) + 1));

				Kalkulace kal = new Kalkulace();
				kal.setKalkulace(Integer.valueOf(aktualRok + mesice));
				//kal.setRok(Integer.valueOf(aktualRok));
				kal.setKalkulacniDatum(aktualRok + mesice + "0" + Integer.toString(monday.get(Calendar.DAY_OF_MONTH)));
				kal.setUtime(new Date());
				kal.setUuser(req.getUserPrincipal().getName().toUpperCase());
				serviceKalkulace.addKalkulace(kal);

				// Naplneni entity MtKalkulace
				Kalkulace kalSaved = serviceKalkulace.getKalkulace(Integer.valueOf(aktualRok + mesice));
				List<Mt> mtPlatne = serviceMt.getMtPlatneK(kalSaved.getKalkulace());
				for (Mt mt : mtPlatne) {
					MtKalkulace mtk = new MtKalkulace();
					mtk.setGz39tKalkulace(kalSaved);
					mtk.setGz39tMt(mt);
					mtk.setUtime(new Date());
					mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
					serviceMtKalkulace.setMtKalkulace(mtk);
				}
			}
		} else {
			// povoluji zalozit nove roky pouze na jeden dopredu, vice je uz kravina
			if (Integer.valueOf(aktualRok) + 1 > lastRok) {
				log.debug("###\t Zakladam nove kalkulace pro rok: " + (lastRok + 1));
				for (int i = 1; i <= 12; i++) {
					Calendar monday = Calendar.getInstance();
					monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
					monday.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
					monday.set(Calendar.YEAR, Integer.valueOf(aktualRok));
					monday.set(Calendar.MONTH, (i - 1)); // mesice v CALENDAR jsou 0 do 11 !!!
					// System.out.println(monday.get(Calendar.YEAR) + " - " + (monday.get(Calendar.MONTH)) + " - " + monday.get(Calendar.DAY_OF_MONTH));

					String mesice = (monday.get(Calendar.MONTH) + 1) < 10 ? "0" + Integer.toString((monday.get(Calendar.MONTH) + 1)) : Integer.toString((monday.get(Calendar.MONTH) + 1));

					Kalkulace kal = new Kalkulace();
					kal.setKalkulace(Integer.valueOf(Integer.toString(lastRok + 1) + mesice));
					//kal.setRok(Integer.valueOf(Integer.toString(lastRok + 1)));
					kal.setKalkulacniDatum(Integer.toString(lastRok + 1) + mesice + "0" + Integer.toString(monday.get(Calendar.DAY_OF_MONTH)));
					kal.setUtime(new Date());
					kal.setUuser(req.getUserPrincipal().getName().toUpperCase());
					serviceKalkulace.addKalkulace(kal);

					// Naplneni entity MtKalkulace
					Kalkulace kalSaved = serviceKalkulace.getKalkulace(Integer.valueOf(Integer.toString(lastRok + 1) + mesice));
					List<Mt> mtPlatne = serviceMt.getMtPlatneK(kalSaved.getKalkulace());
					for (Mt mt : mtPlatne) {
						MtKalkulace mtk = new MtKalkulace();
						mtk.setGz39tKalkulace(kalSaved);
						mtk.setGz39tMt(mt);
						mtk.setUtime(new Date());
						mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
						serviceMtKalkulace.setMtKalkulace(mtk);
					}
				}
			} else
				log.debug("###\t Neni treba zakladat dalsi kalkulace pro nove roky, jiz je zalozeno pro rok: " + lastRok);
		}
		return "redirect:/srv/kalkulace/seznam";
	}

	@RequestMapping(value = "/editKalkulaceForm/{idKalkulace}")
	public String editKalkulaceForm(@PathVariable long idKalkulace, Kalkulace kalkalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t editKalkulaceForm(" + idKalkulace + ");");
		session.setAttribute("pageTitle", "Editace kalkulace");

		model.addAttribute("kalkulaceInput", serviceKalkulace.getKalkulace(idKalkulace));

		return "/kalkulaceEdit";
	}

	@RequestMapping(value = "/editKalkulaceFormSubmit")
	public String editKalkulaceFormSubmit(Kalkulace kalkalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t editKalkulaceFormSubmit(" + kalkalkulace.getId() + ", " + kalkalkulace.getKalkulace() + ", " + kalkalkulace.getKalkulacniDatum() + ")");

		Kalkulace k = serviceKalkulace.getKalkulace(kalkalkulace.getId());
		k.setKalkulacniDatum(kalkalkulace.getKalkulacniDatum());
		k.setUtime(new Date());
		k.setUuser(req.getUserPrincipal().getName().toUpperCase());
		serviceKalkulace.setKalkulace(k);
		return "redirect:/srv/kalkulace/seznam";
	}

	/* ============================================================================================================= */

	@RequestMapping("/detail")
	public String kalkulaceDetail(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t kalkulaceDetail()");
		// spousti se jen pri kliknutim v navigacni liste

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} else if (aktualUser.getUserRole().equals("ADMINS".trim())) {
			log.debug("###\t Uzivatel s roli ADMINS ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/logging";
		}

		if (serviceKalkulace.getKalkulaceAll().isEmpty()) {
			log.debug("###\t Neexistuji zadne kalkulace! Je nutne je nejdrive zadat.");
			return "redirect:/srv/kalkulace/seznam";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return "redirect:/srv/kalkulace/detail/" + sdf.format(new Date());
	}

	@RequestMapping("/detail/plusMesic")
	public String kalkulaceDetailPlusMesic(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t kalkulaceDetailPlus()");
		Kalkulace k = serviceKalkulace.getKalkulacePlusMesic((Integer) session.getAttribute("kalukaceRRRRMM"));
		return "redirect:/srv/kalkulace/detail/" + k.getKalkulace();
	}

	@RequestMapping("/detail/minusMesic")
	public String kalkulaceDetailMinusMesic(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t kalkulaceDetailMinusMesic()");
		Kalkulace k = serviceKalkulace.getKalkulaceMinusMesic((Integer) session.getAttribute("kalukaceRRRRMM"));
		return "redirect:/srv/kalkulace/detail/" + k.getKalkulace();
	}

	@RequestMapping("/detail/{kalukaceRRRRMM}")
	public String kalkulaceDetailVybranaKalkulace(@PathVariable int kalukaceRRRRMM, Kalkulace kalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException,
			UnknownHostException {
		log.debug("###\t kalkulaceDetailVybranaKalkulace(" + kalukaceRRRRMM + ")");
		session.setAttribute("pageTitle", "Detail kalkulace");

		session.setAttribute("kalukaceRRRRMM", kalukaceRRRRMM);
		model.addAttribute("kalukaceRRRRMM", kalukaceRRRRMM);

		List<MtKalkulace> mtka = serviceMtKalkulace.getMtKalkulace(kalukaceRRRRMM);
		model.addAttribute("mtk", mtka);

		return "/kalkulaceDetail";
	}

	@RequestMapping(value = "/schvalit/{idKalukace}")
	public String schvalitKalkulaci(@PathVariable long idKalukace, Kalkulace kalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t schvalitKalkulaci(" + idKalukace + ")");

		// TODO: nastavit selectBoxy
		return "redirect:/srv/kalkulace/detail";
	}

}
