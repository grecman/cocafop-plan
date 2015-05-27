<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:form="http://www.springframework.org/tags/form" xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">
	<jsp:output omit-xml-declaration="false" doctype-root-element="html" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />
	<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="lib.jsp" />
<title>COCAFOP-Plan</title>
<script>
	$(document).ready(function() {

		$('#tableId').dataTable({
			"paging" : false,
			"ordering" : false,
			"info" : false,
			"bFilter" : true,
			"iDisplayLength" : 25,
			"language" : {
				// datatables.net/reference/option/language
				"lengthMenu" : "&#160;Zobrazit _MENU_ řádků na stránce.",
				"info" : "&#160;Stránka: _PAGE_/_PAGES_, načteno _TOTAL_ záznamů.",
				"infoEmpty" : "Nenalezeny žádné záznamy.",
				"infoFiltered" : "&#160;(filtr: _TOTAL_ / _MAX_)",
				"loadingRecords" : "Nahrávám...",
				"processing" : "Pracuji...",
				"search" : "Vyhledat:",
				"zeroRecords" : "Nebyly nalezeny žádné záznamy.",
				"paginate" : {
					"first" : "První",
					"last" : "Poslední",
					"next" : "Další",
					"previous" : "Předcházející"
				}
			}
		});

		// 		$("#tableId tr").click(function() {
		// 			// Gre: NACTENI HODNOT ZE RADKU V TABULCE
		// 			// zde pouzivam jen ID daneho radku (tedy Id Mt) pro mazani.
		// 			$(this).addClass('selectedTableRow').siblings().removeClass('selectedTableRow');
		// 		});

		$("#formModelovyRokZmenaButton").click(function() {
			var mrok = (!$("#mrok").val().match(/^[0-9]{4}$/) ? "Modelový rok je špatně zadán: " + $("#mrok").val() : "");
			var result = mrok;
			if (result.length == 0) {
				$("#formModelovyRokZmena").submit();
			} else {
				alert(result);
			}
		});
		
		$("#tlacitkoProveritPrAll").click(function() {
			$("#sedaObr").css("visibility", "visible");
			$("#spinner").css("visibility", "visible");
		});
		
		$("#tlacitkoProveritPr").click(function() {
			$("#sedaObr").css("visibility", "visible");
			$("#spinner").css("visibility", "visible");
		});
	});
