package vwg.skoda.cocafopl.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafopl.entity.Protokol;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.entity.UserZentaAdm;
import vwg.skoda.cocafopl.service.ProtokolService;
import vwg.skoda.cocafopl.service.UserService;
import vwg.skoda.cocafopl.service.UserZentaAdmService;

@Controller
public class IndexController {

	static Logger log = Logger.getLogger(NapovedaController.class);

	@Autowired
	private UserService serviceUser;

	@Autowired
	private UserZentaAdmService serviceUserZentaAdm;

	@Autowired
	private ProtokolService serviceProtokol;

	@RequestMapping(value = "/index.html")
	public String uvodniStranka(HttpServletRequest req, HttpSession session) throws UnknownHostException {
		log.debug("###\t IndexController()");

		// Ulozeni role do promenne, ktera je videt v cele sessione ... pak v
		// kteremkoliv JSP mohu vypsat stringovou hodnotu jako ${userRole} nebo
		// session.getAttribute v controlleru

		String role = null;

		if (req.isUserInRole("USERS")) {
			role = "USERS";
		}

		if (req.isUserInRole("READERS")) {
			if (role == null) {
				role = "READERS";
			} else
				role = role + ",READERS";
		}

		if (req.isUserInRole("ADMINS")) {
			if (role == null) {
				role = "ADMINS";
			} else
				role = role + ",ADMINS";
		}

		if (req.isUserInRole("APPROVERS")) {
			if (role == null) {
				role = "APPROVERS";
			} else
				role = role + ",APPROVERS";
		}

		if (req.isUserInRole("SERVICEDESK")) {
			if (role == null) {
				role = "SERVICEDESK";
			} else
				role = role + ",SERVICEDESK";
		}

		session.setAttribute("userRole", role.trim());
		session.setAttribute("serverName", InetAddress.getLocalHost().getCanonicalHostName());
		session.setAttribute("userName", req.getUserPrincipal().getName().toUpperCase());
		session.setAttribute("errorMesage", null);

		log.info("###\t ... for user: " + session.getAttribute("userName") + " (" + role + ") ... " + session.getAttribute("serverName"));

		// test prvniho connectu do db
		try {
			serviceUserZentaAdm.getUserZentaAdm(req.getUserPrincipal().getName().toUpperCase());
		} catch (Exception e) {
			log.error("###\t Nepodařil se connect do ZentaAdm.GZ09T52@_ZENTA (ověření existence uživatele), nejspíše nefungují databáze!", e);
			session.setAttribute("errorMesage", "Nepodařil se connect do ZentaAdm.GZ09T52@_ZENTA (ověření existence uživatele), nejspíše nefungují databáze nebo nejsou granty na uvedenou tabulku!");
			return "redirect:/srv/errorPage";
		}

		// test existence uzivatele
		UserZentaAdm userZentaAdm = serviceUserZentaAdm.getUserZentaAdm(req.getUserPrincipal().getName().toUpperCase());
		if (userZentaAdm == null || userZentaAdm.getNetUsername().isEmpty()) {
			log.error("###\t Přihlašovaný uživatel " + req.getUserPrincipal().getName().toUpperCase() + " nenalezen v db ZentaAdm.GZ09T52!");
			session.setAttribute("errorMesage", "Přihlašovaný uživatel " + req.getUserPrincipal().getName().toUpperCase() + " nenalezen v db ZentaAdm.GZ09T52!");
			return "redirect:/srv/errorPage";
		} else {
			log.trace("###\t ... přihlašovaný uživatel " + req.getUserPrincipal().getName().toUpperCase() + " již v aplikaci existuje.");
		}

		// zalozeni noveho uzivatele pokud jeste neexistuje v entite USER !!!
		if (!serviceUser.existUser(req.getUserPrincipal().getName().toUpperCase())) {
			log.debug("###\t Uzivatel " + req.getUserPrincipal().getName().toUpperCase() + " neexistuje v entite USER ... zakladam!!!");

			User newUser = new User();
			newUser.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newUser.setJmeno(userZentaAdm.getFirstName());
			newUser.setPrijmeni(userZentaAdm.getSurname());
			newUser.setOddeleni(userZentaAdm.getDepartment());
			newUser.setEmail(userZentaAdm.getEmail());
			newUser.setIdSkonet(userZentaAdm.getIdSkonet());
			newUser.setUserRole(role.trim());
			newUser.setUuser("IndexController");
			newUser.setUtime(new Date());
			serviceUser.addUser(newUser);

			Protokol newProtokol = new Protokol();
			newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newProtokol.setAction("Zalozeni usera");
			newProtokol.setInfo("Prvni prihlaseni: " + req.getUserPrincipal().getName().toUpperCase() + " ID_SKONET: " + userZentaAdm.getIdSkonet());
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);
		} else {
			User user = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());

