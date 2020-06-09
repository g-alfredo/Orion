<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<jsp:include page="header.jsp">
<jsp:param name="pageName" value="Errore"/>
</jsp:include>
<jsp:include page="navbar.jsp"/>

<br>

	<section style="text-align:center;">
		<h1>Oh no! Qualcosa è andato storto</h1>

	
		<h1>Errore ${requestScope['javax.servlet.error.status_code']}</h1>
		
				<img width="200px" src="images/errore.png"><br><br>
				<a class="btn" href="./">Torna alla pagina principale</a>
	</section>
	
<br><br>
	
<jsp:include page="footer.jsp"/>