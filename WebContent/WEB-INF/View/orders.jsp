<%@ 
	page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import="java.util.*, Model.Beans.*"
 %>
		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="Storico ordini"/>
		<jsp:param name="stylesheet" value="CSS/stile-miei-ordini.css"/>
		
		</jsp:include>
		<jsp:include page="navbar.jsp"/>
		
		<div>
			<div style="display: flex; justify-content: center; align-items: center; flex-direction: row;">
				<h2>Gli ordini</h2>&nbsp
				<img src="images/ordini.png" width="30" height="30">
			</div>
			<%
				ArrayList<OrderBean> ordini = (ArrayList<OrderBean>) request.getAttribute("userOrders");
			%>
		</div>
		
		<section style="overflow:auto;">
		
		<table>

			<%			
				for(OrderBean ordine : ordini) {
			
			%>
				<tr style="background-color:#f2fcff;">
					<td class="head-table">Data ordine: <%= ordine.getDataOrdine() %></td>
					<td class="head-table">Prezzo per unità</td>
					<td class="head-table">Quantità</td>
					<td class="head-table">Prezzo</td>
				</tr>
						<%
							ArrayList<OrderedProductBean> prodottiOrdinati = ordine.getProdottiOrdinati();
						double prezzoTotale=0;
							for(OrderedProductBean prodottoOrdinato : prodottiOrdinati) {

						%>
								<tr>
									<td><a href="ProductPage?ID=<%= prodottoOrdinato.getIDProdotto() %>"><b><%= prodottoOrdinato.getTitolo() %></b></a></td>
									<td style="color: #AE2106; font-weight: bold;">€ <%= prodottoOrdinato.getPrezzo() %></td>
									<td><%= prodottoOrdinato.getQuantità() %></td>
									<% double prezzofin=Math.floor((prodottoOrdinato.getPrezzo()*prodottoOrdinato.getQuantità())*100)/100; %>
									<td style="color: #AE2106; font-weight: bold;"><%=prezzofin %> €</td>
									<%prezzoTotale+=prezzofin; %>
								</tr>
							<% } %>
							
								<tr style="border-top:1px solid #ccc;">
									<td></td>
									<td></td>
									<td style="font-weight:bold;">Costo Spedizione:</td>
									<td style="color: #AE2106; font-weight: bold;"><%=ordine.getCostoSpedizione() %> €</td>
								</tr>
								
								<tr style="border-top:1px solid #ccc;">
									<td></td>
									<td></td>
									<td style="font-weight:bold;">Costo totale:</td>
									<td style="color: #AE2106; font-weight: bold;"><%=prezzoTotale+ordine.getCostoSpedizione() %> €</td>
								</tr>
			<% } %>
			
		</table>
		
		</section>
		
		<jsp:include page="footer.jsp"/>
