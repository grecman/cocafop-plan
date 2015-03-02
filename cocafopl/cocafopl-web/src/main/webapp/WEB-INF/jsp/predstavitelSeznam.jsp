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

		<c:set scope="request" var="selectedMenu" value="predstavitel" />
		<c:set scope="request" var="selectedSubMenu" value="seznam" />

		<jsp:include page="header.jsp" />

		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'definice' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/predstavitel/definice">Definice
					představitelů</a> <a class="${selectedSubMenu eq 'seznam' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam">Seznam
					představitelů</a>
			</div>
		</div>

		<div class="formBar">
			<span style="margin-right: 0px;">&#160;Kalkulace:</span> <span style="margin-top: 3px; margin-left: 2px; margin-right: 2px;"> <a
				href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam/minusMesic"> <img title="Editace"
					src="${pageContext.servletContext.contextPath}/resources/ico/gre/go_left_30.png" />
			</a></span> 
			<span style="font-size: 20px; margin-top: 8px; margin-left: 2px; margin-right: 2px;">${kalukaceRRRRMM}</span> <span
				style="margin-top: 3px; margin-left: 2px; margin-right: 2px;"><a href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam/plusMesic">
					<img title="Editace" src="${pageContext.servletContext.contextPath}/resources/ico/gre/go_right_30.png" />
			</a></span> 

			<!-- <c:choose>
				<c:when test="${(empty(vybranyRok))}">
					<span> <form:form commandName="uniObj" action="${pageContext.servletContext.contextPath}/srv/predstavitel/definice/zadatMt">
						&#160;Rok:&#160;
                            <form:select onchange="this.form.submit(); return true;" path="rok">
								<form:option value="0"> . . .  </form:option>
								<c:forEach var="i" items="${listRoku}">
									<form:option value="${i}">${i}</form:option>
								</c:forEach>
							</form:select>
						</form:form>
					</span>
				</c:when>
				<c:otherwise>
					<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/definice"> <SPAN>&#160;Rok:&#160;</SPAN><span
						style="color: #4BA82E; font-weight: bold;">${vybranyRok}</span>
					</a>
				</c:otherwise>
			</c:choose>-->

			<c:if test="${(not(empty(vybranyRok)))}">
				<c:choose>
					<c:when test="${(empty(vybranaMtZavod))}">
						<span> <form:form commandName="uniObj" action="${pageContext.servletContext.contextPath}/srv/predstavitel/definice/list">
						&#160;MT a závod:
                            <form:select onchange="this.form.submit(); return true;" path="mtZavod">
									<form:option value="0"> . . .  </form:option>
									<c:forEach var="i" items="${listMtZavod}">
										<form:option value="${i}">${i}</form:option>
									</c:forEach>
								</form:select>
							</form:form>
						</span>
					</c:when>
					<c:otherwise>
						<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/definice"> <SPAN>&#160;Modelová třída a závod:&#160;</SPAN><span
							style="color: #4BA82E; font-weight: bold;">${vybranaMtZavod}</span>
						</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${not(empty(vybranaMtZavod))}">
				<SPAN style="margin-left: 20px; margin-right: 0px;">Produkt:</SPAN>
				<SPAN style="background-color: white;">&#160;${mt.produkt}&#160;</SPAN>
				<SPAN style="margin-left: 20px; margin-right: 0px;">Popis MT:</SPAN>
				<SPAN style="background-color: white;">&#160;${mt.popis}&#160;</SPAN>
				<SPAN style="margin-left: 20px; margin-right: 0px;">Platnost OD-DO:</SPAN>
				<SPAN style="background-color: white;">&#160;${mt.platnostOd}&#160;-&#160;${mt.platnostDo}&#160;</SPAN>
			</c:if>
		</div>

		<div class="pageBody">
			<div class="mainAreaWide">

				<H3>predstavitel</H3>


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