<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<jsp:include page="header.jsp"/>
<jsp:include page="navbar.jsp"/>
	<section style="text-align:center;">
		<h1>Divieto di accesso</h1>
		<h1><%= exception.getMessage() %></h1>
		<br><br><img width="200px" src="images/non-autorizzato.png"><br><br><br>
	</section>
<jsp:include page="footer.jsp"/>