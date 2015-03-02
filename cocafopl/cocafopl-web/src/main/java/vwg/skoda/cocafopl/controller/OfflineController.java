package vwg.skoda.cocafopl.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.obj.OfflineObj;
import vwg.skoda.cocafopl.service.UserService;

@Controller
@RequestMapping("/offline")
public class OfflineController {

	static Logger log = Logger.getLogger(MonitoringController.class);
	
	@Autowired
	private UserService serviceUser;

	@RequestMapping
	public String offline(Model model, HttpSession session, HttpServletRequest req, HttpServletResponse res) throws SQLException, UnknownHostException {
		log.debug("###\t offline()");
		session.setAttribute("pageTitle", "Offline zpracování");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		}  

		ArrayList<OfflineObj> offlineObj = new ArrayList<OfflineObj>();
		
		OfflineObj g2 = new OfflineObj();
		g2.setUser(req.getUserPrincipal().getName().toUpperCase());
		g2.setCasZadani(new Date());
		g2.setCasSpusteni(null);
		g2.setCasUkonceni(null);
		g2.setPopis("Komunikace FAVAS pro MT: 5J");
		g2.setStatus("Ve vrontě");
		offlineObj.add(g2);

		OfflineObj g = new OfflineObj();
		g.setUser(req.getUserPrincipal().getName().toUpperCase());
		g.setCasZadani(new Date());
		g.setCasSpusteni(new Date());
		g.setCasUkonceni(null);
		g.setPopis("Komunikace FAVAS pro MT: 5E, KG");
		g.setStatus("V procesu");
		offlineObj.add(g);
	
		OfflineObj g1 = new OfflineObj();
		g1.setUser(req.getUserPrincipal().getName().toUpperCase());
		g1.setCasZadani(new Date());
		g1.setCasSpusteni(new Date());
		g1.setCasUkonceni(new Date());
		g1.setPopis("Komunikace FAVAS pro MT: 3T");
		g1.setStatus("HOTOVO");
		offlineObj.add(g1);

		model.addAttribute("off", offlineObj);

		return "/offline";
	}

}
