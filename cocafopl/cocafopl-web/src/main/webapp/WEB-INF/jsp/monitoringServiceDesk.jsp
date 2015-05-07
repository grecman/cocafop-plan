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

		<c:set scope="request" var="selectedMenu" value="monitoring" />
		<c:set scope="request" var="selectedSubMenu" value="serviceDesk" />
		
		<jsp:include page="header.jsp" />

  
		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'serviceDesk' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/monitoring/serviceDesk">ServiceDesk</a> 
				<a class="${selectedSubMenu eq 'logging' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/monitoring/logging">Logging</a>
			</div>
		</div>
 
		<div class="pageBody">
			<div class="mainAreaWide">

				<H3>Informace o uživateli</H3>
				<DIV style="padding-left: 20px; font-size: 14px;">
					Uživatelské jméno:<B>${userName}</B><BR /> Role: <B>${userRole}</B>
				</DIV>
				<BR />
				<HR />
				<H3>Informace o aplikaci</H3>
				<DIV style="padding-left: 20px; font-size: 14px;">
					Server: <B>${serverName}</B><BR /> IP: <B>${ip}</B><BR /> Root aplikace: <B>${pageContext.servletContext.contextPath}</B><BR />
				</DIV>
				<BR />
				<HR />
				<H3>Informace o databázi</H3>
				<DIV style="padding-left: 20px; font-size: 14px;">
					Prostředí: <B>${db}</B>
				</DIV>
				<BR />
				<HR />
				<H3>Návštěvní kniha</H3>
				<DIV style="padding-left: 20px; font-size: 14px;">
					Vaše poslední přihlášení: <B>${lastUserLogin}</B><BR /> 
					Do aplikace jste se celkem přihlásil: <B>${userLogin}</B><BR />
					<BR /> Aplikaci používá celkem: <B>${allUsers}</B> uživatelů. 
					<BR /> Počet všech přihlášení: <B>${allUserLogin}</B>
				</DIV>
				<BR />
				<HR />
			</div>
			
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
	</div>

</body>
	</html>
</jsp:root>