			// kontrola zmeny uzivatelske role
			// System.out.println("session: "+session.getAttribute("userRole").toString().trim().length()+" - '"+session.getAttribute("userRole")+"'");
			// System.out.println("apl    : "+user.getUserRole().toString().trim().length()+" - '"+user.getUserRole()+"'");
			if (!session.getAttribute("userRole").equals(user.getUserRole())) {
				log.debug("###\t U prihlasovaneho uzivatele " + req.getUserPrincipal().getName().toUpperCase() + " se zmenila role z " + user.getUserRole() + " na "
						+ session.getAttribute("userRole").toString());
				String puvodniOpravneni = user.getUserRole().toString();
				user.setUserRole(session.getAttribute("userRole").toString().trim());
				user.setUtime(new Date());
				user.setUuser("IndexController");
				serviceUser.updateUser(user);

				Protokol newProtokol8 = new Protokol();
				newProtokol8.setNetusername(req.getUserPrincipal().getName().toUpperCase());
				newProtokol8.setAction("Zmena opravneni");
				newProtokol8.setInfo("Puvodni: " + puvodniOpravneni + "\t Nove: " + session.getAttribute("userRole").toString());
				newProtokol8.setTime(new Date());
				newProtokol8.setSessionid(req.getSession().getId());
				serviceProtokol.addProtokol(newProtokol8);

			}

			// kontrola ID Skonet
			if (user.getIdSkonet() != userZentaAdm.getIdSkonet()) {
				log.debug("###\t U prihlasovaneho uzivatele " + req.getUserPrincipal().getName().toUpperCase() + " se v ZentaAdm zmenilo ID_SKONET z " + user.getIdSkonet() + " -> "
						+ userZentaAdm.getIdSkonet() + "(nejspise zmena mista)");
				// otazka co ted udelat !!!!
				// pokud bychom puvodniho uzivatele smazali, tak smazeme i veskere podrizene objekty a treba u apl.PRCEK je to vlastne uplne vsechno :)
				// tady to mozna vadit nebude, pokud na entitu User budou navazany pouze Mimoradne Vybavy :)

				// Mazani USERa
				Protokol newProtokol = new Protokol();
				newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
				newProtokol.setAction("Mazani USERa");
				newProtokol.setInfo("User: " + req.getUserPrincipal().getName().toUpperCase() + " ma v ZentaAdm novy ID_SKONET: " + user.getIdSkonet() + " (" + user.getOddeleni() + ") -> "
						+ userZentaAdm.getIdSkonet() + "(" + userZentaAdm.getDepartment() + ")");
				newProtokol.setTime(new Date());
				newProtokol.setSessionid(req.getSession().getId());
				serviceProtokol.addProtokol(newProtokol);
				serviceUser.removeUser(user);

				User newUser = new User();
				newUser.setNetusername(req.getUserPrincipal().getName().toUpperCase());
				newUser.setJmeno(userZentaAdm.getFirstName());
				newUser.setPrijmeni(userZentaAdm.getSurname());
				newUser.setOddeleni(userZentaAdm.getDepartment());
				newUser.setEmail(userZentaAdm.getEmail());
				newUser.setIdSkonet(userZentaAdm.getIdSkonet());
				newUser.setUserRole(role.trim());
				newUser.setUuser("IndexController");
				newUser.setUtime(new Date());
				serviceUser.addUser(newUser);

				Protokol newProtokol2 = new Protokol();
				newProtokol2.setNetusername(req.getUserPrincipal().getName().toUpperCase());
				newProtokol2.setAction("Zalozeni usera");
				newProtokol2.setInfo("Prvni prihlaseni: " + req.getUserPrincipal().getName().toUpperCase() + " ID_SKONET: " + userZentaAdm.getIdSkonet());
				newProtokol2.setTime(new Date());
				newProtokol2.setSessionid(req.getSession().getId());
				serviceProtokol.addProtokol(newProtokol2);
			}
		}

		Protokol newProtokol = new Protokol();
		newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
		newProtokol.setAction("Login do aplikace");
		newProtokol.setInfo(session.getAttribute("serverName") + " - " + req.getSession().getServletContext().getServerInfo());
		newProtokol.setTime(new Date());
		newProtokol.setSessionid(req.getSession().getId());
		serviceProtokol.addProtokol(newProtokol);

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} else if (aktualUser.getUserRole().equals("ADMINS".trim())) {
			log.debug("###\t Uzivatel s roli ADMINS ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/logging";
		}

		// inicializace sessinovych promennych (abych nemusel resit NULLy a mohl se ptat jen na isEmpty)
		session.setAttribute("kalkulaceRRRRMM", "");
		session.setAttribute("archKalkulaceRRRRMM", "");
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		return "redirect:/srv/archiv/kusovnik";
	}

}
