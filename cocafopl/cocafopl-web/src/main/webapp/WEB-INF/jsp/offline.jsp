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
			"order" : [ [ 2, "desc" ] ],
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
		<c:set scope="request" var="selectedMenu" value="offline" />
		<jsp:include page="header.jsp" />
		<div class="pageBody">
			<div class="mainAreaWide">
				<div class="tableContainer">
					<table class="dataTable" id="tableId">
						<col width="80px" />
						<col width="180px" />
						<col width="160px" />
						<col width="160px" />
						<col width="160px" />
						<col width="*" />
						<col width="100px" />
						<thead>
							<tr>
								<th>User</th>
								<th>Agenda</th>
								<th>Čas zadání</th>
								<th>Čas spuštění</th>
								<th>Čas ukončení</th>
								<th>Popis</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${off}" var="off">
								<tr>
									<td>${off.uzivatel}</td>
									<td>${off.agenda}</td>
									<td title="ID:${off.id}"><f:formatDate value="${off.casZadani}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td><f:formatDate value="${off.casSpusteni}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td><f:formatDate value="${off.casUkonceni}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td title="parametr:${off.parametr}">${off.popis}</td>
									<td>${off.status}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="pageFooter">
		<jsp:include page="footerInfo.jsp" />
	</div>
</body>
	</html>
</jsp:root>