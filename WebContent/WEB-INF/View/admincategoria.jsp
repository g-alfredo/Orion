<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="Model.Beans.*"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageName" value="Amministrazione" />
</jsp:include>
<jsp:include page="navbar.jsp" />

<br><br>
<section style="margin:20px;text-align:center;">
	<%
	CategoryBean categoria=(CategoryBean)request.getAttribute("categoria");
	String operazione="Aggiungi";
			if (request.getParameter("operazione")!=null)
				operazione=request.getParameter("operazione");
				%>
		<h2><%=operazione %> categoria</h2>
		<h5><%=request.getAttribute("notifica")==null ?"":request.getAttribute("notifica") %></h5>
		
<% if (request.getParameter("Rimuovi")==null && categoria!=null){ 
%>
	<div>Stai agendo sulla categoria: <b><%=categoria.getNome() %></b></div><br>
<% } %>
	<form action="AdminCategoria" method="post">
		<% if (request.getParameter("Rimuovi")!=null){ %>
				Nome: <input style="padding:10px;font-size:initial;"type="text" name="categoria" required><br>
		<input class="btn" type="submit" name="Aggiungi" value="Aggiungi">
		<% } else { %>
		<input type="hidden" name="hiddenCategoria"
			<%if (categoria!=null) { %>
			value="<%=categoria.getNome() %>" <% } %>> 
		Nome: <input style="padding:10px; font-size: initial;" type="text" name="categoria"
			value="<%=categoria!=null?categoria.getNome():""%>" required><br><br>


		<%if (categoria!=null) { %>

		<input class="btn" type="submit" name="Modifica" value="Modifica">
		 <input class="btn"	type="submit" name="Rimuovi" value="Rimuovi">

		<% } else {%>

		<input class="btn" type="submit" name="Aggiungi" value="Aggiungi">

		<% } %>
		<% } %>
	</form>
	
	<br><br>


</section>
<jsp:include page="footer.jsp" />
