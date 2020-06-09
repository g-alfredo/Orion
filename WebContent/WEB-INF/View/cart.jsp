<%@ 
	page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.ArrayList, java.util.LinkedHashMap, java.util.Map.Entry, Model.Beans.CartBean, Model.Beans.ProductBean, Model.Beans.ImageBean"
 %>
		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="Carrello"/>
		<jsp:param name="stylesheet" value="CSS/stile-carrello.css"/>
		
		</jsp:include>
		<jsp:include page="navbar.jsp"/>
		
		<div style="display: flex; justify-content: center; align-items: center; flex-direction: row;">
			<h2>Carrello&nbsp;</h2>
			<img src="images/carrello.webp" width="30" height="30">
		</div>
		
		<%if(request.getAttribute("notifica")!=null){ %>
			<div style="text-align:center">
				<br><br><span><b style="color:red;"><%=(String)request.getAttribute("notifica") %></b></span><br><br>
			</div>
		<%} %>
		
		<div style="text-align:center;">
		<%
				Boolean excess = (Boolean) request.getAttribute("excess");
				if(excess != null && excess == true) {
			%>
				<br><br><span><b style="color:red;">Alcuni prodotti sono stati rimossi dal carrello perchè non più disponibili</b></span><br><br>
			<% } %>
		</div>
		
		<table>
			<tr id="first">
				<td></td>
				<td></td>
				<td style="font-size: 15px;">Prezzo</td>
				<td style="font-size: 15px;">Quantità</td>
				<td></td>
			</tr>
		
		<%
			double totale = 0;
			LinkedHashMap<Long, CartBean> cart = (LinkedHashMap<Long, CartBean>) session.getAttribute("cart");
			if(cart != null) {
				for(Entry<Long, CartBean> e : cart.entrySet()) {
					CartBean cb = e.getValue();
					ProductBean pb = cb.getProdotto();
					double prezzo = pb.getPrezzo();
					if(pb.getPercSconto() > 0) {
						prezzo -= prezzo * pb.getPercSconto() / 100;
						double prezzofinale=(Math.floor(prezzo*100))/100;
						prezzo=prezzofinale;
					}
					int quantità = cb.getQuantità();
					totale += prezzo * quantità;
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
						<td><%= quantità %></td>
						<td><a href="Cart?remove=<%= pb.getIdProdotto() %>" class="rimuovi">Rimuovi dal carrello</a></td>
					</tr>
					<tr id="media-query">
						<td><a href="ProductPage?ID=<%= pb.getIdProdotto() %>"><img src="<%= ib.getPathname() %>" width="80"></a></td>
						<td>
							<table>
								<tr><td><a href="ProductPage?ID=<%= pb.getIdProdotto() %>"><b><%= pb.getTitolo() %></b></a></td></tr>
								<tr><td style="color: #AE2106; font-weight: bold;">€ <%= prezzo %></td></tr>
								<tr><td>Quantità: <%= quantità %></td></tr>
								<tr><td><a href="Cart?remove=<%= pb.getIdProdotto() %>" class="rimuovi">Rimuovi dal carrello</a></td></tr>
							</table>
						</td>
					</tr>
		<% } } %>
		
		</table>
		
		<br><br>
		
		<div style="display: flex; align-items: center; justify-content: center;">	
			<%
				if(session.getAttribute("user") == null) {
					if(totale < 1){
						%>
						<script>
							$("table").hide();
						</script>
						<%
					}
			%>
			
				<p>E' necessario essere registrati per effettuare un ordine.</p>
				<a href="SignUp" id="registrati">Registrati</a>
			<% } else {	
				
					if(totale > 0){
						double prezzototale=Math.floor(totale*100)/100;%>
						<div id="totale-div">
							<b>Totale provvisorio:</b>&nbsp<b style="color: #AE2106;">€ <%= prezzototale %></b>&nbsp
							<a href="OrderPage?totale=<%= totale %>" id="procedi">Procedi all'ordine</a>
						</div>
					<%}else{ %>
						<script>
							$("table").hide();
						</script>
						<h2>&nbspIl tuo carrello è vuoto.</h2>
					<%}
				} %>
			</div>
			
			<br><br>

		<jsp:include page="footer.jsp"/>
