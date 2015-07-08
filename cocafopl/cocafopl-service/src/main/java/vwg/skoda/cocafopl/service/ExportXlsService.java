package vwg.skoda.cocafopl.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vwg.skoda.cocafopl.entity.ArchCenikView;
import vwg.skoda.cocafopl.entity.ArchKurzCzk;
import vwg.skoda.cocafopl.entity.ArchKurzEur;
import vwg.skoda.cocafopl.entity.ArchKusovnik;
import vwg.skoda.cocafopl.entity.ArchKusyProvView;
import vwg.skoda.cocafopl.entity.ArchPredstavitel;
import vwg.skoda.cocafopl.entity.Predstavitel;
import vwg.skoda.cocafopl.entity.PredstavitelKalkulace;

@Service
public class ExportXlsService {

	static Logger log = Logger.getLogger(ExportXlsService.class);

	@PersistenceContext(name = "ExportXlsService")
	private EntityManager entityManager;

	@Autowired
	private PredstavitelPrService servicePredstavitelPr;

	public void exportPredstavitelSeznam(List<Predstavitel> predstavitel, HttpServletResponse res) throws IOException {

		log.debug("###\t exportSeznamPredstavitel(" + predstavitel.size() + ")");

		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=\"COCAFOPPL_pred_" + predstavitel.get(0).getGz39tMt().getModelTr() + "-" + predstavitel.get(0).getGz39tMt().getZavod() + ".xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		Workbook wb = new XSSFWorkbook();
		/*
		 * CellStyle dateFormat = wb.createCellStyle(); dateFormat.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd")); CellStyle dateFormatFull = wb.createCellStyle();
		 * dateFormatFull.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd, hh:mm:ss")); CellStyle floatFormat5 = wb.createCellStyle();
		 * floatFormat5.setDataFormat(wb.createDataFormat().getFormat("##############0.00000")); CellStyle floatFormat2 = wb.createCellStyle();
		 * floatFormat2.setDataFormat(wb.createDataFormat().getFormat("##############0.00")); Font boldFont = wb.createFont(); boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		 * CellStyle boldFontStyle = wb.createCellStyle(); boldFontStyle.setFont(boldFont); Font greenFont = wb.createFont(); greenFont.setColor(IndexedColors.GREEN.getIndex());
		 * CellStyle greenFontStyle = wb.createCellStyle(); greenFontStyle.setFont(greenFont); Font smallFont = wb.createFont(); smallFont.setFontHeightInPoints((short) 7);
		 * CellStyle smallFontStyle = wb.createCellStyle(); smallFontStyle.setFont(smallFont);
		 */
		CellStyle tableHeader = wb.createCellStyle();
		tableHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font boldFont = wb.createFont();
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		tableHeader.setFont(boldFont);

		Cell cell = null;
		Row row = null;

		String nazevZalozky = "cocafoppl";
		Sheet sheet = wb.createSheet(nazevZalozky);

		int radka = 0;
		int bunka = 0;

		row = sheet.createRow(radka++);

		cell = row.createCell(bunka++);
		cell.setCellValue("Číslo představitele");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Minulé číslo představitele");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Modelový klíč");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Rozloženost");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Kód země");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Typ");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Výbava");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Obsah");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Výkon");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("EU norma");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Poznámka");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Platnost OD");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Platnost DO");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Výbavy");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Četnost");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Comix");
		cell.setCellStyle(tableHeader);

		for (Predstavitel p : predstavitel) {
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(p.getCisloPred());
			if (p.getCisloPredMin() == null || p.getCisloPredMin() == 0) {
				row.createCell(bunka++).setCellValue("");
			} else {
				row.createCell(bunka++).setCellValue(p.getCisloPredMin());
			}
			row.createCell(bunka++).setCellValue(p.getModelovyKlic());
			row.createCell(bunka++).setCellValue(p.getRozlozenost() == null ? "" : p.getRozlozenost());
			row.createCell(bunka++).setCellValue(p.getKodZeme());
			row.createCell(bunka++).setCellValue(p.getTyp());
			row.createCell(bunka++).setCellValue(p.getVybava());
			row.createCell(bunka++).setCellValue(p.getObsah());
			row.createCell(bunka++).setCellValue(p.getVykon());
			row.createCell(bunka++).setCellValue(p.getEuNorma() == null ? "" : p.getEuNorma());
			row.createCell(bunka++).setCellValue(p.getPoznamka() == null ? "" : p.getPoznamka());
			row.createCell(bunka++).setCellValue(p.getPlatnostOd());
			row.createCell(bunka++).setCellValue(p.getPlatnostDo());
			row.createCell(bunka++).setCellValue(p.getVybavy() == null ? "" : p.getVybavy());
			if (p.getCetnost() == null || p.getCetnost() == 0) {
				row.createCell(bunka++).setCellValue("");
			} else {
				row.createCell(bunka++).setCellValue(p.getCetnost());
			}
			row.createCell(bunka++).setCellValue(p.getComix() == true ? "ANO" : "NE");
		}
		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();
	}

	public void exportPredstavitelKalkulaceSeznam(List<PredstavitelKalkulace> predstavitelKalkulace, HttpServletResponse res) throws IOException {

		log.debug("###\t exportPredstavitelKalkulaceSeznam(" + predstavitelKalkulace.size() + ")");

		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=\"COCAFOPPL_pred_" + predstavitelKalkulace.get(0).getGz39tMtKalkulace().getGz39tKalkulace().getKalkulace() + "_"
				+ predstavitelKalkulace.get(0).getGz39tMtKalkulace().getGz39tMt().getModelTr() + "-" + predstavitelKalkulace.get(0).getGz39tMtKalkulace().getGz39tMt().getZavod() + ".xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		Workbook wb = new XSSFWorkbook();

		CellStyle tableHeader = wb.createCellStyle();
		tableHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font boldFont = wb.createFont();
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		tableHeader.setFont(boldFont);

		Cell cell = null;
		Row row = null;

		String nazevZalozky = "cocafoppl";
		Sheet sheet = wb.createSheet(nazevZalozky);

		int radka = 0;
		int bunka = 0;

		row = sheet.createRow(radka++);

		cell = row.createCell(bunka++);
		cell.setCellValue("Číslo představitele");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Modelový klíč");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Rozloženost");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Kód země");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Typ");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Výbava");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Obsah");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Výkon");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("EU norma");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Poznámka");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Platnost OD");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Platnost DO");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Výbavy");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Comix");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("PR popis vozu (korigovaný)");
		cell.setCellStyle(tableHeader);

		for (PredstavitelKalkulace p : predstavitelKalkulace) {
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getCisloPred());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getModelovyKlic());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getRozlozenost() == null ? "" : p.getGz39tPredstavitel().getRozlozenost());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getKodZeme());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getTyp());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getVybava());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getObsah());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getVykon());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getEuNorma() == null ? "" : p.getGz39tPredstavitel().getEuNorma());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getPoznamka() == null ? "" : p.getGz39tPredstavitel().getPoznamka());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getPlatnostOd());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getPlatnostDo());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getVybavy() == null ? "" : p.getGz39tPredstavitel().getVybavy());
			row.createCell(bunka++).setCellValue(p.getGz39tPredstavitel().getComix() == true ? "ANO" : "NE");
			row.createCell(bunka++).setCellValue(servicePredstavitelPr.getPredstavitelPrNudle(p.getId()));
		}
		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();
	}

	public void exportArchivKusovnik(List<ArchKusovnik> archKusovnik, HttpServletResponse res) throws IOException {

		log.debug("###\t exportArchivKusovnik(" + archKusovnik.size() + ")");

		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=\"COCAFOPPL_kusovnik_" + archKusovnik.get(0).getGz40tKalkulace().getKalkulace() + "_" + archKusovnik.get(0).getProdukt() + "-"
				+ archKusovnik.get(0).getZavod() + ".xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		Workbook wb = new XSSFWorkbook();

		CellStyle tableHeader = wb.createCellStyle();
		tableHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font boldFont = wb.createFont();
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		tableHeader.setFont(boldFont);

		Cell cell = null;
		Row row = null;

		String nazevZalozky = "cocafoppl";
		Sheet sheet = wb.createSheet(nazevZalozky);

		int radka = 0;
		int bunka = 0;

		row = sheet.createRow(radka++);

		cell = row.createCell(bunka++);
		cell.setCellValue("Produkt");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Závod produktu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Číslo dílu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Název dílu CZ");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Název dílu ENG");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Název dílu GER");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Platnost OD");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Platnost DO");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Klíč OD");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Klíč DO");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Množství");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("MJ");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Závod dílu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Původ dílu (BZA)");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Škoda");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("VW");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Local");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("PR podínka");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("LFDNR");
		cell.setCellStyle(tableHeader);

		for (ArchKusovnik p : archKusovnik) {
			bunka = 0;
			
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(p.getProdukt());
			row.createCell(bunka++).setCellValue(p.getZavod());
			row.createCell(bunka++).setCellValue(p.getCdilu());
			row.createCell(bunka++).setCellValue(p.getBenCz() == null ? "" : p.getBenCz());
			row.createCell(bunka++).setCellValue(p.getBenUs() == null ? "" : p.getBenUs());
			row.createCell(bunka++).setCellValue(p.getBenD() == null ? "" : p.getBenD());
			row.createCell(bunka++).setCellValue(p.getSerod());
			row.createCell(bunka++).setCellValue(p.getSerdo());
			row.createCell(bunka++).setCellValue(p.getEinschl() == null ? "" : p.getEinschl());
			row.createCell(bunka++).setCellValue(p.getEntschl() == null ? "" : p.getEntschl());
			row.createCell(bunka++).setCellValue(p.getMnpoz().doubleValue());
			row.createCell(bunka++).setCellValue(p.getMe());
			row.createCell(bunka++).setCellValue(p.getCizav());
			row.createCell(bunka++).setCellValue(p.getBza());
			row.createCell(bunka++).setCellValue(p.getSkoda() == null ? "" : p.getSkoda());
			row.createCell(bunka++).setCellValue(p.getVw() == null ? "" : p.getVw());
			row.createCell(bunka++).setCellValue(p.getLocal() == null ? "" : p.getLocal());
			row.createCell(bunka++).setCellValue(p.getPrpod() == null ? "" : p.getPrpod());
			row.createCell(bunka++).setCellValue(p.getLfdnr() == null ? 0 : p.getLfdnr().intValue());
		}
		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();
	}

	public void exportArchivCenik(List<ArchCenikView> archCenik, HttpServletResponse res) throws IOException {

		log.debug("###\t exportArchivCenik(" + archCenik.size() + ")");

		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=\"COCAFOPPL_cenik_" + archCenik.get(0).getKalkulace() + ".xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		Workbook wb = new XSSFWorkbook();

		CellStyle achtung = wb.createCellStyle();
		Font boldRedFont = wb.createFont();
		boldRedFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		boldRedFont.setColor(IndexedColors.RED.getIndex());
		achtung.setFont(boldRedFont);

		CellStyle tableHeader = wb.createCellStyle();
		tableHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font boldFont = wb.createFont();
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		tableHeader.setFont(boldFont);

		Cell cell = null;
		Row row = null;

		String nazevZalozky = "cocafoppl";
		Sheet sheet = wb.createSheet(nazevZalozky);

		int radka = 0;
		int bunka = 0;

		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("POZOR! Jedná se o důvěrná data!");
		cell.setCellStyle(achtung);

		row = sheet.createRow(radka++);
		bunka = 0;
		cell = row.createCell(bunka++);
		cell.setCellValue("Závod");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Číslo dílu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Název dílu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena SA CZK");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena SA EUR");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena SB CZK");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena SB EUR");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena IA CZK");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena IA EUR");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena IB CZK");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena IB EUR");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena MA CZK");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena MA EUR");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena MB CZK");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena MB EUR");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Cena IA orig. / local");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Měna orig.");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("MJ");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Dodavatel");
		cell.setCellStyle(tableHeader);

		for (ArchCenikView p : archCenik) {
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(p.getCizav());
			row.createCell(bunka++).setCellValue(p.getCdilu());
			row.createCell(bunka++).setCellValue(p.getNazev() == null ? "" : p.getNazev());
			row.createCell(bunka++).setCellValue(p.getCenaSaCzk() == null ? 0 : p.getCenaSaCzk().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaSaEur() == null ? 0 : p.getCenaSaEur().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaSbCzk() == null ? 0 : p.getCenaSbCzk().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaSbEur() == null ? 0 : p.getCenaSbEur().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaIaCzk() == null ? 0 : p.getCenaIaCzk().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaIaEur() == null ? 0 : p.getCenaIaEur().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaIbCzk() == null ? 0 : p.getCenaIbCzk().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaIbEur() == null ? 0 : p.getCenaIbEur().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaMaCzk() == null ? 0 : p.getCenaMaCzk().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaMaEur() == null ? 0 : p.getCenaMaEur().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaMbCzk() == null ? 0 : p.getCenaMbCzk().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaMbEur() == null ? 0 : p.getCenaMbEur().doubleValue());
			row.createCell(bunka++).setCellValue(p.getCenaOrigLocal() == null ? 0 : p.getCenaOrigLocal().doubleValue());
			row.createCell(bunka++).setCellValue(p.getMena() == null ? "" : p.getMena());
			row.createCell(bunka++).setCellValue(p.getMe() == null ? "" : p.getMe());
			row.createCell(bunka++).setCellValue(p.getDodavatel() == null ? "" : p.getDodavatel());
		}
		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();
	}

	public void exportArchivPredstavitel(List<ArchPredstavitel> archPred, HttpServletResponse res) throws IOException {

		log.debug("###\t exportArchivPredstavitel(" + archPred.size() + ")");

		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=\"COCAFOPPL_arch_pred_" + archPred.get(0).getGz40tKalkulace().getKalkulace() + "_" + archPred.get(0).getModelTr() + "-"
				+ archPred.get(0).getZavod() + ".xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		Workbook wb = new XSSFWorkbook();

		CellStyle tableHeader = wb.createCellStyle();
		tableHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font boldFont = wb.createFont();
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		tableHeader.setFont(boldFont);

		Cell cell = null;
		Row row = null;

		String nazevZalozky = "cocafoppl";
		Sheet sheet = wb.createSheet(nazevZalozky);

		int radka = 0;
		int bunka = 0;

		row = sheet.createRow(radka++);

		cell = row.createCell(bunka++);
		cell.setCellValue("Číslo představitele");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Modelový klíč");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Rozloženost");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Kód země");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Typ");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Výbava");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Obsah");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Výkon");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("EU norma");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Poznámka");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Platnost OD");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Platnost DO");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Výbavy");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Comix");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("PR popis vozu (korigovaný)");
		cell.setCellStyle(tableHeader);

		for (ArchPredstavitel p : archPred) {
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(p.getCisloPred());
			row.createCell(bunka++).setCellValue(p.getModelovyKlic());
			row.createCell(bunka++).setCellValue(p.getRozlozenost() == null ? "" : p.getRozlozenost());
			row.createCell(bunka++).setCellValue(p.getKodZeme());
			row.createCell(bunka++).setCellValue(p.getTyp());
			row.createCell(bunka++).setCellValue(p.getVybava());
			row.createCell(bunka++).setCellValue(p.getObsah());
			row.createCell(bunka++).setCellValue(p.getVykon());
			row.createCell(bunka++).setCellValue(p.getEuNorma() == null ? "" : p.getEuNorma());
			row.createCell(bunka++).setCellValue(p.getPoznamka() == null ? "" : p.getPoznamka());
			row.createCell(bunka++).setCellValue(p.getPlatnostOd());
			row.createCell(bunka++).setCellValue(p.getPlatnostDo());
			row.createCell(bunka++).setCellValue(p.getVybavy() == null ? "" : p.getVybavy());
			row.createCell(bunka++).setCellValue(p.getComix() == true ? "ANO" : "NE");
			row.createCell(bunka++).setCellValue(p.getPrpoz());
		}
		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();
	}

	public void exportArchivKusyNaProvedeni(List<ArchKusyProvView> archKnP, HttpServletResponse res) throws IOException {

		log.debug("###\t exportArchivKusyNaProvedeni(" + archKnP.size() + ")");

		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=\"COCAFOPPL_knp_" + archKnP.get(0).getKalkulace() + "_" + archKnP.get(0).getModelTr() + "-" + archKnP.get(0).getZavod() + ".xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		Workbook wb = new XSSFWorkbook();

		CellStyle tableHeader = wb.createCellStyle();
		tableHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font boldFont = wb.createFont();
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		tableHeader.setFont(boldFont);

		Cell cell = null;
		Row row = null;

		String nazevZalozky = "cocafoppl";
		Sheet sheet = wb.createSheet(nazevZalozky);

		int radka = 0;
		int bunka = 0;

		row = sheet.createRow(radka++);

		cell = row.createCell(bunka++);
		cell.setCellValue("Číslo dílu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Název dílu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Číslo představitele");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Množství");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("MJ");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Závod dílu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Původ dílu (BZA)");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Škoda");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("VW");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Local");
		cell.setCellStyle(tableHeader);


		for (ArchKusyProvView p : archKnP) {
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(p.getCdilu());
			row.createCell(bunka++).setCellValue(p.getBenCz()==null ? "" : p.getBenCz());
			row.createCell(bunka++).setCellValue(p.getCisloPred());
			row.createCell(bunka++).setCellValue(p.getMnfin());
			row.createCell(bunka++).setCellValue(p.getMe());
			row.createCell(bunka++).setCellValue(p.getZavod());
			row.createCell(bunka++).setCellValue(p.getBza()==null ? "" : p.getBza());
			row.createCell(bunka++).setCellValue(p.getSkoda()==null ? "" : p.getSkoda());
			row.createCell(bunka++).setCellValue(p.getVw()==null ? "" : p.getVw());
			row.createCell(bunka++).setCellValue(p.getLocal()==null ? "" : p.getLocal());
		}
		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();
	}
	
	public void exportArchivDilVPredstavitelich(List<ArchKusyProvView> archKnP, HttpServletResponse res) throws IOException {

		log.debug("###\t exportArchivDilVPredstavitelich(" + archKnP.size() + ")");

		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=\"COCAFOPPL_dvp_" + archKnP.get(0).getKalkulace() + ".xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		Workbook wb = new XSSFWorkbook();

		CellStyle tableHeader = wb.createCellStyle();
		tableHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font boldFont = wb.createFont();
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		tableHeader.setFont(boldFont);

		Cell cell = null;
		Row row = null;

		String nazevZalozky = "cocafoppl";
		Sheet sheet = wb.createSheet(nazevZalozky);

		int radka = 0;
		int bunka = 0;

		row = sheet.createRow(radka++);

		cell = row.createCell(bunka++);
		cell.setCellValue("Číslo dílu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Název dílu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Modelová třída");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Produkt");
		cell.setCellStyle(tableHeader);
		
		cell = row.createCell(bunka++);
		cell.setCellValue("Závod dílu");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Číslo představitele");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Množství");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("MJ");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Původ dílu (BZA)");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Škoda");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("VW");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Local");
		cell.setCellStyle(tableHeader);


		for (ArchKusyProvView p : archKnP) {
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(p.getCdilu());
			row.createCell(bunka++).setCellValue(p.getBenCz()==null ? "" : p.getBenCz());
			row.createCell(bunka++).setCellValue(p.getModelTr());
			row.createCell(bunka++).setCellValue(p.getProdukt());
			row.createCell(bunka++).setCellValue(p.getZavod());
			row.createCell(bunka++).setCellValue(p.getCisloPred());
			row.createCell(bunka++).setCellValue(p.getMnfin());
			row.createCell(bunka++).setCellValue(p.getMe());
			row.createCell(bunka++).setCellValue(p.getBza()==null ? "" : p.getBza());
			row.createCell(bunka++).setCellValue(p.getSkoda()==null ? "" : p.getSkoda());
			row.createCell(bunka++).setCellValue(p.getVw()==null ? "" : p.getVw());
			row.createCell(bunka++).setCellValue(p.getLocal()==null ? "" : p.getLocal());
		}
		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();
	}
	
	public void exportArchivKurzovniListek(List<ArchKurzCzk> klCzk, List<ArchKurzEur> klEur, HttpServletResponse res) throws IOException {

		log.debug("###\t exportArchivKurzovniListek(" + klCzk.size()+", "+klEur.size()  + ")");

		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=\"COCAFOPPL_kl_" + klCzk.get(0).getGz40tKalkulace().getKalkulace() + ".xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");

		Workbook wb = new XSSFWorkbook();
		
		CellStyle achtung = wb.createCellStyle();
		Font boldRedFont = wb.createFont();
		boldRedFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		boldRedFont.setColor(IndexedColors.RED.getIndex());
		achtung.setFont(boldRedFont);

		CellStyle tableHeader = wb.createCellStyle();
		tableHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		tableHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font boldFont = wb.createFont();
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		tableHeader.setFont(boldFont);

		Cell cell = null;
		Row row = null;

		String nazevZalozky = "cocafoppl_kl_CZK";
		Sheet sheet = wb.createSheet(nazevZalozky);

		int radka = 0;
		int bunka = 0;
		
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("POZOR! Jedná se o důvěrná data!");
		cell.setCellStyle(achtung);

		row = sheet.createRow(radka++);
		bunka = 0;

		cell = row.createCell(bunka++);
		cell.setCellValue("Měna");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Kurz standard");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Datum");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Kurz ist");
		cell.setCellStyle(tableHeader);
		
		cell = row.createCell(bunka++);
		cell.setCellValue("Datum");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Kurz ist minulý");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Datum");
		cell.setCellStyle(tableHeader);
		
		for (ArchKurzCzk p : klCzk) {
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(p.getSapMena());
			row.createCell(bunka++).setCellValue(p.getKurzS()==null ? 0 : p.getKurzS().doubleValue());
			row.createCell(bunka++).setCellValue(p.getKurzSDat()==null ? "" : p.getKurzSDat());
			row.createCell(bunka++).setCellValue(p.getKurzI()==null ? 0 : p.getKurzI().doubleValue());
			row.createCell(bunka++).setCellValue(p.getKurzIDat()==null ? "" : p.getKurzIDat());
			row.createCell(bunka++).setCellValue(p.getKurzM()==null ? 0 : p.getKurzM().doubleValue());
			row.createCell(bunka++).setCellValue(p.getKurzMDat()==null ? "" : p.getKurzMDat());
		}
				
		nazevZalozky = "cocafoppl_kl_EUR";
		sheet = wb.createSheet(nazevZalozky);

		radka = 0;
		bunka = 0;
		
		row = sheet.createRow(radka++);
		cell = row.createCell(bunka++);
		cell.setCellValue("POZOR! Jedná se o důvěrná data!");
		cell.setCellStyle(achtung);

		row = sheet.createRow(radka++);
		bunka = 0;
		
		cell = row.createCell(bunka++);
		cell.setCellValue("Měna");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Kurz standard");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Datum");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Kurz ist");
		cell.setCellStyle(tableHeader);
		
		cell = row.createCell(bunka++);
		cell.setCellValue("Datum");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Kurz ist minulý");
		cell.setCellStyle(tableHeader);

		cell = row.createCell(bunka++);
		cell.setCellValue("Datum");
		cell.setCellStyle(tableHeader);
		
		for (ArchKurzEur p : klEur) {
			bunka = 0;
			row = sheet.createRow(radka++);
			row.createCell(bunka++).setCellValue(p.getSapMena());
			row.createCell(bunka++).setCellValue(p.getKurzS()==null ? 0 : p.getKurzS().doubleValue());
			row.createCell(bunka++).setCellValue(p.getKurzSDat()==null ? "" : p.getKurzSDat());
			row.createCell(bunka++).setCellValue(p.getKurzI()==null ? 0 : p.getKurzI().doubleValue());
			row.createCell(bunka++).setCellValue(p.getKurzIDat()==null ? "" : p.getKurzIDat());
			row.createCell(bunka++).setCellValue(p.getKurzM()==null ? 0 : p.getKurzM().doubleValue());
			row.createCell(bunka++).setCellValue(p.getKurzMDat()==null ? "" : p.getKurzMDat());
		}
		
		
		
		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();
	}


}
