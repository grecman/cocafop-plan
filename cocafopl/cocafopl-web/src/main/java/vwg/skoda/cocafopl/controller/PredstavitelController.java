package vwg.skoda.cocafopl.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import vwg.skoda.cocafopl.entity.Kalkulace;
import vwg.skoda.cocafopl.entity.Mt;
import vwg.skoda.cocafopl.entity.MtKalkulace;
import vwg.skoda.cocafopl.entity.Predstavitel;
import vwg.skoda.cocafopl.entity.PredstavitelKalkulace;
import vwg.skoda.cocafopl.entity.Protokol;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.obj.UniObj;
import vwg.skoda.cocafopl.service.KalkulaceService;
import vwg.skoda.cocafopl.service.MtKalkulaceService;
import vwg.skoda.cocafopl.service.MtService;
import vwg.skoda.cocafopl.service.PredstavitelKalkulaceService;
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
	private PredstavitelKalkulaceService servicePredstavitelKalkulace;

	@Autowired
	private MtService serviceMt;

	@Autowired
	private MtKalkulaceService serviceMtKalkulace;

	@Autowired
	private KalkulaceService serviceKalkulace;

	@RequestMapping("/definice")
	public String predstavitelDefinice(UniObj uniObj, Model model, HttpSession session, HttpServletRequest req) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefinice()");
		session.setAttribute("pageTitle", "Definice představitele");

		session.setAttribute("vybranyRok", "");
		session.setAttribute("vybranaMtZavod", "");
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");
		session.setAttribute("kalkulaceRRRRMM", "");

		List<Integer> listRoku = serviceMtKalkulace.getMtKalkulaceRoky();

		if (listRoku.isEmpty()) {
			log.debug("###\t Neexistuji zadne kalkulace! Je nutne je nejdrive zadat.");
			return "redirect:/srv/kalkulace/seznam";
		}

		model.addAttribute("listRoku", listRoku);

		return "/predstavitelDefinice";
	}

	@RequestMapping("/definice/zadatMt")
	public String predstavitelDefiniceZadatMt(UniObj uniObj, Model model, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceZadatMt(" + uniObj.getRok() + ")");
		session.setAttribute("pageTitle", "Definice představitele");
		session.setAttribute("vybranyRok", uniObj.getRok());

		List<String> listMtZavod = serviceMtKalkulace.getMtKalkulaceMtZavod(uniObj.getRok());
		model.addAttribute("listMtZavod", listMtZavod);

		return "/predstavitelDefinice";
	}

	@RequestMapping("/definice/list")
	public String predstavitelDefiniceList(UniObj uniObj, Model model, HttpSession session, HttpServletRequest req) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceList(" + session.getAttribute("vybranyRok") + ", " + uniObj.getMtZavod() + " (form) /" + session.getAttribute("vybranaMtZavod") + " (session)" + ")");
		session.setAttribute("pageTitle", "Definice představitele");

		if (session.getAttribute("vybranaMtZavod") == "") {
			session.setAttribute("vybranaMtZavod", uniObj.getMtZavod());
			session.setAttribute("vybranaMt", uniObj.getMtZavod().substring(0, uniObj.getMtZavod().indexOf("-")));
			session.setAttribute("vybranyZavod", uniObj.getMtZavod().substring(uniObj.getMtZavod().indexOf("-") + 1));
		}

		List<Predstavitel> pr = servicePredstavitel.getPredstavitele(Integer.valueOf(((String) session.getAttribute("vybranyRok"))), (String) session.getAttribute("vybranaMt"),
				(String) session.getAttribute("vybranyZavod"));
		model.addAttribute("listPredstavitelu", pr);

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().contains("USERS"))
			// TODO po schvaleni uz ne !!!
			model.addAttribute("moznoEditovat", true);
		else
			model.addAttribute("moznoEditovat", false);

		Mt mt = serviceMt.getMt((String) session.getAttribute("vybranaMt"));
		model.addAttribute("mt", mt);

		return "/predstavitelDefinice";
	}

	@RequestMapping("/definice/novyPredForm")
	public String predstavitelDefiniceNovyForm(Predstavitel p, Model model, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceNovyForm(" + session.getAttribute("vybranyRok") + ", " + session.getAttribute("vybranaMt") + ", " + session.getAttribute("vybranyZavod") + ")");
		session.setAttribute("pageTitle", "Založení nového představitele");

		Mt mt = serviceMt.getMt((String) session.getAttribute("vybranaMt"));
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

		List<Predstavitel> prd = servicePredstavitel.getPredstavitele(Integer.valueOf(((String) session.getAttribute("vybranyRok"))), (String) session.getAttribute("vybranaMt"),
				(String) session.getAttribute("vybranyZavod"));
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

		// kontrola zdali nahodou neexistuje predstavitel se stejnym MK a kode zeme!
		if (servicePredstavitel.getPredstavitel(Integer.valueOf(session.getAttribute("vybranyRok").toString()), (String) session.getAttribute("vybranaMt") + p.getModelovyKlic().toUpperCase(),
				p.getKodZeme()) == null) {

			Integer platOd = Integer.valueOf(((String) session.getAttribute("vybranyRok")) + (p.getPlatnostOd() < 10 ? "0" + String.valueOf(p.getPlatnostOd()) : String.valueOf(p.getPlatnostOd())));
			Integer platDo = Integer.valueOf(((String) session.getAttribute("vybranyRok")) + (p.getPlatnostDo() < 10 ? "0" + String.valueOf(p.getPlatnostDo()) : String.valueOf(p.getPlatnostDo())));

			Mt mt = serviceMt.getMt((String) session.getAttribute("vybranaMt"));
			
			// kalkulace, ktere jsou pro danou MT uz schvaleny
			List<MtKalkulace> mtk = serviceMtKalkulace.getMtKalkulace(mt.getModelTr());
			for (MtKalkulace mtKalkulace : mtk) {
				System.out.println("for");
				if (mtKalkulace.getGz39tKalkulace().getSchvaleno()==null) {
					// TODO: ...
					System.out.println("if - brek");
					break;
				}
			}
			System.out.println(mtk.get(0).getGz39tKalkulace().getKalkulace());

			Predstavitel pred = new Predstavitel();
			pred.setCetnost(p.getCetnost());
			pred.setCisloPred(p.getCisloPred());
			pred.setComix(p.getComix());
			pred.setEuNorma(p.getEuNorma().toUpperCase());
			pred.setKodZeme(p.getKodZeme() == null ? null : p.getKodZeme().toUpperCase());
			pred.setModelovyKlic((String) session.getAttribute("vybranaMt") + p.getModelovyKlic().toUpperCase());
			pred.setObsah(p.getObsah());
			pred.setPlatnostOd(platOd);
			pred.setPlatnostDo(platDo);
			pred.setPoznamka(p.getPoznamka());
			pred.setRok(Integer.valueOf((String) session.getAttribute("vybranyRok")));
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
					mtkx.setPosledniEditaceDuvod("Nový představitel číslo " + p.getCisloPred() + ", " + mt.getModelTr() + p.getModelovyKlic() + " - " + mt.getZavod());
					serviceMtKalkulace.setMtKalkulace(mtkx);
				}
			}

			Predstavitel prrr = servicePredstavitel.getPredstavitel(Integer.valueOf((String) session.getAttribute("vybranyRok")), (String) session.getAttribute("vybranaMt"), p.getCisloPred());
			predstavitelKalkulaceVytvoreni(req, prrr.getId());
			predstavitelKalkulaceSmazani(req, prrr.getId());

		} else {
			log.debug("###\t\t ... predstavitel se stejnym MK a kodeZeme jiz existuje!");
		}

		return "redirect:/srv/predstavitel/definice/list";
	}

	@RequestMapping("/definice/editForm/{idPredstavitele}")
	public String predstavitelDefiniceEditForm(@PathVariable long idPredstavitele, Predstavitel p, Model model, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDefiniceEditForm(" + idPredstavitele + ", " + session.getAttribute("vybranyRok") + ", " + session.getAttribute("vybranaMt") + ", "
				+ session.getAttribute("vybranyZavod") + ")");
		session.setAttribute("pageTitle", "Založení nového představitele");

		Predstavitel pred = servicePredstavitel.getPredstavitel(idPredstavitele);
		model.addAttribute("predInput", pred);

		Mt mt = serviceMt.getMt((String) session.getAttribute("vybranaMt"));
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

		List<Predstavitel> prd = servicePredstavitel.getPredstavitele(Integer.valueOf(((String) session.getAttribute("vybranyRok"))), (String) session.getAttribute("vybranaMt"),
				(String) session.getAttribute("vybranyZavod"));
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
		log.debug("###\t predstavitelDefiniceEditsubmit(" + p.getCisloPred() + ", " + p.getModelovyKlic() + ", "+p.getKodZeme()+ ", " + p.getPlatnostOd() + "-" + p.getPlatnostDo() + ")");

		Predstavitel pred = servicePredstavitel.getPredstavitel(p.getId());

		// kontrola zdali nahodou neexistuje predstavitel se stejnym MK a kode zeme!
		if (servicePredstavitel.getPredstavitel(pred.getRok(), pred.getGz39tMt().getModelTr() + p.getModelovyKlic().toUpperCase(), p.getKodZeme()) == null) {

			Integer platOd = Integer.valueOf(((String) session.getAttribute("vybranyRok")) + (p.getPlatnostOd() < 10 ? "0" + String.valueOf(p.getPlatnostOd()) : String.valueOf(p.getPlatnostOd())));
			Integer platDo = Integer.valueOf(((String) session.getAttribute("vybranyRok")) + (p.getPlatnostDo() < 10 ? "0" + String.valueOf(p.getPlatnostDo()) : String.valueOf(p.getPlatnostDo())));

			pred.setCetnost(p.getCetnost());
			pred.setCisloPred(p.getCisloPred());
			pred.setCisloPredMin(p.getCisloPredMin());
			pred.setComix(p.getComix());
			pred.setEuNorma(p.getEuNorma().toUpperCase());
			pred.setKodZeme(p.getKodZeme() == null ? null : p.getKodZeme().toUpperCase());
			pred.setModelovyKlic(pred.getGz39tMt().getModelTr() + p.getModelovyKlic().toUpperCase());
			pred.setObsah(p.getObsah());
			pred.setPlatnostOd(platOd);
			pred.setPlatnostDo(platDo);
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
					mtk.setPosledniEditaceDuvod("Editace představitele číslo " + p.getCisloPred() + ", " + mtk.getGz39tMt().getModelTr() + p.getModelovyKlic() + " - " + mtk.getGz39tMt().getZavod());
					serviceMtKalkulace.setMtKalkulace(mtk);
				}
			}

			predstavitelKalkulaceVytvoreni(req, pred.getId());
			predstavitelKalkulaceSmazani(req, pred.getId());
		} else {
			log.debug("###\t\t ... predstavitel se stejnym MK a kodeZeme jiz existuje!");
		}

		return "redirect:/srv/predstavitel/definice/list";
	}

	@RequestMapping(value = "/definice/smazatPredstavitele/{idPred}")
	public String smazatPredstavitele(@PathVariable long idPred, Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t smazatPredstavitele(" + idPred + ")");

		Predstavitel predRemove = servicePredstavitel.getPredstavitel(idPred);

		Boolean muzemeSmazat = true;
		List<PredstavitelKalkulace> predKalk = servicePredstavitelKalkulace.getPredstaviteleKalkulace(predRemove.getCisloPred());
		for (PredstavitelKalkulace pk : predKalk) {
			if (pk.getGz39tMtKalkulace().getGz39tKalkulace().getSchvalil() != null) {
				muzemeSmazat = false;
			}
		}
		
		if (muzemeSmazat) {
			String message = predRemove.getModelovyKlic() + ", " + predRemove.getCisloPred() + ", " + predRemove.getKodZeme() + ", " + predRemove.getPlatnostOd() + "-" + predRemove.getPlatnostDo();
			servicePredstavitel.removePredstavitel(predRemove);

			Protokol newProtokol = new Protokol();
			newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newProtokol.setAction("Smazani predstavitele");
			newProtokol.setInfo(message);
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);
		} else {
			log.debug("###\t\t ... smazani neprobehlo, pac pro predstavitele " + predRemove.getCisloPred() + " (" + predRemove.getGz39tMt().getModelTr() + "-" + predRemove.getGz39tMt().getZavod()
					+ ") existuje nejaka schvalena kalkulace)");
		}
		

		return "redirect:/srv/predstavitel/definice/list";
	}

	@RequestMapping(value = "/definice/doplnitMinuleCisloPredstavitele")
	public String doplnitMinuleCisloPredstavitele(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t doplnitMinuleCisloPredstavitele(" + session.getAttribute("vybranyRok") + ", " + session.getAttribute("vybranaMt") + ")");

		List<Predstavitel> predstavitel = servicePredstavitel.getPredstavitele(Integer.valueOf(((String) session.getAttribute("vybranyRok"))), (String) session.getAttribute("vybranaMt"),
				(String) session.getAttribute("vybranyZavod"));
		for (Predstavitel pred : predstavitel) {
			if (pred.getCisloPredMin() == null) {
				//Predstavitel predMin = servicePredstavitel.getPredstavitel(pred.getRok() - 1, pred.getModelovyKlic(), pred.getKodZeme());
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

		session.setAttribute("vybranyRok", "");
		session.setAttribute("vybranaMtZavod", "");
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

		session.setAttribute("kalukaceRRRRMM", kalkulace.getKalkulace());
		return "redirect:/srv/predstavitel/seznam/" + kalkulace.getKalkulace();
	}

	@RequestMapping("/seznam/{kalukaceRRRRMM}")
	public String predstavitelSeznamSKalkulaci(@PathVariable int kalukaceRRRRMM, Mt mt, MtKalkulace mtKalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException,
			UnknownHostException {
		log.debug("###\t predstavitelSeznamSKalkulaci(" + session.getAttribute("kalukaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + ")");

		if (session.getAttribute("vybranaMt").toString().isEmpty()) {
			List<MtKalkulace> mtList = serviceMtKalkulace.getMtKalkulace((Integer) session.getAttribute("kalukaceRRRRMM"));
			model.addAttribute("mtList", mtList);
		} else {
			MtKalkulace mtk = serviceMtKalkulace.getMtKalkulace((Integer) session.getAttribute("kalukaceRRRRMM"), (String) session.getAttribute("vybranaMt"));
			model.addAttribute("mtk", mtk);
		}

		if (!session.getAttribute("vybranaMt").toString().isEmpty()) {
			List<PredstavitelKalkulace> pk = servicePredstavitelKalkulace.getPredstaviteleKalkulace(session.getAttribute("vybranaMt").toString(), (Integer) session.getAttribute("kalukaceRRRRMM"));
			model.addAttribute("pk", pk);
		}

		return "/predstavitelSeznam";
	}

	@RequestMapping("/seznam/plusMesic")
	public String predstavitelSeznamPlusMesic(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamPlusMesic(" + session.getAttribute("kalukaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + ")");
		Kalkulace k = serviceKalkulace.getKalkulacePlusMesic((Integer) session.getAttribute("kalukaceRRRRMM"));
		session.setAttribute("kalukaceRRRRMM", k.getKalkulace());

		if (!session.getAttribute("vybranaMt").toString().isEmpty()) {
			MtKalkulace mtKalkulace = serviceMtKalkulace.getMtKalkulace(k.getKalkulace(), session.getAttribute("vybranaMt").toString());
			if (mtKalkulace == null) {
				session.setAttribute("vybranaMt", "");
			} else {
				session.setAttribute("vybranaMt", mtKalkulace.getGz39tMt().getModelTr());
			}
		}

		return "redirect:/srv/predstavitel/seznam/" + k.getKalkulace();
	}

	@RequestMapping("/seznam/minusMesic")
	public String predstavitelSeznamMinusMesic(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamPlusMesic(" + session.getAttribute("kalukaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + ")");
		Kalkulace k = serviceKalkulace.getKalkulaceMinusMesic((Integer) session.getAttribute("kalukaceRRRRMM"));
		session.setAttribute("kalukaceRRRRMM", k.getKalkulace());

		if (!session.getAttribute("vybranaMt").toString().isEmpty()) {
			MtKalkulace mtKalkulace = serviceMtKalkulace.getMtKalkulace(k.getKalkulace(), session.getAttribute("vybranaMt").toString());
			if (mtKalkulace == null) {
				session.setAttribute("vybranaMt", "");
			} else {
				session.setAttribute("vybranaMt", mtKalkulace.getGz39tMt().getModelTr());
			}
		}

		return "redirect:/srv/predstavitel/seznam/" + k.getKalkulace();
	}

	@RequestMapping("/seznam/vyberMt")
	public String predstavitelSeznamVyberMt(Mt mt, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamVyberMt(" + session.getAttribute("kalukaceRRRRMM") + ", " + mt.getModelTr() + ")");
		session.setAttribute("vybranaMt", mt.getModelTr());
		return "redirect:/srv/predstavitel/seznam/" + session.getAttribute("kalukaceRRRRMM");
	}

	@RequestMapping("/seznam/zmenaModelovehoRoku")
	public String predstavitelSeznamZmenaModelovehoRoku(MtKalkulace mtKalkulace, Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelSeznamZmenaModelovehoRoku(" + mtKalkulace.getMrok() + ", " + session.getAttribute("kalukaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + ")");

		MtKalkulace mtk = serviceMtKalkulace.getMtKalkulace((Integer) session.getAttribute("kalukaceRRRRMM"), (String) session.getAttribute("vybranaMt"));
		mtk.setMrok(mtKalkulace.getMrok());
		mtk.setUtime(new Date());
		mtk.setUuser(req.getUserPrincipal().getName().toUpperCase());
		serviceMtKalkulace.setMtKalkulace(mtk);

		return "redirect:/srv/predstavitel/seznam/" + session.getAttribute("kalukaceRRRRMM");
	}

	/* ******************************************************************************************************************************************** */

	public void predstavitelKalkulaceVytvoreni(HttpServletRequest req, long idPredstavitele) {
		log.debug("###\t predstavitelKalkulaceVytvoreni(" + idPredstavitele + ")");
		// metoda vrati seznam ID MtKalkulace a ID Predstavitele, pro ktere je nutne vyrvorit novy "radek" v entite PredstavitelKalkulace

		List<Object[]> gre = servicePredstavitelKalkulace.getPredstavitelKalkulaceKtereZatimNeexistuji(idPredstavitele);
		for (Object[] result : gre) {
			MtKalkulace mtk = serviceMtKalkulace.getMtKalkulaceOne((Long) result[0]);
			Predstavitel prt = servicePredstavitel.getPredstavitel((Long) result[1]);
			log.debug("###\t Zakladam v entite PredstavitelKalkulace zaznam pro: " + mtk.getGz39tKalkulace().getKalkulace() + ", " + prt.getCisloPred() + " - " + prt.getModelovyKlic());
			PredstavitelKalkulace pk = new PredstavitelKalkulace();
			pk.setGz39tMtKalkulace(mtk);
			pk.setGz39tPredstavitel(prt);
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
			newProtokol.setInfo("a to na zaklade zmeny platnosti predstavitele! " + ggg.getGz39tPredstavitel().getRok() + " č.pred." + ggg.getGz39tPredstavitel().getCisloPred() + " "
					+ ggg.getGz39tPredstavitel().getModelovyKlic() + " pro kalkulaci " + ggg.getGz39tMtKalkulace().getGz39tKalkulace().getKalkulace());
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);
		}

	}

}
