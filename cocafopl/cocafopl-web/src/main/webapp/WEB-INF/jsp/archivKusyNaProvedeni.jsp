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
							"ordering" : true,
							"order" : [ [ 0, "asc" ], [ 2, "asc" ] ],
							"info" : true,
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

						$("#formButton")
								.click(
										function() {
											if ($("#cisloPred").val() == "") {
												var cdilu = (!$("#cdilu").val().match(/^[_a-zA-Z0-9\u0020]{3,15}$/) ? "Protože není zadáno číslo představitele, tak je nutné v masce pro číslo dílu uvést alespoň 3 znaky (čísla/písmena).\nPolovené je i podtržítko pro zástupný znak. Příklad: 5E_827550_"
														: "");
												var result = cdilu;
												if (result.length == 0) {
													$("#formId").submit();
												} else {
													alert(result);
												}
											} else {
												$("#formId").submit();
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
		<c:set scope="request" var="selectedSubMenu" value="kusyNaProvedeni" />
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
							<form:form commandName="archKalkulace" action="${pageContext.servletContext.contextPath}/srv/archiv/kusyNaProvedeni/prodZav">
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
							<a href="${pageContext.servletContext.contextPath}/srv/archiv/kusyNaProvedeni">
								<SPAN style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${archKalkulaceRRRRMM}</SPAN>
							</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${not empty archKalkulaceRRRRMM}">
						<c:choose>
							<c:when test="${empty vybranaMt}">
								<form:form commandName="archKalkulaceMtZavView" action="${pageContext.servletContext.contextPath}/srv/archiv/kusyNaProvedeni/param">
									<SPAN>&#160;Modelová třída a závod:&#160;</SPAN>
									<SPAN>
										<form:select onchange="this.form.submit(); return true;" path="idPom">
											<form:option value="0"> . . .  </form:option>
											<c:forEach var="i" items="${mtZavodList}">
												<form:option value="${i.idPom}">${i.modelTr}-${i.zavod}</form:option>
											</c:forEach>
										</form:select>
									</SPAN>
								</form:form>
							</c:when>
							<c:otherwise>
								<SPAN>&#160;Modelová třída a závod:&#160;</SPAN>
								<a href="${pageContext.servletContext.contextPath}/srv/archiv/kusyNaProvedeni/prodZav">
									<SPAN style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${vybranaMt}-${vybranyZavod}</SPAN>
								</a>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${not empty vybranaMt}">
						<SPAN>
							<form:form commandName="archKusyProvView" id="formId" action="${pageContext.servletContext.contextPath}/srv/archiv/kusyNaProvedeni/list">
								<SPAN style="margin-top: 0px;">Číslo představitele</SPAN>
								<form:input path="cisloPred" id="cisloPred" class="textovePole" cssStyle="width:30px"></form:input>
								<SPAN style="margin-top: 0px;">Číslo dílu:</SPAN>
								<form:input path="cdilu" id="cdilu" class="textovePole" cssStyle="width:160px"></form:input>
								<input type="button" id="formButton" value="Vyhledat" class="heroBtn" style="display: inline; margin-left: 25px;"></input>
							</form:form>
						</SPAN>
					</c:if>
				</div>
				<c:if test="${not empty archKusyNaProvList}">
					<div class="tableContainer">
						<table class="dataTable" id="tableId" style="table-layout: fixed;">
							<col width="130px" />
							<col width="*" />
							<col width="95px" />
							<col width="80px" />
							<col width="60px" />
							<col width="60px" />
							<col width="60px" />
							<col width="75px" />
							<col width="75px" />
							<col width="75px" />
							<thead>
								<tr>
									<th style="font-size: x-small;">Číslo dílu</th>
									<th style="font-size: x-small;">Název dílu</th>
									<th style="font-size: x-small;">Číslo představitele</th>
									<th style="font-size: x-small;">Množství</th>
									<th style="font-size: x-small;">MJ</th>
									<th style="font-size: x-small;">Závod</th>
									<th style="font-size: x-small;">BZA</th>
									<th style="font-size: x-small;">Škoda</th>
									<th style="font-size: x-small;">VW</th>
									<th style="font-size: x-small;">Local</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${archKusyNaProvList}" var="i">
									<tr>
										<td align="left">${i.cdilu}</td>
										<td align="left">${i.benCz}</td>
										<td align="right">${i.cisloPred}</td>
										<td align="right">${i.mnfin}</td>
										<td align="center">${i.me}</td>
										<td align="center">${i.cizav}</td>
										<td align="center">${i.bza}</td>
										<td align="center">${i.skoda}</td>
										<td align="center">${i.vw}</td>
										<td align="center">${i.local}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="formBar">
						<span>
							<input type="button" id="idButtonExport" value="Export EXCEL" class="heroBtn" style="background-color: gray;"></input>
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