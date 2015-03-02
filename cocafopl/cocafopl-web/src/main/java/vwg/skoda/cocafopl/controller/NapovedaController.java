package vwg.skoda.cocafopl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.service.UserService;

@Controller
public class NapovedaController {
	
	static Logger log = Logger.getLogger(NapovedaController.class);
	
	@Autowired
	private UserService serviceUser;
	
	@RequestMapping(value = "/napoveda")
	public String uvodniStranka(Model model, HttpServletRequest req, HttpSession session) {
		log.debug("###\t NapovedaUvodniZobrazeni()");
		
		session.setAttribute("pageTitle", "Nápověda");
		
		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} 
		
		return "/napoveda";
	}

}
