package vwg.skoda.cocafopl.controller;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vwg.skoda.cocafopl.entity.ArchKalkulace;
import vwg.skoda.cocafopl.entity.ArchKalkulaceMtZavView;
import vwg.skoda.cocafopl.entity.ArchMv;
import vwg.skoda.cocafopl.entity.MtProd;
import vwg.skoda.cocafopl.entity.MvPopis;
import vwg.skoda.cocafopl.entity.MvPopisMessage;
import vwg.skoda.cocafopl.entity.Offline;
import vwg.skoda.cocafopl.entity.PrView;
import vwg.skoda.cocafopl.entity.PredstavitelPr;
import vwg.skoda.cocafopl.entity.User;
import vwg.skoda.cocafopl.obj.UniObj;
import vwg.skoda.cocafopl.service.ArchKalkulaceMtZavViewService;
import vwg.skoda.cocafopl.service.ArchKalkulaceService;
import vwg.skoda.cocafopl.service.ArchMvService;
import vwg.skoda.cocafopl.service.MtProdService;
import vwg.skoda.cocafopl.service.MtService;
import vwg.skoda.cocafopl.service.MvPopisMessageService;
import vwg.skoda.cocafopl.service.MvPopisPrService;
import vwg.skoda.cocafopl.service.MvPopisService;
import vwg.skoda.cocafopl.service.OfflineService;
import vwg.skoda.cocafopl.service.PrViewService;
import vwg.skoda.cocafopl.service.UserService;

@Controller
@RequestMapping("/mv")
public class MvController {

	static Logger log = Logger.getLogger(MvController.class);

	@Autowired
	private MvPopisService serviceMvPopis;

	@Autowired
	private UserService serviceUser;

	@Autowired
	private MtService serviceMt;

	@Autowired
	private MtProdService serviceMtProd;

	@Autowired
	private MvPopisPrService serviceMvPopisPr;

	@Autowired
	private MvPopisMessageService serviceMvPopisMessage;

	@Autowired
	private PrViewService servicePrView;

	@Autowired
	private ArchKalkulaceService serviceArchKalkulace;

	@Autowired
	private ArchMvService serviceArchMv;

	@Autowired
	private ArchKalkulaceMtZavViewService serviceArchKalkulaceMtZavView;

	@Autowired
	private OfflineService serviceOffline;

	@RequestMapping("/kalkulace")
	public String mvVyberKalkulaci(UniObj uniObj, ArchKalkulace archKalkulace, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		log.debug("###\t mvVyberKalkulaci()");

		session.setAttribute("pageTitle", "Mimořádná výbava");

		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		if (aktualUser.getUserRole().equals("SERVICEDESK".trim())) {
			log.debug("###\t Uzivatel s roli SERVICEDESK ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/serviceDesk";
		} else if (aktualUser.getUserRole().equals("ADMINS".trim())) {
			log.debug("###\t Uzivatel s roli ADMINS ->- presmerovavam na prislusnou stranku.");
			return "redirect:/srv/monitoring/logging";
		}

		List<MvPopis> mvPopis = serviceMvPopis.getMvPopis(aktualUser.getNetusername());
		if (mvPopis.size() > 0) {
			session.setAttribute("archKalkulaceRRRRMM", mvPopis.get(0).getKalkulace());
			session.setAttribute("vybranaMt", mvPopis.get(0).getModelovaTrida());
			session.setAttribute("vybranyZavod", mvPopis.get(0).getZavod());
			return "redirect:/srv/mv/listMv";
		}

		session.setAttribute("archKalkulaceRRRRMM", "");
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulace> ak = serviceArchKalkulace.getArchKalkulaceAllSchvalene();
		model.addAttribute("archKalkulaceList", ak);

		return "/mv";
	}

	@RequestMapping("/mtZav")
	public String mvVyberMtZav(UniObj uniObj, ArchKalkulace archKalkulace, ArchKalkulaceMtZavView archKalkulaceMtZavView, Model model, HttpServletRequest req, HttpServletResponse res,
			HttpSession session) {
		log.debug("###\t mvVyberMtZav(" + archKalkulace.getKalkulace() + " / " + session.getAttribute("archKalkulaceRRRRMM") + ")");

		if (session.getAttribute("archKalkulaceRRRRMM").toString().isEmpty()) {
			session.setAttribute("archKalkulaceRRRRMM", archKalkulace.getKalkulace());
		}
		session.setAttribute("vybranaMt", "");
		session.setAttribute("vybranyZavod", "");

		List<ArchKalkulaceMtZavView> akMtZavView = serviceArchKalkulaceMtZavView.getArchKalkulaceMtZavView(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()));
		model.addAttribute("mtZavodList", akMtZavView);

		return "/mv";
	}

