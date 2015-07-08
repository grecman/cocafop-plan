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
<script>

	/* test - browser */
	var isFirefox = typeof InstallTrigger !== 'undefined'; // Firefox 1.0+
	var isOpera = !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
	var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
	var isIE = /*@cc_on!@*/ false || !!document.documentMode; // At least IE6
	var isChrome = !!window.chrome &amp;&amp; !isOpera;
	/*	
	if(isOpera || isSafari || isChrome || (isIE &amp;&amp; navigator.appVersion.match("MSIE 9.0"))){
		alert("Tato obrazovka aplikace neni kompaktibilní s vaším prohlížečem!");
		$(window.location).attr('href', '${pageContext.servletContext.contextPath}/srv/napoveda');
	}*/

	var IdVRadku = 0;
	$(document).ready(function() {

		$('#tableId').dataTable({
			"paging" : false,
			"ordering" : false,
			"info" : false,
			"bFilter" : true,
			"iDisplayLength" : 25,
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

		$("#tableId tr").click(function() {
			$(this).addClass('selectedTableRow').siblings().removeClass('selectedTableRow');
			IdVRadku = $(this).find("td:nth-child(1)").html();
		});

		$("#smazatButton").click(function() {
			$(window.location).attr('href', '${pageContext.servletContext.contextPath}/srv/predstavitel/definice/smazatPredstavitele/' + IdVRadku);
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
				<div class="formBar">
					<c:choose>
						<c:when test="${(empty(mt.modelTr))}">
							<span>
								<form:form commandName="mt" action="${pageContext.servletContext.contextPath}/srv/predstavitel/definice/list">
						&#160;Modelová třída a závod:&#160;
                            <form:select onchange="this.form.submit(); return true;" path="id">
										<form:option value="0"> . . .  </form:option>
										<c:forEach var="i" items="${listMt}">
											<form:option value="${i.id}">${i.modelTr}-${i.zavod}</form:option>
										</c:forEach>
									</form:select>
								</form:form>
							</span>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/definice">
								<SPAN>&#160;Modelová třída a závod:&#160;</SPAN>
								<span style="color: #4BA82E; font-weight: bold;">${mt.modelTr}-${mt.zavod}</span>
							</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${not(empty(mt.modelTr))}">
						<SPAN style="margin-left: 50px; margin-right: 0px;">Produkt:</SPAN>
						<SPAN style="background-color: white;">&#160;${mt.produkt}&#160;</SPAN>
						<SPAN style="margin-left: 20px; margin-right: 0px;">Popis modelové třídy:</SPAN>
						<SPAN style="background-color: white;">&#160;${mt.popis}&#160;</SPAN>
						<SPAN style="margin-left: 20px; margin-right: 0px;">Platnost OD - DO:</SPAN>
						<SPAN style="background-color: white;">&#160;${mt.platnostOd}&#160;-&#160;${mt.platnostDo}&#160;</SPAN>
					</c:if>
				</div>
				<c:if test="${not(empty(listPredstavitelu))}">
					<div class="tableContainer">
						<table class="dataTable" id="tableId" style="table-layout: fixed;">
							<col width="40px" />
							<col width="40px" />
							<col width="60px" />
							<col width="45px" />
							<col width="45px" />
							<col width="120px" />
							<col width="120px" />
							<col width="100px" />
							<col width="85px" />
							<col width="50px" />
							<col width="*" />
							<col width="65px" />
							<col width="65px" />
							<col width="140px" />
							<col width="60px" />
							<col width="50px" />
							<col width="45px" />
							<thead>
								<tr>
									<th style="display: none;">Id</th>
									<th style="font-size: x-small;" title="Číslo představitele">Č. před.</th>
									<th style="font-size: x-small;" title="Číslo představitele minulé">Min. číslo</th>
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
									<th style="font-size: x-small;">Četnost</th>
									<th style="font-size: x-small;">Comix</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listPredstavitelu}" var="i">
									<tr>
										<td style="display: none;">${i.id}</td>
										<td align="center">${i.cisloPred}</td>
										<td align="center"><c:choose>
												<c:when test="${i.cisloPredMin==-1}">
													<SPAN style="color: red;" title="Pro tohoto představitele existuje více kombinací modelové třídy a kódu země v archívu.">xxx</SPAN>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${i.cisloPredMin==0}">
															<SPAN title="Pro tohoto představitele neexistuje kombinace modelové třídy a kódu země v archívu.">&#160;&#160;&#160;&#160;</SPAN>
														</c:when>
														<c:otherwise>
															${i.cisloPredMin}
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose></td>
										<td align="left">${i.modelovyKlic}</td>
										<td align="center">${i.rozlozenost}</td>
										<td align="center">${i.kodZeme}</td>
										<td align="left">${i.typ}</td>
										<td align="left">${i.vybava}</td>
										<td align="left">${i.obsah}</td>
										<td align="left">${i.vykon}</td>
										<td align="center">${i.euNorma}</td>
										<td align="left">${i.poznamka}</td>
										<td align="center"><c:choose>
												<c:when test="${(mt.platnostOd > i.platnostOd)}">
													<SPAN style="background-color: red;" title="Platnost představitele je mimo rozsah platnosti modelové třídy!"> ${i.platnostOd}</SPAN>
												</c:when>
												<c:otherwise>
													${i.platnostOd}
												</c:otherwise>
											</c:choose></td>
										<td align="center"><c:choose>
												<c:when test="${(i.platnostDo > mt.platnostDo)}">
													<SPAN style="background-color: red;" title="Platnost představitele je mimo rozsah platnosti modelové třídy!"> ${i.platnostDo}</SPAN>
												</c:when>
												<c:otherwise>
													${i.platnostDo}
												</c:otherwise>
											</c:choose></td>
										<td align="left" style="overflow: hidden;" title="${i.vybavy}">${i.vybavy}</td>
										<td align="right">${i.cetnost}</td>
										<td align="center"><c:choose>
												<c:when test="${i.comix}">
													<img src="${pageContext.servletContext.contextPath}/resources/ico/ok.png" />
												</c:when>
												<c:otherwise>
													<img src="${pageContext.servletContext.contextPath}/resources/ico/zrusit.png" />
												</c:otherwise>
											</c:choose></td>
										<td align="center"><c:if test="${fn:contains(userRole, 'USERS')}">
												<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/definice/editForm/${i.id}">
													<img title="Editovat" style="border: 0px;" src="${pageContext.servletContext.contextPath}/resources/ico/edit.png" />
												</a>
												<a href="#openModalSmazat">
													<img title="Smazat" style="border: 0px;" src="${pageContext.servletContext.contextPath}/resources/ico/smazat.png" />
												</a>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<c:if test="${not(empty(mt.modelTr)) and fn:contains(userRole, 'USERS')}">
					<div class="formBar">
						<span>
							<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/definice/novyPredForm">
								<input type="button" value="Nový" class="heroBtn"></input>
							</a>
						</span>
						<span>
							<a href="${pageContext.servletContext.contextPath}/srv/fileUpload/importCsv">
								<input type="button" value="Import CSV" class="heroBtn"></input>
							</a>
						</span>
						<c:if test="${not(empty(listPredstavitelu))}">
							<span>
								<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/definice/exportXls">
									<input type="button" value="Export EXCEL" class="heroBtn"></input>
								</a>
							</span>
							<span>
								<a href="#openModalMinuleCisloPred">
									<input type="button" value="Doplnit min.číslo" class="heroBtn" title="Doplnit minulé číslo představitele"></input>
								</a>
							</span>
						</c:if>
					</div>
				</c:if>
			</div>
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
	</div>
	<!-- *****************************  M  O  D  A  L   **********************************-->
	<div id="openModalSmazat" class="modalWindow">
		<div class="obalovak">
			<a href="#close" class="close">X</a>
			<div class="modalHeader">
				<h3>Smazání představitele</h3>
			</div>
			<div class="modalContent">
				Opravdu chcete smazat tohoto představitele?<BR /> <BR />
				<SPAN style="font-size: x-small; color: gray;">Pokud pro představitele existuje kalkulace, která je již v daném roce schválena, tak smazání nebude
					provedeno.</SPAN>
			</div>
			<div class="modalFooter">
				<TABLE style="width: 100%;">
					<TR>
						<TD align="right"><input type="button" id="smazatButton" value="Smazat" class="ok"></input></TD>
					</TR>
				</TABLE>
			</div>
		</div>
	</div>
	<div id="openModalMinuleCisloPred" class="modalWindow">
		<div class="obalovak">
			<a href="#close" class="close">X</a>
			<div class="modalHeader">
				<h3>Doplnění minulých čísel představitelů</h3>
			</div>
			<div class="modalContent">Po potvrzení, aplikace doplní "Minulé číslo představitele" z prosince předchozího roku. Shoda se bude vyhledávat dle stejného
				modelového klíče a kódu země.</div>
			<div class="modalFooter">
				<TABLE style="width: 100%;">
					<TR>
						<TD align="right"><a href="${pageContext.servletContext.contextPath}/srv/predstavitel/definice/doplnitMinuleCisloPredstavitele">
								<input type="button" id="doplnitMinCisloButton" value="Doplnit" class="ok"></input>
							</a></TD>
					</TR>
				</TABLE>
			</div>
		</div>
	</div>
	<!-- * E N D **************************  M  O  D  A  L   **********************************-->
</body>
	</html>
</jsp:root>