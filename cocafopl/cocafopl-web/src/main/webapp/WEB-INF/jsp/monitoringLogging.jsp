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
			"paging":   true,
	        "ordering": true,
	        "order": [[ 2, "desc" ]],
	        "info":     true,
	        "bFilter":  true,
			"iDisplayLength": 25,
			"language": {
					// datatables.net/reference/option/language
		            "lengthMenu": "&#160;Zobrazit _MENU_ řádků na stránce.",
		            "info": "&#160;Stránka: _PAGE_/_PAGES_, načteno _TOTAL_ záznamů.",
		            "infoEmpty": "Nenalezeny žádné záznamy.",
		            "infoFiltered": "&#160;(filtr: _TOTAL_ / _MAX_)",
		            "loadingRecords": "Nahrávám...",
		            "processing":     "Pracuji...",
		            "search":         "Vyhledat:",
		            "zeroRecords":    "Nebyly nalezeny žádné záznamy.",
		            "paginate": {
		                "first":      "První",
		                "last":       "Poslední",
		                "next":       "Další",
		                "previous":   "Předcházející"
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

		<c:set scope="request" var="selectedMenu" value="monitoring" />
		<c:set scope="request" var="selectedSubMenu" value="logging" />

		<jsp:include page="header.jsp" />


		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'serviceDesk' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/monitoring/serviceDesk">ServiceDesk</a>
				<a class="${selectedSubMenu eq 'logging' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/monitoring/logging">Logging</a>
			</div>
		</div>

		<div class="pageBody">
			<div class="mainAreaWide">

				<div class="tableContainer">
					<table class="dataTable" id="tableId">
						<col width="170px" />
						<col width="80px" />
						<col width="155px" />
						<col width="*" />
						<thead>
							<tr>
								<th>action</th>
								<th>user</th>
								<th>time</th>
								<th>info</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${protokol}" var="protokol">
								<tr>
									<td>${protokol.action}</td>
									<td>${protokol.netusername}</td>
									<td><f:formatDate value="${protokol.time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td>${protokol.info}</td>
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