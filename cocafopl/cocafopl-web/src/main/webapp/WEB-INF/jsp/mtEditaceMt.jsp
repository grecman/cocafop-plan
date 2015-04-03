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
				$("#formEditMtButton").click(
						function() {
							var mt = (!$("#modelTr").val().match(/^[a-zA-Z0-9]{2}$/) ? "Modelová třída je špatně zadána: " + $("#modelTr").val() + "\n" : "");
							var zavod = (!$("#zavod").val().match(/^[a-zA-Z0-9]{2}$/) ? "Závod je špatně zadán: " + $("#zavod").val() + "\n" : "");
							var kodZeme = (!$("#kodZeme").val().match(/^[a-zA-Z0-9]{3}$/) ? "Kód země je špatně zadán: " + $("#kodZeme").val() + "\n" : "");
							var popis = (!$("#popis").val().match(/^[-,.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{0,29}$/) ? "Popis je špatně zadán: " + $("#popis").val()
									+ "\n\tNejspíše obsahuje nepovolené znaky.\n\tPovolené znaky: a-z A-Z 0-9 áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ-,." + "\n" : "");
							var platnostOd = (!$("#platnostOd").val().match(/^[0-9]{6}$/) ? "Platnost OD je špatně zadáná: " + $("#platnostOd").val()
									+ "\n\t Očekává se formát RRRRMM (př:201502)." : "");
							var platnostDo = (!$("#platnostDo").val().match(/^[0-9]{6}$/) ? "Platnost DO je špatně zadáná: " + $("#platnostDo").val()
									+ "\n\t Očekává se formát RRRRMM (př:999912)." : "");
							var result = mt + zavod + kodZeme + popis + platnostOd + platnostDo;
							if (result.length == 0) {
								$("#formEditMt").submit();
							} else {
								alert(result);
							}
						});
			});
</script>

</head>
<body class="pages">

	<div class="page basePage">

		<c:set scope="request" var="selectedMenu" value="kalkulace" />
		<c:set scope="request" var="selectedSubMenu" value="mt" />


		<jsp:include page="header.jsp" />

		<div class="submenu">
			<div class="items">
				<a class="${selectedSubMenu eq 'mt' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/kalkulace/mtDefinice/">Modelové třídy</a>
				<a class="${selectedSubMenu eq 'kalkulaceSeznam' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/kalkulace/seznam">Seznam
					kalkulací</a> <a class="${selectedSubMenu eq 'kalkulaceDetail' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail">Detail
					kalkulace</a>
			</div>
		</div>

		<div class="pageBody">
			<div class="mainAreaWide">
				<form:form commandName="mt" id="formEditMt" action="${pageContext.servletContext.contextPath}/srv/kalkulace/editMtFormSubmit/">
					<form:hidden path="id" value="${mtInput.id}"></form:hidden>
					<form:hidden path="modelTr" value="${mtInput.modelTr}"></form:hidden>
					<form:hidden path="zavod" value="${mtInput.zavod}"></form:hidden>
					<TABLE style="padding-left: 15px;">
						<TR>
							<TD style="width: 170px; height: 30px; font-weight: bold;">Modelová třída</TD>
							<TD style="font-weight: bold;">${mtInput.modelTr}</TD>
						</TR>
						<TR>
							<TD style="width: 170px; height: 30px; font-weight: bold;">Závod</TD>
							<TD style="font-weight: bold;">${mtInput.zavod}</TD>
						</TR>
						<TR>
							<TD style="width: 170px; height: 30px; font-weight: bold;">Kód země (výchozí)<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="kodZeme" id="kodZeme" value="${mtInput.kodZeme}" class="textovePole" cssStyle="width:30px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 170px; height: 30px; font-weight: bold;">Popis</TD>
							<TD><form:input path="popis" id="popis" value="${mtInput.popis}" class="textovePole" cssStyle="width:300px"></form:input></TD>
						</TR>
						<TR>
							<TD style="width: 170px; height: 30px; font-weight: bold;">Platnost OD<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input  path="platnostOd" id="platnostOd" value="${mtInput.platnostOd}" class="textovePole" cssStyle="width:60px"></form:input><SPAN
								style="font-size: x-small; color: gray;"> V případě, že existuje pro tuto modelovou třídu schválaná kalkulace nebo okomunikovaný představitel, tak platnost nebude změněna.</SPAN></TD>
						</TR>
						<TR>
							<TD style="width: 170px; height: 30px; font-weight: bold;">Platnost DO<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="platnostDo" id="platnostDo" value="${mtInput.platnostDo}" class="textovePole" cssStyle="width:60px"></form:input><SPAN
								style="font-size: x-small; color: gray;"> Minimální hodnota této platnosti je první neschválená kalkulace pro vybranou modelovou třídu. V případě zadání nižší hodnoty proběhne automatická oprava.</SPAN></TD>
						</TR>
						<TR>
							<TD colspan="2"><SPAN style="color: red; font-weight: bold;">*</SPAN><SPAN style="font-size: x-small; color: gray;"> povinný údaj</SPAN></TD>
						</TR>
					</TABLE>
				</form:form>
				<BR />
				<div class="formBar">
					<span><input type="button" id="formEditMtButton" value="Uložit" class="heroBtn"></input></span>
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