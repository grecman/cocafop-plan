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
				$("#formMvButton").click(
						function() {
							var datum = (!$("#datumKomunikace").val().match(/^((0[1-9]|[1-9])|[1-2][0-9]|3[0-1]).((0[1-9]|[1-9])|1[0-2]).[0-9]{4}$/) ? "Datum je špatně zadán: "
									+ $("#datumKomunikace").val() + "\n\t Datum musí být ve formátu: dd.mm.rrrr, povoleno také 1.1.2015\n" : "");
							var mt = (!$("#modelovyKlic").val().match(/^[a-zA-Z0-9]{6}$/) ? "Modelový klíč je špatně zadán: " + $("#modelovyKlic").val() + "\n" : "");
							var mrok = (!$("#mrok").val().match(/^[0-9]{4}$/) ? "Modelový rok je špatně zadán: " + $("#mrok").val() + "\n" : "");
							var kodZeme = (!$("#kodZeme").val().match(/^[a-zA-Z0-9]{3}$/) ? "Kód země je špatně zadán: " + $("#kodZeme").val() + "\n" : "");
							var popis = (!$("#popis").val().match(/^[-,.a-zA-Z0-9áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ\u0020]{0,49}$/) ? "Popis je špatně zadán: " + $("#popis").val()
									+ "\n\tNejspíše obsahuje nepovolené znaky.\n\tPovolené znaky: a-z A-Z 0-9 áäéëěíóöôúůüýčďňřšťžĺľĚŠČŘŽÝÁÍÉ-,." + "\n" : "");
							var result = datum + mt + mrok + kodZeme + popis;
							if (result.length == 0) {
								$("#formMv").submit();
							} else {
								alert(result);
							}
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
				<form:form commandName="mvPopis" id="formMv" action="${pageContext.servletContext.contextPath}/srv/mv/showMv/">
					<div class="formBar">
						<form:hidden path="typVozu" value="Z"></form:hidden>
						<span title="Požadovaný formát: 31.09.2015">Datum </span>
						<span>
							<form:input path="datumKomunikace" id="datumKomunikace" class="textovePole" cssStyle="width:10em;"></form:input>
						</span>
						<span> Modelový klíč</span>
						<span>
							<form:input class="textovePole" path="modelovyKlic" id="modelovyKlic" cssStyle="width:6em;"></form:input>
						</span>
						<span> Modelový rok</span>
						<span>
							<form:input class="textovePole" path="mrok" id="mrok" cssStyle="width:4em;"></form:input>
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
					<H3>Základní vůz</H3>
					<div class="formBar">
						<span> Výbavy</span>
						<span>
							<form:input class="textovePole" path="vybavy" cssStyle="width:40em;"></form:input>
						</span>
						<span>
							<input type="button" id="formMvButton" value="Získat PR popis" class="heroBtn" />
						</span>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div class="pageFooter">
		<div class="buttonBar">
			<span>
				<a id="opener" class="heroBtn">Výpočet</a>
			</span>
			<span>
				<a id="opener" class="heroBtn">Export</a>
			</span>
		</div>
		<jsp:include page="footerInfo.jsp" />
	</div>
</body>
	</html>
</jsp:root>