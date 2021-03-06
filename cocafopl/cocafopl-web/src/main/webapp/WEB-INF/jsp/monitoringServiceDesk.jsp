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
				<H3>Informace o uživatelích</H3>
				<DIV style="padding-left: 20px; font-size: 14px;">
					Aktuální uživatel: <B>${userName}</B><BR /> Role: <B>${userRole}</B><BR />
				</DIV>
				<BR />
				<HR />
				<H3>Návštěvní kniha</H3>
				<DIV style="padding-left: 20px; font-size: 14px;">
					Vaše poslední přihlášení: <B>${lastUserLogin}</B><BR /> Do aplikace jste se celkem přihlásil: <B>${userLogin}</B><BR /> <BR /> Aplikaci používá celkem:
					<B>${allUsersCount}</B> uživatelů. <BR /> Počet všech přihlášení: <B>${allUserLoginCount}</B><BR /> <BR />
					<div class="tableContainer">
						<table class="dataTable" id="tableIdx">
							<col width="120px" />
							<col width="120px" />
							<col width="100px" />
							<col width="350px" />
							<col width="100px" />
							<col width="*" />
							<thead>
								<tr>
									<th>Příjmení</th>
									<th>Jméno</th>
									<th>DZC...</th>
									<th>Role</th>
									<th>Oddělení</th>
									<th>E-mail</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${users}" var="i">
									<tr>
										<td>${i.prijmeni}</td>
										<td>${i.jmeno}</td>
										<td>${i.netusername}</td>
										<td>${i.userRole}</td>
										<td>${i.oddeleni}</td>
										<td>${i.email}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</DIV>
				<BR />
				<HR />
				<H3>Informace o aplikaci</H3>
				<DIV style="padding-left: 20px; font-size: 14px;">
					Server: <B>${serverName}</B><BR /> IP: <B>${ip}</B><BR /> Root aplikace: <B>${pageContext.servletContext.contextPath}</B><BR /> Poslední build: <B>${initParam.buildTimeStamp}</B>
				</DIV>
				<BR />
				<HR />
				<H3>Informace o databázi</H3>
				<DIV style="padding-left: 20px; font-size: 14px;">
					Prostředí: <B>${db}</B>
				</DIV>
				<BR />
				<HR />
				<H3>Informace o prohlížeči</H3>
				<script>
					document.write("Jméno prohlížeče: "+navigator.appName);
					document.write("<BR />User Agent: "+navigator.userAgent);
					document.write("<BR />Číslo verze: "+navigator.appVersion);
					document.write("<BR />Jednoduché číslo verze: "+parseInt(navigator.appVersion));
					document.write("<BR />Kódové jméno aplikace: "+navigator.appCodeName);
					
					var isOpera = !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
					document.write("<BR />Opera: "+isOpera);
					var isFirefox = typeof InstallTrigger !== 'undefined'; // Firefox 1.0+
					document.write("<BR />Firefox: "+isFirefox);
					var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
					document.write("<BR />Safari: "+isSafari);
					var isIE = /*@cc_on!@*/ false || !!document.documentMode; // At least IE6
					document.write("<BR />IE: "+isIE);
					var isChrome = !!window.chrome &amp;&amp; !isOpera;
					document.write("<BR />Chrome: "+isChrome);
				</script>
				<BR /> <BR />
			</div>
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
	</div>
</body>
	</html>
</jsp:root>