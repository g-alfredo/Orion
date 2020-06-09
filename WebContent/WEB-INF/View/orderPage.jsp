<%@ 
	page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.ArrayList, Model.Beans.ShippingAddressBean, Model.Beans.CardBean"
%>

		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="Completa l'ordine"/>
		</jsp:include>
		<jsp:include page="navbar.jsp"/>
		
		<%	
			Boolean success = (Boolean) request.getAttribute("success");
			if(success != null && success) {
		%>
			<div style="text-align:center;">
				<h2>&nbsp;Ordine completato con successo!</h2>
				<a style="color:black;text-decoration:none;" class="btn" href=".">&nbsp;Continua su Orion</a><br><br><br>
			</div>
		<% } else { %>
			<div style="text-align:center;">
				<form action="OrderPage" method="post">
					<div class="addressArea" style="border-bottom: 1px solid #ccc;">
						<h3>&nbsp;Scegli l'indirizzo di spedizione</h3>
					
						<a href="Addresses" class="btn">Inserisci un nuovo indirizzo</a><br><br>
						
						<% ArrayList<ShippingAddressBean> addresses = (ArrayList<ShippingAddressBean>) request.getAttribute("addresses"); 
						   for(ShippingAddressBean ab : addresses) {
						   		if(ab.isPreferred()) {
						%>
							<input type="radio" name="address" value="<%=ab.getNumIndirizzo()%>" checked required>&nbsp;<%=ab.getVia()%>, <%=ab.getCivico()%>, <%=ab.getCittà()%>, <%=ab.getProvincia()%><br><br>
						<% } else {%>
							<input type="radio" name="address" value="<%=ab.getNumIndirizzo()%>" required>&nbsp;<%=ab.getVia()%>, <%=ab.getCivico()%>, <%=ab.getCittà()%>, <%=ab.getProvincia()%><br><br>			
						<% } } %>
 						
					</div>
			
					<div class="paymentMethodArea" style="border-bottom: 1px solid #ccc;">
						<h3>&nbsp;Scegli il metodo di pagamento</h3>
						<a href="PaymentMethods" class="btn">Inserisci una nuova carta</a><br><br>
						
						<% ArrayList<CardBean> cards = (ArrayList<CardBean>) request.getAttribute("cards");
						   for(CardBean cb : cards) {
								if(cb.isPreferred()) {
						%>
							<input type="radio" name="card" value="<%= cb.getNrCarta() %>" checked required>&nbsp;<%=cb.getNrCarta()%>, <%=cb.getNome()%> <%=cb.getCognome()%><br><br>
						<% } else {%>
							<input type="radio" name="card" value="<%= cb.getNrCarta() %>" required>&nbsp;<%=cb.getNrCarta()%>, <%=cb.getNome()%> <%=cb.getCognome()%><br><br>	
						<% } } %>
					</div>
					<input type="hidden" name="ordina">
					<%if(!addresses.isEmpty() || !cards.isEmpty()) { %>
					<input class="btn" type="submit" value="Completa ordine">
					<%} else { %>
												<br><br>
						
						<span style="color:red;font-weight:bold;">Non puoi completare l'ordine senza un indirizzo ed un metodo di pagamento!</span>
						<br><br>
						
						<input class="btn" type="submit" value="Completa ordine" style="  opacity: 0.5;  cursor: not-allowed;" disabled>
						<br><br>
					<% } %>
				</form>
			</div>
		<% } %>
		
		<jsp:include page="footer.jsp"/>

