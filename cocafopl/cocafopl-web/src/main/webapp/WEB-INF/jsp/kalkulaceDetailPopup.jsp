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
				<UL style="width: 750px;">
					<LI><H3>Kontrola</H3>
						<UL>
							<img src="${pageContext.servletContext.contextPath}/resources/ico/151.png" /> - všichni představitelé vybrané kalkulace a modelové třídy nemají PR
							popis.
							<br />
							<img src="${pageContext.servletContext.contextPath}/resources/ico/154.png" /> - některý z představitelů vybrané kalkulace a modelové třídy nemá PR
							popis.
							<br />
							<img src="${pageContext.servletContext.contextPath}/resources/ico/152.png" /> - všichni představitelé vybrané kalkulace a modelové třídy obsahují PR
							popis ze systému MBV/FAVAS.
						</UL></LI>
					<LI><H3>Poslední editace</H3>
						<UL>
							Datum a čas poslední editace jakéhokoliv představitele dané modelové třídy a závodu, pokud má vliv na aktuálně připravovanou (schvalovanou) kalkulaci. Po
							najetí myši na tento datum se v bublině zobrazí důvod nebo předmět poslední editace.
							<br />Pokud má tento datum
							<span style="color: red;">červené písmo</span>
							, tak to znamená, že od posledního výpočtu proběhla nějaká editace představitele nebo modelové třídy. Toto označení napovídá, že by se měl spustit
							výpočet.
						</UL></LI>
					<LI><H3>Poslední výpočet</H3>
						<UL>Datum a čas posledního výpočtu. Tento údaj je uveden u každé modelévé třídy, protože může být rozdílný! Blíže popsáno u "Spustit výpočet".
						</UL></LI>
					<LI><H3>Schvalování modelových tříd</H3>
						<UL>
							Uživatel s aplikační rolí USERS nebo APPROVERS má opravnění schválit danou modelovou třídu pro vybranou kalkulaci. Tímto označením pouze oznamuje
							uživateli s rolí APPROVERS, že tato třída je zkontolovaná a připravená k archvivaci, nic víc.
							<br />Toto schválení může být zrušeno dvěma způsoby:
							<UL>
								<LI>Spuštění výpočtu.</LI>
								<LI>Uživatel s rolí APPROVERS toto schválení zrušil.</LI>
							</UL>
						</UL></LI>
					<LI><H3>Spustit výpočet</H3>
						<UL>
							Výpočet zle spustit (tzn, že se zobrazí tlačítko) pokud:
							<UL>
								<LI>Uživatel má roli USERS nebo APPROVERS.</LI>
								<LI>Alespoň jedna modelová třída má všechny okomunikované všechny představitele (<B>Kontrola</B>=<img
									src="${pageContext.servletContext.contextPath}/resources/ico/152.png" />)
								</LI>
							</UL>
							Po spuštění výpočtu se provádí následující operace:
							<UL>
								<LI>Kontrola, zda-li neexistuje v Offline zpracování nějaký předchozí aktivní výpočet pro představitelé. Pokud neexistuje, tak se tento požadavek
									zařadí do fronty zpracování pro offline dávku a se vyplní "Čas zadaní" na obrazovce "Offline zpracování".</LI>
								<LI>Smažou se všechny příznaky "Schváleno" u jednotlivých modelovách tříd ve vybrané kalkulaci.</LI>
								<LI>Z archívu se smaže celý pracovní svat předchozího zpracování.</LI>
								<LI>Do archívu se nahraje základní definice (popis) představitelů.</LI>
								<LI>Aplikace se přesměruje na obrazovku "Offline zpracování".</LI>
								<LI>Spuštění offline dávky. Toto se provádí každých 5 minut. V té chvíli, kdy se dávka aktivně spustí, je na obrazovce "Offline zpracování"
									doplněno pole "Čas spuštění", po kompletním dojetí dávky je doplněno pole "Čas ukončení". V tuto chvíli jsou všechny spočítané data uložena v archivní
									časti aplikace pod pracovní (neschválenou) kalkulací. Také jsou k dispozici na disku nové sestavy (soubory).</LI>
							</UL>
						</UL></LI>
					<LI><H3>Schválit kalkulaci a archivovat</H3>
						<UL>
							Schvalovat (archivovat) kalkulaci může pouze uživatel s rolí APPROVERS a to pouze nejstarší stav pracovní kalkulace (nelze tedy schválit dubnová
							kalkulace, pokud není před tím scválen březen). Kalkulaci lze schválit pouze pokud jsou schváleny všechny zobrazené modelové třídy, a to i přesto, že
							nemají
							<img src="${pageContext.servletContext.contextPath}/resources/ico/152.png" />.
							<br /> Po schválení se schvalený stav kalkulace odstraní ze "zadávací části" aplikace.
						</UL></LI>
				</UL>
			</div>
		</div>
	</div>
</body>
	</html>
</jsp:root>