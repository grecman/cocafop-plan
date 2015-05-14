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

import vwg.skoda.cocafopl.entity.ArchKalkulace;
import vwg.skoda.cocafopl.entity.ArchKalkulaceMtZavView;
import vwg.skoda.cocafopl.entity.ArchPredstavitel;
import vwg.skoda.cocafopl.entity.Kalkulace;
import vwg.skoda.cocafopl.entity.KalkulaceMtZavod;
import vwg.skoda.cocafopl.entity.Mt;
import vwg.skoda.cocafopl.entity.MtKalkulace;
import vwg.skoda.cocafopl.entity.MtKalkulaceView;
import vwg.skoda.cocafopl.entity.MtProd;
import vwg.skoda.cocafopl.entity.Offline;
import vwg.skoda.cocafopl.entity.PredstavitelKalkulace;
import vwg.skoda.cocafopl.entity.Protokol;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.obj.UniObj;
import vwg.skoda.cocafopl.service.ArchKalkulaceMtZavViewService;
import vwg.skoda.cocafopl.service.ArchKalkulaceService;
import vwg.skoda.cocafopl.service.ArchPredstavitelPrService;
import vwg.skoda.cocafopl.service.ArchPredstavitelService;
import vwg.skoda.cocafopl.service.KalkulaceMtZavodService;
import vwg.skoda.cocafopl.service.KalkulaceService;
import vwg.skoda.cocafopl.service.MtKalkulaceService;
import vwg.skoda.cocafopl.service.MtKalkulaceViewService;
import vwg.skoda.cocafopl.service.MtProdService;
import vwg.skoda.cocafopl.service.MtService;
import vwg.skoda.cocafopl.service.OfflineService;
import vwg.skoda.cocafopl.service.PredstavitelKalkulaceService;
import vwg.skoda.cocafopl.service.PredstavitelMessageService;
import vwg.skoda.cocafopl.service.PredstavitelPrService;
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
	private PredstavitelKalkulaceService servicePredstavitelKalkulace;

	@Autowired
	private MtKalkulaceService serviceMtKalkulace;

	@Autowired
	private MtKalkulaceViewService serviceMtKalkulaceView;

	@Autowired
	private KalkulaceMtZavodService serviceKalkulaceMtZavod;

	@Autowired
	private ArchKalkulaceService serviceArchKalkulace;

	@Autowired
	private ArchPredstavitelService serviceArchPredstavitel;

	@Autowired
	private ArchPredstavitelPrService serviceArchPredstavitelPr;

	@Autowired
	private PredstavitelPrService servicePredstavitelPr;

	@Autowired
	private PredstavitelMessageService servicePredstavitelMessage;

	@Autowired
	private OfflineService serviceOffline;

	@Autowired
	private ArchKalkulaceMtZavViewService serviceArchKalkuaceMtZavView;

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
		model.addAttribute("aktualniRRRRMM", sdf.format(new Date()));

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

	// @RequestMapping(headers = "Content-Type=application/json", value = "/getMt")
	// @ResponseBody
	// public Mt getMt(@RequestParam long id) {
	// log.debug("\t### getMt:" + id);
	// return serviceMt.getMt(id);
	// }

	@RequestMapping("/mtDefiniceNovaMt")
	public String mtDefiniceNovaMt(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t mtDefiniceNovaMt(" + mt.getModelTr() + "," + mt.getZavod() + "," + mt.getKodZeme() + "," + mt.getPlatnostOd() + "-" + mt.getPlatnostDo() + ", " + mt.getPopis() + ")");

		MtProd mtProd = serviceMtProd.getMtProd(mt.getModelTr());
		log.debug("###\t\t Dotazeni produktu k modelove tride: " + mt.getModelTr() + " -> " + mtProd.getProdukt());

		// System.out.println("|"+serviceMt.getMt(mt.getModelTr())+"|"+serviceMt.getMt(mt.getModelTr()).getModelTr().length());
		if (serviceMt.getMt(mt.getModelTr(), mt.getZavod()) == null) {
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
		} else {
			log.debug("###\t\t ... kombinace MT a zavodu (" + mt.getModelTr() + "-" + mt.getZavod() + ") jiz existuje!!");
		}

		mtKalkulaceVytvoreni(req);

		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Nova MT");
		newProtokol.setInfo("Zalozeni nove MT - " + mt.getModelTr().toUpperCase());
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);

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

		List<ArchKalkulaceMtZavView> akMt = serviceArchKalkuaceMtZavView.getArchKalkulaceMtZavView(mt.getModelTr(), mt.getZavod());
		if (akMt.isEmpty()) {
			mtEdit.setPlatnostOd(mt.getPlatnostOd());
		} else {
			log.debug("###\t\t Platnost-OD  nebyla zmenena, protoze existuje pro tuto modelovou třídu schválaná kalkulace");
		}

		List<MtKalkulace> mtk = serviceMtKalkulace.getMtKalkulace(mt.getModelTr(), mt.getZavod());
		if (mtEdit.getPlatnostDo().intValue() == mt.getPlatnostDo().intValue()) {
			log.debug("###\t\t Platnost-DO nebyla zmenena, needituji.");
			// toto je tady kvuli tomu, aby pri editaci jiz neplatnych MT se automaticky neopravila platnostDo dle posledni prac.kalkulace, viz druhy ELSE
		} else if (mt.getPlatnostDo() >= mtk.get(0).getGz39tKalkulace().getKalkulace()) {
			mtEdit.setPlatnostDo(mt.getPlatnostDo());
		} else {
			log.debug("###\t\t Platnost-DO byla mensi nez posledni pracovni kalkulace, proto ji opravuji na hodnotu posledni pracovni kalkulace.");
			mtEdit.setPlatnostDo(mtk.get(0).getGz39tKalkulace().getKalkulace());
		}

		mtEdit.setUtime(new Date());
		mtEdit.setUuser(req.getUserPrincipal().getName().toUpperCase());
		serviceMt.setMt(mtEdit);

		mtKalkulaceVytvoreni(req);
		mtKalkulaceMazani(req);

		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Editace MT");
		newProtokol.setInfo(mtEdit.getModelTr());
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);

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

		if (uniObj.getRok() == null || uniObj.getRok() == "0") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String aktualRok = sdf.format(new Date());
			uniObj.setRok(aktualRok);
		}

		mtKalkulaceVytvoreni(req);

		List<KalkulaceMtZavod> kalkulaceProRokProPlatneModeloveTridy = serviceKalkulaceMtZavod.getKalkulaceMtZavod(Integer.valueOf(uniObj.getRok()));
		model.addAttribute("kalkulaceProRokProPlatneModeloveTridy", kalkulaceProRokProPlatneModeloveTridy);

		List<ArchKalkulace> archKalk = serviceArchKalkulace.getArchKalkulaceAll();
		// List archKalk je serazen dle kalkulace DESC
		for (ArchKalkulace ak : archKalk) {
			if (ak.getSchvaleno() != null) {
				model.addAttribute("posledniArchivniKalkulace", ak);
				break;
			}
		}

		return "/kalkulaceSeznam";
	}

	@RequestMapping("/kalkulaceNova")
	public String kalkulaceNova(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t kalkulaceNova()");
		session.setAttribute("pageTitle", "Seznam kalkulací");

		Integer lastRok = serviceKalkulace.getKalkulacePosledniRok();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String aktualRok = sdf.format(new Date());
		// model.addAttribute("aktualniRRRRMM", sdf.format(new Date()));

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
				// System.out.println(aktualRok+"-"+mesice);
				Kalkulace kal = new Kalkulace();
				kal.setKalkulace(Integer.valueOf(aktualRok + mesice));
				// kal.setRok(Integer.valueOf(aktualRok));
				kal.setKalkulacniDatum(aktualRok + mesice + "0" + Integer.toString(monday.get(Calendar.DAY_OF_MONTH)));
				kal.setUtime(new Date());
				kal.setUuser(req.getUserPrincipal().getName().toUpperCase());
				serviceKalkulace.addKalkulace(kal);

				// Naplneni entity MtKalkulace
				Kalkulace kalSaved = serviceKalkulace.getKalkulace(Integer.valueOf(aktualRok + mesice));
				// System.out.println("kalkul: "+kalSaved+", "+kalSaved.getKalkulace());
				List<Mt> mtPlatne = serviceMt.getMtPlatneK(kalSaved.getKalkulace());
				for (Mt mt : mtPlatne) {
					MtKalkulace mtk = new MtKalkulace();
					mtk.setGz39tKalkulace(kalSaved);
					mtk.setGz39tMt(mt);
					mtk.setMrok(Integer.valueOf(aktualRok));
					mtk.setUtime(new Date());
					mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
					serviceMtKalkulace.addMtKalkulace(mtk);
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
					// kal.setRok(Integer.valueOf(Integer.toString(lastRok + 1)));
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
						mtk.setMrok(Integer.valueOf(aktualRok));
						mtk.setUtime(new Date());
						mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
						serviceMtKalkulace.addMtKalkulace(mtk);
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

		model.addAttribute("kalkulaceInput", serviceKalkulace.getKalkulaceId(idKalkulace));

		return "/kalkulaceEdit";
	}

	@RequestMapping(value = "/editKalkulaceFormSubmit")
	public String editKalkulaceFormSubmit(Kalkulace kalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t editKalkulaceFormSubmit(" + kalkulace.getId() + ", " + kalkulace.getKalkulace() + ", " + kalkulace.getKalkulacniDatum() + ")");

		Kalkulace k = serviceKalkulace.getKalkulaceId(kalkulace.getId());
		k.setKalkulacniDatum(kalkulace.getKalkulacniDatum());
		k.setUtime(new Date());
		k.setUuser(req.getUserPrincipal().getName().toUpperCase());
		serviceKalkulace.setKalkulace(k);

		List<PredstavitelKalkulace> pk = servicePredstavitelKalkulace.getPredstaviteleKalkulaceProKalkulaci(k.getKalkulace());
		log.debug("###\t ...zmenil se kalkulacni datum - mazu PR cisla a PR messages pro postihnute predstavitele.");
		for (PredstavitelKalkulace predstavitelKalk : pk) {
			servicePredstavitelPr.removePredstavitelPrAll(predstavitelKalk.getId());
			servicePredstavitelMessage.removePredstavitelMessageAll(predstavitelKalk.getId());

			predstavitelKalk.setExistsPr(null);
			servicePredstavitelKalkulace.setPredstavitelKalkulace(predstavitelKalk);
		}

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

		if (session.getAttribute("kalkulaceRRRRMM").toString().isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			session.setAttribute("kalkulaceRRRRMM", sdf.format(new Date()));
		}
		return "redirect:/srv/kalkulace/detail/" + session.getAttribute("kalkulaceRRRRMM");
	}

	@RequestMapping("/detail/plusMesic")
	public String kalkulaceDetailPlusMesic(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t kalkulaceDetailPlusMesic()");
		Kalkulace k = serviceKalkulace.getKalkulacePlusMesic((Integer) session.getAttribute("kalkulaceRRRRMM"));
		return "redirect:/srv/kalkulace/detail/" + k.getKalkulace();
	}

	@RequestMapping("/detail/minusMesic")
	public String kalkulaceDetailMinusMesic(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t kalkulaceDetailMinusMesic()");
		Kalkulace k = serviceKalkulace.getKalkulaceMinusMesic((Integer) session.getAttribute("kalkulaceRRRRMM"));
		return "redirect:/srv/kalkulace/detail/" + k.getKalkulace();
	}

	@RequestMapping("/detail/{kalkulaceRRRRMM}")
	public String kalkulaceDetailVybranaKalkulace(@PathVariable int kalkulaceRRRRMM, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t kalkulaceDetailVybranaKalkulace(" + kalkulaceRRRRMM + ")");
		session.setAttribute("pageTitle", "Detail kalkulace");
		session.setAttribute("kalkulaceRRRRMM", kalkulaceRRRRMM);

		Kalkulace kal = serviceKalkulace.getKalkulace(kalkulaceRRRRMM);
		model.addAttribute("kalkulace", kal);

		List<ArchKalkulace> archKalkList = serviceArchKalkulace.getArchKalkulaceAll();
		// List archKalk je serazen dle kalkulace DESC, takze beru prvni vyskyt
		for (ArchKalkulace ak : archKalkList) {
			if (ak.getSchvaleno() != null) {
				model.addAttribute("posledniArchivniKalkulace", ak);
				break;
			}
		}

		List<MtKalkulaceView> mtkView = serviceMtKalkulaceView.getMtKalkulaceView(kal.getKalkulace());
		model.addAttribute("mtk", mtkView);

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().contains("APPROVERS"))
			model.addAttribute("moznoEditovat", true);
		else
			model.addAttribute("moznoEditovat", false);

		return "/kalkulaceDetail";
	}

	@RequestMapping(value = "/detail/schvalitMt/{mt}/{zavod}/{kalkulace}")
	public String schvalitMt(@PathVariable String mt, @PathVariable String zavod, @PathVariable int kalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException,
			UnknownHostException {
		log.debug("###\t schvalitMt(" + mt + "-" + zavod + ", " + kalkulace + ")");

		MtKalkulace mtk = serviceMtKalkulace.getMtKalkulace(kalkulace, mt, zavod);
		mtk.setSchvaleno(new Date());
		mtk.setSchvalil(req.getUserPrincipal().getName().toUpperCase());
		mtk.setUtime(new Date());
		mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
		serviceMtKalkulace.setMtKalkulace(mtk);

		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Schvaleni MT");
		newProtokol.setInfo(mt + "-" + zavod + " pro kalkulace " + kalkulace);
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);

		return "redirect:/srv/kalkulace/detail/" + kalkulace;
	}

	@RequestMapping(value = "/detail/reSchvalitMt/{mt}/{zavod}/{kalkulace}")
	public String reSchvalitMt(@PathVariable String mt, @PathVariable String zavod, @PathVariable int kalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException,
			UnknownHostException {
		log.debug("###\t reSchvalitMt(" + mt + "-" + zavod + ", " + kalkulace + ")");

		MtKalkulace mtk = serviceMtKalkulace.getMtKalkulace(kalkulace, mt, zavod);
		mtk.setSchvaleno(null);
		mtk.setSchvalil(null);
		mtk.setUtime(new Date());
		mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
		serviceMtKalkulace.setMtKalkulace(mtk);

		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Zruseni schvaleni MT");
		newProtokol.setInfo(mt + "-" + zavod + " pro kalkulace " + kalkulace);
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);

		return "redirect:/srv/kalkulace/detail/" + kalkulace;
	}

	/* ============================================================================================================= */

	@RequestMapping(value = "/spustitVypocet/{kalkulaceRRRRMM}")
	public String spustitVypocet(@PathVariable int kalkulaceRRRRMM, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t spustitVypocet(" + kalkulaceRRRRMM + ")");

		List<Offline> offVypocet = serviceOffline.getOfflineAgendaNeukoncena("Představitelé - výpočet");
		if (offVypocet.isEmpty()) {

			Offline off = new Offline();
			off.setAgenda("Představitelé - výpočet");
			off.setCasZadani(new Date());
			off.setStatus("Nový");
			off.setUzivatel(req.getUserPrincipal().getName().toUpperCase());
			// GRE: v popisu nutno ukladat ciste jen rrrrmm, pac to pak slouzi jako parametr davky !!!
			off.setPopis(String.valueOf(kalkulaceRRRRMM));
			serviceOffline.addOffline(off);

			log.debug("###\t ...mazu pripadny priznak SCHAVLENO u vsech modelovych trid v entite MtKalkulace");
			List<MtKalkulace> mtkList = serviceMtKalkulace.getMtKalkulace(kalkulaceRRRRMM);
			for (MtKalkulace mtk : mtkList) {
				mtk.setSchvaleno(null);
				mtk.setSchvalil(null);
				mtk.setUtime(new Date());
				mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
				serviceMtKalkulace.setMtKalkulace(mtk);
			}

			List<ArchKalkulace> akSmazat = serviceArchKalkulace.getArchKalkulaceAll();
			for (ArchKalkulace akDel : akSmazat) {
				if (akDel.getSchvaleno() == null) {
					log.debug("###\t ...mazu " + akDel.getKalkulace() + " z celeho archvivu.");
					serviceArchKalkulace.removeArchKalkulace(akDel);
					// GRE: pracovni (neschvaleny) stav muze byt jen jeden, ale proto pro jistotu mazu ve for cyklu vse, kdyby se tam nejakou chybkou dostala dalsi prac.kalkulace
				}
			}

			log.debug("###\t ...zakladam kalkulaci v ArchKalkulace.");
			Kalkulace k = serviceKalkulace.getKalkulace(kalkulaceRRRRMM);
			ArchKalkulace ak = new ArchKalkulace();
			ak.setKalkulace(k.getKalkulace());
			ak.setKalkulacniDatum(k.getKalkulacniDatum());
			ak.setPosledniVypocet(new Date());
			ak.setTerkach(serviceArchKalkulace.getTerkach());
			ak.setTerka(serviceArchKalkulace.getTerka());
			serviceArchKalkulace.addArchKalkulace(ak);

			String protokolInfo = "";
			int protokolPredCount = 0;

			List<MtKalkulace> mtkListFullPr = serviceMtKalkulace.getMtKalkulaceFullPr(kalkulaceRRRRMM);
			for (MtKalkulace mtk : mtkListFullPr) {
				List<PredstavitelKalkulace> pkList = servicePredstavitelKalkulace.getPredstaviteleKalkulace(mtk.getGz39tMt().getModelTr(), mtk.getGz39tMt().getZavod(), mtk.getGz39tKalkulace()
						.getKalkulace());
				for (PredstavitelKalkulace pk : pkList) {
					log.debug("###\t ...zakladam pro " + pk.getGz39tPredstavitel().getModelovyKlic() + " pred.c.: " + pk.getGz39tPredstavitel().getCisloPred() + "  do ArchPredstavitel");
					ArchPredstavitel apk = new ArchPredstavitel();
					apk.setGz40tKalkulace(serviceArchKalkulace.getArchKalkulaceId(kalkulaceRRRRMM));
					apk.setCisloPred(pk.getGz39tPredstavitel().getCisloPred());
					apk.setCisloPredMin(pk.getGz39tPredstavitel().getCisloPredMin());
					apk.setEuNorma(pk.getGz39tPredstavitel().getEuNorma());
					apk.setKodZeme(pk.getGz39tPredstavitel().getKodZeme());
					apk.setModelovyKlic(pk.getGz39tPredstavitel().getModelovyKlic());
					apk.setModelovyRok(pk.getGz39tMtKalkulace().getMrok());
					apk.setModelTr(pk.getGz39tMtKalkulace().getGz39tMt().getModelTr());
					apk.setObsah(pk.getGz39tPredstavitel().getObsah());
					apk.setPlatnostDo(pk.getGz39tPredstavitel().getPlatnostDo());
					apk.setPlatnostOd(pk.getGz39tPredstavitel().getPlatnostOd());
					apk.setPoznamka(pk.getGz39tPredstavitel().getPoznamka());
					apk.setProdukt(pk.getGz39tMtKalkulace().getGz39tMt().getProdukt());
					apk.setPrpoz(servicePredstavitelPr.getPredstavitelPrNudle(pk.getId()));
					apk.setRozlozenost(pk.getGz39tPredstavitel().getRozlozenost());
					apk.setTyp(pk.getGz39tPredstavitel().getTyp());
					apk.setVybava(pk.getGz39tPredstavitel().getVybava());
					if (pk.getVybavyEdit() != null && !pk.getVybavyEdit().startsWith("+")) {
						// puvodni vybava zamerne potlacena
						apk.setVybavy(null);
					} else if (pk.getVybavyEdit() != null && pk.getVybavyEdit().startsWith("+")) {
						apk.setVybavy(pk.getVybavyEdit());
					} else {
						apk.setVybavy(pk.getGz39tPredstavitel().getVybavy());
					}
					apk.setComix(pk.getGz39tPredstavitel().getComix());
					apk.setVykon(pk.getGz39tPredstavitel().getVykon());
					apk.setZavod(pk.getGz39tMtKalkulace().getGz39tMt().getZavod());
					serviceArchPredstavitel.addArchPredstavitel(apk);

					// GRE - insert PR do GZ40T_PREDSTAVITEL_PR trvalo skoro minutu, proto jsem to presunul do davky.
					// ArchPredstavitel apkgre = serviceArchPredstavitel.getArchPredstavitel(pk.getGz39tMtKalkulace().getGz39tKalkulace().getKalkulace(),
					// pk.getGz39tMtKalkulace().getGz39tMt()
					// .getModelTr(), pk.getGz39tMtKalkulace().getGz39tMt().getZavod(), pk.getGz39tPredstavitel().getCisloPred());
					// List<PredstavitelPr> prList = servicePredstavitelPr.getPredstavitelPr(pk.getId());
					// for (PredstavitelPr pr : prList) {
					// ArchPredstavitelPr prArch = new ArchPredstavitelPr();
					// prArch.setPr(pr.getPr());
					// prArch.setPrEditovane(pr.getPrEditovane());
					// prArch.setRodina(pr.getRodina());
					// prArch.setTyp(pr.getTyp());
					// prArch.setGz40tPredstavitel(apkgre);
					// serviceArchPredstavitelPr.addArchPredstavitelPr(prArch);
					// }

					protokolPredCount++;
				}
				protokolInfo = protokolInfo + mtk.getGz39tMt().getModelTr() + "-" + mtk.getGz39tMt().getZavod() + " (" + protokolPredCount + "),  ";
				protokolPredCount = 0;
				mtk.setPosledniVypocet(new Date());
				mtk.setPosledniVypocetUser(req.getUserPrincipal().getName().toUpperCase());
				serviceMtKalkulace.setMtKalkulace(mtk);
			}

			Protokol newProtokol = new Protokol();
			newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newProtokol.setAction("Spuštěn výpocet");
			newProtokol.setInfo(protokolInfo + " (počet představitelů)");
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);

		} else {
			log.debug("###\t Vypocet nebude spuštěn, protože v době zadání již jeden nedokončený požadavek ve frontě je!");
			Protokol newProtokol = new Protokol();
			newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newProtokol.setAction("Spuštěn výpocet");
			newProtokol.setInfo("Vypocet nebude spuštěn, protože v době zadání již jeden nedokončený požadavek ve frontě je!");
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);
		}
		return "redirect:/srv/kalkulace/detail/" + kalkulaceRRRRMM;
	}

	@RequestMapping(value = "/schvalit/{kalkulaceRRRRMM}")
	public String schvalitKalkulaci(@PathVariable int kalkulaceRRRRMM, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t schvalitKalkulaci(" + kalkulaceRRRRMM + ")");

		ArchKalkulace k = serviceArchKalkulace.getArchKalkulaceId(kalkulaceRRRRMM);
		if (k != null) {
			k.setSchvaleno(new Date());
			k.setSchvalil(req.getUserPrincipal().getName().toUpperCase());
			serviceArchKalkulace.setArchKalkulace(k);

			Kalkulace kalk = serviceKalkulace.getKalkulace(kalkulaceRRRRMM);
			serviceKalkulace.removeKalkulace(kalk);
			session.setAttribute("kalkulaceRRRRMM", "");

			Protokol newProtokol = new Protokol();
			newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newProtokol.setAction("Schvaleni kalkulace");
			newProtokol.setInfo(String.valueOf(kalkulaceRRRRMM));
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);

		} else {
			log.debug("###\t ... pokus o schvaleni kalkulace ve ktere nebylo nic spocitano!!!");
		}
		return "redirect:/srv/archiv/kalkulace";
	}

	/* ============================================================================================================= */

	public void mtKalkulaceVytvoreni(HttpServletRequest req) {
		log.debug("###\t mtKalkulaceVytvoreni()");
		// Spoji entity MT a KALKULACE (jen neschvalene!) pres platnost a vrati IDcka, ktere jeste nejsou v entity MtKalkulace,
		// aby se mohly nasledne vytvorit zatim neexistujici "radky" v entite MtKalkulace
		// Duvodem tohoto kroku je to, ze MT a kalkulace mohou vznikat nezavisle na sobe a je treba zajistit vzdy spravny stav v entite MtKalkulace, kde jsou MT a Kalkulace
		// spojeny.

		List<Object[]> idKalkMt = serviceMtKalkulace.getIdKalkulacaAndIdMtForCreate();
		if (!idKalkMt.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			for (Object[] s : idKalkMt) {
				Kalkulace k = serviceKalkulace.getKalkulaceId((Long) s[0]);
				Mt mt = serviceMt.getMt((Long) s[1]);

				MtKalkulace mtk = new MtKalkulace();
				mtk.setGz39tKalkulace(k);
				mtk.setGz39tMt(mt);
				mtk.setMrok(Integer.valueOf(sdf.format(new Date())));
				mtk.setUtime(new Date());
				mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
				serviceMtKalkulace.addMtKalkulace(mtk);
			}
		}
	}

	public void mtKalkulaceMazani(HttpServletRequest req) {
		log.debug("###\t mtKalkulaceMazani()");

		// Vrati vsechny objekty MtKalkulace, u ktere jiz nemaji existovat, a to z duvodu zkraceni platnostiDo u modelove tridy
		List<MtKalkulace> mtKalkulace = serviceMtKalkulace.getMtKalkulaceForDelete();
		for (MtKalkulace mtk : mtKalkulace) {

			String message = "Bylo provedeno na zaklade zmeny platnostiOd nebo Do pro modelovou třídu " + mtk.getGz39tMt().getModelTr() + " pro kalkulaci " + mtk.getGz39tKalkulace().getKalkulace();
			serviceMtKalkulace.removeMtKalkulace(mtk);

			Protokol newProtokol = new Protokol();
			newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newProtokol.setAction("Smazani MtKalkulace");
			newProtokol.setInfo(message);
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);
		}
	}

}
