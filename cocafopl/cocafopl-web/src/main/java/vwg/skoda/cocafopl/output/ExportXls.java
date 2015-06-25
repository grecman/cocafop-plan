package vwg.skoda.cocafopl.output;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import vwg.skoda.cocafopl.entity.PredstavitelKalkulace;

public class ExportXls {

	static Logger log = Logger.getLogger(ExportXls.class);

	//NumberFormat floatToText = new DecimalFormat("##############0.00000", new DecimalFormatSymbols(new Locale("cs")));
	
	public void exportSeznamPredstavitel(List<PredstavitelKalkulace> pk, HttpServletResponse res) throws IOException {

		log.debug("###\t exportSeznamPredstavitel()");
		
		//res.setContentType("application/ms-excel");
		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition", "attachment; filename=\"PRCEK_neco.xlsx\"");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-Control", "max-age=0");
		
		XSSFWorkbook wb = new XSSFWorkbook(); 

		XSSFCellStyle dateFormat = wb.createCellStyle();
		dateFormat.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd"));

		XSSFCellStyle dateFormatFull = wb.createCellStyle();
		dateFormatFull.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd, hh:mm:ss"));

		XSSFCellStyle floatFormat5 = wb.createCellStyle();
		floatFormat5.setDataFormat(wb.createDataFormat().getFormat("##############0.00000"));
		XSSFCellStyle floatFormat2 = wb.createCellStyle();
		floatFormat2.setDataFormat(wb.createDataFormat().getFormat("##############0.00"));

		XSSFFont boldFont = wb.createFont();
		boldFont.setBold(true);
		XSSFCellStyle boldFontStyle = wb.createCellStyle();
		boldFontStyle.setFont(boldFont);

		XSSFFont greenFont = wb.createFont();
		greenFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());
		XSSFCellStyle greenFontStyle = wb.createCellStyle();
		greenFontStyle.setFont(greenFont);

		XSSFFont smallFont = wb.createFont();
		smallFont.setFontHeightInPoints((short) 7);
		XSSFCellStyle smallFontStyle = wb.createCellStyle();
		smallFontStyle.setFont(smallFont);
		
		Cell cell = null;
		Row row = null;

		XSSFCellStyle alignCenter = wb.createCellStyle();
		alignCenter.setAlignment(HorizontalAlignment.CENTER);

		
		String nazevzalozky = "gregre";
		Sheet sheet = wb.createSheet(nazevzalozky);

		
		int radka = 0;
		int bunka = 0;

		row = sheet.createRow(radka++);

		cell = row.createCell(bunka++);
		cell.setCellValue("Modelova trida");
		cell.setCellStyle(boldFontStyle);
		
		OutputStream os = res.getOutputStream();
		wb.write(os);
		os.close();
	
	}
}
