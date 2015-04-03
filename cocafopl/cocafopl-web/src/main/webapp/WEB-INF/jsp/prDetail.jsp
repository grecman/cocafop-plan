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
		<div class="pageBody">
			<div class="mainAreaWide">
				<TABLE>
					<col width="75px" />
					<col width="55px" />
					<col width="400px" />
					<col width="80px" />
					<col width="80px" />
					<col width="80px" />
					<col width="80px" />
					<TR style="height: 25px;">
						<TD style="font-weight: bold;">Produkt</TD>
						<TD><SPAN style="background-color: white;">${mtProd.produkt}</SPAN></TD>
						<TD><SPAN style="background-color: white;">${mtProd.popisProduktu}</SPAN></TD>
					</TR>
					<TR style="height: 25px;">
						<TD style="font-weight: bold;">Rodina</TD>
						<TD><SPAN style="background-color: white;">${prOne.famkz}</SPAN></TD>
						<TD><SPAN style="background-color: white;">${prOne.famBen}</SPAN></TD>
					</TR>
					<TR style="height: 30px;">
						<TD style="font-weight: bold; border-bottom-color: black; border-bottom: thin; border-bottom-style: solid;">PR číslo</TD>
						<TD style="border-bottom-color: black; border-bottom: thin; border-bottom-style: solid;"><SPAN style="background-color: white;">${prOne.prnr}</SPAN></TD>
						<TD style="border-bottom-color: black; border-bottom: thin; border-bottom-style: solid;"><SPAN style="background-color: white;">${prOne.beschreibung}</SPAN></TD>
						<TD style="border-bottom-color: black; border-bottom: thin; border-bottom-style: solid;"><SPAN style="background-color: white;">${prOne.eindat}</SPAN></TD>
						<TD style="border-bottom-color: black; border-bottom: thin; border-bottom-style: solid;"><SPAN style="background-color: white;">${prOne.entdat}</SPAN></TD>
						<TD style="border-bottom-color: black; border-bottom: thin; border-bottom-style: solid;"><SPAN style="background-color: white;">${prOne.einschl}</SPAN></TD>
						<TD style="border-bottom-color: black; border-bottom: thin; border-bottom-style: solid;"><SPAN style="background-color: white;">${prOne.entschl}</SPAN></TD>
					</TR>
					<c:forEach items="${prList}" var="i">
						<c:if test="${i.prnr != prOne.prnr}">
							<TR style="height: 25px;">
								<TD></TD>
								<TD><SPAN>${i.prnr}</SPAN></TD>
								<TD><SPAN>${i.beschreibung}</SPAN></TD>
								<TD><SPAN>${i.eindat}</SPAN></TD>
								<TD><SPAN>${i.entdat}</SPAN></TD>
								<TD><SPAN>${i.einschl}</SPAN></TD>
								<TD><SPAN>${i.entschl}</SPAN></TD>
							</TR>
						</c:if>
					</c:forEach>
				</TABLE>
			</div>
		</div>
	</div>
</body>
	</html>
</jsp:root>