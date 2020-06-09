<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.*, java.util.Map.Entry, Model.Beans.WishListBean, Model.Beans.*"
 %>

		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="Lista desideri"/>
		<jsp:param name="stylesheet" value="CSS/stile-carrello.css"/>
		</jsp:include>
		
		<jsp:include page="navbar.jsp"/>
		
		<div style="display: flex; justify-content: center; align-items: center; flex-direction: row;">
			<h2>Lista desideri</h2>&nbsp
			<img src="images/wishlist.png" width="30" height="30">
		</div>
		
		<table style="width: 100%;">
			<tr id="first">
				<td></td>
				<td style="font-size: 15px;">Titolo</td>
				<td style="font-size: 15px;">Prezzo</td>
				<td>Azioni</td>
			</tr>
		
		<%
			int flag=0;
			LinkedHashMap<Long, WishListBean> wishlist = (LinkedHashMap<Long, WishListBean>) session.getAttribute("wishlist");
			if(wishlist != null) {
				for(Entry<Long, WishListBean> e : wishlist.entrySet()) {
					flag=1;
					ProductBean pb = e.getValue().getProdotto();
					double prezzo = pb.getPrezzo();
					ArrayList<ImageBean> images = pb.getImmagini();
					ImageBean ib = new ImageBean();
					if(!images.isEmpty())
						ib = images.get(0);
					else ib.setPathname("images/no-images.jpg");
		%>
					<tr id="normale">
						<td><a href="ProductPage?ID=<%= pb.getIdProdotto() %>"><img src="<%= ib.getPathname() %>" width="80"></a></td>
						<td><a href="ProductPage?ID=<%= pb.getIdProdotto() %>"><b><%= pb.getTitolo() %></b></a></td>
						<td style="color: #AE2106; font-weight: bold;">€ <%= prezzo %></td>
						<td><a href="WishList?remove=<%= pb.getIdProdotto() %>" class="rimuovi">Rimuovi dalla lista desideri</a></td>
					</tr>
					<tr id="media-query">
						<td><a href="ProductPage?ID=<%= pb.getIdProdotto() %>"><img src="<%= ib.getPathname() %>" width="80"></a></td>
						<td>
							<table>
								<tr><td><a href="ProductPage?ID=<%= pb.getIdProdotto() %>"><b><%= pb.getTitolo() %></b></a></td></tr>
								<tr><td style="color: #AE2106; font-weight: bold;">€ <%= prezzo %></td></tr>
								<tr><td><a href="WishList?remove=<%= pb.getIdProdotto() %>" class="rimuovi">Rimuovi dalla lista desideri</a></td></tr>
							</table>
						</td>
					</tr>
		<% } } %>
		
		</table>
		
		<%if(flag == 0){ %>
			<script>
				$("table").hide();
			</script>
			<br><br><h2 style="text-align: center;">&nbspLa tua lista desideri è vuota.</h2>
		<%} %>
		
		<br><br>
		
		<jsp:include page="footer.jsp"/>
