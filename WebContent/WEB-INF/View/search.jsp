<%@ 
	page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.ArrayList, Model.Beans.*, Model.DAO.*, java.util.LinkedHashMap, java.util.Map.Entry"
%>

		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="Ricerca"/>
		<jsp:param name="stylesheet" value="CSS/stile-search.css"/>
		</jsp:include>
		<jsp:include page="navbar.jsp"/>
		
		<%
			ArrayList<ProductBean> products = (ArrayList<ProductBean>) request.getAttribute("prodotti");
		
			int pageNumber = 0;
			int offset = 0;
			int pageParam=0;
			int precOffset=0;
			pageParam = (int) request.getAttribute("pageNumber");
			if(pageParam != 0) {
				pageNumber = pageParam + 1;
				offset = pageNumber * 10;
				precOffset=(pageParam-1)*10;
			}
			if(products != null) {
				if(products.isEmpty()) {
		%>
					<br><br><h3>&nbsp;Nessun prodotto trovato</h3><br><br>
			<% } else { %>
					
					<section class="sezione-immagini-search">
					
					<%for(ProductBean pb : products) {
						ArrayList<ImageBean> images = pb.getImmagini();
						ImageBean ib = new ImageBean();
						if(!images.isEmpty()) ib = images.get(0);					
						else ib.setPathname("images/no-images.jpg");
					%>	
							<a class="product-area-search" href="ProductPage?ID=<%= pb.getIdProdotto() %>"> 
								<img src="<%= ib.getPathname() %>" alt="" class="product-image-search" style="margin-right:30px;">
								<div >
									<h3><%= pb.getTitolo() %></h3>
									<p><%= pb.getDescrizione() %></p>
									<span>Prezzo: <b style="color: #AE2106;"><%= pb.getPrezzo() %> &euro;</b></span>
								</div>
							</a><br>
						
					<% } %>					
				
			<% }%> 
				</section>
				<% if (request.getAttribute("prevPage")!=null){
				int prevPage=pageNumber-2;%>
				<a id="prevPage" href="Search?search-field=<%= request.getAttribute("search-field") %>&categoria=<%= request.getAttribute("categoria") %>&pageNumber=<%= prevPage %>">Pagina precedente</a>
			<% } else{} %>
				
				<% if (request.getAttribute("nextPage")!=null){%>
				<a id="nextPage" href="Search?search-field=<%= request.getAttribute("search-field") %>&categoria=<%= request.getAttribute("categoria") %>&pageNumber=<%= pageNumber+1 %>">Pagina successiva</a>
			<% } else{} }  %>
	 
		<jsp:include page="footer.jsp"/>