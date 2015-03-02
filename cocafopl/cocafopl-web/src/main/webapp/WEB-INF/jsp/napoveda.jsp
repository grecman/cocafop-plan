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
		modalConfirmationDeleteAll();
	});
</script>
</head>
<body class="pages">

	<div class="page basePage">

		<c:set scope="request" var="selectedMenu" value="napoveda" />
		<jsp:include page="header.jsp" />

		<div class="pageBody">
			<div class="mainAreaWide">
	

			</div>

			<div id="dialog-confirm" title="Smazat vše?">
				<p> Všechny položky budou nenávratně smazány. Opravdu provést?</p>
			</div>

			<div class="messages" id="messages">
				<div id="feedback" class="feedback">&#160;</div>
			</div>
		</div>

		<div class="pageFooter">
			<div class="buttonBar">
				<span> <a id="opener" class="heroBtn">Smazat vše</a></span>
			</div>
			<jsp:include page="footerInfo.jsp" />
		</div>
	</div>

</body>
	</html>
</jsp:root>