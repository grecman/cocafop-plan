package vwg.skoda.cocafopl.controller;

import java.sql.SQLException;
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

import vwg.skoda.cocafopl.entity.ArchCenikView;
import vwg.skoda.cocafopl.entity.ArchKalkulace;
import vwg.skoda.cocafopl.entity.ArchKalkulaceMtZavView;
import vwg.skoda.cocafopl.entity.ArchKalkulaceView;
import vwg.skoda.cocafopl.entity.ArchKurzCzk;
import vwg.skoda.cocafopl.entity.ArchKurzEur;
import vwg.skoda.cocafopl.entity.ArchKusovnik;
import vwg.skoda.cocafopl.entity.ArchKusyProvView;
import vwg.skoda.cocafopl.entity.ArchPredstavitel;
import vwg.skoda.cocafopl.entity.ArchPredstavitelPr;
import vwg.skoda.cocafopl.entity.MtProd;
import vwg.skoda.cocafopl.entity.PrView;
import vwg.skoda.cocafopl.entity.PredstavitelPr;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.service.ArchCenikViewService;
import vwg.skoda.cocafopl.service.ArchKalkulaceMtZavViewService;
import vwg.skoda.cocafopl.service.ArchKalkulaceService;
import vwg.skoda.cocafopl.service.ArchKalkulaceViewService;
import vwg.skoda.cocafopl.service.ArchKurzCzkService;
import vwg.skoda.cocafopl.service.ArchKurzEurService;
import vwg.skoda.cocafopl.service.ArchKusovnikService;
import vwg.skoda.cocafopl.service.ArchKusyProvViewService;
import vwg.skoda.cocafopl.service.ArchPredstavitelPrService;
import vwg.skoda.cocafopl.service.ArchPredstavitelService;
import vwg.skoda.cocafopl.service.MtProdService;
import vwg.skoda.cocafopl.service.PrViewService;
import vwg.skoda.cocafopl.service.UserService;

@Controller
@RequestMapping("/archiv")
public class ArchivController {

