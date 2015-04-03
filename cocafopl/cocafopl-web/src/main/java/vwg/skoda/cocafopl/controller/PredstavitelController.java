package vwg.skoda.cocafopl.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;
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

import vwg.skoda.cocafopl.entity.Kalkulace;
import vwg.skoda.cocafopl.entity.Mt;
import vwg.skoda.cocafopl.entity.MtKalkulace;
import vwg.skoda.cocafopl.entity.MtProd;
import vwg.skoda.cocafopl.entity.PrView;
import vwg.skoda.cocafopl.entity.Predstavitel;
import vwg.skoda.cocafopl.entity.PredstavitelKalkulace;
import vwg.skoda.cocafopl.entity.PredstavitelMessage;
import vwg.skoda.cocafopl.entity.PredstavitelPr;
import vwg.skoda.cocafopl.entity.Protokol;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.service.ArchKalkulaceService;
import vwg.skoda.cocafopl.service.KalkulaceService;
import vwg.skoda.cocafopl.service.MessageExceptionService;
import vwg.skoda.cocafopl.service.MtKalkulaceService;
import vwg.skoda.cocafopl.service.MtProdService;
import vwg.skoda.cocafopl.service.MtService;
import vwg.skoda.cocafopl.service.PrViewService;
import vwg.skoda.cocafopl.service.PredstavitelKalkulaceService;
import vwg.skoda.cocafopl.service.PredstavitelKalkulaceViewService;
import vwg.skoda.cocafopl.service.PredstavitelMessageService;
import vwg.skoda.cocafopl.service.PredstavitelPrService;
import vwg.skoda.cocafopl.service.PredstavitelService;
import vwg.skoda.cocafopl.service.ProtokolService;
import vwg.skoda.cocafopl.service.UserService;
import vwg.skoda.favas.mbv.Equipment;
import vwg.skoda.favas.mbv.Message;
import vwg.skoda.favas.mbv.VehicleOrder;
import vwg.skoda.favas.mbv.VehicleOrder.Favas;

@Controller
@RequestMapping("/predstavitel")
public class PredstavitelController {

	static Logger log = Logger.getLogger(PredstavitelController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private ProtokolService serviceProtokol;

	@Autowired
	private PredstavitelService servicePredstavitel;

	@Autowired
	private PredstavitelPrService servicePredstavitelPr;

	@Autowired
	private PredstavitelMessageService servicePredstavitelMessage;

	@Autowired
	private PredstavitelKalkulaceService servicePredstavitelKalkulace;

	@Autowired
	private PredstavitelKalkulaceViewService servicePredstavitelKalkulaceView;

	@Autowired
	private MtService serviceMt;

	@Autowired
	private MtProdService serviceMtProd;

	@Autowired
	private MtKalkulaceService serviceMtKalkulace;

	@Autowired
	private KalkulaceService serviceKalkulace;

	@Autowired
	private MessageExceptionService serviceMessageException;

	@Autowired
	private ArchKalkulaceService serviceArchKalkuace;

	@Autowired
	private PrViewService servicePrView;

	@RequestMapping("/definice")
	public String predstavitelDefinice(Mt mt, Model model, HttpSession session, HttpServletRequest req) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefinice()");
		session.setAttribute("pageTitle", "Definice představitele");

		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");
		session.setAttribute("kalkulaceRRRRMM", "");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<Mt> mtr = serviceMt.getMtPlatneK(Integer.valueOf(sdf.format(new Date())));
		model.addAttribute("listMt", mtr);

		return "/predstavitelDefinice";
	}

