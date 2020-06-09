<%@ 
	page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.ArrayList, Model.Beans.CardBean"
%>

		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="Metodi di pagamento"/>
		</jsp:include>
		<jsp:include page="navbar.jsp"/>
		<h2>&nbsp;I tuoi metodi di pagamento</h2>
		
		<div style="text-align:center;">
			<%
				ArrayList<CardBean> cards = (ArrayList<CardBean>) request.getAttribute("cards");
	          	if(cards != null && !cards.isEmpty()) {
	          		for(CardBean cb : cards) {
	          			String numeroCarta = cb.getNrCarta();
			%>
						<div style="display: inline-block; border: 1px solid #ccc; border-radius: 4px; margin: 10px; padding: 10px;" class="cardArea">
							<%
								if(cb.isPreferred()) {
							%>
								<h6>Opzione preferita</h6>
							<% }%>
							Numero carta: <%= numeroCarta %><br>
							Titolare: <%= cb.getNome() %> <%= cb.getCognome() %>
							
							<br>
							<button class="btn updateButton" id="updateButton">Modifica</button>			
							<div id="cardUpdateBlock" class="cardUpdateBlock" style="display: none;">
								<form id="paymentMethodForm" class="form-container" name="paymentMethodForm" action="PaymentMethods" method="post" onsubmit="return validateUpdateMethod()">
									<span id="validUpdateMethod"></span>
									<label for="nome">Nome titolare</label>
									<input id="UpdNome" class="form-field" type="text" name="UpdNome" value="<%= cb.getNome() %>"><br>
					
									<label for="cognome">Cognome titolare</label>
									<input id="UpdCognome" class="form-field" type="text" name="UpdCognome" value="<%= cb.getCognome() %>"><br>
									
									<input type="hidden" name="update" value="<%= numeroCarta %>">
				          			<input class="btn" type="submit" value="Salva modifiche">
								</form>
							</div>
							
							<form action="PaymentMethods">
								<input type="hidden" name="delete" value="<%= numeroCarta  %>">
								<input class="btn" type="submit" value="Rimuovi">
							</form>				
						</div>
			<% } } %>
		</div>
		
		<div style="text-align: center;">
			<h3>Aggiungi un metodo di pagamento</h3>
			<div class="form-block">
				<form id="paymentMethodForm" class="form-container" name="paymentMethodForm" action="PaymentMethods" method="post" onsubmit="return validateMethod()">
					<span id="validMethod"></span>
					<label for="nrCarta">Numero carta</label>
					<input id="nrCarta" class="form-field" type="text" name="nrCarta"><br>
	
					<label for="nome">Nome titolare</label>
					<input id="nome" class="form-field" type="text" name="nome"><br>
	
					<label for="cognome">Cognome titolare</label>
					<input id="cognome" class="form-field" type="text" name="cognome"><br>
					
					<label for="preferito">Salvare come metodo di pagamento preferito? </label><br><br>
	          		Sì<input type="radio" name="preferito" value="si" checked>&nbsp;&nbsp;
	          		No<input type="radio" name="preferito" value="no"><br>
					
					<input class="btn" type="submit" value="Invia">
				</form>
			</div>
		</div>
		
		<jsp:include page="footer.jsp"/>
		
		<script type="text/javascript">
			$(".updateButton").click(function() {
				$(this).next().toggle();
			});
			
			function validateMethod() {
				var numeroOk = validateDigits($("#nrCarta").val());
				var nomeOk = validateCharacters($("#nome").val());
				var cognomeOk = validateCharacters($("#cognome").val());
						
				if(numeroOk && nomeOk && cognomeOk)	{
					$("#validMethod").html("");
					return true;
				}
				else {
					$("#validMethod").html("Riempi tutti i campi correttamente");
					return false;
				}
			}
					
			function validateUpdateMethod() {
				var nomeOk = validateCharacters($("#UpdNome").val());
				var cognomeOk = validateCharacters($("#UpdCognome").val());
						
				if(nomeOk && cognomeOk)	{
					$("#validUpdateMethod").html("");
					return true;
				}
				else {
					$("#validUpdateMethod").html("Riempi tutti i campi correttamente");
					return false;
				}
			}
			
			function validateCharacters(input) {
				var regex = /^[A-Za-z]*$/;
				if(input.match(regex)){
					return true;
				} else return false; 
			}


			function validateDigits(input) {
				var regex = /^[0-9]*$/;
				if(input.match(regex)){
					return true;
				} else return false; 
			}
		</script>

