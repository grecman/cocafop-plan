package vwg.skoda.cocafopl.controller;

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

import vwg.skoda.cocafopl.entity.Napoveda;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.service.NapovedaService;
import vwg.skoda.cocafopl.service.UserService;

@Controller
@RequestMapping("/napoveda")
public class NapovedaController {
	
	static Logger log = Logger.getLogger(NapovedaController.class);
	
	@Autowired
	private UserService serviceUser;
	
	@Autowired
	private NapovedaService serviceNapoveda;
	
	@RequestMapping
	public String uvodniStranka(Napoveda napoveda, Model model, HttpServletRequest req, HttpSession session) {
		log.debug("###\t NapovedaUvodniZobrazeni()");
		
		session.setAttribute("pageTitle", "Nápověda");
		
		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} 
		
		if (aktualUser.getUserRole().contains("ADMINS")) {
			model.addAttribute("adminRole", true);
		} else {
			model.addAttribute("adminRole", false);
		}
		
		List<Napoveda> napovedaList = serviceNapoveda.getNapoveda();
		model.addAttribute("napovedaList", napovedaList);
		
		return "/napoveda";
	}
	
	@RequestMapping(value = "/nova")
	public String novaNapoveda(Napoveda napoveda, Model model, HttpServletRequest req) {
		log.debug("###\t novaNapoveda(" + napoveda.getTema() + ")");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		model.addAttribute("aktualUser", aktualUser);

		Napoveda newNap = new Napoveda();
		newNap.setTema(napoveda.getTema());
		newNap.setPopis(napoveda.getPopis());
		newNap.setPoradi(napoveda.getPoradi());
		newNap.setUuser(aktualUser.getNetusername());
		newNap.setUtime(new Date());
		serviceNapoveda.addNapoveda(newNap);

		List<Napoveda> n = serviceNapoveda.getNapoveda();
		model.addAttribute("napovedaList", n);

		return "redirect:/srv/napoveda";
	}

	@RequestMapping(value = "/smazat/{idNapoveda}")
	public String smazatNapovedu(@PathVariable long idNapoveda, Napoveda napoveda, Model model, HttpServletRequest req) {
		log.debug("###\t smazatNapovedu(" + idNapoveda + ")");

		serviceNapoveda.removeNapoveda(idNapoveda);
		return "redirect:/srv/napoveda";
	}

}