	@RequestMapping("/param")
	public String mvZadejParametry(UniObj uniObj, ArchKalkulaceMtZavView archKalkulaceMtZavView, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		log.debug("###\t mvZadejParametry(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + "  /  "
				+ archKalkulaceMtZavView.getIdPom() + ")");

		ArchKalkulaceMtZavView akMtZavView = null;
		if (archKalkulaceMtZavView.getIdPom() != null) {
			akMtZavView = serviceArchKalkulaceMtZavView.getArchKalkulaceMtZavViewId(archKalkulaceMtZavView.getIdPom());
		} else if (!session.getAttribute("vybranaMt").toString().isEmpty()) {
			akMtZavView = serviceArchKalkulaceMtZavView.getArchKalkulaceMtZavView(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()),
					session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());
		}

		session.setAttribute("vybranaMt", akMtZavView.getModelTr());
		session.setAttribute("vybranyZavod", akMtZavView.getZavod());

		return "/mv";
	}

	@RequestMapping("/saveMv")
	public String saveMv(UniObj uniObj, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws ParseException, InterruptedException {
		log.debug("###\t saveMv(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ", "
				+ uniObj.getModelovyKlic() + ", " + uniObj.getRok() + ", " + uniObj.getKodZeme() + ", " + uniObj.getPopis() + ", " + uniObj.getVybavyZaklad() + ", " + uniObj.getVybavyVybav() + ")");

		User user = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		MtProd mt = serviceMtProd.getMtProd(session.getAttribute("vybranaMt").toString());
		ArchKalkulace ak = serviceArchKalkulace.getArchKalkulaceId(Integer.valueOf(session.getAttribute("archKalkulaceRRRRMM").toString()));

		MvPopis mvPopZakl = new MvPopis();
		mvPopZakl.setKalkulace(ak.getKalkulace());
		mvPopZakl.setKalkulacniDatum(ak.getKalkulacniDatum());
		mvPopZakl.setKodZeme(uniObj.getKodZeme().toUpperCase());
		mvPopZakl.setModelovaTrida(mt.getModelovaTrida());
		mvPopZakl.setModelovyKlic(mt.getModelovaTrida() + uniObj.getModelovyKlic().toUpperCase());
		mvPopZakl.setMrok(Integer.valueOf(uniObj.getRok()));
		mvPopZakl.setPopis(uniObj.getPopis());
		mvPopZakl.setProdukt(mt.getProdukt());
		mvPopZakl.setZavod(session.getAttribute("vybranyZavod").toString());
		mvPopZakl.setTypVozu("Z");
		mvPopZakl.setVybavy(uniObj.getVybavyZaklad().toUpperCase());
		mvPopZakl.setUuser(req.getUserPrincipal().getName().toUpperCase());
		mvPopZakl.setUtime(new Date());
		mvPopZakl.setGz39tUser(user);
		serviceMvPopis.addMvPopis(mvPopZakl);

		MvPopis mvPopVyb = new MvPopis();
		mvPopVyb.setKalkulace(ak.getKalkulace());
		mvPopVyb.setKalkulacniDatum(ak.getKalkulacniDatum());
		mvPopVyb.setKodZeme(uniObj.getKodZeme().toUpperCase());
		mvPopVyb.setModelovaTrida(mt.getModelovaTrida());
		mvPopVyb.setModelovyKlic(mt.getModelovaTrida() + uniObj.getModelovyKlic().toUpperCase());
		mvPopVyb.setMrok(Integer.valueOf(uniObj.getRok()));
		mvPopVyb.setPopis(uniObj.getPopis());
		mvPopVyb.setProdukt(mt.getProdukt());
		mvPopVyb.setZavod(session.getAttribute("vybranyZavod").toString());
		mvPopVyb.setTypVozu("V");
		mvPopVyb.setVybavy(uniObj.getVybavyVybav().toUpperCase());
		mvPopVyb.setUuser(req.getUserPrincipal().getName().toUpperCase());
		mvPopVyb.setUtime(new Date());
		mvPopVyb.setGz39tUser(user);
		serviceMvPopis.addMvPopis(mvPopVyb);

		return "redirect:/srv/komunikaceFavas/mv";
	}

	@RequestMapping("/listMv")
	public String mvList(UniObj uniObj, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws SQLException {
		log.debug("###\t mvList(" + session.getAttribute("archKalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");

		List<ArchKalkulace> ak = serviceArchKalkulace.getArchKalkulaceAllSchvalene();
		model.addAttribute("kalkulaceArch", ak);

		User user = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		List<MvPopis> mvPopis = serviceMvPopis.getMvPopis(user.getNetusername());
		for (MvPopis gre : mvPopis) {
			if (gre.getTypVozu().startsWith("Z")) {
				model.addAttribute("mvZaklad", gre);
				model.addAttribute("mvZakladPr", serviceMvPopisPr.getMvPopisPr(gre.getId()));
				model.addAttribute("mvZakladMessage", serviceMvPopisMessage.getMvPopisMessageWithoutException(gre.getId()));

				List<MvPopisMessage> prMessageException = serviceMvPopisMessage.getMvPopisMessageOnlyException(gre.getId());
				String prMesEx = null;
				for (MvPopisMessage pmes : prMessageException) {
					prMesEx = (prMesEx == null ? "" : prMesEx + "\t\n") + pmes.getKod() + "-" + pmes.getText() + "\t\n";
				}
				model.addAttribute("mvZakladMessageVyjimky", prMesEx);
			}

			if (gre.getTypVozu().startsWith("V")) {
				model.addAttribute("mvVybav", gre);
				model.addAttribute("mvVybavPr", serviceMvPopisPr.getMvPopisPr(gre.getId()));
				model.addAttribute("mvVybavMessage", serviceMvPopisMessage.getMvPopisMessageWithoutException(gre.getId()));

				List<MvPopisMessage> prMessageException = serviceMvPopisMessage.getMvPopisMessageOnlyException(gre.getId());
				String prMesEx = null;
				for (MvPopisMessage pmes : prMessageException) {
					prMesEx = (prMesEx == null ? "" : prMesEx + "\t\n") + pmes.getKod() + "-" + pmes.getText() + "\t\n";
				}
				model.addAttribute("mvVybavMessageVyjimky", prMesEx);
			}
		}
		
		User aktualUser = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		List<String> rozdilneRodiny = serviceMvPopisPr.getMvPopisPrRozdilneRodiny(aktualUser.getNetusername());
		String gre = rozdilneRodiny.toString().replace("[", "").replace("]", "").replace(" ", "");
		model.addAttribute("rozdilneRodiny", gre);

		return "/mv";
	}

	@RequestMapping("/smazatMv")
	public String smazatMv(UniObj uniObj, Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		log.debug("###\t smazatMv()");

		User user = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		List<MvPopis> mvKeSmazaniBezPr = serviceMvPopis.getMvPopis(user.getNetusername());
		for (MvPopis mvPopix : mvKeSmazaniBezPr) {
			serviceMvPopis.removeMvPopis(mvPopix);
		}

		return "redirect:/srv/mv/kalkulace";
	}

	@RequestMapping("/detailPr/{idMvPopis}/{prCislo}")
	public String predstavitelDetailPr(@PathVariable long idMvPopis, @PathVariable String prCislo, PredstavitelPr predstavitelPr, Model model, HttpServletRequest req, HttpSession session)
			throws SQLException, UnknownHostException {
		log.debug("###\t predstavitelDetailPr(" + idMvPopis + ", " + prCislo + ")");

		// PredstavitelKalkulace pk = servicePredstavitelKalkulace.getPredstavitelKalkulaceId(idPredKalk);
		MvPopis mv = serviceMvPopis.getMvPopis(idMvPopis);
		MtProd mtProd = serviceMtProd.getMtProd(mv.getModelovaTrida());
		model.addAttribute("mtProd", mtProd);

		PrView prOne = servicePrView.getPrView(mv.getProdukt(), prCislo);
		model.addAttribute("prOne", prOne);

		List<PrView> prList = servicePrView.getPrViews(mv.getProdukt(), prOne.getFamkz());
		model.addAttribute("prList", prList);

		return "/predstavitelDetailPrPopup";
	}

	@RequestMapping("/vypocet")
	public String mvVypocet(Model model, HttpServletRequest req, HttpSession session) throws SQLException, UnknownHostException {
		log.debug("###\t mvVypocet("+session.getAttribute("archKalkulaceRRRRMM") + ", " + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod")+")");

		User user = serviceUser.getUser(req.getUserPrincipal().getName().toUpperCase());
		List<MvPopis> mvPopis = serviceMvPopis.getMvPopis(user.getNetusername());
		ArchMv amv = new ArchMv();

		Long idZaklVozu = 0L;
		
		for (MvPopis gre : mvPopis) {
			if (gre.getTypVozu().startsWith("Z")) {
				amv.setKalkulace(gre.getKalkulace());
				amv.setMk(gre.getModelovyKlic());
				amv.setModelTr(gre.getModelovaTrida());
				amv.setProdukt(gre.getProdukt());
				amv.setUsername(user.getNetusername());
				amv.setZavod(gre.getZavod());
				amv.setKalkulacniDatum(gre.getKalkulacniDatum());
				amv.setMrok(gre.getMrok());
				amv.setKodZeme(gre.getKodZeme());
				amv.setJmeno(user.getJmeno());
				amv.setPrijmeni(user.getPrijmeni());
				amv.setOddeleni(user.getOddeleni());
				amv.setPopis(gre.getPopis());
				amv.setVybavyZv(gre.getVybavy());
				amv.setIdZv(gre.getId());
				amv.setPrpozZv(serviceMvPopisPr.getMvPopisPrNudle(gre.getId()));
				amv.setUtime(new Date());
				idZaklVozu = gre.getId();
			}
			if (gre.getTypVozu().startsWith("V")) {
				amv.setIdVv(gre.getId());
				amv.setVybavyVv(gre.getVybavy());
				amv.setPrpozVv(serviceMvPopisPr.getMvPopisPrNudle(gre.getId()));
			}
		}
		serviceArchMv.setArchMv(amv);

		Offline off = new Offline();
		off.setUzivatel(user.getNetusername());
		off.setAgenda("MV - výpočet");
		off.setCasZadani(new Date());
		off.setStatus("Nový");
		off.setParametr(idZaklVozu.toString());
		off.setPopis(mvPopis.get(0).getKalkulacniDatum()+", "+mvPopis.get(0).getModelovyKlic()+", "+mvPopis.get(0).getKodZeme()+", "+mvPopis.get(0).getMrok() + ", " + mvPopis.get(0).getPopis());
		serviceOffline.addOffline(off);

		return "redirect:/srv/offline";
	}

}
