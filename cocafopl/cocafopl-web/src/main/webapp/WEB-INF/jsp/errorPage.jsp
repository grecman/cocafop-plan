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
</head>
<body class="pages">
	<div class="page basePage">
		<div class="logo">
			<SCRIPT type="text/javascript">
				/* pri prazdnem tagu v DIVu prohlizece neumeji tento div uzavrit a ignoruji nasledujici /DIV, proto tento komentar (btw: normalni komentar nefunguje!!!!) */
			</SCRIPT>
		</div>
		<div class="header">
			<div class="title">COCAFOPPL</div>
			<div class="pageTitle">
				<span id="page.title">${pageTitle}</span>
			</div>
			<div class="info">
				<div class="user">
					<span id="uzivatel">${userName}&#160;</span>
					<!-- <span id="uzivatel" style="color: gray;">|&#160;DE&#160;|&#160;EN&#160;</span> -->
				</div>
				<div class="lang">
					<p style="display: inline;">
						|&#160;
						<a href="#" id="link.cz">CZ</a>
						&#160;
					</p>
					<p style="color: gray; display: inline;">
						|&#160;
						<a href="#" id="link.cz">DE</a>
						&#160;
					</p>
					<p style="color: gray; display: inline;">
						|&#160;
						<a href="#" id="link.cz">EN</a>
						&#160;
					</p>
				</div>
			</div>
			<div class="menu">
				<SCRIPT type="text/javascript">
					/* pri prazdnem tagu v DIVu prohlizece neumeji tento div uzavrit a ignoruji nasledujici /DIV, proto tento komentar (btw: normalni komentar nefunguje!!!!) */
				</SCRIPT>
			</div>
		</div>
		<div class="pageBody">
			<div class="mainAreaWide">
				<BR />
				<H2>${errorMsg}</H2>
				<H4>Kontaktujte klíčového uživatele nebo EO partnera aplikace.</H4>
				<H5>${serverName}</H5>
			</div>
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
	</div>
</body>
	</html>
</jsp:root>