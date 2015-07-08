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
			"paging" : true,
			"ordering" : true,
			"order" : [ [ 0, "asc" ] ],
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

		$("#tableId").on('click', 'tr', function() {
			$(this).addClass('selectedTableRow').siblings().removeClass('selectedTableRow');
		});
	});
</script>
</head>
<body class="pages">
	<div class="page basePage">
		<c:set scope="request" var="selectedMenu" value="archiv" />
		<c:set scope="request" var="selectedSubMenu" value="kurzovniListek" />
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
							<form:form commandName="archKalkulace" action="${pageContext.servletContext.contextPath}/srv/archiv/kurzovniListek/list/czk">
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
							<a href="${pageContext.servletContext.contextPath}/srv/archiv/kurzovniListek">
								<SPAN style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${archKalkulaceRRRRMM}</SPAN>
							</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${not empty archKalkulaceRRRRMM}">
						<SPAN>&#160;&#160;&#160;Vztažná měna:&#160;</SPAN>
						<c:choose>
							<c:when test="${vybranyKurzovniListek=='czk'}">
								<SPAN style="color: blue; font-weight: bold; margin-left: 0px; margin-right: 0px;">CZK</SPAN>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.servletContext.contextPath}/srv/archiv/kurzovniListek/list/czk">
									<SPAN style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">CZK</SPAN>
								</a>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${vybranyKurzovniListek=='eur'}">
								<SPAN style="color: blue; font-weight: bold; margin-left: 15px;">EUR</SPAN>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.servletContext.contextPath}/srv/archiv/kurzovniListek/list/eur">
									<SPAN style="color: #4BA82E; font-weight: bold; margin-left: 15px; margin-right: 0px;">EUR</SPAN>
								</a>
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
				<c:if test="${not empty archKurzovniListekList}">
					<div class="tableContainer">
						<table class="dataTable" id="tableId" style="table-layout: fixed;">
							<col width="50px" />
							<col width="120px" />
							<col width="80px" />
							<col width="120px" />
							<col width="80px" />
							<col width="120px" />
							<col width="80px" />
							<thead>
								<tr>
									<th style="font-size: x-small;">Měna</th>
									<th style="font-size: x-small;">Kurz standard</th>
									<th style="font-size: x-small;">Datum</th>
									<th style="font-size: x-small;">Kurz ist</th>
									<th style="font-size: x-small;">Datum</th>
									<th style="font-size: x-small;">Kurz ist minuly</th>
									<th style="font-size: x-small;">Datum</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${archKurzovniListekList}" var="i">
									<tr>
										<td align="center">${i.sapMena}</td>
										<td align="right">${i.kurzS}</td>
										<td align="center">${i.kurzSDat}</td>
										<td align="right">${i.kurzI}</td>
										<td align="center">${i.kurzIDat}</td>
										<td align="right">${i.kurzM}</td>
										<td align="center">${i.kurzMDat}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="formBar">
						<span>
							<a href="${pageContext.servletContext.contextPath}/srv/archiv/kurzovniListek/exportXls">
								<input type="button" value="Export EXCEL" class="heroBtn"></input>
							</a>
						</span>
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