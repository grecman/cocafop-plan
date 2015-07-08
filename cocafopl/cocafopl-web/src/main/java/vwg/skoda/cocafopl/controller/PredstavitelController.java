package vwg.skoda.cocafopl.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafopl.entity.ArchPredstavitel;
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
import vwg.skoda.cocafopl.obj.UniObj;
import vwg.skoda.cocafopl.service.ArchKalkulaceService;
import vwg.skoda.cocafopl.service.ArchPredstavitelService;
import vwg.skoda.cocafopl.service.ExportXlsService;
import vwg.skoda.cocafopl.service.KalkulaceService;
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
	private ArchKalkulaceService serviceArchKalkuace;

	@Autowired
	private ArchPredstavitelService serviceArchPredstavitel;

	@Autowired
	private PrViewService servicePrView;

	@Autowired
	private ExportXlsService serviceExportXls;

	@RequestMapping("/definice")
	public String predstavitelDefinice(Mt mt, Model model, HttpSession session, HttpServletRequest req) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefinice()");
		session.setAttribute("pageTitle", "Definice představitele");

		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");
		session.setAttribute("kalkulaceRRRRMM", "");
		session.setAttribute("errorMesage", "");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		List<Mt> mtr = serviceMt.getMtPlatneK(Integer.valueOf(sdf.format(new Date())));
		model.addAttribute("listMt", mtr);

		return "/predstavitelDefinice";
	}

	@RequestMapping("/definice/list")
	public String predstavitelDefiniceList(Mt mt, Model model, HttpSession session, HttpServletRequest req) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceList(" + mt.getId() + " / " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");
		session.setAttribute("pageTitle", "Definice představitele");

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

		// GRE: ziskani unikatniho seznamu predstavitelu
		HashSet<Integer> existujiciCislaPred = new HashSet<Integer>();
		ArrayList<Integer> cislaPredstavitelu = new ArrayList<Integer>();

		// cisla predstavitelu z archivu aktualniho roku
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String AktualniRok = sdf.format(new Date());
		List<Integer> prdArch = serviceArchPredstavitel.getArchPredstavitelCislaPredVRoce(AktualniRok, mt.getModelTr());
		existujiciCislaPred.addAll(prdArch);

		// cisla predstavitelu z archivu aktualniho roku
		List<Predstavitel> prd = servicePredstavitel.getPredstavitele(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
		for (Predstavitel pr : prd) {
			existujiciCislaPred.add(pr.getCisloPred());
		}

		if (existujiciCislaPred.isEmpty()) {
			for (int i = 1; i <= 15; i++) {
				cislaPredstavitelu.add(i);
			}
		} else {
			for (int i = 1; i <= (15 + existujiciCislaPred.size()); i++) {
				cislaPredstavitelu.add(i);
			}
			for (Integer cpnv : existujiciCislaPred) {
				cislaPredstavitelu.remove(cpnv);
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
		// if (servicePredstavitel.getPredstavitel(mt.getModelTr() + p.getModelovyKlic().toUpperCase(), p.getKodZeme()) == null) {

		Predstavitel pred = new Predstavitel();
		pred.setCetnost(p.getCetnost());
		pred.setCisloPred(p.getCisloPred());

		// zajisteni unikatnosti MK a COMIX=TRUE
		Predstavitel predstavitelDoComixu = servicePredstavitel.getPredstavitelComix(mt.getModelTr(), mt.getZavod(), mt.getModelTr() + p.getModelovyKlic().toUpperCase());
		if (predstavitelDoComixu == null) {
			pred.setComix(p.getComix());
		} else {
			log.debug("###\t\t ... predstavitel se stejnym MK a 'zaskrtnutym' COMIXem jiz existuje! Comix bude uložen jako FALSE.");
			pred.setComix(false);
		}
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

		log.trace("###\t Novy predstavitel - ulozeni posledni editace do MT_KALKULACE.");
		List<MtKalkulace> mtKalkulacePoslEdit = serviceMtKalkulace.getMtKalkulace(pred.getGz39tMt().getModelTr(), pred.getGz39tMt().getZavod());
		for (MtKalkulace mtkx : mtKalkulacePoslEdit) {
			mtkx.setPosledniEditace(new Date());
			mtkx.setPosledniEditaceDuvod("Nový představitel číslo " + p.getCisloPred() + ", " + mt.getModelTr() + p.getModelovyKlic().toUpperCase() + " - " + mt.getZavod());
			serviceMtKalkulace.setMtKalkulace(mtkx);
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

		// GRE: ziskani unikatniho seznamu predstavitelu
		HashSet<Integer> existujiciCislaPred = new HashSet<Integer>();
		ArrayList<Integer> cislaPredstavitelu = new ArrayList<Integer>();

		// cisla predstavitelu z archivu aktualniho roku
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String AktualniRok = sdf.format(new Date());
		List<Integer> prdArch = serviceArchPredstavitel.getArchPredstavitelCislaPredVRoce(AktualniRok, mt.getModelTr());
		existujiciCislaPred.addAll(prdArch);

		// cisla predstavitelu z archivu aktualniho roku
		List<Predstavitel> prd = servicePredstavitel.getPredstavitele(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
		for (Predstavitel pr : prd) {
			existujiciCislaPred.add(pr.getCisloPred());
		}

		if (existujiciCislaPred.isEmpty()) {
			for (int i = 1; i <= 15; i++) {
				cislaPredstavitelu.add(i);
			}
		} else {
			for (int i = 1; i <= (15 + existujiciCislaPred.size()); i++) {
				cislaPredstavitelu.add(i);
			}
			for (Integer cpnv : existujiciCislaPred) {
				cislaPredstavitelu.remove(cpnv);
			}
		}
		model.addAttribute("cislaPredstavitelu", cislaPredstavitelu);

		return "/predstavitelDefiniceEdit";
	}

	@RequestMapping("/definice/editSubmit")
	public String predstavitelDefiniceRditSubmit(Predstavitel p, Model model, HttpSession session, HttpServletRequest req) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceEditsubmit(" + p.getId() + ", " + p.getCisloPred() + ", " + p.getModelovyKlic() + ", " + p.getKodZeme() + ", " + p.getPlatnostOd() + "-"
				+ p.getPlatnostDo() + ")");

		Predstavitel pred = servicePredstavitel.getPredstavitel(p.getId());

		if (!pred.getKodZeme().startsWith(p.getKodZeme().toUpperCase()) || !pred.getModelovyKlic().startsWith(pred.getGz39tMt().getModelTr() + p.getModelovyKlic().toUpperCase())) {
			log.debug("###\t ...zmenil se modelovy klic nebo kod zeme - mazu PR cisla a PR messages a to pro vsechny aktivni kalkulace.");
			List<PredstavitelKalkulace> pk = servicePredstavitelKalkulace.getPredstaviteleKalkulace(p.getId());
			for (PredstavitelKalkulace predstavitelKalk : pk) {
				servicePredstavitelPr.removePredstavitelPrAll(predstavitelKalk.getId());
				servicePredstavitelMessage.removePredstavitelMessageAll(predstavitelKalk.getId());
				predstavitelKalk.setExistsPr(null);
				servicePredstavitelKalkulace.setPredstavitelKalkulace(predstavitelKalk);
			}
		}
		pred.setCetnost(p.getCetnost());
		pred.setCisloPred(p.getCisloPred());
		pred.setCisloPredMin(p.getCisloPredMin());

		// zajisteni unikatnosti MK a COMIX=TRUE
		Predstavitel predstavitelDoComixu = servicePredstavitel.getPredstavitelComix(pred.getCisloPred(), pred.getGz39tMt().getModelTr(), pred.getGz39tMt().getZavod(), pred.getGz39tMt().getModelTr()
				+ p.getModelovyKlic().toUpperCase());
		if (predstavitelDoComixu == null) {
			pred.setComix(p.getComix());
		} else {
			log.debug("###\t\t ... predstavitel se stejnym MK a 'zaskrtnutym' COMIXem jiz existuje! Comix bude uložen jako FALSE.");
			pred.setComix(false);
		}

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

		log.debug("###\t  editace predstavitele - ulozeni posledni editace do MT_KALKULACE.");
		List<MtKalkulace> mtKalkulacePoslEdit = serviceMtKalkulace.getMtKalkulace(pred.getGz39tMt().getModelTr(), pred.getGz39tMt().getZavod());
		for (MtKalkulace mtkx : mtKalkulacePoslEdit) {
			mtkx.setPosledniEditace(new Date());
			mtkx.setPosledniEditaceDuvod("Editace představitele číslo " + p.getCisloPred() + ", " + mtkx.getGz39tMt().getModelTr() + p.getModelovyKlic().toUpperCase() + " - "
					+ mtkx.getGz39tMt().getZavod());
			serviceMtKalkulace.setMtKalkulace(mtkx);
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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Integer AktualniRok = Integer.valueOf(sdf.format(new Date()));
		String predchoziRRRRMM = AktualniRok - 1 + "" + 12;

		List<Predstavitel> predstavitel = servicePredstavitel.getPredstavitele(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
		for (Predstavitel pred : predstavitel) {
			List<ArchPredstavitel> listAP = serviceArchPredstavitel.getArchPredstavitelProMinuleCislo(Integer.valueOf(predchoziRRRRMM), pred.getGz39tMt().getModelTr(), pred.getGz39tMt().getZavod(),
					pred.getModelovyKlic(), pred.getKodZeme());
			if (listAP.size() == 0) {
				pred.setCisloPredMin(0);
			} else if (listAP.size() == 1) {
				pred.setCisloPredMin(listAP.get(0).getCisloPred());
			} else {
				// pro jednoho predstavitele existuje vice vyskytu predstavitelu v archivu (kombinace modelovy klic a kod zeme)
				pred.setCisloPredMin(-1);
			}
			servicePredstavitel.setPredstavitel(pred);
		}
		return "redirect:/srv/predstavitel/definice/list";
	}
	
	
	@RequestMapping("/definice/exportXls")
	public String predstavitelSeznamExportXls(HttpServletRequest req, HttpSession session, HttpServletResponse res) throws SQLException, IOException {
		log.debug("###\t predstavitelSeznamExportXls(" + session.getAttribute("vybranaMt").toString() + "-" + session.getAttribute("vybranyZavod").toString() + ")");

		List<Predstavitel> p = servicePredstavitel.getPredstavitele(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());

		serviceExportXls.exportPredstavitelSeznam(p, res);
		res.flushBuffer();

		return "redirect:/srv/predstavitel/seznam/" + session.getAttribute("kalkulaceRRRRMM");
	}

	/* ******************************************************************************************************************************************** */

	@RequestMapping("/seznam")
	public String predstavitelSeznam(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznam()");
		session.setAttribute("pageTitle", "Seznam představitelů");

		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");
		session.setAttribute("errorMesage", "");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} else if (aktualUser.getUserRole().equals("ADMINS".trim())) {
			log.debug("###\t Uzivatel s roli ADMINS ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/logging";
		}

		predstavitelKalkulaceVytvoreni(req);
		if (session.getAttribute("kalkulaceRRRRMM") == null || session.getAttribute("kalkulaceRRRRMM").toString().isEmpty()) {
			List<Kalkulace> prvniPracovniKalkulace = serviceKalkulace.getKalkulaceAll();
			Kalkulace kalkulace = serviceKalkulace.getKalkulace(prvniPracovniKalkulace.get(prvniPracovniKalkulace.size() - 1).getKalkulace());
			if (kalkulace == null) {
				log.debug("###\t Neexistuji zadne kalkulace! Je nutne je nejdrive zadat.");
				return "redirect:/srv/kalkulace/seznam";
			}
			session.setAttribute("kalkulaceRRRRMM", kalkulace.getKalkulace());
			return "redirect:/srv/predstavitel/seznam/" + kalkulace.getKalkulace();
		} else {
			return "redirect:/srv/predstavitel/seznam/" + session.getAttribute("kalkulaceRRRRMM");
		}

	}

	@RequestMapping("/seznam/{kalkulaceRRRRMM}")
	public String predstavitelSeznamSKalkulaci(@PathVariable int kalkulaceRRRRMM, UniObj uniObj, Mt mt, MtKalkulace mtKalkulace, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamSKalkulaci(" + session.getAttribute("kalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");

		session.setAttribute("errorMesage", "");

		if (session.getAttribute("vybranaMt").toString().isEmpty() && session.getAttribute("vybranyZavod").toString().isEmpty()) {
			List<MtKalkulace> mtList = serviceMtKalkulace.getMtKalkulace((Integer) session.getAttribute("kalkulaceRRRRMM"));
			model.addAttribute("mtList", mtList);
		} else {
			MtKalkulace mtk = serviceMtKalkulace.getMtKalkulace((Integer) session.getAttribute("kalkulaceRRRRMM"), session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod")
					.toString());
			model.addAttribute("mtk", mtk);
			List<PredstavitelKalkulace> pk = servicePredstavitelKalkulace.getPredstaviteleKalkulace(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString(),
					(Integer) session.getAttribute("kalkulaceRRRRMM"));
			model.addAttribute("pk", pk);
		}
		return "/predstavitelSeznam";
	}

	@RequestMapping("/seznam/{kalkulaceRRRRMM}/{modelTr}/{zavod}")
	public String predstavitelSeznamSKalkulaciPom(@PathVariable int kalkulaceRRRRMM, @PathVariable String modelTr, @PathVariable String zavod, Mt mt, MtKalkulace mtKalkulace, Model model,
			HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamSKalkulaciPom(" + kalkulaceRRRRMM + ", " + modelTr + "-" + zavod + ")");
		// GRE: tuto metodu jsem dodelaval az zpetne, abych mohl skocit primo z tabulky "detail kalkulace" (z barevneho puntiku) na "seznam predstavitelu"
		session.setAttribute("kalkulaceRRRRMM", kalkulaceRRRRMM);
		session.setAttribute("vybranaMt", modelTr);
		session.setAttribute("vybranyZavod", zavod);

		return "redirect:/srv/predstavitel/seznam/" + kalkulaceRRRRMM;
	}

	@RequestMapping("/seznam/plusMesic")
	public String predstavitelSeznamPlusMesic(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamPlusMesic(" + session.getAttribute("kalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");
		Kalkulace k = serviceKalkulace.getKalkulacePlusMesic((Integer) session.getAttribute("kalkulaceRRRRMM"));
		session.setAttribute("kalkulaceRRRRMM", k.getKalkulace());

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
		log.debug("###\t predstavitelSeznamPlusMesic(" + session.getAttribute("kalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");
		Kalkulace k = serviceKalkulace.getKalkulaceMinusMesic((Integer) session.getAttribute("kalkulaceRRRRMM"));
		session.setAttribute("kalkulaceRRRRMM", k.getKalkulace());

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
		log.debug("###\t predstavitelSeznamVyberMt(" + session.getAttribute("kalkulaceRRRRMM") + ", " + mt.getId() + ")");
		Mt mtx = serviceMt.getMt(mt.getId());
		session.setAttribute("vybranaMt", mtx.getModelTr());
		session.setAttribute("vybranyZavod", mtx.getZavod());
		return "redirect:/srv/predstavitel/seznam/" + session.getAttribute("kalkulaceRRRRMM");
	}

	@RequestMapping("/seznam/zmenaModelovehoRoku")
	public String predstavitelSeznamZmenaModelovehoRoku(UniObj uniObj, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamZmenaModelovehoRoku(" + uniObj.getRok() + ", " + uniObj.getZmenaMrok() + ", " + session.getAttribute("kalkulaceRRRRMM") + ", "
				+ session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");

		String infoProtokol = null;
		if (uniObj.getZmenaMrok()) {
			// zmena MROKu pro vsechny kalkulace
			List<MtKalkulace> mtKalk = serviceMtKalkulace.getMtKalkulace(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
			for (MtKalkulace mtk : mtKalk) {
				mtk.setMrok(Integer.valueOf(uniObj.getRok()));
				mtk.setUtime(new Date());
				mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
				mtk.setPosledniEditace(new Date());
				mtk.setPosledniEditaceDuvod("Změna modelového roku u " + mtk.getGz39tMt().getModelTr() + " - " + mtk.getGz39tMt().getZavod() + " na " + uniObj.getRok());
				serviceMtKalkulace.setMtKalkulace(mtk);
			}

			List<PredstavitelKalkulace> pk = servicePredstavitelKalkulace.getPredstaviteleKalkulace(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
			log.debug("###\t ...zmenil se modelovy rok - mazu PR cisla a PR messages pro postihnute predstavitele.");
			for (PredstavitelKalkulace predstavitelKalk : pk) {
				servicePredstavitelPr.removePredstavitelPrAll(predstavitelKalk.getId());
				servicePredstavitelMessage.removePredstavitelMessageAll(predstavitelKalk.getId());
				predstavitelKalk.setExistsPr(null);
				servicePredstavitelKalkulace.setPredstavitelKalkulace(predstavitelKalk);
			}
			infoProtokol = "pro " + session.getAttribute("vybranaMt").toString() + "-" + session.getAttribute("vybranyZavod").toString() + " na " + uniObj.getRok() + " (pro všechny kalkulace)!";
		} else {
			// zmena MROKu pro jednu kalkulaci
			MtKalkulace mtk = serviceMtKalkulace.getMtKalkulace((Integer) session.getAttribute("kalkulaceRRRRMM"), session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod")
					.toString());
			mtk.setMrok(Integer.valueOf(uniObj.getRok()));
			mtk.setUtime(new Date());
			mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
			mtk.setPosledniEditace(new Date());
			mtk.setPosledniEditaceDuvod("Změna modelového roku u " + mtk.getGz39tMt().getModelTr() + " - " + mtk.getGz39tMt().getZavod() + " na " + uniObj.getRok());
			serviceMtKalkulace.setMtKalkulace(mtk);

			List<PredstavitelKalkulace> pk = servicePredstavitelKalkulace.getPredstaviteleKalkulace(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString(),
					(Integer) session.getAttribute("kalkulaceRRRRMM"));
			log.debug("###\t ...zmenil se modelovy rok - mazu PR cisla a PR messages pro postihnute predstavitele.");
			for (PredstavitelKalkulace predstavitelKalk : pk) {
				servicePredstavitelPr.removePredstavitelPrAll(predstavitelKalk.getId());
				servicePredstavitelMessage.removePredstavitelMessageAll(predstavitelKalk.getId());
				predstavitelKalk.setExistsPr(null);
				servicePredstavitelKalkulace.setPredstavitelKalkulace(predstavitelKalk);
			}
			infoProtokol = "pro kalkulaci " + session.getAttribute("kalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt").toString() + "-" + session.getAttribute("vybranyZavod").toString()
					+ " na " + uniObj.getRok() + "!";
		}

		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Změna modelového roku");
		newProtokol.setInfo(infoProtokol);
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);

		return "redirect:/srv/predstavitel/seznam/" + session.getAttribute("kalkulaceRRRRMM");
	}

	@RequestMapping("/seznamSKalkulaci/exportXls")
	public String predstavitelSeznamSKalkulaciExportXls(HttpServletRequest req, HttpSession session, HttpServletResponse res) throws SQLException, IOException {
		log.debug("###\t predstavitelSeznamSKalkulaciExportXls(" + session.getAttribute("kalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt").toString() + "-"
				+ session.getAttribute("vybranyZavod").toString() + ")");
		List<PredstavitelKalkulace> pk = servicePredstavitelKalkulace.getPredstaviteleKalkulace(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString(),
				Integer.valueOf(session.getAttribute("kalkulaceRRRRMM").toString().toString()));

		serviceExportXls.exportPredstavitelKalkulaceSeznam(pk, res);
		res.flushBuffer();

		return "redirect:/srv/predstavitel/seznam/" + session.getAttribute("kalkulaceRRRRMM");
	}

	/* ******************************************************************************************************************************************** */

	@RequestMapping("/detail/{idPredKalk}")
	public String predstavitelDetail(@PathVariable long idPredKalk, PredstavitelKalkulace predstavitelKalkulace, PredstavitelPr predstavitelPr, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDetail(" + idPredKalk + ")");
		session.setAttribute("pageTitle", "Detail představitele");

		PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);
		model.addAttribute("pk", pk);

		List<PredstavitelPr> prPodminky = servicePredstavitelPr.getPredstavitelPr(pk.getId());
		model.addAttribute("prPodminky", prPodminky);

		List<PredstavitelMessage> prMessage = servicePredstavitelMessage.getPredstavitelMessageWithoutException(pk.getId());
		model.addAttribute("prMessage", prMessage);

		List<PredstavitelMessage> prMessageException = servicePredstavitelMessage.getPredstavitelMessageOnlyException(pk.getId());
		String prMesEx = null;
		for (PredstavitelMessage pmes : prMessageException) {
			prMesEx = (prMesEx == null ? "" : prMesEx + "\t\n") + pmes.getKod() + "-" + pmes.getText() + "\t\n";
		}
		model.addAttribute("prMessageException", prMesEx);

		session.setAttribute("vybranaMt", pk.getGz39tMtKalkulace().getGz39tMt().getModelTr());
		session.setAttribute("vybranyZavod", pk.getGz39tMtKalkulace().getGz39tMt().getZavod());
		session.setAttribute("kalkulaceRRRRMM", pk.getGz39tMtKalkulace().getGz39tKalkulace().getKalkulace());

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

			log.debug("###\t Korekce PR popisu predstavitele - ulozeni posledni editace do MT_KALKULACE.");
			List<MtKalkulace> mtKalkulacePoslEdit = serviceMtKalkulace.getMtKalkulace(pk.getGz39tMtKalkulace().getGz39tMt().getModelTr(), pk.getGz39tMtKalkulace().getGz39tMt().getZavod());
			for (MtKalkulace mtkx : mtKalkulacePoslEdit) {
				mtkx.setPosledniEditace(new Date());
				mtkx.setPosledniEditaceDuvod("Korekce PR popisu představitele " + pk.getGz39tPredstavitel().getCisloPred() + ", " + pk.getGz39tPredstavitel().getModelovyKlic().toUpperCase() + " - "
						+ mtkx.getGz39tMt().getZavod());
				serviceMtKalkulace.setMtKalkulace(mtkx);
			}
		}
		return "redirect:/srv/predstavitel/detail/" + idPredKalk;
	}

	@RequestMapping("/detail/zmenaVybavy/{idPredKalk}")
	public String predstavitelDetailZmenaVybavy(@PathVariable long idPredKalk, PredstavitelKalkulace predstavitelKalkulace, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDetailZmenaVybavy(" + idPredKalk + ", " + predstavitelKalkulace.getVybavyEdit() + ")");

		PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);
		pk.setVybavyEdit(predstavitelKalkulace.getVybavyEdit().toUpperCase());
		pk.setExistsPr(null);
		pk.setUtime(new Date());
		pk.setUuser(req.getUserPrincipal().getName().toUpperCase());
		servicePredstavitelKalkulace.setPredstavitelKalkulace(pk);

		servicePredstavitelPr.removePredstavitelPrAll(pk.getId());
		servicePredstavitelMessage.removePredstavitelMessageAll(pk.getId());

		log.debug("###\t Zmena vybavy predstavitele - ulozeni posledni editace do MT_KALKULACE.");
		List<MtKalkulace> mtKalkulacePoslEdit = serviceMtKalkulace.getMtKalkulace(pk.getGz39tMtKalkulace().getGz39tMt().getModelTr(), pk.getGz39tMtKalkulace().getGz39tMt().getZavod());
		for (MtKalkulace mtkx : mtKalkulacePoslEdit) {
			mtkx.setPosledniEditace(new Date());
			mtkx.setPosledniEditaceDuvod("Změna výbavy představitele " + pk.getGz39tPredstavitel().getCisloPred() + ", " + pk.getGz39tPredstavitel().getModelovyKlic().toUpperCase() + " - "
					+ mtkx.getGz39tMt().getZavod());
			serviceMtKalkulace.setMtKalkulace(mtkx);
		}
		return "redirect:/srv/predstavitel/detail/" + idPredKalk;
	}

	@RequestMapping("/detail/zrusitVybavu/{idPredKalk}")
	public String predstavitelDetailZrusitVybavu(@PathVariable long idPredKalk, PredstavitelKalkulace predstavitelKalkulace, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDetailZrusitVybavu(" + idPredKalk + ", " + predstavitelKalkulace.getVybavyEdit() + ")");

		PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);
		pk.setVybavyEdit(null);
		pk.setExistsPr(null);
		pk.setUtime(new Date());
		pk.setUuser(req.getUserPrincipal().getName().toUpperCase());
		servicePredstavitelKalkulace.setPredstavitelKalkulace(pk);

		servicePredstavitelPr.removePredstavitelPrAll(pk.getId());
		servicePredstavitelMessage.removePredstavitelMessageAll(pk.getId());

		log.debug("###\t Zmena (zruseni) vybavy predstavitele - ulozeni posledni editace do MT_KALKULACE.");
		List<MtKalkulace> mtKalkulacePoslEdit = serviceMtKalkulace.getMtKalkulace(pk.getGz39tMtKalkulace().getGz39tMt().getModelTr(), pk.getGz39tMtKalkulace().getGz39tMt().getZavod());
		for (MtKalkulace mtkx : mtKalkulacePoslEdit) {
			mtkx.setPosledniEditace(new Date());
			mtkx.setPosledniEditaceDuvod("Změna (zrušení) výbavy představitele " + pk.getGz39tPredstavitel().getCisloPred() + ", " + pk.getGz39tPredstavitel().getModelovyKlic().toUpperCase() + " - "
					+ mtkx.getGz39tMt().getZavod());
			serviceMtKalkulace.setMtKalkulace(mtkx);
		}

		return "redirect:/srv/predstavitel/detail/" + idPredKalk;
	}

	@RequestMapping("/detail/potlacitVybavu/{idPredKalk}")
	public String predstavitelDetailPotlacitVybavu(@PathVariable long idPredKalk, PredstavitelKalkulace predstavitelKalkulace, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDetailPotlacitVybavu(" + idPredKalk + ", " + predstavitelKalkulace.getVybavyEdit() + ")");

		PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);
		pk.setVybavyEdit("Původní výbava potlačena!");
		pk.setUtime(new Date());
		pk.setExistsPr(null);
		pk.setUuser(req.getUserPrincipal().getName().toUpperCase());
		servicePredstavitelKalkulace.setPredstavitelKalkulace(pk);

		servicePredstavitelPr.removePredstavitelPrAll(pk.getId());
		servicePredstavitelMessage.removePredstavitelMessageAll(pk.getId());

		log.debug("###\t Zmena (potlaceni) vybavy predstavitele - ulozeni posledni editace do MT_KALKULACE.");
		List<MtKalkulace> mtKalkulacePoslEdit = serviceMtKalkulace.getMtKalkulace(pk.getGz39tMtKalkulace().getGz39tMt().getModelTr(), pk.getGz39tMtKalkulace().getGz39tMt().getZavod());
		for (MtKalkulace mtkx : mtKalkulacePoslEdit) {
			mtkx.setPosledniEditace(new Date());
			mtkx.setPosledniEditaceDuvod("Změna (potlačení) výbavy představitele " + pk.getGz39tPredstavitel().getCisloPred() + ", " + pk.getGz39tPredstavitel().getModelovyKlic().toUpperCase()
					+ " - " + mtkx.getGz39tMt().getZavod());
			serviceMtKalkulace.setMtKalkulace(mtkx);
		}

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

		return "/predstavitelDetailPrPopup";
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
