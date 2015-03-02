package vwg.skoda.cocafopl.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafopl.entity.Protokol;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.service.ProtokolService;
import vwg.skoda.cocafopl.service.UserService;

@Controller
@RequestMapping("/monitoring")
public class MonitoringController {
	
	static Logger log = Logger.getLogger(MonitoringController.class);	
	
	@Autowired
	private ProtokolService serviceProtokol;
	
	@Autowired
	private UserService serviceUser;

	
	@RequestMapping("/serviceDesk")
	public String monitoringServiceDesk(Model model, HttpSession session, HttpServletRequest req, HttpServletResponse res) throws SQLException, UnknownHostException {
		log.debug("###\t monitoringServiceDesk()");
		session.setAttribute("pageTitle", "Monitoring - service desk");
		//model.addAttribute("server", session.getAttribute("serverName"));
		//model.addAttribute("userName", session.getAttribute("serverName"));
		model.addAttribute("ip", InetAddress.getLocalHost().getHostAddress());
		
		try {
			User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());

			model.addAttribute("aktualUser", aktualUser);
			model.addAttribute("userRole",aktualUser.getUserRole());

			List<Protokol> userLogin = serviceProtokol.getUserLogin(req.getUserPrincipal().getName().toUpperCase());
			model.addAttribute("userLogin", userLogin.size());
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			// userLogin je seznam loginu daneho uzivatele serazeny dle casu prihlaseni DESC! 0 je ten nejaktualnejsi login a 1 je tedy ten druhy.
			model.addAttribute("userLogin", userLogin.size());
			
			if (userLogin.size() <= 1){
				// pripad, kdy je uzivatel poprve v aplikace s hned si klikne na monitoring
				model.addAttribute("lastUserLogin", DATE_FORMAT.format(new Date()));
			} else {
				model.addAttribute("lastUserLogin", DATE_FORMAT.format(userLogin.get(1).getTime()));	
			}

			List<Protokol> allUserLogin = serviceProtokol.getAllLogin();
			model.addAttribute("allUserLogin", allUserLogin.size());
			
			Set<String> netusernames = new TreeSet<String>();
			for (Protokol p : allUserLogin) {
				netusernames.add(p.getNetusername());
			}
			model.addAttribute("allUsers", netusernames.size());

			model.addAttribute("db", serviceProtokol.getDbName());
		} catch (Exception e) {
			log.error("###\t Chyby pri ziskavani dat z databaze", e);
			model.addAttribute("db", "Databaze nezjistena");
		}
		

		return "/monitoringServiceDesk";
	}
	
	@RequestMapping("/logging")
	public String monitoringLogging(Model model, HttpSession session, HttpServletRequest req, HttpServletResponse res) throws SQLException, UnknownHostException {
		log.debug("###\t monitoringLogging()");
		session.setAttribute("pageTitle", "Monitoring - logging");
		
		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().contains("ADMINS")) {
			List<Protokol> protokol = serviceProtokol.getAll();
			model.addAttribute("protokol", protokol);
		} else {
			List<Protokol> protokol = serviceProtokol.getAllUserActivity(req.getUserPrincipal().getName().toUpperCase());
			model.addAttribute("protokol", protokol);
		}

		return "/monitoringLogging";
	}

}
