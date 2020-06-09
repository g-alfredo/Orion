<%@ 
	page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	import="java.util.ArrayList, Model.Beans.*, Model.DAO.*, java.util.LinkedHashMap, java.util.Map.Entry"
%>


		<jsp:include page="header.jsp">
<jsp:param name="pageName" value="Homepage"/>
<jsp:param name="stylesheet" value="CSS/stile-immagini-homepage.css"/>
</jsp:include>
		<jsp:include page="navbar.jsp"/>
	
		<section class="sezione-immagini">
			<% ArrayList<ProductBean> prodotti=(ArrayList<ProductBean>) request.getAttribute("prodotti");
			   for(ProductBean prodotto : prodotti) {
			%>
				<a class="product-area" href="ProductPage?ID=<%= prodotto.getIdProdotto() %>" style="text-decoration: none;">
				<% 
				   ArrayList<ImageBean> immagini=prodotto.getImmagini();
					if(!immagini.isEmpty()){
						ImageBean ib=immagini.get(0);
				%>
						<img class="product-image-homepage" src="<%= ib.getPathname() %>" alt="" >
						<% } else { %>
						<img class="product-image-homepage" src="images/no-images.jpg" alt="" >
						<% } %>
				</a>		
			<% } %>
		</section>
<jsp:include page="footer.jsp"/>