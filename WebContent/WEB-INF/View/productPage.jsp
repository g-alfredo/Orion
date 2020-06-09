<%@ 
	page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
	import="java.util.ArrayList, Model.Beans.*, Model.DAO.*, java.util.LinkedHashMap, java.util.Map.Entry"
%>

		<%	UserBean user=(UserBean)session.getAttribute("user");
		ProductBean pb = (ProductBean) request.getAttribute("prodotto");
			ArrayList<ImageBean> images = pb.getImmagini(); 
			ArrayList<ReviewBean> recensioni = (ArrayList<ReviewBean>) request.getAttribute("recensioni");
			long IDProdotto = pb.getIdProdotto();
		%>
		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="<%= pb.getTitolo() %>"/>
		<jsp:param name="stylesheet" value="CSS/stile-pagina-prodotto.css"/>
		
		</jsp:include>
		<jsp:include page="navbar.jsp"/>
				
		<div class="product-image-and-title">
		
		<div style="display:flex;align-items:center;">
		
			<%if(images.size() > 1){ %>
				<button style="height:50px;" class="btn" onclick="plusDivs(-1)">&#10094;</button>
			<%} %>
			
			<% for(ImageBean ib : images) {%>
					<img style="display:none;" class="product-image mySlides" src="<%= ib.getPathname() %>" alt="">
			<% } %>
			
			<% if(images.isEmpty()){ %>
			
				<img style="display:none;" class="product-image mySlides" src="images/no-images.jpg" alt="">
				
			<%}else{ 
				for(ImageBean ib : images) {%>
				
					<img style="display:none;" class="product-image mySlides" src="<%= ib.getPathname() %>" alt="">
					
				<% }} %>
			
			<%if(images.size() > 1){ %>	
  				<button style="height:50px;" class="btn" onclick="plusDivs(1)">&#10095;</button>
  			<%} %>
  			
  		</div>
					
					<div class="product-title-and-price">	
					
						<% if (user!=null && user.getAdmin()){ %>
						<form action="AdminProdotto" method="post">
							<input type="hidden" name="id" value="<%= pb.getIdProdotto() %>">

							<input class="btn" type="submit" name="Modifica" value="Modifica prodotto">
							<input class="btn" type="submit" name="Rimuovi" value="Rimuovi prodotto">

						</form>
						<% } %>		
								
						<%	int media = 0;
							int count = 0;
							for(ReviewBean recensione : recensioni){
								media += recensione.getPunteggio();
								count++;
							}
							if(count != 0) {
								media /= count;
						%>
								<h1 id="productTitle"><%= pb.getTitolo() %> &nbsp; <img src="images/<%= media %>-star.jpg" style="width: 110px;height: auto;"></h1>
						<% } else { %>
								<h1 id="productTitle"><%= pb.getTitolo() %></h1>
						<% } %>
						
						<%if(pb.getPercSconto() > 0){ %>
							<span><span style="color: #868686;">Prezzo: <strike><%= pb.getPrezzo() %> €</strike></span>
						<%}else{ %>
							<span><span style="color: #868686;">Prezzo: <b style="color: #AE2106;"><%= pb.getPrezzo() %> €</b></span><br><br>
						<%} %>
			
						<%
							int sconto = pb.getPercSconto();
							if(sconto > 0) {
								double prezzo = pb.getPrezzo() - (pb.getPrezzo() * pb.getPercSconto() / 100);
								double prezzofinale=(Math.floor(prezzo*100))/100;

						%>
							<p>Prezzo speciale: <b style="color: #AE2106;"><%= prezzofinale%> €</b> (<b style="color: #04FC04;"><%= sconto %>%</b>)</p>
						<% } 
						
						if (pb.getQuantità()>0) {%>
						
				<span>Quantità: 
					<form style="display: inline;" action="Cart" method="post">
						<input type="hidden" name="Prodotto" value=<%=pb.getIdProdotto() %>>
						<select name="Qnt" style="width: 20%;">
							<% 
								for(int i = 1; i <= pb.getQuantità() && i <= 10; i++) {%>
								<option value="<%= i %>"><%= i %></option>
							<% } %>
						</select>
						<br>								
						<div style="margin: 10px; display: flex; align-items: center;">
							<img class="immagini-dei-bottoni" src="images/carrello.webp" style="width: 25px;">&nbsp;&nbsp;
							<input id="carrello-button" type="submit" value="Aggiungi al carrello">
						</div>
					</form>
				</span>
						<% } else { %>
						<p>Prodotto esaurito</p>
						<% } %>
						
						<div style="margin: 10px; display: flex; align-items: center;">
							<img class="immagini-dei-bottoni" src="images/wishlist.png" style="width: 25px;">&nbsp&nbsp
							<a id="lista-button" href="WishList?Prodotto=<%= pb.getIdProdotto() %>">Aggiungi alla lista desideri</a>
						</div>
						<p><%= pb.getDescrizione() %></p>
					</div>
				</div>
			
		<div id="reviewsArea" style="margin: 30px;">
			<% if(recensioni.size() == 0) { %>
				<h2>Non sono ancora disponibili recensioni per questo prodotto.</h2>
			<% } else { %>
					<h2>Recensioni:</h2>
					<% for(ReviewBean recensione : recensioni) { %>		
						<div class="review" style="padding: 10px; border-top: 1px solid grey;">
							<h3><img src="images/<%= recensione.getPunteggio() %>-star.jpg" style="width: 100px;height: auto;"> &nbsp <%= recensione.getTitolo() %></h3>
							<span style="color: #868686; font-size: 15px;">Pubblicata da: <%= recensione.getNome() %> <%= recensione.getCognome() %> il <%= recensione.getDataPubblicazione() %></span>
							<p ><%= recensione.getDescrizione() %></p>
							<% 
								UserBean ub = (UserBean) session.getAttribute("user");
								if(ub != null && (recensione.getIdUser() == ub.getIDUser() || ub.getAdmin())) {%>
								<form action="Review" method="get" id="recensione-form">
									<input type="hidden" name="delete">
									<% if(ub.getAdmin()) { %> <input type="hidden" name="userID" value="<%= recensione.getIdUser() %>"> <% } %>
									<input type="hidden" name="IDProdotto" value=<%= IDProdotto %>>
									<input form="recensione-form" type="submit" value="Elimina recensione" id="elimina-recensione">
								</form>
							<% } %>
						</div>
					<% } %>
			<% } %>
		</div>		
		<br>
		<% boolean reviewFlag = (boolean) request.getAttribute("reviewFlag");
		   if(reviewFlag == true) {
		%>
			<div id="reviewForm" style="margin: 30px;">
				<h3>Crea recensione</h3>
				<form action="Review?IDProdotto=<%= IDProdotto %>" method="post">
					<input type="text" name="titolo" placeholder="Aggiungi un titolo..." required style="padding: 8px; width:300px;"><br><br>
					<input type="number" name="punteggio" min="1" max=5 placeholder="Punteggio" required style="padding: 8px; width:300px;"><br><br>
					<textarea name="descrizione" placeholder="Scrivi la tua recensione..." required style="padding: 8px; width:500px; height:100px;"></textarea><br>
					<input type="hidden" name="IDProdotto" value=<%= IDProdotto %>>
					<input class="btn" type="submit" value="Invia la recensione">
				</form>
			</div>	
		<% } %>
		
		<script>
			var slideIndex = 1;
			showDivs(slideIndex);

			function plusDivs(n) {
 	 			showDivs(slideIndex += n);
			}

			function showDivs(n) {
  				var i;
 				var x = document.getElementsByClassName("mySlides");
  				if (n > x.length) {slideIndex = 1}
 	 			if (n < 1) {slideIndex = x.length}
  				for (i = 0; i < x.length; i++) {
    				x[i].style.display = "none";
 	 			}
  				x[slideIndex-1].style.display = "block";
			}
		</script>
		
		<jsp:include page="footer.jsp"/>
