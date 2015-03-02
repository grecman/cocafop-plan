package vwg.skoda.cocafopl.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.service.UserService;

@Controller
@RequestMapping("/archiv")
public class ArchivController {
	
	static Logger log = Logger.getLogger(PredstavitelController.class);
	
	@Autowired
	private UserService serviceUser;
	
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
		
		session.setAttribute("pageTitle", "Archív - Kusy na provedení");
		
		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} else if (aktualUser.getUserRole().equals("ADMINS".trim())){
			log.debug("###\t Uzivatel s roli ADMINS ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/logging";
		}

		return "/archivKusyNaProvedeni";
	}

}
