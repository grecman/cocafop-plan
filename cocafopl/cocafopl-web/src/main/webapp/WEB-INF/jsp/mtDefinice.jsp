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
	var IdMtVRadku = 0;
	var MtMtVRadku = "";
	var ZavodMtVRadku = "";
	var KodZemeMtVRadku = "";
	var PopisMtVRadku = "";
	var PlatnostOdMtVRadku = 0;
	var PlatnostDoMtVRadku = 0;

	$(document).ready(
			function() {

				$('#tableId').dataTable({
					"paging" : false,
					"ordering" : true,
					"info" : false,
					"bFilter" : false,
					"iDisplayLength" : 25,
					"language" : {
						// datatables.net/reference/option/language
						"lengthMenu" : "&#160;Zobrazit _MENU_ řádků na stránce.",
						"info" : "&#160;Stránka: _PAGE_/_PAGES_, načteno _TOTAL_ záznamů.",
						"infoEmpty" : "Nenalezeny žádné záznamy.",
						"infoFiltered" : "&#160;(filtr: _TOTAL_ / _MAX_)",
						"loadingRecords" : "Nahrávám...",
						"processing" : "Pracuji...",
						"search" : "Vyhledat:",
						"zeroRecords" : "Nebyly nalezeny žádné záznamy.",
						"paginate" : {
							"first" : "První",
							"last" : "Poslední",
							"next" : "Další",
							"previous" : "Předcházející"
						}
					}
				});

				$("#tableId tr").click(function() {
					// Gre: NACTENI HODNOT ZE RADKU V TABULCE
					// zde pouzivam jen ID daneho radku (tedy Id Mt) pro mazani.
					$(this).addClass('selectedTableRow').siblings().removeClass('selectedTableRow');

					IdMtVRadku = $(this).find("td:nth-child(1)").html();
					MtMtVRadku = $(this).find("td:nth-child(2)").html();
					ZavodMtVRadku = $(this).find("td:nth-child(4)").html();
					KodZemeMtVRadku = $(this).find("td:nth-child(5)").html();
					PopisMtVRadku = $(this).find("td:nth-child(6)").html();
					PlatnostOdMtVRadku = $(this).find("td:nth-child(7)").html();
					PlatnostDoMtVRadku = $(this).find("td:nth-child(8)").html();
					//alert(IdMtVRadku + "," + MtMtVRadku + "," + ZavodMtVRadku + "," + KodZemeMtVRadku + "," + PopisMtVRadku + "," + PlatnostOdMtVRadku + "," + PlatnostDoMtVRadku);
				});

				$("#smazatMtButton").click(function() {
					$(window.location).attr('href', '${pageContext.servletContext.contextPath}/srv/kalkulace/smazatMt/' + IdMtVRadku);
				});

				$("#formNovaMtButton").click(
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
								$("#formNovaMt").submit();
							} else {
								alert(result);
							}
						});

				$("#modelTr").autocomplete({
					source : function(request, response) {
						/*console.log("z-index: " + $(".selector").zIndex());*/
						var input = request.term;
						$.ajax({
							//url : "./autocompleteMtProd.json?string=" + input,
							url : "${pageContext.servletContext.contextPath}/srv/kalkulace/autocompleteMtProd.json?string=" + input,
							dataType : 'json',
							type : 'POST',
							contentType : "application/json"
						}).done(function(data) {
							response(data);
						});
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
					kalkulací</a>
				<a class="${selectedSubMenu eq 'kalkulaceDetail' ? 'selected' : 'passive'}" href="${pageContext.servletContext.contextPath}/srv/kalkulace/detail">Detail
					kalkulace</a>
			</div>
		</div>
		<div class="pageBody">
			<div class="mainAreaWide">
				<div class="formBar">
					<span>
						<a href="#openModalNovaMt">
							<input type="button" value="Nová modelová třída" class="heroBtn" style="width: 160px;" />
						</a>
					</span>
					<span>
						<a href="${pageContext.servletContext.contextPath}/srv/kalkulace/mtDefiniceZobrazitVse">
							<input type="button" value="Zobrazit vše" class="heroBtn" />
						</a>
					</span>
				</div>
				<div class="tableContainer">
					<table class="dataTable" id="tableId">
						<col width="100px" />
						<col width="100px" />
						<col width="100px" />
						<col width="100px" />
						<col width="*" />
						<col width="120px" />
						<col width="120px" />
						<col width="70px" />
						<thead>
							<tr>
								<th style="display: none;">Id</th>
								<th>Modelová třída</th>
								<th>Produkt</th>
								<th>Závod</th>
								<th>Kód země</th>
								<th>Popis</th>
								<th>Platnost OD</th>
								<th>Platnost DO</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mtList}" var="i">
								<tr>
									<td align="center" style="display: none;">${i.id}</td>
									<td align="center">${i.modelTr}</td>
									<td align="center">${i.produkt}</td>
									<td align="center">${i.zavod}</td>
									<td align="center">${i.kodZeme}</td>
									<td align="left">${i.popis}</td>
									<td align="center">${i.platnostOd}</td>
									<td align="center">${i.platnostDo}</td>
									<td align="center"><c:if test="${moznoEditovat}">
											<a href="${pageContext.servletContext.contextPath}/srv/kalkulace/editMtForm/${i.id}">
												<img title="Editovat" style="border: 0px;" src="${pageContext.servletContext.contextPath}/resources/ico/edit.png" />
											</a>&#160;
											<a href="#openModalSmazatMt">
												<img title="Smazat" style="border: 0px;" src="${pageContext.servletContext.contextPath}/resources/ico/smazat.png" />
											</a>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="pageFooter">
			<jsp:include page="footerInfo.jsp" />
		</div>
	</div>
	<!-- *****************************  M  O  D  A  L   **********************************-->
	<div id="openModalNovaMt" class="modalWindow">
		<div class="obalovak">
			<a href="#close" class="close">X</a>
			<div class="modalHeader">
				<h3>Zadání nové modelové třídy</h3>
			</div>
			<div class="modalContent">
				<form:form commandName="mt" id="formNovaMt" action="${pageContext.servletContext.contextPath}/srv/kalkulace/mtDefiniceNovaMt/">
					<TABLE>
						<TR>
							<TD style="width: 150px;">Modelová třída<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="modelTr" id="modelTr" class="modelTr textovePole" cssStyle="width:30px;"></form:input>
								<SPAN style="font-size: x-small; color: gray;"> Funkce automatického dokončování.</SPAN></TD>
						</TR>
						<TR>
							<TD>Závod<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="zavod" id="zavod" class="zavod textovePole" cssStyle="width:20px"></form:input>
								<SPAN style="color: red; font-weight: bold;">!</SPAN></TD>
						</TR>
						<TR>
							<TD>Kód země (výchozí)<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="kodZeme" id="kodZeme" class="textovePole" cssStyle="width:30px"></form:input></TD>
						</TR>
						<TR>
							<TD>Popis</TD>
							<TD><form:input path="popis" id="popis" class="textovePole" cssStyle="width:300px"></form:input></TD>
						</TR>
						<TR>
							<TD>Platnost OD<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="platnostOd" id="platnostOd" value="${aktualniRRRRMM}" class="textovePole" cssStyle="width:60px"></form:input></TD>
						</TR>
						<TR>
							<TD>Platnost DO<SPAN style="color: red; font-weight: bold;">*</SPAN></TD>
							<TD><form:input path="platnostDo" id="platnostDo" value="999912" class="textovePole" cssStyle="width:60px"></form:input></TD>
						</TR>
						<TR>
							<TD colspan="2"><SPAN style="color: red; font-weight: bold;">!</SPAN>
								<SPAN style="font-size: x-small; color: gray;"> Modelová třída a závod musí být unikátní, pokud toto nebude splněno, tak formulář nebude uložen!</SPAN></TD>
						</TR>
					</TABLE>
				</form:form>
			</div>
			<div class="modalFooter">
				<TABLE style="width: 100%;">
					<TR>
						<TD><SPAN style="color: red; font-weight: bold;">*</SPAN>
							<SPAN style="font-size: x-small; color: gray;"> povinný údaj</SPAN></TD>
						<TD align="right"><input type="button" id="formNovaMtButton" value="Uložit" class="heroBtn"></input></TD>
					</TR>
				</TABLE>
			</div>
		</div>
	</div>
	<div id="openModalSmazatMt" class="modalWindow">
		<div class="obalovak">
			<a href="#close" class="close">X</a>
			<div class="modalHeader">
				<h3>Smazání modelové třídy</h3>
			</div>
			<div class="modalContent">
				<SPAN>Opravdu chcete smazat celou modelovou třídu?</SPAN>
				<BR /> <BR />
				<SPAN style="font-size: x-small; color: gray;">POZOR: budou smazáni i všichni přerdtavitelé nadefinováni pro tuto modelovou třídu! </SPAN>
			</div>
			<div class="modalFooter">
				<input type="button" id="smazatMtButton" value="Smazat" class="heroBtn"></input>
			</div>
		</div>
	</div>
	<!-- * E N D **************************  M  O  D  A  L   **********************************-->
</body>
	</html>
</jsp:root>