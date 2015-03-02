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

import vwg.skoda.cocafopl.entity.MvPopis;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.service.MvPopisService;
import vwg.skoda.cocafopl.service.UserService;

@Controller
@RequestMapping("/mv")
public class MvController {

	static Logger log = Logger.getLogger(MvController.class);
	
	@Autowired
	private MvPopisService serviceMvPopis;
	
	@Autowired
	private UserService serviceUser;

	@RequestMapping("/uvodniZobrazeni")
	public String uvodniZobrazeni(MvPopis mvPop, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t uvodniZobrazeni()");

		session.setAttribute("pageTitle", "Mimořádná výbava");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} else if (aktualUser.getUserRole().equals("ADMINS".trim())){
			log.debug("###\t Uzivatel s roli ADMINS ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/logging";
		}
		
		mvPop.setKodZeme(null);
		mvPop.setModelovyKlic(null);
		mvPop.setDatumKomunikace(null);
		
		return "/mv";
	}
	
	@RequestMapping("/showMv")
	public String showMv(MvPopis mvPop, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t showMv()");
		//SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		//log.debug("###\t\t "+sdf.format(mvPop.getDatumKomunikace())+"\t"+mvPop.getModelovyKlic()+"\t"+mvPop.getMrok()+"\t"+mvPop.getKodZeme());
		log.debug("###\t\t typ vozu: "+mvPop.getTypVozu()+"\t"+mvPop.getVybavy());
		
		//VehicleOrder o = new VehicleOrder();

		//String kodZeme = "X7X";
		//String modelovyKlic = "5422G4";
		//Integer modelovyRok = 2015;
		//String vybavy = "PK4PFCQI1";

		//Calendar datum = new GregorianCalendar(2014, 7, 10);
		//System.out.println(sdf.format(datum.getTime()));
//		Calendar datum = Calendar.getInstance();
//		datum.setTime(mvPop.getDatumKomunikace());
//		
//		o.setDate(datum);
//		o.setNationalSalesProgramme(mvPop.getKodZeme());
//		o.setModelCode(mvPop.getModelovyKlic());
//		o.setModelYear(mvPop.getMrok());
//		o.setEquipment(mvPop.getVybavy());
		
		// o.setDate(datum);
		// o.setNationalSalesProgramme(kodZeme);
		// o.setModelCode(modelovyKlic);
		// o.setModelYear(modelovyRok);
/*		
		Favas favas = o.getFavas();

		Set<Equipment> equipment = favas.getEquipment();
		if (equipment.size() > 0) {
			System.out.println("### equipment: ");
			for (Equipment eq : equipment) {
				System.out.print(eq.getFamily() + "-" + eq.getType() + "-" + eq.getValue() + "\t");
			}
			System.out.println();
		}

		Set<vwg.skoda.favas.mbv.Message> message = favas.getMessage();
		if (message.size() > 0) {
			for (Message me : message) {
				System.out.println("### message\t" + me.getCode() + "\t" + me.getText());
			}
		}

		Set<vwg.skoda.favas.mbv.Error> errorFavas = favas.getError();
		if (errorFavas.size() > 0) {
			for (vwg.skoda.favas.mbv.Error er : errorFavas) {
				log.error("### errorFavas\t" + er.getErrNo() + ", " + er.getSource() + ", " + er.getText() + ", " + er.getWeight() + ", " + er.getType().toString());
			}
		}

		for (Entry<String, String[]> e: favas.getPacketInfo().entrySet()) {
			for (String pr: e.getValue()) {
				System.out.println("### packetInfo\t"+e.getKey() + " - " + pr);
			}
		}

//		 String[] packStruk = favas.getPacketStruc("PK4");
//		 for (int i = 0; i < packStruk.length; i++) {
//		 System.out.println("### packet struk\t" + packStruk[i]);
//		 }

		model.addAttribute("eq", favas.getEquipment());
		model.addAttribute("er", favas.getError());
		model.addAttribute("message", favas.getMessage());
		model.addAttribute("packetInfo", favas.getPacketInfo());
*/
		return "/mv";
	}

}
