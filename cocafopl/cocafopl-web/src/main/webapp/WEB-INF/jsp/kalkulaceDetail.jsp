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
	$(document).ready(function() {
		
		/* test - browser */
		var isFirefox = typeof InstallTrigger !== 'undefined'; // Firefox 1.0+
		var isOpera = !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
		var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
		var isIE = /*@cc_on!@*/ false || !!document.documentMode; // At least IE6
		var isChrome = !!window.chrome &amp;&amp; !isOpera;

		if(isOpera || isSafari || isChrome || (isIE &amp;&amp; navigator.appVersion.match("MSIE 9.0"))){
			alert("Tato obrazovka aplikace neni kompaktibilní s vaším prohlížečem!");
			$(window.location).attr('href', '${pageContext.servletContext.contextPath}/srv/napoveda');
		}		
		
		$('#tableId').dataTable({
			"paging" : false,
			"bFilter" : false,
			"ordering" : false,
			"info" : false,
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

		$("#tlacitkoSpustitVypocet").click(function() {
			$("#sedaObr").css("visibility", "visible");
			$("#spinner").css("visibility", "visible");
		});

		$('.popup').click(function(event) {
			event.preventDefault();
			window.open($(this).attr("href"), "popupWindow", "width=850,height=850,scrollbars=yes,menubar=no,location=no");
		});

	});
</script>
</head>
<body class="pages">
	<div class="page basePage">
		<c:set scope="request" var="selectedMenu" value="kalkulace" />
		<c:set scope="request" var="selectedSubMenu" value="kalkulaceDetail" />
		<jsp:include page="header.jsp" />
		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'mt' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/kalkulace/mtDefinice">Modelové třídy</a>
				<a class="${selectedSubMenu eq 'kalkulaceSeznam' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/kalkulace/seznam">Seznam
					kalkulací</a>
				<a class="${selectedSubMenu eq 'kalkulaceDetail' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail">Detail
					kalkulace</a>
			</div>
		</div>
		<div class="pageBody">
			<div class="mainAreaWide">
				<div class="formBar">
					<span style="margin-right: 0px;">&#160;Kalkulace:</span>
					<span style="margin-top: 3px; margin-left: 2px; margin-right: 2px;">
						<a href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail/minusMesic">
							<img src="${pageContext.servletContext.contextPath}/resources/ico/gre/go_left_30.png" />
						</a>
					</span>
					<span style="font-size: 16px; margin-left: 0px; margin-right: 0px;">${kalkulace.kalkulace}</span>
					<span style="margin-top: 3px; margin-left: 2px; margin-right: 2px;">
						<a href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail/plusMesic">
							<img src="${pageContext.servletContext.contextPath}/resources/ico/gre/go_right_30.png" />
						</a>
					</span>
					<span style="padding-left: 750px;">Poslední schválená kalkulace:</span>
					<a href="${pageContext.servletContext.contextPath}/srv/archiv/kalkulace">
						<span style="color: #4BA82E; font-weight: bold;">${posledniArchivniKalkulace.kalkulace}</span>
					</a>
				</div>
				<div class="tableContainer">
					<table class="dataTable" id="tableId">
						<col width="80px" />
						<col width="80px" />
						<col width="60px" />
						<col width="*" />
						<col width="80px" />
						<col width="160px" />
						<col width="1px" />
						<col width="160px" />
						<col width="160px" />
						<col width="90px" />
						<col width="160px" />
						<thead>
							<tr>
								<th style="display: none;">Id</th>
								<th>Modelová třída</th>
								<th>Produkt</th>
								<th>Závod</th>
								<th>Popis</th>
								<th>Kontrola</th>
								<th>Posledni editace</th>
								<th></th>
								<th>Poslední výpočet</th>
								<th>Schváleno</th>
								<th>Schválil</th>
								<th><a href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail/help" class="popup">
										<SPAN style="color: #4BA82E; font-weight: bolder; font-size: large;">?</SPAN>
									</a></th>
							</tr>
						</thead>
						<tbody>
							<c:set var="alesponJedenPrJeOk" value="KO" />
							<c:forEach items="${mtk}" var="i">
								<c:set var="vsechnyMtSchvaleny" value="OK" />
								<tr>
									<td align="center" style="display: none;">${i.id}</td>
									<td align="center">${i.modelTr}</td>
									<td align="center">${i.produkt}</td>
									<td align="center">${i.zavod}</td>
									<td align="left">${i.popis}</td>
									<td align="center"><a href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam/${kalkulace.kalkulace}/${i.modelTr}/${i.zavod}">
											<c:choose>
												<c:when test="${fn:contains(i.prAnoNe,'AN') or fn:contains(i.prAnoNe,'NA')}">
													<img title="Část představitelů nemá PR-popis" src="${pageContext.servletContext.contextPath}/resources/ico/154.png" />
													<!-- <c:set var="stavPR" value="MID" /> -->
												</c:when>
												<c:otherwise>
													<c:if test="${fn:contains(i.prAnoNe,'N')}">
														<img title="Žádný představitel nemá PR-popis" src="${pageContext.servletContext.contextPath}/resources/ico/151.png" />
														<!-- 	<c:set var="stavPR" value="KO" /> -->
													</c:if>
													<c:if test="${fn:contains(i.prAnoNe,'A')}">
														<img title="Všichni představitelé mají PR-popis" src="${pageContext.servletContext.contextPath}/resources/ico/152.png" />
														<!--	<c:set var="stavPR" value="OK" /> -->
														<c:set var="alesponJedenPrJeOk" value="OK" />
													</c:if>
												</c:otherwise>
											</c:choose>
										</a></td>
									<td align="center" title="${i.posledniEditaceDuvod}"><c:choose>
											<c:when test="${(not empty i.posledniVypocet) and (i.posledniEditace > i.posledniVypocet)}">
												<span style="color: red;">
													<f:formatDate value="${i.posledniEditace}" pattern="yyyy-MM-dd HH:mm:ss" />
												</span>
											</c:when>
											<c:otherwise>
												<f:formatDate value="${i.posledniEditace}" pattern="yyyy-MM-dd HH:mm:ss" />
											</c:otherwise>
										</c:choose></td>
									<td></td>
									<td align="center" title="${i.posledniVypocetUser}"><f:formatDate value="${i.posledniVypocet}" pattern="yyyy-MM-dd HH:mm" /></td>
									<td align="center"><f:formatDate value="${i.schvaleno}" pattern="yyyy-MM-dd HH:mm" /></td>
									<td align="center">${i.schvalil}</td>
									<td align="center"><c:if test="${(empty posledniArchivniKalkulace.kalkulace) or kalkulace.kalkulace>posledniArchivniKalkulace.kalkulace}">
											<c:if test="${empty(i.schvalil) and (fn:contains(userRole,'APPROVERS') or fn:contains(userRole,'USERS'))}">
												<a href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail/schvalitMt/${i.modelTr}/${i.zavod}/${i.kalkulace}">
													<input type="button" value="Schválit" class="heroBtn"></input>
												</a>
												<c:set var="vsechnyMtSchvaleny" value="KO" />
											</c:if>
											<c:if test="${(not empty i.schvalil) and fn:contains(userRole,'APPROVERS')}">
												<a href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail/reSchvalitMt/${i.modelTr}/${i.zavod}/${i.kalkulace}">
													<input type="button" value="Zrušit schválení" class="heroBtn"></input>
												</a>
											</c:if>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="formBar">
					<c:if test="${((empty posledniArchivniKalkulace.kalkulace) or kalkulace.kalkulace>posledniArchivniKalkulace.kalkulace) and alesponJedenPrJeOk=='OK'}">
						<c:if test="${fn:contains(userRole,'APPROVERS') or fn:contains(userRole,'USERS')}">
							<span>
								<a href="${pageContext.servletContext.contextPath}/srv/kalkulace/spustitVypocet/${kalkulace.kalkulace}">
									<input type="button" id="tlacitkoSpustitVypocet" value="Spustit výpočet" class="heroBtn"></input>
								</a>
							</span>
						</c:if>
						<c:if test="${fn:contains(userRole,'APPROVERS') and vsechnyMtSchvaleny=='OK' and kalkulace.kalkulace==prvniPracovniKalkulace}">
							<span>
								<a href="${pageContext.servletContext.contextPath}/srv/kalkulace/schvalit/${kalkulace.kalkulace}">
									<input type="button" value="Schválit kalkulaci a archivovat" class="heroBtn" style="width: auto;"></input>
								</a>
							</span>
						</c:if>
					</c:if>
				</div>
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
	</div>
</body>
	</html>
</jsp:root>