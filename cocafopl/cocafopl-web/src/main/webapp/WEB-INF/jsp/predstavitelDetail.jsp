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
				$("#formVybavaZmenaButton").click(
						function() {
							var vybavy = (!$("#vybavy").val().match(/^((([/+]{1})([A-Za-z0-9]{3}))+){0,20}$/) ? "Výbavy jsou špatně zadány: " + $("#vybavy").val()
									+ "\n\tJe třeba dodržet požadovaný formát (bez mezer): +2B1+PK4" + "\n" : "");
							var result = vybavy;
							if (result.length == 0) {
								$("#formVybavaZmena").submit();
							} else {
								alert(result);
							}
						});

				$("#formPrZmenaButton").click(function() {
					var pr = (!$("#pr").val().match(/^[A-Za-z0-9]{3}$/) ? "PR číslo je špatně zadáno: " + $("#pr").val() : "");
					var result = pr;
					if (result.length == 0) {
						$("#formPrZmena").submit();
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
		<c:set scope="request" var="selectedMenu" value="predstavitel" />
		<c:set scope="request" var="selectedSubMenu" value="seznam" />
		<jsp:include page="header.jsp" />
		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'definice' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/predstavitel/definice">Definice
					představitelů</a>
				<a class="${selectedSubMenu eq 'seznam' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam">Seznam
					představitelů</a>
			</div>
		</div>
		<div class="pageBody">
			<div class="mainAreaWide">
				<div class="formBar">
					<SPAN style="margin-left: 20px; margin-right: 0px;">Kalkulace</SPAN>
					<SPAN style="background-color: white;" title="${pk.gz39tMtKalkulace.gz39tKalkulace.kalkulacniDatum}">&#160;${pk.gz39tMtKalkulace.gz39tKalkulace.kalkulace}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Modelová třída - závod</SPAN>
					<SPAN style="background-color: white;">&#160;${pk.gz39tMtKalkulace.gz39tMt.modelTr}-${pk.gz39tMtKalkulace.gz39tMt.zavod}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Produkt:</SPAN>
					<SPAN style="background-color: white;">&#160;${pk.gz39tMtKalkulace.gz39tMt.produkt}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Popis MT:</SPAN>
					<SPAN style="background-color: white;">&#160;${pk.gz39tMtKalkulace.gz39tMt.popis}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Platnost OD-DO:</SPAN>
					<SPAN style="background-color: white;">&#160;${pk.gz39tMtKalkulace.gz39tMt.platnostOd}&#160;-&#160;${pk.gz39tMtKalkulace.gz39tMt.platnostDo}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Modelový rok:</SPAN>
					<SPAN style="background-color: white;">&#160;${pk.gz39tMtKalkulace.mrok}&#160;</SPAN>
				</div>
				<BR />
				<table style="padding-left: 20px;">
					<col width="150px" />
					<col width="180px" />
					<col width="180px" />
					<col width="180px" />
					<col width="180px" />
					<col width="180px" />
					<col width="205px" />
					<tr>
						<td title="${pk.gz39tPredstavitel.id}">Číslo představitele</td>
						<td title="${pk.gz39tPredstavitel.id}"><SPAN style="background-color: white;">&#160;${pk.gz39tPredstavitel.cisloPred}&#160;</SPAN></td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Modelový klíč</td>
						<td><SPAN style="background-color: white;">&#160;${pk.gz39tPredstavitel.modelovyKlic}&#160;</SPAN></td>
						<td style="padding-left: 100px;">Kód země</td>
						<td><SPAN style="background-color: white;">&#160;${pk.gz39tPredstavitel.kodZeme}&#160;</SPAN></td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>EU norma</td>
						<td style="background-color: white;">${pk.gz39tPredstavitel.euNorma}</td>
						<td style="padding-left: 100px;">Rozloženost</td>
						<td style="background-color: white;">${pk.gz39tPredstavitel.rozlozenost}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Typ / výbava</td>
						<td colspan="3" style="background-color: white;">${pk.gz39tPredstavitel.typ}&#160;&#160;/&#160;&#160;${pk.gz39tPredstavitel.vybava}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Obsah / výkon</td>
						<td style="background-color: white;" colspan="3">${pk.gz39tPredstavitel.obsah}&#160;&#160;/&#160;&#160;${pk.gz39tPredstavitel.vykon}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Poznámka</td>
						<td style="background-color: white;" colspan="3">${pk.gz39tPredstavitel.poznamka}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Platnost OD-DO</td>
						<td style="background-color: white;">${pk.gz39tPredstavitel.platnostOd}-${pk.gz39tPredstavitel.platnostDo}</td>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Výbava původní</td>
						<c:choose>
							<c:when test="${(not empty pk.vybavyEdit) || pk.vybavyEdit=='Potlačit původní výbavu'}">
								<td style="background-color: white; color: gray;" colspan="2">${pk.gz39tPredstavitel.vybavy}</td>
								<td style="padding-left: 70px;">Výbava aktuální</td>
								<td style="background-color: white;" colspan="2">${pk.vybavyEdit}</td>
							</c:when>
							<c:otherwise>
								<td style="background-color: white;" colspan="2">${pk.gz39tPredstavitel.vybavy}</td>
								<td style="padding-left: 70px;">Výbava aktuální</td>
								<td style="background-color: white;" colspan="2">${pk.vybavyEdit}</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr style="height: 10px;" />
					<tr>
						<td>Comix</td>
						<td><c:choose>
								<c:when test="${pk.gz39tPredstavitel.comix}">
									<img src="${pageContext.servletContext.contextPath}/resources/ico/ok.png" />
								</c:when>
								<c:otherwise>
									<img src="${pageContext.servletContext.contextPath}/resources/ico/zrusit.png" />
								</c:otherwise>
							</c:choose></td>
					</tr>
					<tr style="height: 10px;" />
					<c:if test="${pk.existsPr>0}">
						<tr>
							<td title="${pk.id}">PR popis původní</td>
							<td />
							<td />
							<td />
							<td align="right" colspan="3" style="font-size: xx-small; color: grey;">Datum komunikace: <f:formatDate value="${pk.utime}"
									pattern="yyyy-MM-dd HH:mm:ss" />&#160;&#160;&#160;&#160;&#160;&#160;Počet PR: ${pk.existsPr}
							</td>
						</tr>
						<tr>
							<td style="background-color: white; font-family: monospace;" colspan="7"><c:forEach items="${prPodminky}" var="i">
									<c:choose>
										<c:when test="${empty i.prEditovane}">
											<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/detailPr/${pk.id}/${i.pr}" class="popup">
												<div style="display: inline-block; padding-right: 7px; padding-bottom: 1px;">${i.pr}</div>
											</a>
										</c:when>
										<c:otherwise>
											<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/detailPr/${pk.id}/${i.pr}" class="popup">
												<div style="color: red; display: inline-block; padding-right: 7px; padding-bottom: 1px;">${i.pr}</div>
											</a>
										</c:otherwise>
									</c:choose>
								</c:forEach></td>
						</tr>
						<tr style="height: 10px;" />
						<tr>
							<td title="${pk.id}">PR popis korigovaný</td>
						</tr>
						<tr>
							<td style="background-color: white; font-family: monospace;" colspan="7"><c:forEach items="${prPodminky}" var="i">
									<c:choose>
										<c:when test="${(empty i.prEditovane)}">
											<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/detailPr/${pk.id}/${i.pr}" class="popup">
												<div style="display: inline-block; padding-right: 7px; padding-bottom: 1px;">${i.pr}</div>
											</a>
										</c:when>
										<c:otherwise>
											<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/detailPr/${pk.id}/${i.pr}" class="popup">
												<div style="color: red; display: inline-block; padding-right: 7px; padding-bottom: 1px;" title="Původní PR: ${i.pr}">${i.prEditovane}</div>
											</a>
										</c:otherwise>
									</c:choose>
								</c:forEach></td>
						</tr>
						<tr style="height: 10px;" />
					</c:if>
					<c:if test="${not empty prMessage}">
						<tr>
							<td>Chyby MBV/FAVAS</td>
							<td />
							<td />
							<td />
							<td align="right" colspan="3" style="font-size: xx-small; color: grey;" title="${prMessageException}">Chyby MBV/FAVAS nadefinované ve vyjímkách.</td>
						</tr>
						<tr>
							<td style="background-color: white; font-family: monospace;" colspan="7"><c:forEach items="${prMessage}" var="i">
									<div class="boxPR" style="display: inline-block; padding-right: 7px; padding-bottom: 1px;">${i.kod}-${i.text}</div>
								</c:forEach></td>
						</tr>
					</c:if>
				</table>
				<BR />
				<DIV style="color: red; font-weight: bold;">${errorMesage}</DIV>
				<div class="formBar">
					<c:if test="${fn:contains(userRole, 'USERS')}">
						<span>
							<a href="${pageContext.servletContext.contextPath}/srv/komunikaceFavas/0/${pk.id}/vse">
								<input type="button" value="Prověřit MBV/Favas" class="heroBtn" style="width: auto;"></input>
							</a>
						</span>
						<span>
							<!-- <input type="button" id="idButtonKorekceVybavy" value="Korekce výbavy" class="heroBtn"></input> -->
							<a href="#openModalKorekceVybavy">
								<input type="button" value="Korekce výbavy" class="heroBtn"></input>
							</a>
						</span>
						<span>
							<a href="#openModalKorekcePr">
								<input type="button" value="Korekce PR čísla" class="heroBtn"></input>
							</a>
						</span>
					</c:if>
					<span>
						<a href="${pageContext.servletContext.contextPath}/srv/predstavitel/seznam/${pk.gz39tMtKalkulace.gz39tKalkulace.kalkulace}">
							<input type="button" value="Zpět" class="heroBtn"></input>
						</a>
					</span>
				</div>
			</div>
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
		<!-- *****************************  M  O  D  A  L   **********************************-->
		<div id="openModalKorekceVybavy" class="modalWindow">
			<div class="obalovak">
				<a href="#close" class="close">X</a>
				<div class="modalHeader">
					<h3>Korekce výbavy</h3>
				</div>
				<div class="modalContent">
					<form:form commandName="predstavitelKalkulace" id="formVybavaZmena"
						action="${pageContext.servletContext.contextPath}/srv/predstavitel/detail/zmenaVybavy/${pk.id}">
						<TABLE>
							<col width="80px" />
							<col width="*" />
							<TR>
								<TD>Výbava</TD>
								<TD><form:input path="vybavyEdit" id="vybavy" class="textovePole" cssStyle="width:200px"></form:input></TD>
							</TR>
							<TR style="height: 60px;">
								<TD colspan="2" style="font-size: x-small; color: gray;">Nová neboli aktuální výbava má při komunikaci vždy prioritu před výbavou původní. Tlačítko
									"POTLAČIT PŮVODNÍ" zajistí komunikaci s MBV/FAVAS bez výbav.</TD>
							</TR>
						</TABLE>
					</form:form>
				</div>
				<div class="modalFooter">
					<TABLE style="width: 100%;">
						<TR>
							<TD><input type="button" id="formVybavaZmenaButton" value="Uložit" class="heroBtn"></input></TD>
							<TD align="right"><a href="${pageContext.servletContext.contextPath}/srv/predstavitel/detail/potlacitVybavu/${pk.id}">
									<input type="button" value="Potlačit původní" class="heroBtn"></input>
								</a></TD>
							<TD align="right"><a href="${pageContext.servletContext.contextPath}/srv/predstavitel/detail/zrusitVybavu/${pk.id}">
									<input type="button" value="Zrušit korekci" class="heroBtn"></input>
								</a></TD>
						</TR>
					</TABLE>
				</div>
			</div>
		</div>
		<div id="openModalKorekcePr" class="modalWindow">
			<div class="obalovak">
				<a href="#close" class="close">X</a>
				<div class="modalHeader">
					<h3>Korekce PR čísla</h3>
				</div>
				<div class="modalContent">
					<form:form commandName="predstavitelPr" id="formPrZmena" action="${pageContext.servletContext.contextPath}/srv/predstavitel/detail/zmenaPr/${pk.id}">
						<TABLE>
							<col width="80px" />
							<col width="*" />
							<TR>
								<TD>PR číslo</TD>
								<TD><form:input path="pr" id="pr" class="textovePole" cssStyle="width:30px"></form:input></TD>
							</TR>
							<TR style="height: 60px;">
								<TD colspan="2" style="font-size: x-small; color: gray;">Popis korekce - pro zadané PR číslo bude nalezena rodina a následně bude v PR popisu
									zakázky nahrazeno PR číslo se stejnou rodinou. Pokud rodina k zadanému PR číslu nebude nalezena, korekce se neprovede.</TD>
							</TR>
							<TR>
								<TD colspan="2" style="font-size: x-small; color: gray;">Korekci PR čísla lze zrušit opětovným zadaním původního PR čísla. Korekce budou také
									odstraněny společně s celým PR popisem zakázky při další komunikaci s MBV/FAVAS.</TD>
							</TR>
						</TABLE>
					</form:form>
				</div>
				<div class="modalFooter">
					<input type="button" id="formPrZmenaButton" value="Uložit" class="heroBtn"></input>
				</div>
			</div>
		</div>
	</div>
</body>
	</html>
</jsp:root>