<%@ 
	page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	import="java.util.ArrayList, Model.Beans.ProductBean, Model.Beans.ImageBean"
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="Offerte"/>
		<jsp:param name="stylesheet" value="CSS/stile-search.css"/>
		</jsp:include>
		<jsp:include page="navbar.jsp"></jsp:include>
	
		<section class="sezione-immagini">
			<% ArrayList<ProductBean> products=(ArrayList<ProductBean>) request.getAttribute("prodotti");
				int pageNumber = 0;
				int offset = 0;
				int pageParam = 0;
				int precOffset = 0;
				
				pageParam = (int) request.getAttribute("pageNumber");
				if(pageParam != 0) {
					pageNumber = pageParam + 1;
					offset = pageNumber * 10;
					precOffset = (pageParam - 1) * 10;
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
						
						double prezzoOriginale = pb.getPrezzo();
						double prezzoScontato = prezzoOriginale - (pb.getPercSconto() * prezzoOriginale / 100);
						double prezzofinale=(Math.floor(prezzoScontato*100))/100;

					%>	
							<a class="product-area-search" href="ProductPage?ID=<%= pb.getIdProdotto() %>"> 
								<img src="<%= ib.getPathname() %>" alt="" class="product-image-search" style="margin-right:30px;">
								<div>
									<h3><%= pb.getTitolo() %></h3>
									<p><%= pb.getDescrizione() %></p>
									
									<span style="color:black;">Prezzo speciale: <b style="color: #AE2106;"><%= prezzofinale%> €</b> (<b style="color: #04FC04;"><%= pb.getPercSconto() %>%</b>)</span>
								</div>
							</a><br>
						
					<% } %>					
				
			<% }%> 
				</section>
				<section style="text-align:center">
				<% if (request.getAttribute("prevPage")!=null){
				int prevPage=pageNumber-2;%>
				<a class="btn" id="prevPage" href="OnSale?pageNumber=<%= prevPage %>">Pagina precedente</a>
			<% } %>
			<%if(request.getAttribute("prevPage")!=null && request.getAttribute("nextPage")!=null){ %>
				<br><br><br>
				<%} if (request.getAttribute("nextPage")!=null){%>
				<a class="btn" id="nextPage" href="OnSale?pageNumber=<%= pageNumber+1 %>">Pagina successiva</a>
			<% } }  %>
			<br><br>
			</section>
<jsp:include page="footer.jsp"/>