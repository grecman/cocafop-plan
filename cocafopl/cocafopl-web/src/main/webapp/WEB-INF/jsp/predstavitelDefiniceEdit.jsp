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
				$("#formButton").click(
						function() {

							var mt = (!$("#modelovyKlic4").val().match(/^[a-zA-Z0-9]{4}$/) ? "Modelová třída je špatně zadána: " + $("#modelovyKlic4").val() + "\n" : "");
							var kodZeme = (!$("#kodZeme").val().match(/^[a-zA-Z0-9]{3}$/) ? "Kód země je špatně zadán: " + $("#kodZeme").val() + "\n" : "");
							var typ = (!$("#typ").val().match(/^[-,.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{1,29}$/) ? "Typ je špatně zadán: " + $("#typ").val()
									+ "\n\tNejspíše obsahuje nepovolené znaky nebo je prázdný.\n\tPovolené znaky: a-z A-Z 0-9 áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ-,." + "\n" : "");
							var vybava = (!$("#vybava").val().match(/^[-,.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{1,29}$/) ? "Výbava je špatně zadána: " + $("#vybava").val()
									+ "\n\tNejspíše obsahuje nepovolené znaky nebo je prázdná.\n\tPovolené znaky: a-z A-Z 0-9 áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ-,." + "\n" : "");
							var obsah = (!$("#obsah").val().match(/^[-,.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{1,29}$/) ? "Obsah je špatně zadán: " + $("#obsah").val()
									+ "\n\tNejspíše obsahuje nepovolené znaky nebo je prázdný.\n\tPovolené znaky: a-z A-Z 0-9 áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ-,." + "\n" : "");
							var vykon = (!$("#vykon").val().match(/^[-,.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{1,29}$/) ? "Výkon je špatně zadán: " + $("#vykon").val()
									+ "\n\tNejspíše obsahuje nepovolené znaky nebo je prázdný.\n\tPovolené znaky: a-z A-Z 0-9 áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ-,." + "\n" : "");
							var platnostOd = (!$("#platnostOd").val().match(/^[0-9]{6}$/) ? "Platnost OD je špatně zadáná: " + $("#platnostOd").val()
									+ "\n\t Očekává se formát RRRRMM (př:201502)." + "\n" : "");
							var platnostDo = (!$("#platnostDo").val().match(/^[0-9]{6}$/) ? "Platnost DO je špatně zadáná: " + $("#platnostDo").val()
									+ "\n\t Očekává se formát RRRRMM (př:203812)." + "\n" : "");
							var vybavy = (!$("#vybavy").val().match(/^((([/+]{1})([A-Za-z0-9]{3}))+){0,20}$/) ? "Výbavy jsou špatně zadány: " + $("#vybavy").val()
									+ "\n\tJe třeba dodržet požadovaný formát (bez mezer): +L0L+A8G+3FE" + "\n" : "");
							var result = mt + kodZeme + typ + vybava + obsah + vykon + platnostOd + platnostDo + vybavy;
							if (result.length == 0) {
								$("#formPredstavitel").submit();
							} else {
								alert(result);
							}

						});
			});
</script>
</head>
<body class="pages">
	<div class="page basePage">
		<c:set scope="request" var="selectedMenu" value="predstavitel" />
		<c:set scope="request" var="selectedSubMenu" value="definice" />
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
					<SPAN style="margin-left: 20px; margin-right: 0px;">Modelová třída:</SPAN>
					<SPAN style="background-color: white;">&#160;${modelovaTrida.modelTr}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Závod:</SPAN>
					<SPAN style="background-color: white;">&#160;${modelovaTrida.zavod}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Produkt:</SPAN>
					<SPAN style="background-color: white;">&#160;${modelovaTrida.produkt}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Popis:</SPAN>
					<SPAN style="background-color: white;">&#160;${modelovaTrida.popis}&#160;</SPAN>
					<SPAN style="margin-left: 20px; margin-right: 0px;">Platnost OD - DO:</SPAN>
					<SPAN style="background-color: white;">&#160;${modelovaTrida.platnostOd}&#160;-&#160;${modelovaTrida.platnostDo}&#160;</SPAN>
				</div>
				<form:form commandName="predstavitel" id="formPredstavitel" action="${pageContext.servletContext.contextPath}/srv/predstavitel/definice/editSubmit/">
					<form:hidden path="id" value="${predInput.id}"></form:hidden>
					<TABLE style="padding-left: 15px;">
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Číslo představitele<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:select path="cisloPred">
									<form:option value="${predInput.cisloPred}">${predInput.cisloPred}</form:option>
									<c:forEach var="i" items="${cislaPredstavitelu}">
										<form:option value="${i}">${i}</form:option>
									</c:forEach>
								</form:select></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Minulé číslo</TD>
							<TD><form:input path="cisloPredMin" id="cisloPredMin" value="${predInput.cisloPredMin}" class="textovePole" cssStyle="width:30px"></form:input>
								<SPAN style="font-size: x-small; color: gray;">&#160;Pokud je hodnota vyplněna, tak se jedná o číslo představitele z prosince předchozího roku.</SPAN></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Modelový klíč<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><SPAN style="font-size: 15px;">${modelovaTrida.modelTr}</SPAN> <form:input path="modelovyKlic" id="modelovyKlic4"
									value="${fn:substring(predInput.modelovyKlic, 2, 6)}" class="textovePole" cssStyle="width:45px"></form:input>
								<SPAN style="color: red; font-weight: bold;">!</SPAN>
								<SPAN style="font-size: x-small; color: gray;">&#160; Zadat pouze poslední čtyři znaky modelového klíče.</SPAN></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Kód země<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><c:choose>
									<c:when test="${empty predInput.kodZeme}">
										<form:input path="kodZeme" id="kodZeme" value="${modelovaTrida.kodZeme}" class="textovePole" cssStyle="width:30px"></form:input>
										<SPAN style="font-size: x-small; color: gray;">&#160;Přednastavená hodnota z modelové třídy.</SPAN>
									</c:when>
									<c:otherwise>
										<form:input path="kodZeme" id="kodZeme" value="${predInput.kodZeme}" class="textovePole" cssStyle="width:30px"></form:input>
										<SPAN style="color: red; font-weight: bold;">!</SPAN>
									</c:otherwise>
								</c:choose></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Typ<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="typ" id="typ" value="${predInput.typ}" class="textovePole" cssStyle="width:200px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Výbava<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="vybava" id="vybava" value="${predInput.vybava}" class="textovePole" cssStyle="width:200px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Obsah<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="obsah" id="obsah" value="${predInput.obsah}" class="textovePole" cssStyle="width:200px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Výkon<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="vykon" id="vykon" value="${predInput.vykon}" class="textovePole" cssStyle="width:200px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">EU norma</TD>
							<TD><form:input path="euNorma" id="euNorma" value="${predInput.euNorma}" class="textovePole" cssStyle="width:30px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Rozloženost</TD>
							<TD><form:input path="cisloPred" id="cisloPred" value="${predInput.cisloPred}" class="textovePole" cssStyle="width:30px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Poznámka</TD>
							<TD><form:input path="poznamka" id="poznamka" value="${predInput.poznamka}" class="textovePole" cssStyle="width:300px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Platnost Od<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="platnostOd" id="platnostOd" value="${predInput.platnostOd}" class="textovePole" cssStyle="width:60px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Platnost Do<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="platnostDo" id="platnostDo" value="${predInput.platnostDo}" class="textovePole" cssStyle="width:60px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Výbavy</TD>
							<TD><form:input path="vybavy" id="vybavy" value="${predInput.vybavy}" class="textovePole" cssStyle="width:800px"></form:input>
								<SPAN style="font-size: x-small; color: gray;">&#160;Vzor: +L0L+A8G+3FE (bez mezer)</SPAN></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">Četnost</TD>
							<TD><form:input path="cetnost" id="cetnost" value="${predInput.cetnost}" class="textovePole" cssStyle="width:60px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 190px; height: 30px; font-weight: bold;">COMIX</TD>
							<TD><c:choose>
									<c:when test="${predInput.comix}">
										<form:checkbox path="comix" checked="yes" value="${predInput.comix}" />
									</c:when>
									<c:otherwise>
										<form:checkbox path="comix" value="${predInput.comix}" />
									</c:otherwise>
								</c:choose></TD>
						</TR>
						<TR>
							<TD colspan="2"><SPAN style="color: red; font-weight: bold;">*</SPAN>
								<SPAN style="font-size: x-small; color: gray;"> povinný údaj</SPAN></TD>
						</TR>
						<TR>
							<TD colspan="2"><SPAN style="color: red; font-weight: bold;">!</SPAN>
								<SPAN style="font-size: x-small; color: gray;"> Modelové klíče pro následný export do systému COMIX musí být unikátní. Při nalezení duplicity
									bude pole COMIX automaticky opraveno.</SPAN></TD>
						</TR>
						<TR>
							<TD colspan="2"><SPAN style="color: red; font-weight: bold;">!&#160;</SPAN>
								<SPAN style="font-size: x-small; color: gray;"> V případě editace modelového klíče nebo kódu země, budou následně smazány všechny stávající PR
									čísla k vybranému představiteli ve všech aktivních kalkulacích.</SPAN></TD>
						</TR>
					</TABLE>
				</form:form>
				<div class="formBar">
					<span>
						<input type="button" id="formButton" value="Uložit" class="heroBtn"></input>
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