package vwg.skoda.cocafopl.controller;

import java.net.UnknownHostException;
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

import vwg.skoda.cocafopl.entity.ArchKalkulace;
import vwg.skoda.cocafopl.entity.ArchKalkulaceMtZavView;
import vwg.skoda.cocafopl.entity.ArchKalkulaceView;
import vwg.skoda.cocafopl.entity.ArchPredstavitel;
import vwg.skoda.cocafopl.entity.ArchPredstavitelPr;
import vwg.skoda.cocafopl.entity.MtProd;
import vwg.skoda.cocafopl.entity.PrView;
import vwg.skoda.cocafopl.entity.PredstavitelPr;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.service.ArchKalkulaceMtZavViewService;
import vwg.skoda.cocafopl.service.ArchKalkulaceService;
import vwg.skoda.cocafopl.service.ArchKalkulaceViewService;
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
	private ArchKalkulaceService serviceArchKalkuace;

	@Autowired
	private ArchPredstavitelService serviceArchPredstavitel;

	@Autowired
	private ArchPredstavitelPrService serviceArchPredstavitelPr;

	@Autowired
	private ArchKalkulaceViewService serviceArchKalkuaceView;

	@Autowired
	private ArchKalkulaceMtZavViewService serviceArchKalkuaceMtZavView;
	
	@Autowired
	private MtProdService serviceMtProd;
	
	@Autowired
	private PrViewService servicePrView;

	@RequestMapping("/kalkulace")
	public String archivKalkulace(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
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

		List<ArchKalkulaceView> ak = serviceArchKalkuaceView.getArchKalkulaceViewAll();
		model.addAttribute("archKalkulace", ak);

		return "/archivKalkulace";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/kusovnik")
	public String archivKusovnik(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivKusovnik()");
		session.setAttribute("pageTitle", "Archív - kusovník");
		return "/archivKusovnik";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/cenik")
	public String archivCenik(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivCenik()");
		session.setAttribute("pageTitle", "Archív - ceník");
		return "/archivCenik";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/kusyNaProvedeni")
	public String archivKusyNaProvedeni(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivKusyNaProvedeni()");
		session.setAttribute("pageTitle", "Archív - kusy na provedení");
		return "/archivKusyNaProvedeni";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/predstavitel")
	public String archivPredstavitel(Model model, ArchKalkulace archKalkulace, ArchKalkulaceMtZavView archKalkulaceMtZavView, HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t archivPredstavitel()");
		session.setAttribute("pageTitle", "Archív - představitel");

		session.setAttribute("archKalkulaceRRRRMM", "");
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulace> ak = serviceArchKalkuace.getArchKalkulaceAll();
		model.addAttribute("archKalkulaceList", ak);

		return "/archivPredstavitel";
	}

	@RequestMapping("/predstavitel/mtZav")
	public String archivPredstavitelMtZav(Model model, ArchKalkulace archKalkulace, ArchKalkulaceMtZavView archKalkulaceMtZavView, ArchPredstavitel archPredstavitel, HttpServletRequest req,
			HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivPredstavitelMtZav(" + archKalkulace.getKalkulace() + ")");

		session.setAttribute("archKalkulaceRRRRMM", archKalkulace.getKalkulace());
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulaceMtZavView> akMtZavView = serviceArchKalkuaceMtZavView.getArchKalkulaceMtZavView(archKalkulace.getKalkulace());
		model.addAttribute("mtZavodList", akMtZavView);

		return "/archivPredstavitel";
	}

	@RequestMapping("/predstavitel/list")
	public String archivPredstavitelList(Model model, ArchKalkulace archKalkulace, ArchKalkulaceMtZavView archKalkulaceMtZavView, HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t archivPredstavitelList(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + "  /  "
				+ archKalkulaceMtZavView.getId() + ")");

		ArchKalkulaceMtZavView akMtZavView = null;
		if (archKalkulaceMtZavView.getId() != null) {
			akMtZavView = serviceArchKalkuaceMtZavView.getArchKalkulaceMtZavViewId(archKalkulaceMtZavView.getId());
		} else if (!session.getAttribute("vybranaMt").toString().isEmpty()) {
			akMtZavView = serviceArchKalkuaceMtZavView.getArchKalkulaceMtZavView(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()), session.getAttribute("vybranaMt").toString(),
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
			HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivPredstavitelDetail(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + ", " + idPredstavitele + ")");

		ArchPredstavitel ap = serviceArchPredstavitel.getArchPredstavitelId(idPredstavitele);
		model.addAttribute("p", ap);

		List<ArchPredstavitelPr> prPodminky = serviceArchPredstavitelPr.getArchPredstavitelPr(idPredstavitele);
		model.addAttribute("prPodminky", prPodminky);

		return "/archivPredstavitelDetail";
	}
	
	@RequestMapping("/predstavitel/detailPr/{idPred}/{prCislo}")
	public String archivPredstavitelDetailPr(@PathVariable long idPred, @PathVariable String prCislo, PredstavitelPr predstavitelPr, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
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

	@RequestMapping("/dilVPredstavitelich")
	public String archivDilVPredstavitelich(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivDilVPredstavitelich()");
		session.setAttribute("pageTitle", "Archív - díl v představitelích");
		return "/archivDilVPredstavitelich";
	}

	// ###################################################################################################################################################################

	@RequestMapping("/kurzovniListek")
	public String archivKurzovniListek(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivKurzovniListek()");
		session.setAttribute("pageTitle", "Archív - kurzovní lístek");
		return "/archivKurzovniListek";
	}

}
