<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
    		import="java.util.ArrayList, Model.Beans.*, Model.DAO.*"
    %>

<jsp:include page="header.jsp">
<jsp:param name="pageName" value="Amministrazione"/>
<jsp:param name="stylesheet" value="CSS/stile-admin-users.css"/>
</jsp:include>
<jsp:include page="navbar.jsp"/>
<br><br>
<% if(request.getParameter("Esauriti")==null){ %>
<div style="text-align:center"> Vuoi visualizzare solo i prodotti esauriti?<br><br><a href="AdminProdotti?Esauriti" class="btn">Clicca qui</a> </div><br><br><br>
<% } else { %>
<div style="text-align:center;"> Vuoi visualizzare tutti i prodotti?<br><br><a href="AdminProdotti" class="btn">Clicca qui</a> </div><br><br><br>
<% } 
ArrayList<ProductBean> prodotti=(ArrayList<ProductBean>) request.getAttribute("prodotti");
%>
<section>
	<%if (!prodotti.isEmpty()){ %>
		<h2>Prodotti in magazzino</h2>
		<table>
			<thead>
				<tr>
					<th>id</th>
					<th>Titolo</th>
					<th>Data pubblicazione</th>
					<th>Prezzo</th>
					<th>Sconto</th>
					<th>Quantità</th>
					<th>Azioni</th>
				</tr>
			</thead>

			<tbody>
				<% 	for(ProductBean prodotto:prodotti){
				%>
					<tr>
						<td><%=prodotto.getIdProdotto() %></td>
						<td><%=prodotto.getTitolo() %></td>
						<td><%=prodotto.getDataPubblicazione() %></td>
						<td><%=prodotto.getPrezzo() %></td>
						<td><%=prodotto.getPercSconto() %></td>
						<% if(prodotto.getQuantità()>0){ %>
						<td><%=prodotto.getQuantità() %></td>
						<%} else { %>
						<td><span style="color:red">Prodotto esaurito</span></td>
						
						<% } %>
						<td>
							<form action="AdminProdotto" method="post" style='display:inline;'>
							<input type="hidden" name="id" value="<%= prodotto.getIdProdotto() %>">
							<input class="btn" type="submit" name="Modifica" value="Modifica prodotto">
						</form>
							<div class="btn removebtn" style='display:inline;' value="<%=prodotto.getIdProdotto() %>" >Rimuovi Prodotto</div>
						</td>
					</tr>
					<%} %>
			</tbody>
		</table>
		<% } else {%>
		<h3 style="text-align:center">La tua ricerca non ha prodotto nessun risultato</h3>
		<% } %>
		
		<br><br>

	</section>
	<section style="text-align:center">
	<br>
	<% if (request.getAttribute("prevPage")!=null){%>
				<a class="btn" id="prevPage" href="AdminProdotti?pagina=<%= (int)request.getAttribute("prevPage") %>">Pagina precedente</a>
			<% } %>
				<br><br><br>
				<% if (request.getAttribute("nextPage")!=null){%>
				<a class="btn" id="prevPage" href="AdminProdotti?pagina=<%= (int)request.getAttribute("nextPage") %>">Pagina Succesiva</a>
			<% } %>
			<br><br><br>	
	</section>
	<script>



		$('.removebtn').click(function() {
			var $tr = $(this).closest('tr');
			$.ajax({
				type : "POST",
				url : "AdminProdotto",
				data : {
					"Rimuovi" : "Rimuovi",
					"id" : $(this).attr("value")

				},
				dataType : "json",
				async : true,
				complete : function() {
					$tr.fadeOut(1000, function() {
						$tr.remove();
					});
				}
			});
		});
	</script>

<jsp:include page="footer.jsp"/>