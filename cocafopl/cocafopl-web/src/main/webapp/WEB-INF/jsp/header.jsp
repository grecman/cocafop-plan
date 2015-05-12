<?xml version="1.0" encoding="UTF-8" ?>

<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:form="http://www.springframework.org/tags/form" xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">
	<div class="logo"><SCRIPT type="text/javascript">/* gre */</SCRIPT>
		<!-- pri prazdnem tagu v DIVu prohlizece neumeji tento div uzavrit a ignoruji nasledujici /DIV, proto tento komentar -->
	</div>
	<div class="header">
		<div class="title">COCAFOPPL | ${pageTitle}</div>
		<div class="info">
			<div class="user">
				<span id="uzivatel">${userName}&#160;</span>
				<!-- <span id="uzivatel" style="color: gray;">|&#160;DE&#160;|&#160;EN&#160;</span> -->
			</div>
			<div class="lang">
				<p style="display: inline;">
					|&#160;<a href="#" id="link.cz">CZ</a>&#160;
				</p>
				<p style="color: gray; display: inline;">
					|&#160;<a href="#" id="link.cz">DE</a>&#160;
				</p>
				<p style="color: gray; display: inline;">
					|&#160;<a href="#" id="link.cz">EN</a>&#160;
				</p>
			</div>
		</div>
		<div class="menu">
			<a class="${selectedMenu eq 'kalkulace' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail">Kalkulace a modely</a>
			<a class="${selectedMenu eq 'predstavitel' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam">Představitelé</a>
			<a class="${selectedMenu eq 'archiv' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kalkulace">Archív</a>
			<a class="${selectedMenu eq 'mv' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/mv/uvodniZobrazeni">MV</a>
			<a class="${selectedMenu eq 'offline' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/offline">Off-line</a>
 			<a class="${selectedMenu eq 'monitoring' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/monitoring/logging">Monitoring</a>  
			<a class="${selectedMenu eq 'napoveda' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/napoveda">Nápověda</a> 
		</div>
	</div>


</jsp:root>