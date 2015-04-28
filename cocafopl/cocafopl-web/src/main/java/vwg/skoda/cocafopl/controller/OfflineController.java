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

import vwg.skoda.cocafopl.entity.Offline;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.service.OfflineService;
import vwg.skoda.cocafopl.service.UserService;

@Controller
@RequestMapping("/offline")
public class OfflineController {

	static Logger log = Logger.getLogger(MonitoringController.class);
	
	@Autowired
	private UserService serviceUser;
	
	@Autowired
	private OfflineService serviceOffline;

	@RequestMapping
	public String offline(Model model, HttpSession session, HttpServletRequest req, HttpServletResponse res) throws SQLException, UnknownHostException {
		log.debug("###\t offline()");
		session.setAttribute("pageTitle", "Offline zpracování");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		}  

		List<Offline> offlineList = serviceOffline.getOffline();
		model.addAttribute("off", offlineList);

		return "/offline";
	}

}
