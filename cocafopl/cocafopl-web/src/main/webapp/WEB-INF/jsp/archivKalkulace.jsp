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
	var IdKalkulaceVRadku = 0;

	$(document).ready(function() {

		$('#tableId').dataTable({
			"paging" : true,
			"iDisplayLength" : 25,
			"ordering" : true,
			"order": [[ 1, "desc" ]],
			"info" : true,
			"bFilter" : true,
			"language" : {
				// datatables.net/reference/option/language
				"lengthMenu" : "&#160;Zobrazit _MENU_ řádků na stránce",
				"info" : "&#160;Stránka: _PAGE_/_PAGES_, načteno _TOTAL_ záznamů.",
				"infoEmpty" : "Nenalezeny žádné záznamy",
				"infoFiltered" : "&#160;(filtr: _TOTAL_ / _MAX_)",
				"loadingRecords" : "Nahrávám...",
				"processing" : "Pracuji...",
				"search" : "Vyhledat:",
				"zeroRecords" : "Nebyly nalezeny žádné záznamy",
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

		<c:set scope="request" var="selectedMenu" value="archiv" />
		<c:set scope="request" var="selectedSubMenu" value="kalkulace" />

		<jsp:include page="header.jsp" />

		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'kalkulace' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kalkulace">Kalkulace</a> <a
					class="${selectedSubMenu eq 'kusovnik' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kusovnik">Kusovník</a> <a
					class="${selectedSubMenu eq 'cenik' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/cenik">Ceník</a> <a
					class="${selectedSubMenu eq 'kusyNaProvedeni' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kusyNaProvedeni">Kusy
					na provedení</a> <a class="${selectedSubMenu eq 'predstavitel' ? 'selected' : 'passive'}"
					href="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel">Představitel</a> <a
					class="${selectedSubMenu eq 'dilVPredstavitelych' ? 'selected' : 'passive'}"
					href="${pageContext.servletContext.contextPath}/srv/archiv/dilVPredstavitelich">Díl v představitelích</a> <a
					class="${selectedSubMenu eq 'kurzovniListek' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kurzovniListek">Kurzovní
					lístek</a>
			</div>
		</div>
		<div class="pageBody">
			<div class="mainAreaWide">
				
				<div class="tableContainer">
					<table class="dataTable" id="tableId">
						<col width="110px" />
						<col width="*" />
						<col width="160px" />
						<col width="160px" />
						<col width="150px" />
						<col width="100px" />
						<thead>
							<tr>
								<th style="display: none;">Id</th>
								<th>Kalkulace</th>
								<th>Modelové třídy a závody</th>
								<th>Kalkulační datum</th>
								<th>Poslední výpočet</th>
								<th>Schváleno</th>
								<th>Schválil</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${archKalkulace}" var="i">
								<tr>
									<td align="center" style="display: none;">${i.id}</td>
									<td align="center" title="TERKA:${i.terka} TERKACH:${i.terkach}">${i.kalkulace}</td>
									<td align="left">dodělat !!! asi zase přes nějaké view</td>
									<td align="center">${i.kalkulacniDatum}</td>
									<td align="center"><f:formatDate value="${i.posledniVypocet}" pattern="yyyy-MM-dd HH:mm" /></td>
									<td align="center"><f:formatDate value="${i.schvaleno}" pattern="yyyy-MM-dd HH:mm" /></td>
									<td align="center">${i.schvalil}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

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