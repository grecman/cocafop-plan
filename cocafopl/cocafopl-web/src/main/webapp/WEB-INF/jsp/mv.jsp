<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:form="http://www.springframework.org/tags/form" xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">
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
				$("#formMvButton").click(
						function() {
							var mt = (!$("#modelovyKlic").val().match(/^[a-zA-Z0-9]{4}$/) ? "Modelový klíč je špatně zadán: " + $("#modelovyKlic").val() + "\n" : "");
							var rok = (!$("#rok").val().match(/^[0-9]{4}$/) ? "Modelový rok je špatně zadán: " + $("#rok").val() + "\n" : "");
							var kodZeme = (!$("#kodZeme").val().match(/^[a-zA-Z0-9]{3}$/) ? "Kód země je špatně zadán: " + $("#kodZeme").val() + "\n" : "");
							var popis = (!$("#popis").val().match(/^[-,.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{1,49}$/) ? "Popis je špatně zadán: " + $("#popis").val()
									+ "\n\tNejspíše obsahuje nepovolené znaky.\n\tPovolené znaky: a-z A-Z 0-9 áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ-,." + "\n" : "");
							var vybavyZaklad = (!$("#vybavyZaklad").val().match(/^((([/+]{1})([A-Za-z0-9]{3}))+){0,20}$/) ? "Výbavy pro základní vůz jsou špatně zadány: "
									+ $("#vybavyZaklad").val() + "\n\tJe třeba dodržet požadovaný formát (bez mezer): +L0L+A8G+3FE" + "\n" : "");
							var vybavyVybav = (!$("#vybavyVybav").val().match(/^((([/+]{1})([A-Za-z0-9]{3}))+){0,20}$/) ? "Výbavy pro vybavený vůz jsou špatně zadány: "
									+ $("#vybavyVybav").val() + "\n\tJe třeba dodržet požadovaný formát (bez mezer): +L0L+A8G+3FE" + "\n" : "");
							var result = mt + rok + kodZeme + popis + vybavyZaklad + vybavyVybav;
							if (result.length == 0) {
								$("#formMv").submit();
							} else {
								alert(result);
							}
						});

				$('.popup').click(function(event) {
					event.preventDefault();
					window.open($(this).attr("href"), "popupWindow", "width=850,height=300,scrollbars=no,menubar=no,location=no");
				});
			});
