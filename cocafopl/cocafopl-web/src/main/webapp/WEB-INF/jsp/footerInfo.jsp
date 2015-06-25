<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:form="http://www.springframework.org/tags/form" xmlns:Spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:f="http://java.sun.com/jsp/jstl/fmt" version="2.0">
	<div class="infoFooter">
		<div class="copyright">
			&#160;© SKODA AUTO a.s.
			<c:choose>
				<c:when test="${empty userRole}">
					<SPAN style="color: red; font-weight: bolder; margin-left: 150px;">Aplikace byla dlouho neaktivní! Ztratila se informace o uživateli (tzn. oprávnění). Je nutné aplikaci znova spustit.</SPAN>
				</c:when>
				<c:otherwise>
					<SPAN style="color: gray; font-size: x-small; margin-left: 750px;">Server:&#160;${serverName}&#160;(${userRole})</SPAN>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</jsp:root>