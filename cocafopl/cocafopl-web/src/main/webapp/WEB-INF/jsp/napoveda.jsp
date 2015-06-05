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

	});
</script>
</head>
<body class="pages">
	<div class="page basePage">
		<c:set scope="request" var="selectedMenu" value="napoveda" />
		<jsp:include page="header.jsp" />
		<div class="pageBody">
			<div class="mainAreaWide">
				<H3>Informace o aplikaci</H3>
				<DIV style="padding-left: 20px; font-size: 14px;">
					Aplikace zajišťuje systémovou podporu pro tvorbu a vyhodnocení materiálových kalkulací a mimořádných výbav sloužících pro plánování materiálových nákladů vozů vyráběných v
					zahraničních závodech (v současnosti závod SAIPL Indie, VGR NiNo a Kaluga) a to především pro vozy Škoda.<BR /> <BR /> <B>Klíčový uživatel:</B> Konečný
					David (ECA)<BR /> <B>EO partner:</B> Grecman Petr (EOT)<BR />
				</DIV>
				<BR /> <BR />
				<c:if test="${not empty napovedaList}">
					<div class="tableContainer">
						<table class="dataTable" id="tableId" style="table-layout: fixed;">
							<col width="150px" />
							<col width="*" />
							<col width="55px" />
							<thead>
								<tr>
									<th>Téma</th>
									<th>Popis</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="i" items="${napovedaList}" varStatus="iterator">
									<tr class="${ (iterator.index mod 2) == 0 ? 'rowOdd' : 'rowEven' }">
										<td>${i.tema}</td>
										<td>${i.popis}</td>
										<td><c:if test="${adminRole}">
												${i.poradi}<a onClick="return confirm('Opravdu smazat?')" href="${pageContext.servletContext.contextPath}/srv/napoveda/smazat/${i.id}">
													<img title="Smazat" style="border: 0px;" src="${pageContext.servletContext.contextPath}/resources/ico/smazat.png" />
												</a>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<BR />
				<c:if test="${adminRole}">
					<form:form commandName="napoveda" name="kontrola" action="${pageContext.servletContext.contextPath}/srv/napoveda/nova">
						<TABLE id="tab1" width="100%" style="table-layout: fixed;">
							<col width="150px" />
							<col width="1000px" />
							<col width="50px" />
							<col width="*" />
							<tbody>
								<TR>
									<TD><form:input class="textovePole" cssStyle="width:150px" path="tema"></form:input></TD>
									<TD><form:input class="textovePole" cssStyle="width:1000px" path="popis"></form:input></TD>
									<TD><form:input class="textovePole" cssStyle="width:50px" path="poradi"></form:input></TD>
									<TD><input type="submit" value="Přidej" class="submit" /></TD>
								</TR>
							</tbody>
						</TABLE>
					</form:form>
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