</script>
</head>
<body class="pages">
	<div class="page basePage">
		<c:set scope="request" var="selectedMenu" value="predstavitel" />
		<c:set scope="request" var="selectedSubMenu" value="seznam" />
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
				<div class="formBar">
					<span style="margin-right: 0px;">&#160;Kalkulace:</span>
					<span style="margin-top: 3px; margin-left: 2px; margin-right: 2px;">
						<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam/minusMesic">
							<img src="${pageContext.servletContext.contextPath}/resources/ico/gre/go_left_30.png" />
						</a>
					</span>
					<span style="font-size: 16px; margin-left: 0px; margin-right: 0px;">${kalkulaceRRRRMM}</span>
					<span style="margin-top: 3px; margin-left: 2px; margin-right: 2px;">
						<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam/plusMesic">
							<img src="${pageContext.servletContext.contextPath}/resources/ico/gre/go_right_30.png" />
						</a>
					</span>
					<c:if test="${(not(empty(kalkulaceRRRRMM)))}">
						<c:choose>
							<c:when test="${(empty(mtk))}">
								<span>
									<form:form commandName="mt" action="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam/vyberMt"> &#160;MT a závod:
                            			<form:select onchange="this.form.submit(); return true;" path="id">
											<form:option value="0"> . . .  </form:option>
											<c:forEach var="i" items="${mtList}">
												<form:option value="${i.gz39tMt.id}">${i.gz39tMt.modelTr}-${i.gz39tMt.zavod}</form:option>
											</c:forEach>
										</form:select>
									</form:form>
								</span>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam">
									<SPAN>&#160;MT a závod:</SPAN>
									<span style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${mtk.gz39tMt.modelTr}-${mtk.gz39tMt.zavod}</span>
								</a>
								<SPAN style="margin-left: 20px; margin-right: 0px;">Produkt:</SPAN>
								<SPAN style="background-color: white;">&#160;${mtk.gz39tMt.produkt}&#160;</SPAN>
								<SPAN style="margin-left: 20px; margin-right: 0px;">Popis MT:</SPAN>
								<SPAN style="background-color: white;">&#160;${mtk.gz39tMt.popis}&#160;</SPAN>
								<SPAN style="margin-left: 20px; margin-right: 0px;">Platnost OD-DO:</SPAN>
								<SPAN style="background-color: white;">&#160;${mtk.gz39tMt.platnostOd}&#160;-&#160;${mtk.gz39tMt.platnostDo}&#160;</SPAN>
								<SPAN style="margin-left: 20px; margin-right: 0px;">Modelový rok:</SPAN>
								<SPAN style="background-color: white;">&#160;${mtk.mrok}&#160;</SPAN>
								<SPAN>
									<a href="#openModalModelovyrok">
										<input type="button" value="Změnit" class="heroBtn" title="Změnit modelový rok"></input>
									</a>
								</SPAN>
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
				<c:if test="${not empty pk}">
					<div class="tableContainer">
						<table class="dataTable" id="tableId" style="table-layout: fixed;">
							<col width="35px" />
							<col width="65px" />
							<col width="40px" />
							<col width="40px" />
							<col width="120px" />
							<col width="120px" />
							<col width="100px" />
							<col width="85px" />
							<col width="50px" />
							<col width="*" />
							<col width="60px" />
							<col width="60px" />
							<col width="200px" />
							<col width="45px" />
							<col width="45px" />
							<thead>
								<tr>
									<th style="display: none;">Id</th>
									<th style="font-size: x-small;" title="Číslo představitele">Č. před.</th>
									<th style="font-size: x-small;" title="Modelový klíč">Model. klíč</th>
									<th style="font-size: x-small;" title="Rozloženost">Rozl.</th>
									<th style="font-size: x-small;">Kód země</th>
									<th style="font-size: x-small;">Typ</th>
									<th style="font-size: x-small;">Výbava</th>
									<th style="font-size: x-small;">Obsah</th>
									<th style="font-size: x-small;">Výkon</th>
									<th style="font-size: x-small;">EU norma</th>
									<th style="font-size: x-small;">Poznámka</th>
									<th style="font-size: x-small;">Platnost OD</th>
									<th style="font-size: x-small;">Platnost DO</th>
									<th style="font-size: x-small;">Výbavy</th>
									<th style="font-size: x-small;">Comix</th>
									<th style="font-size: x-small;">MBV Favas</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pk}" var="i">
									<tr>
										<td style="display: none;">${i.id}</td>
										<td align="center">${i.gz39tPredstavitel.cisloPred}</td>
										<td align="left"><a href="${pageContext.servletContext.contextPath}/srv/predstavitel/detail/${i.id}">
												<span style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${i.gz39tPredstavitel.modelovyKlic}</span>
											</a></td>
										<td align="center">${i.gz39tPredstavitel.rozlozenost}</td>
										<td align="center">${i.gz39tPredstavitel.kodZeme}</td>
										<td align="left">${i.gz39tPredstavitel.typ}</td>
										<td align="left">${i.gz39tPredstavitel.vybava}</td>
										<td align="left">${i.gz39tPredstavitel.obsah}</td>
										<td align="left">${i.gz39tPredstavitel.vykon}</td>
										<td align="center">${i.gz39tPredstavitel.euNorma}</td>
										<td align="left">${i.gz39tPredstavitel.poznamka}</td>
										<td align="center"><c:choose>
												<c:when test="${(i.gz39tMtKalkulace.gz39tMt.platnostOd > i.gz39tPredstavitel.platnostOd)}">
													<SPAN style="background-color: red;" title="Platnost představitele je mimo rozsah platnosti modelové třídy!">
														${i.gz39tPredstavitel.platnostOd}</SPAN>
												</c:when>
												<c:otherwise>
													${i.gz39tPredstavitel.platnostOd}
												</c:otherwise>
											</c:choose></td>
										<td align="center"><c:choose>
												<c:when test="${(i.gz39tPredstavitel.platnostDo > i.gz39tMtKalkulace.gz39tMt.platnostDo)}">
													<SPAN style="background-color: red;" title="Platnost představitele je mimo rozsah platnosti modelové třídy!">
														${i.gz39tPredstavitel.platnostDo}</SPAN>
												</c:when>
												<c:otherwise>
													${i.gz39tPredstavitel.platnostDo}
												</c:otherwise>
											</c:choose></td>
										<td align="left" style="overflow: hidden; font-size: x-small; font-weight: bold;"><c:choose>
												<c:when test="${empty i.vybavyEdit}">
													${i.gz39tPredstavitel.vybavy}
												</c:when>
												<c:otherwise>
													<span title="Původní výbava: ${i.gz39tPredstavitel.vybavy}" style="color: blue;">${i.vybavyEdit}</span>
												</c:otherwise>
											</c:choose></td>
										<td align="center"><c:choose>
												<c:when test="${i.gz39tPredstavitel.comix}">
													<img src="${pageContext.servletContext.contextPath}/resources/ico/ok.png" />
												</c:when>
												<c:otherwise>
													<img src="${pageContext.servletContext.contextPath}/resources/ico/zrusit.png" />
												</c:otherwise>
											</c:choose></td>
										<td align="center"><c:choose>
												<c:when test="${i.existsPr>0}">
													<img title="${i.utime}" src="${pageContext.servletContext.contextPath}/resources/ico/152.png" />
												</c:when>
												<c:otherwise>
													<img src="${pageContext.servletContext.contextPath}/resources/ico/151.png" />
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<DIV style="color: red; font-weight: bold;">${errorMesage}</DIV>
					<div class="formBar">
						<span>
							<a href="${pageContext.servletContext.contextPath}/srv/komunikaceFavas/${mtk.id}/0/vse">
								<input type="button" id="tlacitkoProveritPrAll" value="Prověřit vše" class="heroBtn" title="Komunikace MBV/Favas"></input>
							</a>
						</span>
						<span>
							<a href="${pageContext.servletContext.contextPath}/srv/komunikaceFavas/${mtk.id}/0/zbyvajici">
								<input type="button" id="tlacitkoProveritPr" value="Prověřit zbývající" class="heroBtn" title="Komunikace MBV/Favas"></input>
							</a>
						</span>
						<span>
							<input type="button" id="idButtonExport" value="Export EXCEL" class="heroBtn" style="background-color: gray;"></input>
						</span>
					</div>
				</c:if>
				<div class="whirly-loader" id="spinner" style="visibility: hidden; left: 50%; top: 50%; float: left; z-index: 200;">
					<script>
						/*http://www.css-spinners.com */
					</script>
				</div>
				<div class="sedaObrProSpinner" id="sedaObr" style="visibility: hidden;">
					<script>
						/*problem s prazdnym tagem :( */
					</script>
				</div>
			</div>
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
		<!-- *****************************  M  O  D  A  L   **********************************-->
		<div id="openModalModelovyrok" class="modalWindow">
			<div class="obalovak">
				<a href="#close" class="close">X</a>
				<div class="modalHeader">
					<h3>Zadání nové modelové třídy</h3>
				</div>
				<div class="modalContent">
					<form:form commandName="mtKalkulace" id="formModelovyRokZmena"
						action="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam/zmenaModelovehoRoku/">
						<TABLE>
							<TR>
								<TD style="width: 110px;">Modelový rok<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
								<TD><form:input path="mrok" id="mrok" class="textovePole" cssStyle="width:40px"></form:input></TD>
							</TR>
							<TR style="height: 40px;">
								<TD colspan="2"><SPAN style="color: red; font-weight: bold;">!&#160;</SPAN> <SPAN style="font-size: x-small; color: gray;"> Po uložení bude
										u všech představitelů dané kalkulace a modelové třídy smazán stávající PR popis.</SPAN></TD>
							</TR>
						</TABLE>
					</form:form>
				</div>
				<div class="modalFooter">
					<TABLE style="width: 100%;">
						<TR>
							<TD><SPAN style="color: red; font-weight: bold;">*</SPAN> <SPAN style="font-size: x-small; color: gray;"> povinný údaj</SPAN></TD>
							<TD align="right"><input type="button" id="formModelovyRokZmenaButton" value="Uložit" class="heroBtn"></input></TD>
						</TR>
					</TABLE>
				</div>
			</div>
		</div>
	</div>
</body>
	</html>
</jsp:root>