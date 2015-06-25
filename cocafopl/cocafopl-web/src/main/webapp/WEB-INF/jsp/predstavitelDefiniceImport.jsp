<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:form="http://www.springframework.org/tags/form" xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">
	<jsp:output omit-xml-declaration="false" doctype-root-element="html" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />
	<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="lib.jsp" />
<title>COCAFOP-Plan</title>
<script type="text/javascript">
	$(document).ready(function() {

		$("#buttonNahratSoubor").click(function() {
			if ($("#inputFile").val().length == 0) {
				alert("Nevybrán žádný soubor k nahrání");
			} else {
				$("#formNahratSoubor").submit();
			}
		});

		$('#tableId').dataTable({
			"paging" : true,
			"ordering" : false,
			"info" : false,
			"bFilter" : false,
			"iDisplayLength" : 10,
			"language" : {
				// datatables.net/reference/option/language
				"lengthMenu" : "&#160;Zobrazit _MENU_ řádků na stránce",
				"info" : "&#160;Stránka: _PAGE_/_PAGES_, načteno _TOTAL_ záznamů.",
				"infoEmpty" : "Nenalezeny žádné záznamy",
				"infoFiltered" : "&#160;(filtr: _TOTAL_ / _MAX_)",
				"loadingRecords" : "Nahrávám...",
				"processing" : "Pracuji...",
				"search" : "Vyhledat:",
				"paginate" : {
					"first" : "První",
					"last" : "Poslední",
					"next" : "Další",
					"previous" : "Předcházející"
				}
			}
		});
	});
</script>
</head>
<body class="pages">
	<div class="page basePage">
		<c:set scope="request" var="selectedMenu" value="predstavitel" />
		<c:set scope="request" var="selectedSubMenu" value="definice" />
		<jsp:include page="header.jsp" />
		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'definice' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/predstavitel/definice">Definice
					představitelů</a>
				<a class="${selectedSubMenu eq 'seznam' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam">Seznam
					představitelů</a>
			</div>
		</div>
		<div class="pageBody">
			<div class="mainAreaWide">
				<H2>Import bude provedem pro modelovou třídu ${vybranaMt} a závod ${vybranyZavod}</H2>
				<TABLE>
					<col width="260px" />
					<col width="*" />
					<TBODY>
						<form:form method="post" id="formNahratSoubor" action="${pageContext.servletContext.contextPath}/srv/fileUpload/checkFile" modelAttribute="uploadForm"
							enctype="multipart/form-data">
							<TR height="35px">
								<TD style="font-weight: bold;">1. krok - vybrat soubor&#160;&#160;<SPAN onmouseover="jQuery(&#039;&#035;obrazky&#039;).show()" onmouseout="jQuery(&#039;&#035;obrazky&#039;).hide()"
										style="color: blue;">(VZOR)</SPAN></TD>
								<TD><input name="filePredstaviteleCocafoppl" type="file" id="inputFile" style="background: none; width: 300px; color: red; padding-left: 0px;" />
								</TD>
							</TR>
							<TR height="35px">
								<TD style="font-weight: bold;">2. krok - nahrát soubor</TD>
								<TD><input type="button" id="buttonNahratSoubor" value="Import" /></TD>
							</TR>
						</form:form>
						<TR height="35px">
							<TD style="font-weight: bold;">3. krok - kontrola načtených dat</TD>
							<TD><c:if test="${not empty uniObjList}">
									<SPAN style="color: red; padding-left: 20px;">Všechny zobrazené sloupce musí být řádně vyplněny.</SPAN>
								</c:if></TD>
						</TR>
						<c:if test="${not empty uniObjList}">
							<TR height="35px">
								<TD colspan="2">
									<div class="tableContainer">
										<TABLE class="dataTable" id="tableId" style="table-layout: fixed;">
											<THEAD>
												<th>Číslo představitele</th>
												<th>Modelový klíč</th>
												<th>Typ</th>
												<th>Výbava</th>
												<th>Obsah</th>
												<th>Výkon</th>
												<th>Rozloženost</th>
												<th>Četnost</th>
												<th>Platnost OD</th>
												<th>Platnost Do</th>
												<th>Kód země}</th>
												<th>Comix</th>
											</THEAD>
											<TBODY>
												<c:forEach items="${uniObjList}" var="i">
													<TR>
														<td align="center" style="color: gray;">${i.cisloPred}</td>
														<td align="left" style="font-weight: bold;">${i.modelovyKlic}</td>
														<td align="left" style="font-weight: bold;">${i.typ}</td>
														<td align="left" style="font-weight: bold;">${i.vybava}</td>
														<td align="left" style="font-weight: bold;">${i.obsah}</td>
														<td align="left" style="font-weight: bold;">${i.vykon}</td>
														<td align="center" style="font-weight: bold;">${i.rozlozenost}</td>
														<td align="center" style="font-weight: bold;">${i.cetnost}</td>
														<td align="center" style="color: gray;">${i.platnostOd}</td>
														<td align="center" style="color: gray;">${i.platnostDo}</td>
														<td align="center" style="color: gray;">${i.kodZeme}</td>
														<td align="center" style="color: gray;">${i.comix ? "ano" : "ne"}</td>
													</TR>
												</c:forEach>
											</TBODY>
										</TABLE>
									</div>
								</TD>
							</TR>
						</c:if>
						<TR height="35px">
							<TD style="font-weight: bold;">4. krok - uložení</TD>
							<TD><c:if test="${not empty uniObjList}">
									<a href="${pageContext.servletContext.contextPath}/srv/fileUpload/saveFile">
										<input type="button" value="Uložit"></input>
									</a>
									<SPAN style="color: red; padding-left: 20px;">Pozor!!! Uložení znamená i odstranění všech původních představitelů pro vybranou modelovou třídu.</SPAN>
								</c:if> <BR /></TD>
						</TR>
					</TBODY>
				</TABLE>
				<c:if test="${not empty errorMsg}">
					<DIV style="padding: 50px;">
						<div style="font-weight: bold; font-size: x-large; color: red;">Importovaný soubor nemá očekávaný formát dat. Mrkněte se prosím na VZOR.</div>
						<BR />První řádek importovaného souboru: ${errorMsg}
					</DIV>
				</c:if>
				<div id="obrazky" style="display: none;">
					<DIV style="padding-bottom: 15px;">
						<BR /> <BR /> Importovaný soubor musí být v textové podobě (příklad: *.txt nebo *.csv), první řádek musí obsahovat slovo "CODE". V dalších řádcích musí
						být uložen: Modelový klíč, Typ, Výbava, Obsah, Výkon, Rozloženost a Četnost. Vše ooděleno středníkem. Náhled na importovaný CSV soubor (otevreno v
						poznámkovém bloku a excelu).
					</DIV>
					<img style="border: 0px;" src="${pageContext.servletContext.contextPath}/resources/images/cocafoppl_import_vzor_txt.JPG" />&#160;&#160;<img
						style="border: 0px;" src="${pageContext.servletContext.contextPath}/resources/images/cocafoppl_import_vzor_excel.JPG" />
				</div>
				<BR />
			</div>
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
	</div>
</body>
	</html>
</jsp:root>