	@RequestMapping("/definice/list")
	public String predstavitelDefiniceList(Mt mt, Model model, HttpSession session, HttpServletRequest req) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceList(" + mt.getId() + " / " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");
		session.setAttribute("pageTitle", "Definice představitele");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().contains("USERS"))
			model.addAttribute("moznoEditovat", true);
		else
			model.addAttribute("moznoEditovat", false);

		// jednou sem prijdu kdyz vyberu MT ze selectBoxu (znam jeji ID), jindy uplne odjinut a mam MT a zavod v session
		Mt mtx = null;
		if (mt.getId() > 0) {
			mtx = serviceMt.getMt(mt.getId());
			session.setAttribute("vybranaMt", mtx.getModelTr());
			session.setAttribute("vybranyZavod", mtx.getZavod());
		} else if (session.getAttribute("vybranaMt") != "" && session.getAttribute("vybranyZavod") != "") {
			mtx = serviceMt.getMt(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
		}

		List<Predstavitel> pr = servicePredstavitel.getPredstavitele(mtx.getModelTr(), mtx.getZavod());
		model.addAttribute("listPredstavitelu", pr);

		model.addAttribute("mt", mtx);

		return "/predstavitelDefinice";
	}

	@RequestMapping("/definice/novyPredForm")
	public String predstavitelDefiniceNovyForm(Predstavitel p, Model model, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceNovyForm(" + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod").toString() + ")");
		session.setAttribute("pageTitle", "Založení nového představitele");

		Mt mt = serviceMt.getMt(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
		model.addAttribute("modelovaTrida", mt);

		// ziskani unikatni nabidky seznamu predstavitelu pro vybranou MT a zavod
		// TODO: nutno i vyloucit "cisla" z archivu daneho roku (MT-zavod)
		List<Predstavitel> prd = servicePredstavitel.getPredstavitele(mt.getModelTr(), mt.getZavod());
		ArrayList<Integer> cislaPredstavitelu = new ArrayList<Integer>();
		if (prd.isEmpty()) {
			for (int i = 1; i <= 20; i++) {
				cislaPredstavitelu.add(i);
			}
		} else {
			for (int i = 1; i <= (20 + prd.size()); i++) {
				cislaPredstavitelu.add(i);
			}
			for (Predstavitel predstavitel : prd) {
				cislaPredstavitelu.remove(predstavitel.getCisloPred());
			}
		}
		model.addAttribute("cislaPredstavitelu", cislaPredstavitelu);

		return "/predstavitelDefiniceNovy";
	}

	@RequestMapping("/definice/novyPredSubmit")
	public String predstavitelDefiniceNovySubmit(Predstavitel p, Model model, HttpSession session, HttpServletRequest req) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceNovysubmit(" + p.getCisloPred() + ", " + p.getModelovyKlic() + ", " + p.getKodZeme() + ", " + p.getPlatnostOd() + "-" + p.getPlatnostDo() + ")");

		Mt mt = serviceMt.getMt(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());

		// kontrola zdali nahodou neexistuje predstavitel se stejnym MK a kode zeme!
		if (servicePredstavitel.getPredstavitel(session.getAttribute("vybranaMt").toString() + p.getModelovyKlic().toUpperCase(), p.getKodZeme()) == null) {

			Predstavitel pred = new Predstavitel();
			pred.setCetnost(p.getCetnost());
			pred.setCisloPred(p.getCisloPred());
			pred.setComix(p.getComix());
			pred.setEuNorma(p.getEuNorma().toUpperCase());
			pred.setKodZeme(p.getKodZeme().toUpperCase());
			pred.setModelovyKlic(mt.getModelTr() + p.getModelovyKlic().toUpperCase());
			pred.setObsah(p.getObsah());
			pred.setPlatnostOd(p.getPlatnostOd());
			pred.setPlatnostDo(p.getPlatnostDo());
			pred.setPoznamka(p.getPoznamka());
			pred.setRozlozenost(p.getRozlozenost() == null ? null : p.getRozlozenost().toUpperCase());
			pred.setTyp(p.getTyp());
			pred.setVybava(p.getVybava());
			pred.setVybavy(p.getVybavy() == null ? null : p.getVybavy().toUpperCase());
			pred.setVykon(p.getVykon());
			pred.setUtime(new Date());
			pred.setUuser(req.getUserPrincipal().getName().toUpperCase());
			pred.setGz39tMt(mt);
			servicePredstavitel.addPredstavitel(pred);

			log.debug("###\t Novy predstavitel - ulozeni posledni editace do MT_KALKULACE.");
			List<MtKalkulace> mtKalkulace = serviceMtKalkulace.getMtKalkulace(pred.getGz39tMt().getModelTr());
			for (MtKalkulace mtkx : mtKalkulace) {
				if (mtkx.getSchvaleno() == null) {
					mtkx.setPosledniEditace(new Date());
					mtkx.setPosledniEditaceDuvod("Nový představitel číslo " + p.getCisloPred() + ", " + mt.getModelTr() + p.getModelovyKlic().toUpperCase() + " - " + mt.getZavod());
					serviceMtKalkulace.setMtKalkulace(mtkx);
				}
			}

			//Predstavitel prrr = servicePredstavitel.getPredstavitel((String) session.getAttribute("vybranaMt"), p.getCisloPred());
			//predstavitelKalkulaceSmazani(req, prrr.getId());

		} else {
			log.debug("###\t\t ... predstavitel se stejnym MK a kodeZeme jiz existuje!");
		}

		return "redirect:/srv/predstavitel/definice/list";
	}

	@RequestMapping("/definice/editForm/{idPredstavitele}")
	public String predstavitelDefiniceEditForm(@PathVariable long idPredstavitele, Predstavitel p, Model model, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceEditForm(" + idPredstavitele + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");
		session.setAttribute("pageTitle", "Editace představitele");

		Predstavitel pred = servicePredstavitel.getPredstavitel(idPredstavitele);
		model.addAttribute("predInput", pred);

		Mt mt = serviceMt.getMt(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
		model.addAttribute("modelovaTrida", mt);

		ArrayList<String> mesice = new ArrayList<String>();
		for (Integer i = 1; i <= 12; i++) {
			if (i < 10) {
				mesice.add("0" + i.toString());
			} else {
				mesice.add(i.toString());
			}
		}
		model.addAttribute("mesice", mesice);

		List<Predstavitel> prd = servicePredstavitel.getPredstavitele(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
		ArrayList<Integer> cislaPredstavitelu = new ArrayList<Integer>();

		if (prd.isEmpty()) {
			for (int i = 1; i <= 20; i++) {
				cislaPredstavitelu.add(i);
			}
		} else {
			for (int i = 1; i <= (20 + prd.size()); i++) {
				cislaPredstavitelu.add(i);
			}
			for (Predstavitel predstavitel : prd) {
				cislaPredstavitelu.remove(predstavitel.getCisloPred());
			}
		}
		model.addAttribute("cislaPredstavitelu", cislaPredstavitelu);

		return "/predstavitelDefiniceEdit";
	}

	@RequestMapping("/definice/editSubmit")
	public String predstavitelDefiniceRditSubmit(Predstavitel p, Model model, HttpSession session, HttpServletRequest req) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceEditsubmit(" + p.getCisloPred() + ", " + p.getModelovyKlic() + ", " + p.getKodZeme() + ", " + p.getPlatnostOd() + "-" + p.getPlatnostDo() + ")");

		Predstavitel pred = servicePredstavitel.getPredstavitel(p.getId());
		pred.setCetnost(p.getCetnost());
		pred.setCisloPred(p.getCisloPred());
		pred.setCisloPredMin(p.getCisloPredMin());
		pred.setComix(p.getComix());
		pred.setEuNorma(p.getEuNorma().toUpperCase());
		pred.setKodZeme(p.getKodZeme() == null ? null : p.getKodZeme().toUpperCase());
		pred.setModelovyKlic(pred.getGz39tMt().getModelTr() + p.getModelovyKlic().toUpperCase());
		pred.setObsah(p.getObsah());
		pred.setPlatnostOd(p.getPlatnostOd());
		pred.setPlatnostDo(p.getPlatnostDo());
		pred.setPoznamka(p.getPoznamka());
		pred.setRozlozenost(p.getRozlozenost() == null ? null : p.getRozlozenost().toUpperCase());
		pred.setTyp(p.getTyp());
		pred.setVybava(p.getVybava());
		pred.setVybavy(p.getVybavy() == null ? null : p.getVybavy().toUpperCase());
		pred.setVykon(p.getVykon());
		pred.setUtime(new Date());
		pred.setUuser(req.getUserPrincipal().getName().toUpperCase());
		servicePredstavitel.setPredstavitel(pred);

		log.debug("###\t Editace predstavitele - ulozeni posledni editace do MT_KALKULACE.");
		List<MtKalkulace> mtKalkulace = serviceMtKalkulace.getMtKalkulace(pred.getGz39tMt().getModelTr());
		for (MtKalkulace mtk : mtKalkulace) {
			if (mtk.getSchvaleno() == null) {
				mtk.setPosledniEditace(new Date());
				mtk.setPosledniEditaceDuvod("Editace představitele číslo " + p.getCisloPred() + ", " + mtk.getGz39tMt().getModelTr() + p.getModelovyKlic().toUpperCase() + " - "
						+ mtk.getGz39tMt().getZavod());
				serviceMtKalkulace.setMtKalkulace(mtk);
			}
		}

		predstavitelKalkulaceSmazani(req, pred.getId());

		return "redirect:/srv/predstavitel/definice/list";
	}

	@RequestMapping(value = "/definice/smazatPredstavitele/{idPred}")
	public String smazatPredstavitele(@PathVariable long idPred, Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t smazatPredstavitele(" + idPred + ")");

		Predstavitel predRemove = servicePredstavitel.getPredstavitel(idPred);
		String message = predRemove.getModelovyKlic() + ", " + predRemove.getCisloPred() + ", " + predRemove.getKodZeme() + ", " + predRemove.getPlatnostOd() + "-" + predRemove.getPlatnostDo();
		servicePredstavitel.removePredstavitel(predRemove);

		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Smazani predstavitele");
		newProtokol.setInfo(message);
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);

		return "redirect:/srv/predstavitel/definice/list";
	}

	@RequestMapping(value = "/definice/doplnitMinuleCisloPredstavitele")
	public String doplnitMinuleCisloPredstavitele(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t doplnitMinuleCisloPredstavitele(" + session.getAttribute("vybranyRok") + ", " + session.getAttribute("vybranaMt") + ")");

		List<Predstavitel> predstavitel = servicePredstavitel.getPredstavitele(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
		for (Predstavitel pred : predstavitel) {
			if (pred.getCisloPredMin() == null) {
				// Predstavitel predMin = servicePredstavitel.getPredstavitel(pred.getRok() - 1, pred.getModelovyKlic(), pred.getKodZeme());
				// TODO
				// pr = predstavitel v aktualnim roce s prazdnym cislem predstaviteme minuleho
				// predMin = predstavitel z minuleho roku (sparovano pred MK a kodZeme)
			}
		}
		return "redirect:/srv/predstavitel/definice/list";
	}

	/* ******************************************************************************************************************************************** */

	@RequestMapping("/seznam")
	public String predstavitelSeznam(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznam()");
		session.setAttribute("pageTitle", "Seznam představitelů");

		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");
		session.setAttribute("kalukaceRRRRMM", "");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} else if (aktualUser.getUserRole().equals("ADMINS".trim())) {
			log.debug("###\t Uzivatel s roli ADMINS ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/logging";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Kalkulace kalkulace = serviceKalkulace.getKalkulace(Integer.valueOf(sdf.format(new Date())));

		if (kalkulace == null) {
			log.debug("###\t Neexistuji zadne kalkulace! Je nutne je nejdrive zadat.");
			return "redirect:/srv/kalkulace/seznam";
		}
		
		predstavitelKalkulaceVytvoreni(req);

		session.setAttribute("kalukaceRRRRMM", kalkulace.getKalkulace());
		return "redirect:/srv/predstavitel/seznam/" + kalkulace.getKalkulace();
	}

	@RequestMapping("/seznam/{kalukaceRRRRMM}")
	public String predstavitelSeznamSKalkulaci(@PathVariable int kalukaceRRRRMM, Mt mt, MtKalkulace mtKalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException,
			UnknownHostException {
		log.debug("###\t predstavitelSeznamSKalkulaci(" + session.getAttribute("kalukaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");

		if (session.getAttribute("vybranaMt").toString().isEmpty() && session.getAttribute("vybranyZavod").toString().isEmpty()) {
			List<MtKalkulace> mtList = serviceMtKalkulace.getMtKalkulace((Integer) session.getAttribute("kalukaceRRRRMM"));
			model.addAttribute("mtList", mtList);
		} else {
			MtKalkulace mtk = serviceMtKalkulace.getMtKalkulace((Integer) session.getAttribute("kalukaceRRRRMM"), session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod")
					.toString());
			model.addAttribute("mtk", mtk);
			// List<PredstavitelKalkulaceView> pkView = servicePredstavitelKalkulaceView.getPredstavitelKalkulaceView((Integer) session.getAttribute("kalukaceRRRRMM"),
			// session.getAttribute("vybranaMt")
			// .toString(), session.getAttribute("vybranyZavod").toString());
			// model.addAttribute("pkView", pkView);
			List<PredstavitelKalkulace> pk = servicePredstavitelKalkulace.getPredstaviteleKalkulace(session.getAttribute("vybranaMt").toString(), (Integer) session.getAttribute("kalukaceRRRRMM"));
			model.addAttribute("pk", pk);
		}
		return "/predstavitelSeznam";
	}

	@RequestMapping("/seznam/plusMesic")
	public String predstavitelSeznamPlusMesic(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamPlusMesic(" + session.getAttribute("kalukaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");
		Kalkulace k = serviceKalkulace.getKalkulacePlusMesic((Integer) session.getAttribute("kalukaceRRRRMM"));
		session.setAttribute("kalukaceRRRRMM", k.getKalkulace());

		if (!session.getAttribute("vybranaMt").toString().isEmpty() && !session.getAttribute("vybranyZavod").toString().isEmpty()) {
			MtKalkulace mtKalkulace = serviceMtKalkulace.getMtKalkulace(k.getKalkulace(), session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
			if (mtKalkulace == null) {
				session.setAttribute("vybranaMt", "");
				session.setAttribute("vybranyZavod", "");
			} else {
				session.setAttribute("vybranaMt", mtKalkulace.getGz39tMt().getModelTr());
				session.setAttribute("vybranyZavod", mtKalkulace.getGz39tMt().getZavod());
			}
		}
		return "redirect:/srv/predstavitel/seznam/" + k.getKalkulace();
	}

	@RequestMapping("/seznam/minusMesic")
	public String predstavitelSeznamMinusMesic(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamPlusMesic(" + session.getAttribute("kalukaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");
		Kalkulace k = serviceKalkulace.getKalkulaceMinusMesic((Integer) session.getAttribute("kalukaceRRRRMM"));
		session.setAttribute("kalukaceRRRRMM", k.getKalkulace());

		if (!session.getAttribute("vybranaMt").toString().isEmpty() && !session.getAttribute("vybranyZavod").toString().isEmpty()) {
			MtKalkulace mtKalkulace = serviceMtKalkulace.getMtKalkulace(k.getKalkulace(), session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
			if (mtKalkulace == null) {
				session.setAttribute("vybranaMt", "");
				session.setAttribute("vybranyZavod", "");
			} else {
				session.setAttribute("vybranaMt", mtKalkulace.getGz39tMt().getModelTr());
				session.setAttribute("vybranyZavod", mtKalkulace.getGz39tMt().getZavod());
			}
		}
		return "redirect:/srv/predstavitel/seznam/" + k.getKalkulace();
	}

	@RequestMapping("/seznam/vyberMt")
	public String predstavitelSeznamVyberMt(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamVyberMt(" + session.getAttribute("kalukaceRRRRMM") + ", " + mt.getId() + ")");
		Mt mtx = serviceMt.getMt(mt.getId());
		session.setAttribute("vybranaMt", mtx.getModelTr());
		session.setAttribute("vybranyZavod", mtx.getZavod());
		return "redirect:/srv/predstavitel/seznam/" + session.getAttribute("kalukaceRRRRMM");
	}

	@RequestMapping("/seznam/zmenaModelovehoRoku")
	public String predstavitelSeznamZmenaModelovehoRoku(MtKalkulace mtKalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamZmenaModelovehoRoku(" + mtKalkulace.getMrok() + ", " + session.getAttribute("kalukaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + ")");

		MtKalkulace mtk = serviceMtKalkulace.getMtKalkulace((Integer) session.getAttribute("kalukaceRRRRMM"), session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod")
				.toString());
		mtk.setMrok(mtKalkulace.getMrok());
		mtk.setUtime(new Date());
		mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
		serviceMtKalkulace.setMtKalkulace(mtk);

		return "redirect:/srv/predstavitel/seznam/" + session.getAttribute("kalukaceRRRRMM");
	}

	/* ******************************************************************************************************************************************** */

	@RequestMapping("/detail/{idPredKalk}")
	public String predstavitelDetail(@PathVariable long idPredKalk, PredstavitelKalkulace predstavitelKalkulace, PredstavitelPr predstavitelPr, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDetail(" + idPredKalk + ")");
		session.setAttribute("pageTitle", "Detail představitele");

		PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);
		model.addAttribute("pk", pk);

		List<PredstavitelPr> pp = servicePredstavitelPr.getPredstavitelPr(pk.getId());
		model.addAttribute("pp", pp);

		List<PredstavitelMessage> pm = servicePredstavitelMessage.getPredstavitel(pk.getId());
		model.addAttribute("pm", pm);

		session.setAttribute("vybranaMt", pk.getGz39tMtKalkulace().getGz39tMt().getModelTr());
		session.setAttribute("vybranyZavod", pk.getGz39tMtKalkulace().getGz39tMt().getZavod());
		session.setAttribute("kalukaceRRRRMM", pk.getGz39tMtKalkulace().getGz39tKalkulace().getKalkulace());

		return "/predstavitelDetail";
	}

	@RequestMapping("/detail/zmenaPr/{idPredKalk}")
	public String predstavitelDetailZmenaPr(@PathVariable long idPredKalk, PredstavitelPr predstavitelPr, Model model, HttpServletRequest req, HttpSession session) throws SQLException,
			UnknownHostException {
		log.debug("###\t predstavitelDetailZmenaPr(" + idPredKalk + ", " + predstavitelPr.getPr() + ")");

		PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);

		PrView zadanePR = servicePrView.getPrView(pk.getGz39tMtKalkulace().getGz39tMt().getProdukt(), predstavitelPr.getPr().toUpperCase());
		if (zadanePR != null) {
			PredstavitelPr pp = servicePredstavitelPr.getPredstavitelPrDleRodiny(pk.getId(), zadanePR.getFamkz());
			if (zadanePR.getPrnr().startsWith(pp.getPr())) {
				// zruseni korekce
				pp.setPrEditovane(null);
			} else {
				pp.setPrEditovane(zadanePR.getPrnr());
			}
			pp.setUtime(new Date());
			pp.setUuser(req.getUserPrincipal().getName().toUpperCase());
			servicePredstavitelPr.setPredstavitelPr(pp);
		}
		return "redirect:/srv/predstavitel/detail/" + idPredKalk;
	}

	@RequestMapping("/detail/zmenaVybavy/{idPredKalk}")
	public String predstavitelDetailZmenaVybavy(@PathVariable long idPredKalk, PredstavitelKalkulace predstavitelKalkulace, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDetailZmenaVybavy(" + idPredKalk + ", " + predstavitelKalkulace.getVybavyEdit() + ")");

		PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);
		pk.setVybavyEdit(predstavitelKalkulace.getVybavyEdit().toUpperCase());
		pk.setUtime(new Date());
		pk.setUuser(req.getUserPrincipal().getName().toUpperCase());
		servicePredstavitelKalkulace.setPredstavitelKalkulace(pk);

		return "redirect:/srv/predstavitel/detail/" + idPredKalk;
	}

	@RequestMapping("/detail/zrusitVybavu/{idPredKalk}")
	public String predstavitelDetailZrusitVybavu(@PathVariable long idPredKalk, PredstavitelKalkulace predstavitelKalkulace, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDetailZrusitVybavu(" + idPredKalk + ", " + predstavitelKalkulace.getVybavyEdit() + ")");

		PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);
		pk.setVybavyEdit(null);
		pk.setUtime(new Date());
		pk.setUuser(req.getUserPrincipal().getName().toUpperCase());
		servicePredstavitelKalkulace.setPredstavitelKalkulace(pk);

		return "redirect:/srv/predstavitel/detail/" + idPredKalk;
	}

	@RequestMapping("/detail/potlacitVybavu/{idPredKalk}")
	public String predstavitelDetailPotlacitVybavu(@PathVariable long idPredKalk, PredstavitelKalkulace predstavitelKalkulace, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDetailPotlacitVybavu(" + idPredKalk + ", " + predstavitelKalkulace.getVybavyEdit() + ")");

		PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);
		pk.setVybavyEdit("Potlačit původní výbavu");
		pk.setUtime(new Date());
		pk.setUuser(req.getUserPrincipal().getName().toUpperCase());
		servicePredstavitelKalkulace.setPredstavitelKalkulace(pk);

		return "redirect:/srv/predstavitel/detail/" + idPredKalk;
	}

	@RequestMapping("/detailPr/{idPredKalk}/{prCislo}")
	public String predstavitelDetailPr(@PathVariable long idPredKalk, @PathVariable String prCislo, PredstavitelPr predstavitelPr, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDetailPr(" + idPredKalk + ", " + prCislo + ")");

		PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);
		MtProd mtProd = serviceMtProd.getMtProd(pk.getGz39tMtKalkulace().getGz39tMt().getModelTr());
		model.addAttribute("mtProd", mtProd);

		PrView prOne = servicePrView.getPrView(pk.getGz39tMtKalkulace().getGz39tMt().getProdukt(), prCislo);
		model.addAttribute("prOne", prOne);

		List<PrView> prList = servicePrView.getPrViews(pk.getGz39tMtKalkulace().getGz39tMt().getProdukt(), prOne.getFamkz());
		model.addAttribute("prList", prList);

		return "/prDetail";
	}

	@RequestMapping("/detail/komunikaceFavas/{idMtKalk}/{idPredKalk}")
	public String detailKomunikaceFavas(@PathVariable long idMtKalk, @PathVariable long idPredKalk, Model model, HttpServletRequest req, HttpSession session) throws ParseException,
			InterruptedException {
		// log.debug("###\t detailKomunikaceFavas(" + idMtKalk + ", " + idPredKalk + ")");

		List<PredstavitelKalkulace> pk_ke_zpracovani = new ArrayList<PredstavitelKalkulace>();
		MtKalkulace mtk = new MtKalkulace();

		if (idMtKalk == 0 && idPredKalk > 0) {
			log.debug("###\t detailKomunikaceFavas(" + idMtKalk + ", " + idPredKalk + ") ... komunikace jednoho predstavitele z obrazovky DETAIL PREDSTAVITELE");
			pk_ke_zpracovani.add(servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk));
		} else {
			log.debug("###\t detailKomunikaceFavas(" + idMtKalk + ", " + idPredKalk + ") ... hromadna komunikace predstavitelu z obrazovky SEZNAM PREDSTAVITELU");
			mtk = serviceMtKalkulace.getMtKalkulaceId(idMtKalk);
			List<PredstavitelKalkulace> pkxxx = servicePredstavitelKalkulace.getPredstaviteleKalkulace(mtk.getGz39tMt().getModelTr(), mtk.getGz39tKalkulace().getKalkulace());
			for (PredstavitelKalkulace predstavitelKalkulace : pkxxx) {
				pk_ke_zpracovani.add(predstavitelKalkulace);
			}
		}
		int pocet_komunikovanych_predstavitelu = pk_ke_zpracovani.size();
		int i = 1;
		for (PredstavitelKalkulace pk : pk_ke_zpracovani) {

			log.debug("###\t\t " + i++ + "/" + pocet_komunikovanych_predstavitelu + " komunikace pro: " + pk.getGz39tPredstavitel().getModelovyKlic() + ", " + pk.getGz39tMtKalkulace().getMrok()
					+ ", " + pk.getGz39tPredstavitel().getKodZeme() + ", " + pk.getGz39tMtKalkulace().getGz39tKalkulace().getKalkulacniDatum() + ", "
					+ (pk.getVybavyEdit() != null ? pk.getVybavyEdit() : pk.getGz39tPredstavitel().getVybavy()));

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
				o.setEquipment(pk.getVybavyEdit().replace("+", "").trim());
			} else if (pk.getGz39tPredstavitel().getVybavy() != null) {
				o.setEquipment(pk.getGz39tPredstavitel().getVybavy().replace("+", "").trim());
			}

			// FAVAS (konkretne metoda o.getFavas()) obcas vyhodi chybu: Server returned HTTP response code: 501 for URL: https://smbdpp1c.fw.skoda.vwg:30700 ... ale jen ji vypise
			// a metoda v tomto pripade vraci NULL.
			// V 99% pripadech se po okamzitem opetovnem zavolani teto metody chyba jiz nevyskytne. Proto kdyz mi metoda vrati null, tak to zkusim jeste 2x a az potom vyhodim
			// chybu. V tom pripade ale uz padne aplikace!
			Favas favas = null;
			if (o.getFavas() == null) {
				log.error("### BACHA ... 1 neuspesny pokus komunikace s FAVASem, zkusim znova za 5 sekund).");
				Thread.sleep(5000);
			} else if (o.getFavas() == null) {
				log.error("### BACHA ... 2 neuspesny pokus komunikace s FAVASem, zkusim znova za NAPOSLEDY za 5 sekund).");
				Thread.sleep(5000);
			} else {
				favas = o.getFavas();
				List<PredstavitelPr> ppList = servicePredstavitelPr.getPredstavitelPr(pk.getId());
				if (ppList.size() > 0) {
					for (PredstavitelPr predstavitelPr : ppList) {
						servicePredstavitelPr.removePredstavitelPr(predstavitelPr);
					}
				}

				List<PredstavitelMessage> ppMesList = servicePredstavitelMessage.getPredstavitel(pk.getId());
				if (ppMesList.size() > 0) {
					for (PredstavitelMessage predstavitelMessage : ppMesList) {
						servicePredstavitelMessage.removePredstavitelMessage(predstavitelMessage);
					}
				}

				Set<Equipment> equipment = favas.getEquipment();
				if (equipment.size() > 0) {
					//log.debug("###\t\t equipment: " + equipment.size());
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
						//log.debug("###\t\t message\t" + me.getCode() + "\t" + me.getText());
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
						log.error("###\t\t errorFavas\t" + er.getErrNo() + ", " + er.getSource() + ", " + er.getText() + ", " + er.getWeight() + ", " + er.getType().toString());
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

	/* ******************************************************************************************************************************************** */

	public void predstavitelKalkulaceVytvoreni(HttpServletRequest req) {
		log.debug("###\t predstavitelKalkulaceVytvoreni()");
		// metoda vrati seznam ID MtKalkulace a ID Predstavitele, pro ktere je nutne vyrvorit novy "radek" v entite PredstavitelKalkulace

		List<Object[]> gre = servicePredstavitelKalkulace.getPredstavitelKalkulaceKtereZatimNeexistuji();
		for (Object[] result : gre) {
			MtKalkulace mtk = serviceMtKalkulace.getMtKalkulaceId((Long) result[0]);
			Predstavitel prt = servicePredstavitel.getPredstavitel((Long) result[1]);
			log.debug("###\t Zakladam v entite PredstavitelKalkulace zaznam pro: " + mtk.getGz39tKalkulace().getKalkulace() + ", " + prt.getCisloPred() + " - " + prt.getModelovyKlic());
			PredstavitelKalkulace pk = new PredstavitelKalkulace();
			pk.setGz39tMtKalkulace(mtk);
			pk.setGz39tPredstavitel(prt);
			pk.setExistsPr(null);
			pk.setUtime(new Date());
			pk.setUuser(req.getUserPrincipal().getName().toUpperCase());
			servicePredstavitelKalkulace.addPredstavitelKalkulace(pk);
		}
	}

	public void predstavitelKalkulaceSmazani(HttpServletRequest req, long idPredstavitele) {
		log.debug("###\t predstavitelKalkulaceSmazani(" + idPredstavitele + ")");

		List<PredstavitelKalkulace> pk = servicePredstavitelKalkulace.getPredstaviteleKalkulaceKtereNemajiExistovat(idPredstavitele);
		for (PredstavitelKalkulace ggg : pk) {
			servicePredstavitelKalkulace.removePredstavitelKalkulace(ggg);

			Protokol newProtokol = new Protokol();
			newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newProtokol.setAction("Smazani PredstavitelKalkulace");
			newProtokol.setInfo("a to na zaklade zmeny platnosti predstavitele - " + " č.pred: " + ggg.getGz39tPredstavitel().getCisloPred() + " " + ggg.getGz39tPredstavitel().getModelovyKlic()
					+ " pro kalkulaci " + ggg.getGz39tMtKalkulace().getGz39tKalkulace().getKalkulace());
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);
		}

	}

}
