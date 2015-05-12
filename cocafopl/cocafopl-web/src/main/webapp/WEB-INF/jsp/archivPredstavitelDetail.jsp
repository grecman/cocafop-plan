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
	$(document).ready(
			function() {
				$('.popup').click(function(event) {
					event.preventDefault();
					window.open($(this).attr("href"), "popupWindow", "width=850,height=300,scrollbars=no,menubar=no,location=no");
				});
			});
</script>
</head>
<body class="pages">
	<div class="page basePage">
		<c:set scope="request" var="selectedMenu" value="archiv" />
		<c:set scope="request" var="selectedSubMenu" value="predstavitel" />
		<jsp:include page="header.jsp" />
		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'kalkulace' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kalkulace">Kalkulace</a>
				<a class="${selectedSubMenu eq 'kusovnik' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kusovnik">Kusovník</a>
				<a class="${selectedSubMenu eq 'cenik' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/cenik">Ceník</a>
				<a class="${selectedSubMenu eq 'kusyNaProvedeni' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kusyNaProvedeni">Kusy
					na provedení</a>
				<a class="${selectedSubMenu eq 'predstavitel' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel">Představitel</a>
				<a class="${selectedSubMenu eq 'dilVPredstavitelych' ? 'selected' : 'passive'}"
					href="${pageContext.servletContext.contextPath}/srv/archiv/dilVPredstavitelich">Díl v představitelích</a>
				<a class="${selectedSubMenu eq 'kurzovniListek' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/archiv/kurzovniListek">Kurzovní
					lístek</a>
			</div>
		</div>
		<div class="pageBody">
			<div class="mainAreaWide">
				<div class="formBar">
					<SPAN style="margin-left: 20px; margin-right: 0px;">Kalkulace</SPAN>
					<SPAN style="background-color: white;" title="${p.gz40tKalkulace.kalkulacniDatum}">&#160;${p.gz40tKalkulace.kalkulace}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Modelová třída - závod</SPAN>
					<SPAN style="background-color: white;">&#160;${p.modelTr}-${p.zavod}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Produkt:</SPAN>
					<SPAN style="background-color: white;">&#160;${p.produkt}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Modelový rok:</SPAN>
					<SPAN style="background-color: white;">&#160;${p.modelovyRok}&#160;</SPAN>
				</div>
				<BR />
				<table style="padding-left: 20px;">
					<col width="150px" />
					<col width="180px" />
					<col width="180px" />
					<col width="180px" />
					<col width="180px" />
					<col width="180px" />
					<tr>
						<td>Číslo představitele</td>
						<td><SPAN style="background-color: white;">&#160;${p.cisloPred}&#160;</SPAN></td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Modelový klíč</td>
						<td><SPAN style="background-color: white;">&#160;${p.modelovyKlic}&#160;</SPAN></td>
						<td style="padding-left: 100px;">Kód země</td>
						<td><SPAN style="background-color: white;">&#160;${p.kodZeme}&#160;</SPAN></td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>EU norma</td>
						<td style="background-color: white;">${p.euNorma}</td>
						<td style="padding-left: 100px;">Rozloženost</td>
						<td style="background-color: white;">${p.rozlozenost}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Typ / výbava</td>
						<td colspan="3" style="background-color: white;">${p.typ}&#160;&#160;/&#160;&#160;${p.vybava}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Obsah / výkon</td>
						<td style="background-color: white;" colspan="3">${p.obsah}&#160;&#160;/&#160;&#160;${p.vykon}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Poznámka</td>
						<td style="background-color: white;" colspan="3">${p.poznamka}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Platnost OD-DO</td>
						<td style="background-color: white;">${p.platnostOd}-${p.platnostDo}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Výbavy</td>
						<td style="background-color: white;">${p.vybavy}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Comix</td>
						<td><c:choose>
								<c:when test="${p.comix}">
									<img src="${pageContext.servletContext.contextPath}/resources/ico/ok.png" />
								</c:when>
								<c:otherwise>
									<img src="${pageContext.servletContext.contextPath}/resources/ico/zrusit.png" />
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr style="height: 10px;" />

						<tr>
							<td>PR popis</td>
						</tr>
						<tr>
							<td style="background-color: white; font-family: monospace;" colspan="6">
								<c:forEach items="${prPodminky}" var="i">
									<c:choose>
										<c:when test="${empty i.prEditovane}">
											<a href="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel/detailPr/${p.id}/${i.pr}" class="popup">
												<div style="display: inline-block; padding-right: 7px; padding-bottom: 1px;">${i.pr}</div>
											</a>
										</c:when>
										<c:otherwise>
											<a href="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel/detailPr/${p.id}/${i.prEditovane}" class="popup">
												<div style="color: red; display: inline-block; padding-right: 7px; padding-bottom: 1px;" title="${i.pr}">${i.prEditovane}</div>
											</a>
										</c:otherwise>
									</c:choose>
								</c:forEach></td>
						</tr>
				</table>
				<BR />
				<div class="formBar">
					<span>
						<a href="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel/list">
							<input type="button" value="Zpět" class="heroBtn"></input>
						</a>
					</span>
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