</script>
</head>
<body class="pages">
	<div class="page basePage">
		<c:set scope="request" var="selectedMenu" value="mv" />
		<jsp:include page="header.jsp" />
		<div class="pageBody">
			<div class="mainAreaWide">
				<c:if test="${empty mvZaklad}">
					<div class="formBar">
						<c:choose>
							<c:when test="${empty archKalkulaceRRRRMM}">
								<form:form commandName="archKalkulace" action="${pageContext.servletContext.contextPath}/srv/mv/mtZav">
									<SPAN>Kalkulace</SPAN>
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
								<SPAN>Kalkulace</SPAN>
								<a href="${pageContext.servletContext.contextPath}/srv/mv/kalkulace">
									<SPAN style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${archKalkulaceRRRRMM}</SPAN>
								</a>
							</c:otherwise>
						</c:choose>
						<c:if test="${not empty archKalkulaceRRRRMM}">
							<c:choose>
								<c:when test="${empty vybranaMt}">
									<form:form commandName="archKalkulaceMtZavView" id="formMv" action="${pageContext.servletContext.contextPath}/srv/mv/param">
										<SPAN>&#160;Modelová třída a závod:&#160;</SPAN>
										<SPAN>
											<form:select onchange="this.form.submit(); return true;" path="idPom">
												<form:option value="0"> . . .  </form:option>
												<c:forEach var="i" items="${mtZavodList}">
													<form:option value="${i.idPom}">${i.modelTr}-${i.zavod}</form:option>
												</c:forEach>
											</form:select>
										</SPAN>
									</form:form>
								</c:when>
								<c:otherwise>
									<SPAN>&#160;Modelová třída a závod:&#160;:</SPAN>
									<a href="${pageContext.servletContext.contextPath}/srv/mv/mtZav">
										<SPAN style="color: #4BA82E; font-weight: bold; margin-left: 0px; margin-right: 0px;">${vybranaMt}-${vybranyZavod}</SPAN>
									</a>
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
				</c:if>
				<c:if test="${(empty mvZaklad) and (not empty archKalkulaceRRRRMM) and (not empty vybranaMt)}">
					<form:form commandName="uniObj" id="formMv" action="${pageContext.servletContext.contextPath}/srv/mv/saveMv">
						<form:hidden path="modelovaTrida" value="${vybranaMt}" />
						<form:hidden path="zavod" value="${vybranyZavod}" />
						<div class="formBar">
							<span> Modelový klíč</span>
							<span>
								<span style="margin-top: 0px; margin-right: 1px;">${vybranaMt}</span>
								<form:input class="textovePole" path="modelovyKlic" id="modelovyKlic" cssStyle="width:4em;"></form:input>
							</span>
							<span> Modelový rok</span>
							<span>
								<form:input class="textovePole" path="rok" id="rok" cssStyle="width:4em;"></form:input>
							</span>
							<span> Kód země</span>
							<span>
								<form:input class="textovePole" path="kodZeme" id="kodZeme" cssStyle="width:3em;"></form:input>
							</span>
							<span> Popis</span>
							<span>
								<form:input class="textovePole" path="popis" id="popis" cssStyle="width:33em;"></form:input>
							</span>
						</div>
						<div class="formBar">
							<span> Výbavy pro základní vůz&#160;&#160;</span>
							<span>
								<form:input class="textovePole" path="vybavyZaklad" id="vybavyZaklad" cssStyle="width:40em;"></form:input>
							</span>
						</div>
						<div class="formBar">
							<span> Výbavy pro vybavený vůz</span>
							<span>
								<form:input class="textovePole" path="vybavyVybav" id="vybavyVybav" cssStyle="width:40em;"></form:input>
							</span>
							<span>
								<input type="button" id="formMvButton" value="Získat PR popis" class="heroBtn"></input>
							</span>
						</div>
					</form:form>
				</c:if>
				<c:if test="${not empty mvZaklad}">
					<table style="padding-left: 20px;">
						<col width="150px" />
						<col width="180px" />
						<col width="130px" />
						<col width="180px" />
						<col width="130px" />
						<col width="180px" />
						<col width="300px" />
						<tr>
							<td>Datum</td>
							<td><SPAN style="background-color: white;"> &#160; ${mvZaklad.kalkulacniDatum} &#160; </SPAN></td>
							<td>Modelový klíč</td>
							<td><SPAN style="background-color: white;">&#160;${mvZaklad.modelovyKlic}&#160;</SPAN></td>
							<td>Modelový rok</td>
							<td><SPAN style="background-color: white;">&#160;${mvZaklad.mrok}&#160;</SPAN></td>
							<td></td>
						</tr>
						<tr style="height: 10px;" />
						<tr>
							<td>Kód země</td>
							<td><SPAN style="background-color: white;">&#160;${mvZaklad.kodZeme}&#160;</SPAN></td>
							<td>Popis</td>
							<td colspan="3" style="background-color: white;">${mvZaklad.popis}</td>
							<td></td>
						</tr>
						<tr style="height: 20px;" />
						<tr>
							<td style="font-weight: bold;">Základní vůz</td>
						</tr>
						<tr style="height: 10px;" />
						<tr>
							<td>Výbavy</td>
							<td colspan="3" style="background-color: white;">${mvZaklad.vybavy}</td>
						</tr>
						<tr style="height: 10px;" />
						<tr>
							<td>PR popis</td>
							<td style="background-color: white; font-family: monospace;" colspan="7"><c:forEach items="${mvZakladPr}" var="i">
									<a href="${pageContext.servletContext.contextPath}/srv/mv/detailPr/${mvZaklad.id}/${i.pr}" class="popup">
										<div style="display: inline-block; padding-right: 7px; padding-bottom: 1px;">
											<c:choose>
												<c:when test="${(not empty rozdilneRodiny) and (fn:contains(rozdilneRodiny, i.rodina))}">
													<SPAN style="color: red;">${i.pr}</SPAN>
												</c:when>
												<c:otherwise>${i.pr}
													</c:otherwise>
											</c:choose>
										</div>
									</a>
								</c:forEach></td>
						</tr>
						<tr style="height: 10px;" />
						<tr>
							<td align="right" colspan="7" style="font-size: xx-small; color: grey;" title="${mvZakladMessageVyjimky}">Chyby MBV/FAVAS nadefinované ve vyjímkách.</td>
						</tr>
						<tr>
							<td>Chyby MBV/FAVAS</td>
							<td style="background-color: white; font-family: monospace;" colspan="6"><c:forEach items="${mvZakladMessage}" var="i">
									<div class="boxPR" style="display: inline-block; padding-right: 7px; padding-bottom: 1px;">${i.kod}-${i.text}</div>
								</c:forEach></td>
						</tr>
						<tr style="height: 30px;" />
						<tr>
							<td style="font-weight: bold;">Vybavený vůz</td>
						</tr>
						<tr style="height: 10px;" />
						<tr>
							<td>Výbavy</td>
							<td colspan="3" style="background-color: white;">${mvVybav.vybavy}</td>
						</tr>
						<tr style="height: 10px;" />
						<tr>
							<td>PR popis</td>
							<td style="background-color: white; font-family: monospace;" colspan="7"><c:forEach items="${mvVybavPr}" var="i">
									<a href="${pageContext.servletContext.contextPath}/srv/mv/detailPr/${mvVybav.id}/${i.pr}" class="popup">
										<div style="display: inline-block; padding-right: 7px; padding-bottom: 1px;">
											<c:choose>
												<c:when test="${(not empty rozdilneRodiny) and (fn:contains(rozdilneRodiny, i.rodina))}">
													<SPAN style="color: red;">${i.pr}</SPAN>
												</c:when>
												<c:otherwise>${i.pr}
													</c:otherwise>
											</c:choose>
										</div>
									</a>
								</c:forEach></td>
						</tr>
						<tr style="height: 10px;" />
						<tr>
							<td align="right" colspan="7" style="font-size: xx-small; color: grey;" title="${mvVybavMessageVyjimky}">Chyby MBV/FAVAS nadefinované ve vyjímkách.</td>
						</tr>
						<tr>
							<td>Chyby MBV/FAVAS</td>
							<td style="background-color: white; font-family: monospace;" colspan="6"><c:forEach items="${mvVybavMessage}" var="i">
									<div class="boxPR" style="display: inline-block; padding-right: 7px; padding-bottom: 1px;">${i.kod}-${i.text}</div>
								</c:forEach></td>
						</tr>
						<tr style="height: 10px;" />
					</table>
				</c:if>
			</div>
		</div>
		<c:if test="${fn:contains(userRole, 'USERS') and (not empty mvZaklad)}">
			<div class="formBar">
				<span>
					<a href="${pageContext.servletContext.contextPath}/srv/mv/smazatMv">
						<input type="button" value="Smazat MV" class="heroBtn"></input>
					</a>
				</span>
				<c:if test="${(not empty mvZakladPr) and (not empty mvVybavPr)}">
					<span>
						<a href="${pageContext.servletContext.contextPath}/srv/mv/vypocet">
							<input type="button" value="Výpočet a export" class="heroBtn"></input>
						</a>
					</span>
				</c:if>
			</div>
		</c:if>
	</div>
	<div class="pageFooter">
		<jsp:include page="footerInfo.jsp" />
	</div>
</body>
	</html>
</jsp:root>