	static Logger log = Logger.getLogger(PredstavitelController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private ArchKalkulaceService serviceArchKalkulace;

	@Autowired
	private ArchKusovnikService serviceArchKusovnik;

	@Autowired
	private ArchCenikViewService serviceArchCenikView;

	@Autowired
	private ArchKusyProvViewService serviceArchKusyProvView;

	@Autowired
	private ArchPredstavitelService serviceArchPredstavitel;

	@Autowired
	private ArchPredstavitelPrService serviceArchPredstavitelPr;

	@Autowired
	private ArchKalkulaceViewService serviceArchKalkulaceView;

	@Autowired
	private ArchKalkulaceMtZavViewService serviceArchKalkulaceMtZavView;

	@Autowired
	private MtProdService serviceMtProd;

	@Autowired
	private PrViewService servicePrView;

	@Autowired
	private ArchKurzCzkService serviceArchKurzCzk;

	@Autowired
	private ArchKurzEurService serviceArchKurzEur;
	
	Integer maxLimitNaZobrazeni = 1000;

	@RequestMapping("/kalkulace")
	public String archivKalkulace(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivKalkulace()");
		session.setAttribute("pageTitle", "Archív - kalkulace");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} else if (aktualUser.getUserRole().equals("ADMINS".trim())) {
			log.debug("###\t Uzivatel s roli ADMINS ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/logging";
		}

		List<ArchKalkulaceView> ak = serviceArchKalkulaceView.getArchKalkulaceViewAll();
		model.addAttribute("archKalkulace", ak);

		return "/archivKalkulace";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/kusovnik")
	public String archivKusovnik(ArchKusovnik archKusovnik, ArchKalkulace archKalkulace, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivKusovnik()");
		session.setAttribute("pageTitle", "Archív - kusovník");
		session.setAttribute("archKalkulaceRRRRMM", "");
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyProdukt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulaceView> ak = serviceArchKalkulaceView.getArchKalkulaceViewAll();
		model.addAttribute("archKalkulaceList", ak);
		return "/archivKusovnik";
	}

	@RequestMapping("/kusovnik/prodZav")
	public String archivKusovnikProdZav(ArchKusovnik archKusovnik, ArchKalkulaceMtZavView archKalkulaceMtZavView, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws SQLException {
		log.debug("###\t archivKusovnikProdZav(" + archKalkulaceMtZavView.getKalkulace() + " / " + session.getAttribute("archKalkulaceRRRRMM") + ")");

		if (session.getAttribute("archKalkulaceRRRRMM").toString().isEmpty()) {
			session.setAttribute("archKalkulaceRRRRMM", archKalkulaceMtZavView.getKalkulace());
		}
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyProdukt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulaceMtZavView> akMtZavView = serviceArchKalkulaceMtZavView.getArchKalkulaceMtZavView(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()));
		model.addAttribute("mtZavodList", akMtZavView);

		return "/archivKusovnik";
	}

	@RequestMapping("/kusovnik/param")
	public String archivKusovnikParam(ArchKusovnik archKusovnik, ArchKalkulaceMtZavView archKalkulaceMtZavView, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws SQLException {
		log.debug("###\t archivKusovnikParam(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + archKalkulaceMtZavView.getIdPom() + ")");

		ArchKalkulaceMtZavView akv = serviceArchKalkulaceMtZavView.getArchKalkulaceMtZavViewId(archKalkulaceMtZavView.getIdPom());
		session.setAttribute("vybranyProdukt", akv.getProdukt());
		session.setAttribute("vybranyZavod", akv.getZavod());
		session.setAttribute("vybranaMt", akv.getModelTr());

		return "/archivKusovnik";
	}

	@RequestMapping("/kusovnik/list")
	public String archivKusovnikList(ArchKusovnik archKusovnik, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivKusovnikList(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + session.getAttribute("vybranyProdukt") + ", " + session.getAttribute("vybranyZavod") + ", '%"
				+ archKusovnik.getCdilu().toUpperCase().trim() + "%')");

		List<ArchKusovnik> ak = serviceArchKusovnik.getArchKusovnik(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()), session.getAttribute("vybranyProdukt").toString(), session
				.getAttribute("vybranyZavod").toString(), "%" + archKusovnik.getCdilu().toUpperCase().trim() + "%");

		if (ak.size() > maxLimitNaZobrazeni) {
			model.addAttribute("archKusList", ak.subList(0, maxLimitNaZobrazeni));
		} else {
			model.addAttribute("archKusList", ak);
		}
		model.addAttribute("maxLimitNaZobrazeni", maxLimitNaZobrazeni);
		model.addAttribute("pocetNactenychZaznamu", ak.size());

		return "/archivKusovnik";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/cenik")
	public String archivCenik(ArchKalkulace archKalkulace, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivCenik()");
		session.setAttribute("pageTitle", "Archív - ceník");

		session.setAttribute("archKalkulaceRRRRMM", "");
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulaceView> ak = serviceArchKalkulaceView.getArchKalkulaceViewAll();
		model.addAttribute("archKalkulaceList", ak);
		return "/archivCenik";
	}

	@RequestMapping("/cenik/param")
	public String archivCenikParam(ArchKalkulace archKalkulace, ArchCenikView archCenikView, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivCenikParam(" + archKalkulace.getKalkulace() + " / " + session.getAttribute("archKalkulaceRRRRMM") + ")");

		if (session.getAttribute("archKalkulaceRRRRMM").toString().isEmpty()) {
			session.setAttribute("archKalkulaceRRRRMM", archKalkulace.getKalkulace());
		}
		return "/archivCenik";
	}

	@RequestMapping("/cenik/list")
	public String archivCenikList(ArchCenikView archCenikView, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivCenikList(" + session.getAttribute("archKalkulaceRRRRMM") + ", '%" + archCenikView.getCdilu().toUpperCase().trim() + "%', " + archCenikView.getCizav() + ", "
				+ archCenikView.getDodavatel() + ")");

		List<ArchCenikView> ac = serviceArchCenikView.getArchCenikView(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()), "%" + archCenikView.getCdilu().toUpperCase().trim()
				+ "%", archCenikView.getCizav(), archCenikView.getDodavatel().toUpperCase());

		if (ac.size() > maxLimitNaZobrazeni) {
			model.addAttribute("archCenikList", ac.subList(0, maxLimitNaZobrazeni));
		} else {
			model.addAttribute("archCenikList", ac);
		}
		model.addAttribute("maxLimitNaZobrazeni", maxLimitNaZobrazeni);
		model.addAttribute("pocetNactenychZaznamu", ac.size());

		return "/archivCenik";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/predstavitel")
	public String archivPredstavitel(Model model, ArchKalkulace archKalkulace, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivPredstavitel()");
		session.setAttribute("pageTitle", "Archív - představitel");

		session.setAttribute("archKalkulaceRRRRMM", "");
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulace> ak = serviceArchKalkulace.getArchKalkulaceAll();
		model.addAttribute("archKalkulaceList", ak);

		return "/archivPredstavitel";
	}

	@RequestMapping("/predstavitel/mtZav")
	public String archivPredstavitelMtZav(Model model, ArchKalkulace archKalkulace, ArchKalkulaceMtZavView archKalkulaceMtZavView, ArchPredstavitel archPredstavitel, HttpServletRequest req,
			HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivPredstavitelMtZav(" + archKalkulace.getKalkulace() + " / " + session.getAttribute("archKalkulaceRRRRMM") + ")");

		if (session.getAttribute("archKalkulaceRRRRMM").toString().isEmpty()) {
			session.setAttribute("archKalkulaceRRRRMM", archKalkulace.getKalkulace());
		}
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulaceMtZavView> akMtZavView = serviceArchKalkulaceMtZavView.getArchKalkulaceMtZavView(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()));
		model.addAttribute("mtZavodList", akMtZavView);

		return "/archivPredstavitel";
	}

	@RequestMapping("/predstavitel/list")
	public String archivPredstavitelList(Model model, ArchKalkulace archKalkulace, ArchKalkulaceMtZavView archKalkulaceMtZavView, HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws SQLException {
		log.debug("###\t archivPredstavitelList(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + "  /  "
				+ archKalkulaceMtZavView.getIdPom() + ")");

		ArchKalkulaceMtZavView akMtZavView = null;
		if (archKalkulaceMtZavView.getIdPom() != null) {
			akMtZavView = serviceArchKalkulaceMtZavView.getArchKalkulaceMtZavViewId(archKalkulaceMtZavView.getIdPom());
		} else if (!session.getAttribute("vybranaMt").toString().isEmpty()) {
			akMtZavView = serviceArchKalkulaceMtZavView.getArchKalkulaceMtZavView(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()), session.getAttribute("vybranaMt").toString(),
					session.getAttribute("vybranyZavod").toString());
		}

		model.addAttribute("akMtZavView", akMtZavView);
		List<ArchPredstavitel> ap = serviceArchPredstavitel.getArchPredstavitel(akMtZavView.getKalkulace(), akMtZavView.getModelTr(), akMtZavView.getZavod());
		model.addAttribute("archPredList", ap);

		session.setAttribute("vybranaMt", akMtZavView.getModelTr());
		session.setAttribute("vybranyZavod", akMtZavView.getZavod());

		return "/archivPredstavitel";
	}

	@RequestMapping("/predstavitel/detail/{idPredstavitele}")
	public String archivPredstavitelDetail(@PathVariable long idPredstavitele, Model model, ArchKalkulace archKalkulace, ArchKalkulaceMtZavView archKalkulaceMtZavView, HttpServletRequest req,
			HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivPredstavitelDetail(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + ", " + idPredstavitele + ")");

		ArchPredstavitel ap = serviceArchPredstavitel.getArchPredstavitelId(idPredstavitele);
		model.addAttribute("p", ap);

		List<ArchPredstavitelPr> prPodminky = serviceArchPredstavitelPr.getArchPredstavitelPr(idPredstavitele);
		model.addAttribute("prPodminky", prPodminky);

		return "/archivPredstavitelDetail";
	}

	@RequestMapping("/predstavitel/detailPr/{idPred}/{prCislo}")
	public String archivPredstavitelDetailPr(@PathVariable long idPred, @PathVariable String prCislo, PredstavitelPr predstavitelPr, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException {
		log.debug("###\t archivPredstavitelDetailPr(" + idPred + ", " + prCislo + ")");

		ArchPredstavitel p = serviceArchPredstavitel.getArchPredstavitelId(idPred);
		MtProd mtProd = serviceMtProd.getMtProd(p.getModelTr());
		model.addAttribute("mtProd", mtProd);

		PrView prOne = servicePrView.getPrView(p.getProdukt(), prCislo);
		model.addAttribute("prOne", prOne);

		List<PrView> prList = servicePrView.getPrViews(p.getProdukt(), prOne.getFamkz());
		model.addAttribute("prList", prList);

		return "/predstavitelDetailPrPopup";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/kusyNaProvedeni")
	public String archivKusyNaProvedeni(ArchKusyProvView archKusyProvView, ArchKalkulace archKalkulace, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws SQLException {
		log.debug("###\t archivKusyNaProvedeni()");
		session.setAttribute("pageTitle", "Archív - kusy na provedení");
		session.setAttribute("archKalkulaceRRRRMM", "");
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulaceView> ak = serviceArchKalkulaceView.getArchKalkulaceViewAll();
		model.addAttribute("archKalkulaceList", ak);
		return "/archivKusyNaProvedeni";
	}

	@RequestMapping("/kusyNaProvedeni/prodZav")
	public String archivKusyNaProvedeniProdZav(ArchKusyProvView archKusyProvView, ArchKalkulaceMtZavView archKalkulaceMtZavView, Model model, HttpServletRequest req, HttpServletResponse res,
			HttpSession session) throws SQLException {
		log.debug("###\t archivKusyNaProvedeniProdZav(" + archKalkulaceMtZavView.getKalkulace() + " / " + session.getAttribute("archKalkulaceRRRRMM") + ")");

		if (session.getAttribute("archKalkulaceRRRRMM").toString().isEmpty()) {
			session.setAttribute("archKalkulaceRRRRMM", archKalkulaceMtZavView.getKalkulace());
		}
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulaceMtZavView> akMtZavView = serviceArchKalkulaceMtZavView.getArchKalkulaceMtZavView(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()));
		model.addAttribute("mtZavodList", akMtZavView);

		return "/archivKusyNaProvedeni";
	}

	@RequestMapping("/kusyNaProvedeni/param")
	public String archivKusyNaProvedeniParam(ArchKusyProvView archKusyProvView, ArchKalkulaceMtZavView archKalkulaceMtZavView, Model model, HttpServletRequest req, HttpServletResponse res,
			HttpSession session) throws SQLException {
		log.debug("###\t archivKusyNaProvedenikParam(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + archKalkulaceMtZavView.getIdPom() + ")");

		ArchKalkulaceMtZavView akv = serviceArchKalkulaceMtZavView.getArchKalkulaceMtZavViewId(archKalkulaceMtZavView.getIdPom());
		session.setAttribute("vybranyZavod", akv.getZavod());
		session.setAttribute("vybranaMt", akv.getModelTr());

		return "/archivKusyNaProvedeni";
	}

	@RequestMapping("/kusyNaProvedeni/list")
	public String archivKusyNaProvedeniList(ArchKusyProvView archKusyProvView, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivKusyNaProvedeniList(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + ", " + session.getAttribute("vybranyZavod") + ", "
				+ archKusyProvView.getCisloPred() + ", '%" + archKusyProvView.getCdilu().toUpperCase().trim() + "%')");

		List<ArchKusyProvView> akp = serviceArchKusyProvView.getArchKusyProvView(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()), session.getAttribute("vybranaMt").toString(),
				session.getAttribute("vybranyZavod").toString(), archKusyProvView.getCisloPred(), "%" + archKusyProvView.getCdilu().toUpperCase().trim() + "%");

		if (akp.size() > maxLimitNaZobrazeni) {
			model.addAttribute("archKusyNaProvList", akp.subList(0, maxLimitNaZobrazeni));
		} else {
			model.addAttribute("archKusyNaProvList", akp);
		}
		model.addAttribute("maxLimitNaZobrazeni", maxLimitNaZobrazeni);
		model.addAttribute("pocetNactenychZaznamu", akp.size());

		return "/archivKusyNaProvedeni";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/dilVPredstavitelich")
	public String archivDilVPredstavitelich(ArchKalkulace archKalkulace, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivDilVPredstavitelich()");
		session.setAttribute("pageTitle", "Archív - díl v představitelích");
		session.setAttribute("archKalkulaceRRRRMM", "");
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulaceView> ak = serviceArchKalkulaceView.getArchKalkulaceViewAll();
		model.addAttribute("archKalkulaceList", ak);
		return "/archivDilVPredstavitelich";
	}

	@RequestMapping("/dilVPredstavitelich/param")
	public String archivDilVPredstavitelichParam(ArchKalkulace archKalkulace, ArchKusyProvView archKusyProvView, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws SQLException {
		log.debug("###\t archivDilVPredstavitelichParam(" + archKalkulace.getKalkulace() + " / " + session.getAttribute("archKalkulaceRRRRMM") + ")");

		if (session.getAttribute("archKalkulaceRRRRMM").toString().isEmpty()) {
			session.setAttribute("archKalkulaceRRRRMM", archKalkulace.getKalkulace());
		}

		return "/archivDilVPredstavitelich";
	}

	@RequestMapping("/dilVPredstavitelich/list")
	public String archivDilVPredstavitelichList(ArchKusyProvView archKusyProvView, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivDilVPredstavitelichList(" + session.getAttribute("archKalkulaceRRRRMM") + ", '%" + archKusyProvView.getCdilu().toUpperCase().trim() + "%')");

		List<ArchKusyProvView> akp = serviceArchKusyProvView.getArchKusyProvView(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()), "%"
				+ archKusyProvView.getCdilu().toUpperCase().trim() + "%");

		if (akp.size() > maxLimitNaZobrazeni) {
			model.addAttribute("archDilVPredList", akp.subList(0, maxLimitNaZobrazeni));
		} else {
			model.addAttribute("archDilVPredList", akp);
		}
		model.addAttribute("maxLimitNaZobrazeni", maxLimitNaZobrazeni);
		model.addAttribute("pocetNactenychZaznamu", akp.size());

		return "/archivDilVPredstavitelich";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/kurzovniListek")
	public String archivKurzovniListek(ArchKalkulace archKalkulace, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivKurzovniListek()");
		session.setAttribute("pageTitle", "Archív - kurzovní lístek");
		session.setAttribute("archKalkulaceRRRRMM", "");
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");
		session.setAttribute("vybranyKurzovniListek", "");

		List<ArchKalkulaceView> ak = serviceArchKalkulaceView.getArchKalkulaceViewAll();
		model.addAttribute("archKalkulaceList", ak);
		return "/archivKurzovniListek";
	}

	@RequestMapping("/kurzovniListek/list/czk")
	public String archivKurzovniListekCzk(ArchKurzCzk archKurzCzk, ArchKalkulace archKalkulace, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivKurzovniListekCzk(" + archKalkulace.getKalkulace() + " / " + session.getAttribute("archKalkulaceRRRRMM") + ")");

		if (session.getAttribute("archKalkulaceRRRRMM").toString().isEmpty()) {
			session.setAttribute("archKalkulaceRRRRMM", archKalkulace.getKalkulace());
		}
		List<ArchKurzCzk> kl = serviceArchKurzCzk.getArchKurzCzk(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()));
		model.addAttribute("archKurzovniListekList", kl);

		session.setAttribute("vybranyKurzovniListek", "czk");

		return "/archivKurzovniListek";
	}

	@RequestMapping("/kurzovniListek/list/eur")
	public String archivKurzovniListekEur(ArchKurzCzk archKurzCzk, ArchKalkulace archKalkulace, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t archivKurzovniListekCzk(" + archKalkulace.getKalkulace() + " / " + session.getAttribute("archKalkulaceRRRRMM") + ")");

		if (session.getAttribute("archKalkulaceRRRRMM").toString().isEmpty()) {
			session.setAttribute("archKalkulaceRRRRMM", archKalkulace.getKalkulace());
		}
		List<ArchKurzEur> kl = serviceArchKurzEur.getArchKurzEur(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()));
		model.addAttribute("archKurzovniListekList", kl);

		session.setAttribute("vybranyKurzovniListek", "eur");

		return "/archivKurzovniListek";
	}

}
