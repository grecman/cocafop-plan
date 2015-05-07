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
					<c:choose>
						<c:when test="${empty archKalkulaceRRRRMM}">
							<form:form commandName="archKalkulace" action="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel/mtZav">
								<SPAN>&#160;Kalkulace:&#160;</SPAN>
								<SPAN>
									<form:select onchange="this.form.submit(); return true;" path="kalkulace">
										<form:option value="0"> . . .  </form:option>
										<c:forEach var="i" items="${archKalkulaceList}">
											<form:option value="${i.kalkulace}">${i.kalkulace}</form:option>
										</c:forEach>
									</form:select>
								</SPAN>
							</form:form>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel">
								<SPAN>&#160;Kalkulace:&#160;</SPAN>
								<SPAN style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${archKalkulaceRRRRMM}</SPAN>
							</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${not empty archKalkulaceRRRRMM}">
						<c:choose>
							<c:when test="${empty vybranaMt}">
								<form:form commandName="archKalkulaceMtZavView" action="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel/list">
									<SPAN>&#160;Modelová třída a závod:&#160;</SPAN>
									<SPAN>
										<form:select onchange="this.form.submit(); return true;" path="id">
											<form:option value="0"> . . .  </form:option>
											<c:forEach var="i" items="${mtZavodList}">
												<form:option value="${i.id}">${i.modelTr}-${i.zavod}</form:option>
											</c:forEach>
										</form:select>
									</SPAN>
								</form:form>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel/mtZav">
									<SPAN>&#160;Modelová třída a závod:&#160;:</SPAN>
									<SPAN style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${akMtZavView.modelTr}-${akMtZavView.zavod}</SPAN>
								</a>
								<SPAN style="margin-left: 20px; margin-right: 0px;">Kalkulační datum:</SPAN>
								<SPAN style="background-color: white;">&#160;${akMtZavView.kalkulacniDatum}&#160;</SPAN>
								<c:choose>
									<c:when test="${not empty akMtZavView.schvaleno}">
										<SPAN style="margin-left: 20px; margin-right: 0px;">Schválil:</SPAN>
										<SPAN style="background-color: white;">&#160;${akMtZavView.schvaleno}&#160;</SPAN>
										<SPAN style="margin-left: 20px; margin-right: 0px;">Schválil:</SPAN>
										<SPAN style="background-color: white;">&#160;${akMtZavView.schvaleno}&#160;</SPAN>
									</c:when>
									<c:otherwise>
										<SPAN style="color: red; font-weight: bolder;">&#160;Pracovní (nechválená) kalkulace!!&#160;</SPAN>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
				<c:if test="${not empty archPredList}">
					<div class="tableContainer">
						<table class="dataTable" id="tableId" style="table-layout: fixed;">
							<col width="35px" />
							<col width="65px" />
							<col width="40px" />
							<col width="40px" />
							<col width="120px" />
							<col width="120px" />
							<col width="100px" />
							<col width="85px" />
							<col width="50px" />
							<col width="*" />
							<col width="60px" />
							<col width="60px" />
							<col width="200px" />
							<col width="60px" />
							<col width="45px" />
							<thead>
								<tr>
									<th style="display: none;">Id</th>
									<th style="font-size: x-small;" title="Číslo představitele">Č. před.</th>
									<th style="font-size: x-small;" title="Modelový klíč">Model. klíč</th>
									<th style="font-size: x-small;" title="Rozloženost">Rozl.</th>
									<th style="font-size: x-small;">Kód země</th>
									<th style="font-size: x-small;">Typ</th>
									<th style="font-size: x-small;">Výbava</th>
									<th style="font-size: x-small;">Obsah</th>
									<th style="font-size: x-small;">Výkon</th>
									<th style="font-size: x-small;">EU norma</th>
									<th style="font-size: x-small;">Poznámka</th>
									<th style="font-size: x-small;">Platnost OD</th>
									<th style="font-size: x-small;">Platnost DO</th>
									<th style="font-size: x-small;">Výbavy</th>
									<th style="font-size: x-small;" title="Modelový rok">MR</th>
									<th style="font-size: x-small;">Comix</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${archPredList}" var="i">
									<tr>
										<td style="display: none;">${i.id}</td>
										<td align="center">${i.cisloPred}</td>
										<td align="left"><a href="${pageContext.servletContext.contextPath}/srv/archiv/predstavitel/detail/${i.id}">
												<span style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${i.modelovyKlic}</span>
											</a></td>
										<td align="center">${i.rozlozenost}</td>
										<td align="center">${i.kodZeme}</td>
										<td align="left">${i.typ}</td>
										<td align="left">${i.vybava}</td>
										<td align="left">${i.obsah}</td>
										<td align="left">${i.vykon}</td>
										<td align="center">${i.euNorma}</td>
										<td align="left">${i.poznamka}</td>
										<td align="center">${i.platnostOd}</td>
										<td align="center">${i.platnostDo}</td>
										<td align="left">${i.vybavy}</td>
										<td align="left">${i.modelovyRok}</td>
										<td align="center"><c:choose>
												<c:when test="${i.comix}">
													<img src="${pageContext.servletContext.contextPath}/resources/ico/ok.png" />
												</c:when>
												<c:otherwise>
													<img src="${pageContext.servletContext.contextPath}/resources/ico/zrusit.png" />
												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</div>
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
	</div>
</body>
	</html>
</jsp:root>