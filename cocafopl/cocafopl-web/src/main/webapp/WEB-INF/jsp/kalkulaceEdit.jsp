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
				$("#formEditKalkulaceButton").click(
						function() {
							var kalkulacniDatum = (!$("#kalkulacniDatum").val().match(/^[0-9]{8}$/) ? "Kalkulační datum je zadán ve špatném formatu: " + $("#platnostOd").val()
									+ "\nOčekává se formát RRRRMMDD (příklady: 20160106 / 20161231)." : "");
							var result = kalkulacniDatum;
							if (result.length == 0) {
								$("#formEditKalkulace").submit();
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
					kalkulací</a> <a class="${selectedSubMenu eq 'kalkulaceDetail' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail">Kalkulace</a>
			</div>
		</div>

		<div class="pageBody">
			<div class="mainAreaWide">
				<form:form commandName="kalkulace" id="formEditKalkulace" action="${pageContext.servletContext.contextPath}/srv/kalkulace/editKalkulaceFormSubmit/">
				<form:hidden path="id" value="${kalkulaceInput.id}"></form:hidden>
				<form:hidden path="kalkulace" value="${kalkulaceInput.kalkulace}"></form:hidden>
					<TABLE style="padding-left: 15px;">
						<TR>
							<TD style="width: 170px; height: 60px; font-weight: bold;">Kalkulace</TD>
							<TD>${kalkulaceInput.kalkulace}</TD>
						</TR>
						<TR>
							<TD style="width: 170px; height: 30px; font-weight: bold;">Kalkulační datum</TD>
							<TD><form:input path="kalkulacniDatum" id="kalkulacniDatum" value="${kalkulaceInput.kalkulacniDatum}" class="textovePole" cssStyle="width:80px"></form:input></TD>
						</TR>
					</TABLE>
				</form:form>
				<BR />
				<div class="formBar">
					<span><input type="button" id="formEditKalkulaceButton" value="Uložit" class="heroBtn"></input></span>
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