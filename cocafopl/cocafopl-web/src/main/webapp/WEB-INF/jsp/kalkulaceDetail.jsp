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
			"bFilter" : false,
			"ordering" : true,
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
		$("#tableId").on('click', 'tr', function() {
			$(this).addClass('selectedTableRow').siblings().removeClass('selectedTableRow');
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
					kalkulací</a> <a class="${selectedSubMenu eq 'kalkulaceDetail' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail">Kalkulace</a>
			</div>
		</div>

		<div class="pageBody">
			<div class="mainAreaWide">
			 
				<div class="formBar">
					<span style="margin-right: 0px;">&#160;Kalkulace:</span> <span style="margin-top: 3px; margin-left: 2px; margin-right: 2px;"><a
						href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail/minusMesic"> <img title="Editace"
							src="${pageContext.servletContext.contextPath}/resources/ico/gre/go_left_30.png" /></a></span> <span
						style="font-size: 20px; margin-top: 8px; margin-left: 2px; margin-right: 2px;">${kalukaceRRRRMM}</span> <span
						style="margin-top: 3px; margin-left: 2px; margin-right: 2px;"><a href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail/plusMesic">
							<img title="Editace" src="${pageContext.servletContext.contextPath}/resources/ico/gre/go_right_30.png" />
					</a></span>
				</div>
				 
				 <div class="tableContainer">
					<table class="dataTable" id="tableId">
						<col width="100px" />
						<col width="100px" />
						<col width="100px" />
						<col width="*" />
						<col width="80px" />
						<col width="150px" />
						<col width="1px" />
						<col width="170px" />
						<col width="150px" />
						<col width="90px" />
						<col width="150px" />
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
								<th>Zpracováno při posledním výpočtu</th>
								<th>Schváleno</th>
								<th>Schválil</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mtk}" var="i">
								<tr>
									<td align="center" style="display: none;">${i.id}</td>
									<td align="center">${i.gz39tMt.modelTr}</td>
									<td align="center">${i.gz39tMt.produkt}</td>
									<td align="center">${i.gz39tMt.zavod}</td>
									<td align="left">${i.gz39tMt.popis}</td>
									<td align="center">puntk</td>
									<td align="center" title="${i.posledniEditaceDuvod}"><f:formatDate value="${i.posledniEditace}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td></td>
									<td align="center">zpracovano</td>
									<td align="center">schvaleno</td>
									<td align="center">schvalil</td>
									<td align="center"><a href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail/..."> <img title="Detail kalkulace"
											src="${pageContext.servletContext.contextPath}/resources/ico/browse.png" /></a>&#160; <c:if test="${moznoEditovat and empty(i.schvalil)}">
											<a href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail/..."><img title="Editace"
												src="${pageContext.servletContext.contextPath}/resources/ico/edit.png" /></a>&#160;
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
				<c:if test="${moznoEditovat}">
					<span><a href="${pageContext.servletContext.contextPath}/srv/kalkulace/kalkulaceNova"><input type="button" id="formEditMtButton"
							value="Spustit výpočet" class="heroBtn"></input></a></span>
					<span><a href="${pageContext.servletContext.contextPath}/srv/kalkulace/kalkulaceNova"><input type="button" id="formEditMtButton"
							value="Schválit kalkulaci" class="heroBtn"></input></a></span>
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