package vwg.skoda.cocafopl.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import vwg.skoda.cocafopl.entity.Mt;
import vwg.skoda.cocafopl.entity.Predstavitel;
import vwg.skoda.cocafopl.entity.Protokol;
import vwg.skoda.cocafopl.obj.UniObj;
import vwg.skoda.cocafopl.service.MtService;
import vwg.skoda.cocafopl.service.PredstavitelService;
import vwg.skoda.cocafopl.service.ProtokolService;

@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

	static Logger log = Logger.getLogger(FileUploadController.class);

	@Autowired
	private MtService serviceMt;

	@Autowired
	private ProtokolService serviceProtokol;

	@Autowired
	private PredstavitelService servicePredstavitel;

	@RequestMapping(value = "/importCsv")
	public String importCsv(HttpSession session, Model model) {
		log.debug("###\t importCsv(" + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");
		session.setAttribute("importovanyFile", "");
		model.addAttribute("errorMsg", "");
		return "/predstavitelDefiniceImport";
	}

	@RequestMapping(value = "/checkFile")
	public String saveFile(final MultipartHttpServletRequest req, HttpSession session, Model model) throws IOException {
		log.debug("###\t checkFile(" + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");

		MultipartFile file = (MultipartFile) req.getFile("filePredstaviteleCocafoppl");
		log.debug("###\t\t Nacteny file: " + file.getOriginalFilename() + "(" + file.getSize() + " bytes)");

		model.addAttribute("errorMsg", "");
		Mt mt = serviceMt.getMt(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		String radek;
		List<UniObj> uniObjList = new ArrayList<UniObj>();
		int cisloPredstavitele = 1;
		int pocetRadku = 0;
		LineNumberReader row = new LineNumberReader(new InputStreamReader(file.getInputStream()));
		while ((radek = row.readLine()) != null) {
			pocetRadku++;
			String[] items = radek.split(";");
			List<String> itemList = new ArrayList<String>(Arrays.asList(items));
			// System.out.println(pocetRadku+"\t"+radek+"\t"+ itemList.get(0));
			if (pocetRadku == 1 && !itemList.get(0).startsWith("CODE")) {
				model.addAttribute("errorMsg", radek);
				break;
			}
			if (!itemList.get(0).startsWith("CODE")) {
				UniObj r = new UniObj();
				r.setModelovyKlic(itemList.get(0));
				r.setTyp(itemList.get(1));
				r.setVybava(itemList.get(2));
				r.setObsah(itemList.get(3));
				r.setVykon(itemList.get(4));
				r.setRozlozenost(itemList.get(5));
				r.setCetnost(Integer.valueOf(itemList.get(6)));
				r.setCisloPred(cisloPredstavitele++);
				r.setComix(true);
				r.setPlatnostOd(Integer.valueOf(sdf.format(new Date()) + "01"));
				r.setPlatnostDo(999912);
				r.setKodZeme(mt.getKodZeme());
				uniObjList.add(r);
			}
		}

		session.setAttribute("importovanyFile", uniObjList);
		model.addAttribute("uniObjList", uniObjList);

		return "/predstavitelDefiniceImport";
	}

	@RequestMapping(value = "/saveFile")
	public String saveFile(HttpSession session, HttpServletRequest req) {
		log.debug("###\t saveFile(" + session.getAttribute("vybranaMt") + "-" + session.getAttribute("vybranyZavod") + ")");

		Mt mt = serviceMt.getMt(session.getAttribute("vybranaMt").toString(), session.getAttribute("vybranyZavod").toString());

		@SuppressWarnings("unchecked")
		List<UniObj> predstavitelNovy = (List<UniObj>) session.getAttribute("importovanyFile");
		if (predstavitelNovy.size() == 0 || predstavitelNovy.isEmpty() || null == predstavitelNovy) {
			log.debug("###\t\t ... není co zpracovávat.");
		} else {

			List<Predstavitel> predstavitelPuv = servicePredstavitel.getPredstavitele(mt.getModelTr(), mt.getZavod());
			for (Predstavitel prd : predstavitelPuv) {
				servicePredstavitel.removePredstavitel(prd);
			}

			for (UniObj u : predstavitelNovy) {
				Predstavitel newPred = new Predstavitel();
				newPred.setGz39tMt(mt);
				newPred.setCisloPred(u.getCisloPred());
				newPred.setModelovyKlic(u.getModelovyKlic());
				newPred.setTyp(u.getTyp());
				newPred.setVybava(u.getVybava());
				newPred.setObsah(u.getObsah());
				newPred.setVykon(u.getVykon());
				newPred.setRozlozenost(u.getRozlozenost());
				newPred.setCetnost(u.getCetnost());
				newPred.setPlatnostOd(u.getPlatnostOd());
				newPred.setPlatnostDo(u.getPlatnostDo());
				newPred.setKodZeme(u.getKodZeme());
				newPred.setComix(u.getComix());
				servicePredstavitel.addPredstavitel(newPred);
			}

			Protokol newProtokol = new Protokol();
			newProtokol.setNetusername(req.getUserPrincipal().getName().toUpperCase());
			newProtokol.setAction("Import predstavitelu");
			newProtokol.setInfo("Smazano " + predstavitelPuv.size() + " původních představitelů. Importováno " + predstavitelNovy.size() + " nových představitelů.");
			newProtokol.setTime(new Date());
			newProtokol.setSessionid(req.getSession().getId());
			serviceProtokol.addProtokol(newProtokol);

		}
		session.setAttribute("importovanyFile", "");
		return "redirect:/srv/predstavitel/definice/list";
	}

}
