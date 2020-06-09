<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
    		import="java.util.ArrayList, Model.Beans.*, Model.DAO.*"
    %>

<jsp:include page="header.jsp">
<jsp:param name="pageName" value="Amministrazione"/>
<jsp:param name="stylesheet" value="CSS/stile-admin-users.css"/>
</jsp:include>
<jsp:include page="navbar.jsp"/>

<br>
<section>
	<h2>Lista utenti</h2>
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>Nome</th>
					<th>Cognome</th>
					<th>Email</th>
					<th>Admin</th>
					<th>Ordini</th>
					<th>Azioni</th>
				</tr>
			</thead>

			<tbody>
				<% ArrayList<UserBean> utenti=(ArrayList<UserBean>) request.getAttribute("utenti");
				for(UserBean utente:utenti){
				%>
					<tr>
						<td><%=utente.getIDUser() %></td>
						<td><%=utente.getNome() %></td>
						<td><%=utente.getCognome() %></td>
						
						<td><%=utente.getEmail() %></td>
						<%String admin="No";
						if(utente.getAdmin())admin="Si";%>
						<td><%=admin %></td>
						<td>
							<form action="Orders" method="post">
								<input type="hidden" name="userAdmin" value ="<%=utente.getIDUser()%>">
								<input type="submit" class="btn" value="Dettagli">
							</form>
						</td>
						<td>
							<form action="Credentials" method="post">
								<input type="hidden" name="userModify" value="<%=utente.getIDUser()%>">
								<input type="submit" class="btn" name="modifica" value="Modifica" >
															<%if (utente.getAdmin()) {%>
								<a class="btn admin-remove-btn">Rimuovi</a>
								<input type="submit" class="btn" name="rimuovi" value="Rimuovi" style="display:none">
								<%} else { %>
								<input type="submit" class="btn" name="rimuovi" value="Rimuovi" >
								<% } %>
							</form>
						</td>
					</tr>
					<%} %>
			</tbody>
		</table>
	</section>
	
	<div style="text-align:center">
	<% if (request.getAttribute("prevPage")!=null){%>
				<br><a class="btn" id="prevPage" href="AdminUsers?pagina=<%= (int)request.getAttribute("prevPage") %>">Pagina precedente</a>
			<% } if(request.getAttribute("prevPage")!=null && request.getAttribute("nextPage")!=null){ %>
				<br><br><br>
				<% }if (request.getAttribute("nextPage")!=null){%>
				<br><a class="btn" id="prevPage" href="AdminUsers?pagina=<%= (int)request.getAttribute("nextPage") %>">Pagina Succesiva</a>
			<% } %>
			<br><br>
	</div>
	
	<script>
		$(".admin-remove-btn").click(function(){
			alert("Stai per rimuovere un admin, se sei sicuro clicca nuovamente sul pulsante");
			$(this).hide();
			$(this).next().show();
			
		})
	</script>


<jsp:include page="footer.jsp"/>