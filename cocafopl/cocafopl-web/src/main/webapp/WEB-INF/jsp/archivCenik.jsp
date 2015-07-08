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
	$(document)
			.ready(
					function() {
						$('#tableId').dataTable({
							"paging" : true,
							"ordering" : false,
							"info" : true,
							"bFilter" : true,
							"iDisplayLength" : 15,
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

						$("#idButtonExport").click(function() {
							$("#tableId").table2excel({
								exclude : ".noExl",
								name : "aaaGreca"
							});
						});

						$("#formButton")
								.click(
										function() {
											var cdilu = (!$("#cdilu").val().match(/^[_a-zA-Z0-9\u0020]{3,15}$/) ? "V masce pro číslo dílu musí být alespoň 3 znaky (čísla/písmena).\nPolovené je i podtržítko pro zástupný znak. Příklad: 5E_827550_"
													: "");
											var result = cdilu;
											if (result.length == 0) {
												$("#formId").submit();
											} else {
												alert(result);
											}
										});

						$("#tableId").on('click', 'tr', function() {
							$(this).addClass('selectedTableRow').siblings().removeClass('selectedTableRow');
						});
					});
</script>
</head>
<body class="pages">
	<div class="page basePage">
		<c:set scope="request" var="selectedMenu" value="archiv" />
		<c:set scope="request" var="selectedSubMenu" value="cenik" />
		<jsp:include page="header.jsp" />
		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'kalkulace' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kalkulace">Kalkulace</a>
				<a class="${selectedSubMenu eq 'kusovnik' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kusovnik">Kusovník</a>
				<a class="${selectedSubMenu eq 'cenik' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/cenik">Ceník</a>
				<a class="${selectedSubMenu eq 'predstavitel' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel">Představitel</a>
				<a class="${selectedSubMenu eq 'kusyNaProvedeni' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kusyNaProvedeni">Kusy
					na provedení</a>
				<a class="${selectedSubMenu eq 'dilVPredstavitelych' ? 'selected' : 'passive'}"
					href="${pageContext.servletContext.contextPath}/srv/archiv/dilVPredstavitelich">Díl v představitelích</a>
				<a class="${selectedSubMenu eq 'kurzovniListek' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kurzovniListek">Kurzovní
					lístek</a>
			</div>
		</div>
		<div class="pageBody">
			<div class="mainAreaWide">
				<div class="formBar">
					<c:choose>
						<c:when test="${empty archKalkulaceRRRRMM}">
							<form:form commandName="archKalkulace" action="${pageContext.servletContext.contextPath}/srv/archiv/cenik/param">
								<SPAN>&#160;Kalkulace:&#160;</SPAN>
								<SPAN>
									<form:select onchange="this.form.submit(); return true;" path="kalkulace">
										<form:option value="0"> . . .  </form:option>
										<c:forEach var="i" items="${archKalkulaceList}">
											<form:option value="${i.kalkulace}">${i.kalkulace}</form:option>
										</c:forEach>
									</form:select>
								</SPAN>
							</form:form>
						</c:when>
						<c:otherwise>
							<SPAN>&#160;Kalkulace:&#160;</SPAN>
							<a href="${pageContext.servletContext.contextPath}/srv/archiv/cenik">
								<SPAN style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${archKalkulaceRRRRMM}</SPAN>
							</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${not empty archKalkulaceRRRRMM}">
						<SPAN>
							<form:form commandName="archCenikView" id="formId" action="${pageContext.servletContext.contextPath}/srv/archiv/cenik/list">
								<SPAN style="margin-top: 0px;">Číslo dílu:</SPAN>
								<form:input path="cdilu" id="cdilu" class="textovePole" cssStyle="width:160px"></form:input>
								<SPAN style="margin-top: 0px;">Závod:</SPAN>
								<form:input path="cizav" id="cizav" class="textovePole" cssStyle="width:30px"></form:input>
								<SPAN style="margin-top: 0px;">Dodavatel:</SPAN>
								<form:input path="dodavatel" id="dodavatel" class="textovePole" cssStyle="width:200px"></form:input>
								<input type="button" id="formButton" value="Vyhledat" class="heroBtn" style="display: inline; margin-left: 25px;"></input>
							</form:form>
						</SPAN>
					</c:if>
				</div>
				<c:if test="${not empty archCenikList}">
					<div class="tableContainer">
						<table class="dataTable" id="tableId" style="table-layout: fixed;">
							<col width="30px" />
							<col width="125px" />
							<col width="*" />
							<col width="120px" />
							<col width="120px" />
							<col width="120px" />
							<col width="120px" />
							<col width="120px" />
							<col width="120px" />
							<col width="40px" />
							<col width="120px" />
							<col width="40px" />
							<col width="30px" />
							<col width="100px" />
							<thead>
								<tr>
									<th style="font-size: x-small;">Záv.</th>
									<th style="font-size: x-small;">Číslo dílu / Předtavitel</th>
									<th style="font-size: x-small;">Název dílu</th>
									<th style="font-size: x-small;">Cena SA</th>
									<th style="font-size: x-small;">Cena SB</th>
									<th style="font-size: x-small;">Cena IA</th>
									<th style="font-size: x-small;">Cena IB</th>
									<th style="font-size: x-small;">Cena MA</th>
									<th style="font-size: x-small;">Cena MB</th>
									<th style="font-size: x-small;">Měna</th>
									<th style="font-size: x-small;">Cena IA orig. / local</th>
									<th style="font-size: x-small;">Měna orig.</th>
									<th style="font-size: x-small;">MJ</th>
									<th style="font-size: x-small;">Dodavatel</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${archCenikList}" var="i">
									<tr>
										<td align="left" style="font-size: x-small;">${i.cizav}</td>
										<td align="left" style="font-size: x-small;"><DIV>${i.cdilu}</DIV>
											<DIV>${i.pred}</DIV></td>
										<td nowrap="nowrap" align="left" style="font-size: x-small; overflow: hidden;" title="${i.nazev}">${i.nazev}</td>
										<td align="right" style="font-size: x-small;"><DIV>${i.cenaSaCzk}</DIV>
											<DIV>${i.cenaSaEur}</DIV></td>
										<td align="right" style="font-size: x-small;"><DIV>${i.cenaSbCzk}</DIV>
											<DIV>${i.cenaSbEur}</DIV></td>
										<td align="right" style="font-size: x-small;"><DIV>${i.cenaIaCzk}</DIV>
											<DIV>${i.cenaIaEur}</DIV></td>
										<td align="right" style="font-size: x-small;"><DIV>${i.cenaIbCzk}</DIV>
											<DIV>${i.cenaIbEur}</DIV></td>
										<td align="right" style="font-size: x-small;"><DIV>${i.cenaMaCzk}</DIV>
											<DIV>${i.cenaMaEur}</DIV></td>
										<td align="right" style="font-size: x-small;"><DIV>${i.cenaMbCzk}</DIV>
											<DIV>${i.cenaMbEur}</DIV></td>
										<td align="left" style="font-size: x-small;"><DIV>CZK</DIV>
											<DIV>EUR</DIV></td>
										<td align="right" style="font-size: x-small;">${i.cenaOrigLocal}</td>
										<td align="left" style="font-size: x-small;">${i.mena}</td>
										<td align="left" style="font-size: x-small;">${i.me}</td>
										<td nowrap="nowrap" align="left" style="font-size: x-small; overflow: hidden;" title="${i.dodavatel}&#13;Čís.dod.:${i.cisloDodavatele}">${i.dodavatel}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="formBar">
						<span>
							<a href="${pageContext.servletContext.contextPath}/srv/archiv/cenik/exportXls">
								<input type="button" value="Export EXCEL" class="heroBtn"></input>
							</a>
						</span>
						<c:if test="${pocetNactenychZaznamu>maxLimitNaZobrazeni}">
							<SPAN style="color: red;">Proveden velký výběr dat! Načteno ${pocetNactenychZaznamu} záznamu, zobrazeno bude pouze prvních ${maxLimitNaZobrazeni}.</SPAN>
						</c:if>
					</div>
				</c:if>
			</div>
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
	</div>
</body>
	</html>
</jsp:root>