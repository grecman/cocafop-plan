package vwg.skoda.cocafopl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errorPage")
public class ErrorController {
	static Logger log = Logger.getLogger(MonitoringController.class);	
	
	@RequestMapping
	public String errorPage(Model model, HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		
		session.setAttribute("pageTitle", "Chybové hlášky");
		
		log.debug("###\t errorPage()");
		Object errorMesage = session.getAttribute("errorMesage");
		session.setAttribute("errorMesage", null);
		System.out.println(errorMesage);
		model.addAttribute("errorMsg", errorMesage);
		return "/errorPage";
	}

}
