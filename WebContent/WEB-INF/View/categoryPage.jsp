<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.ArrayList, Model.Beans.*, Model.DAO.*, java.util.LinkedHashMap, java.util.Map.Entry"
    %>

<%	String categoria=(String) request.getAttribute("categoria");
ArrayList<ProductBean> prodotti =(ArrayList<ProductBean>) request.getAttribute("prodotti");
		%>
		

<jsp:include page="header.jsp">
<jsp:param name="pageName" value="<%=categoria %>"/>
<jsp:param name="stylesheet" value="CSS/stile-immagini-homepage.css"/>
</jsp:include>
<jsp:include page="navbar.jsp"/>
		
		<%UserBean user=(UserBean)session.getAttribute("user");
		if (user!=null && user.getAdmin()){ %>
			<form action="AdminCategoria" method="post">
				<h1 style="text-align:center;"> <%= categoria %> 
					<input type="hidden" name="hiddenCategoria" value="<%= categoria %>">
					<input type="hidden" name="categoria" value="<%= categoria %>">
					
					<input class="btn" type="submit" name="Modifica" value="Modifica categoria">
					<input class="btn" type="submit" name="Rimuovi" value="Rimuovi categoria">
				</h1>
			</form>
		<% } else {%>
<h1 style="text-align: center;"> <%= categoria %> </h1>
	<% } %>
	
<section class="sezione-immagini">

<%	for(ProductBean prodotto : prodotti) {
		ArrayList<ImageBean> immagini=prodotto.getImmagini();
		ImageBean immagine=null;
		if (!immagini.isEmpty()){
			immagine=immagini.get(0);
		}
	
	%>	<a class="product-area" href="ProductPage?ID=<%=prodotto.getIdProdotto() %>" style="text-decoration:none; color:black;">
			<% if (immagine!=null) { %>
			
						<div>
								<img class="productImage product-image-homepage" src="<%= immagine.getPathname() %>" alt="" >
						</div>
								<% } %>
						<div style="text-align:center;">	
							<h2><%= prodotto.getTitolo() %></h2>
						</div>

		</a>
						
				<% } %>
	
	</section>
	
		<section style="text-align:center">
	<% if (request.getAttribute("prevPage")!=null){%>
				<a class="btn" id="prevPage" href="Categoria?categoria=<%=categoria %>&pagina=<%= (int)request.getAttribute("prevPage") %>">Pagina precedente</a>
			<% } %>
							<%if(request.getAttribute("prevPage")!=null && request.getAttribute("nextPage")!=null){ %>
				<br><br><br>
				<% }if (request.getAttribute("nextPage")!=null){%>
				<a class="btn" id="prevPage" href="Categoria?categoria=<%=categoria %>&pagina=<%= (int)request.getAttribute("nextPage") %>">Pagina Succesiva</a><br>
			<% } %>
			<br>
			</section>	
	
	<jsp:include page="footer.jsp"/>	


		
