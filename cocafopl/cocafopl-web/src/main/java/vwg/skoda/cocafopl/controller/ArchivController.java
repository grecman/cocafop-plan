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
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafopl.entity.ArchKalkulace;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.service.ArchKalkulaceService;
import vwg.skoda.cocafopl.service.UserService;

@Controller
@RequestMapping("/archiv")
public class ArchivController {
	
	static Logger log = Logger.getLogger(PredstavitelController.class);
	
	@Autowired
	private UserService serviceUser;
	
	@Autowired
	private ArchKalkulaceService serviceArchKalkuace;
	
	@RequestMapping("/kalkulace")
	public String archivKalkulace(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivKalkulace()");
		session.setAttribute("pageTitle", "Archív - kalkulace");
		
		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} else if (aktualUser.getUserRole().equals("ADMINS".trim())){
			log.debug("###\t Uzivatel s roli ADMINS ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/logging";
		}
		
		List<ArchKalkulace> ak = serviceArchKalkuace.getArchKalkulaceAll();
		// TODO: ... toto asi nepujde, pac zase potrebuji mit v jednom rakdu seznam MT-zavod ... a nejlepe tady po jednotlivych letech!
		model.addAttribute("archKalkulace", ak);
		
		return "/archivKalkulace";
	}
	
	@RequestMapping("/kusovnik")
	public String archivKusovnik(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivKusovnik()");
		session.setAttribute("pageTitle", "Archív - kusovník");
		return "/archivKusovnik";
	}
	
	@RequestMapping("/cenik")
	public String archivCenik(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivCenik()");
		session.setAttribute("pageTitle", "Archív - ceník");
		return "/archivCenik";
	}
	
	@RequestMapping("/kusyNaProvedeni")
	public String archivKusyNaProvedeni(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivKusyNaProvedeni()");
		session.setAttribute("pageTitle", "Archív - kusy na provedení");
		return "/archivKusyNaProvedeni";
	}

	@RequestMapping("/predstavitel")
	public String archivPredstavitel(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivPredstavitel()");
		session.setAttribute("pageTitle", "Archív - představitel");
		return "/archivPredstavitel";
	}

	@RequestMapping("/dilVPredstavitelich")
	public String archivDilVPredstavitelich(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivDilVPredstavitelich()");
		session.setAttribute("pageTitle", "Archív - díl v představitelích");
		return "/archivDilVPredstavitelich";
	}
	
	@RequestMapping("/kurzovniListek")
	public String archivKurzovniListek(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t archivKurzovniListek()");
		session.setAttribute("pageTitle", "Archív - kurzovní lístek");
		return "/archivKurzovniListek";
	}

	
}
