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

		<c:set scope="request" var="selectedMenu" value="archiv" />
		<c:set scope="request" var="selectedSubMenu" value="kusyNaProvedeni" />
		
		<jsp:include page="header.jsp" />
  
		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'kalkulace' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kalkulace">Kalkulace</a>
				<a class="${selectedSubMenu eq 'kusovnik' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kusovnik">Kusovník</a>
				<a class="${selectedSubMenu eq 'cenik' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/cenik">Ceník</a>
				<a class="${selectedSubMenu eq 'kusyNaProvedeni' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kusyNaProvedeni">Kusy na provedení</a>
				<a class="${selectedSubMenu eq 'predstavitel' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel">Představitel</a>
				<a class="${selectedSubMenu eq 'dilVPredstavitelych' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/dilVPredstavitelich">Díl v představitelích</a>
				<a class="${selectedSubMenu eq 'kurzovniListek' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kurzovniListek">Kurzovní lístek</a>
			</div>
		</div>
 
		<div class="pageBody">
			<div class="mainAreaWide">

				
				

			</div>
			<div class="messages" id="messages">
				<div id="feedback" class="feedback">&#160;</div>
			</div>
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
	</div>

</body>
	</html>
</jsp:root>