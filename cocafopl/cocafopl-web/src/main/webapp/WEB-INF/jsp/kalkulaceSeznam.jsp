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
		$("#tableId tr").click(function() {
			// Gre: NACTENI HODNOT ZE RADKU V TABULCE
			// zde pouzivam jen ID daneho radku (tedy Id Kalkulace) pro mazani.
			$(this).addClass('selectedTableRow').siblings().removeClass('selectedTableRow');
			IdKalkulaceVRadku = $(this).find("td:nth-child(1)").html();
			//alert("#tableId tr: "+IdKalkulaceVRadku);
		});
		
		$('#tableId').dataTable({
			"paging":   false,
	        "ordering": false,
	        "info":     false,
	        "bFilter":  true,
			"iDisplayLength": 25,
			"language": {
					// datatables.net/reference/option/language
		            "lengthMenu": "&#160;Zobrazit _MENU_ řádků na stránce",
		            "info": "&#160;Stránka: _PAGE_/_PAGES_, načteno _TOTAL_ záznamů.",
		            "infoEmpty": "Nenalezeny žádné záznamy",
		            "infoFiltered": "&#160;(filtr: _TOTAL_ / _MAX_)",
		            "loadingRecords": "Nahrávám...",
		            "processing":     "Pracuji...",
		            "search":         "Vyhledat:",
		            "zeroRecords":    "Nebyly nalezeny žádné záznamy",
		            "paginate": {
		                "first":      "První",
		                "last":       "Poslední",
		                "next":       "Další",
		                "previous":   "Předcházející"
		            }
		        }
		});

		$("#schavlitKalkulaciMtButton").click(function() {
			//alert("#schavlitKalkulaciMtButton: "+IdKalkulaceVRadku);
			$(window.location).attr('href', '${pageContext.servletContext.contextPath}/srv/kalkulace/schvalit/' + IdKalkulaceVRadku);
		});
	});
</script>

</head>
<body class="pages">

	<div class="page basePage">

		<c:set scope="request" var="selectedMenu" value="kalkulace" />
		<c:set scope="request" var="selectedSubMenu" value="kalkulaceSeznam" />


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
				<c:if test="${not(empty(listRoku))}">
					<span> <form:form commandName="uniObj" action="${pageContext.servletContext.contextPath}/srv/kalkulace/seznam">
						&#160;Rok:&#160;
                            <form:select onchange="this.form.submit(); return true;" path="rok">
								<form:option value="0"> . . .  </form:option>
								<c:forEach var="i" items="${listRoku}">
									<form:option value="${i}">${i}</form:option>
								</c:forEach>
							</form:select>
						</form:form>
					</span>
				</c:if>
					<c:if test="${moznoEditovat}">
						<span><a href="${pageContext.servletContext.contextPath}/srv/kalkulace/kalkulaceNova"><input type="button" id="formEditMtButton"
								value="Přidat rok" class="heroBtn"></input></a></span>
					</c:if>
				</div>


				<div class="tableContainer">
					<table class="dataTable" id="tableId">
						<col width="100px" />
						<col width="*" />
						<col width="200px" />
						<col width="200px" />
						<col width="150px" />
						<col width="90px" />
						<thead>
							<tr>
								<th style="display: none;">Id</th>
								<th>Kalkulace</th>
								<th>Modelové třídy a závody</th>
								<th>Kalkulační datum</th>
								<th>Schváleno</th>
								<th>Schválil</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${kalkulaceProRok}" var="i">
								<tr>
									<td align="center" style="display: none;">${i.idKalkulace}</td>
									<td align="center">${i.kalkulace}</td>
									<td align="left">&#160; ${i.modZav}</td>
									<td align="center">${i.kalkulacniDatum}</td>
									<td align="center"><f:formatDate value="${i.schvaleno}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td align="center">${i.schvalil}</td>
									<td align="center"><a href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail/${i.kalkulace}"> <img title="Detail kalkulace"
											style="border: 0px;" src="${pageContext.servletContext.contextPath}/resources/ico/browse.png" /></a>&#160; 
										<c:if test="${moznoEditovat and empty(i.schvalil)}">
											<a href="${pageContext.servletContext.contextPath}/srv/kalkulace/editKalkulaceForm/${i.idKalkulace}"><img title="Editace" style="border: 0px;" src="${pageContext.servletContext.contextPath}/resources/ico/edit.png" /></a>&#160;
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>

				<!-- *****************************  M  O  D  A  L   **********************************-->
				<div id="openModalSchavit" class="modalWindow">
					<div class="obalovak">
						<a href="#close" class="close">X</a>
						<div class="modalHeader">
							<h3>Schávení kalkulace</h3>
						</div>
						<div class="modalContent">Opravdu chcete schválit vybranou kalkulaci?</div>
						<div class="modalFooter">
							<input type="button" id="schavlitKalkulaciMtButton" value="Schválit" class="ok"></input>
						</div>

					</div>
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