<%@ 
	page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" 
	import="java.util.ArrayList, Model.Beans.ShippingAddressBean"
%>

		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="I tuoi indirizzi"/>
		</jsp:include>
		<jsp:include page="navbar.jsp"/>
		<h2>&nbsp;&nbsp;I tuoi indirizzi</h2>
		
		<div  style="text-align:center;">
			<%
				ArrayList<ShippingAddressBean> addresses = (ArrayList<ShippingAddressBean>) request.getAttribute("addresses");
	          	if(addresses != null && !addresses.isEmpty()) {
	          		for(ShippingAddressBean ab : addresses) {
			%> 
						<div class="addressArea" style="display: inline-block; border: 1px solid #ccc; border-radius: 4px; margin: 10px; padding: 10px;">
						<%
							if(ab.isPreferred()) {
						%>
							<h6>Indirizzo preferito</h6>
						<% }%>	
						<%= ab.getVia() %> <%= ab.getCivico() %> <%= ab.getCittà() %><br><br>
						<%= ab.getCap() %> <%= ab.getProvincia() %>	<br>	
							
							<button class="updateButton btn">Modifica</button>
							<div id="addressUpdateBlock" style="display: none;">
								<form id="addressUpdateForm" class="form-container" name="addressUpdateForm" action="Addresses" method="post" onsubmit="return validateUpdateAddress()">
									<span id="validUpdateAddress"></span>
									<label for="provincia">Provincia*</label>
				          			<input id="UpdProvincia" class="form-field" type="text" name="UpdProvincia" value="<%= ab.getProvincia() %>"><br>
				          			
				          			<label for="città">Città*</label>
				          			<input id="UpdCittà" class="città form-field" type="text" name="UpdCitta" value="<%= ab.getCittà() %>"><br>
				          
				          			<label for="cap">CAP*</label>
				          			<input id="UpdCap" class="form-field" type="text" name="UpdCap" value="<%= ab.getCap() %>"><br>
				          			
				          			<label for="via">Via*</label>
				          			<input id="UpdVia" class="form-field" type="text" name="UpdVia" value="<%= ab.getVia() %>"><br>
				          			
				          			<label for="civico">Numero civico*</label>
				          			<input id="UpdCivico" class="form-field" type="text" name="UpdCivico" value="<%= ab.getCivico() %>"><br>
				          			
				          			<label for="preferito">Salvare come indirizzo preferito? </label><br>
				          			Sì<input type="radio" name="UpdPreferito" value="si" checked>&nbsp&nbsp
				          			No<input type="radio" name="UpdPreferito" value="no"><br>
				          			
				          			<input type="hidden" name="update" value="<%= ab.getNumIndirizzo() %>">
				          			<input class="btn" type="submit" value="Salva modifiche">
								</form>
							</div>
							
							<form action="Addresses">
								<input type="hidden" name="delete" value="<%= ab.getNumIndirizzo() %>">
								<input class="btn" type="submit" value="Rimuovi">
							</form>						
						</div>
			<% } } %>
		</div>
		
		<div style="text-align:center;">
			<h3>Aggiungi un indirizzo di spedizione</h3>
				<div class="form-block">
					<form id="addressForm" class="form-container" name="addressForm" action="Addresses" method="post" onsubmit="return validateAddress()"> 
						<span id="validAddress"></span>         
	          			<label for="provincia">Provincia*</label>
	          			<input id="provincia" class="form-field" type="text" name="provincia" required><br>
	          			
	          			<label for="città">Città*</label>
	          			<input id="città" class="città form-field" type="text" name="citta" required><br>
	          
	          			<label for="cap">CAP*</label>
	          			<input id="cap" class="form-field" type="number" min="0" max="99999" step="1" name="cap" required><br>
	          			
	          			<label for="via">Via*</label>
	          			<input id="via" class="form-field" type="text" name="via" required><br>
	          			
	          			<label for="civico">Numero civico*</label>
	          			<input id="civico" class="form-field" type="text" name="civico" required><br>
	          			
	          			<label for="preferito">Salvare come indirizzo preferito? </label><br><br>
	          			Sì<input type="radio" name="preferito" value="si" checked>&nbsp&nbsp
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
					
			function validateAddress() {
				var provOk = validateProvincia($("#provincia").val());
				var cittàOk = validateCharacters($("#città").val());
				var capOk = validateCAP($("#cap").val());
				var viaOk = validateCharacters($("#via").val());
				var civicoOk = validateDigits($("#civico").val());
				
				if(provOk && cittàOk && capOk && viaOk && civicoOk){
					$("#validAddress").html("");
					return true;
				}
				else {
					$("#validAddress").html("Riempi tutti i campi correttamente");
	    			return false;
				}			
			}
					
			function validateUpdateAddress() {
				var provOk = validateProvincia($("#UpdProvincia").val());
				var cittàOk = validateCharacters($("#UpdCittà").val());
				var capOk = validateCAP($("#UpdCap").val());
				var viaOk = validateCharacters($("#UpdVia").val());
				var civicoOk = validateDigits($("#UpdCivico").val());
				
				if(provOk && cittàOk && capOk && viaOk && civicoOk) {
					$("#validAddress").html("");
					return true;
				}
				else {
					$("#validUpdateAddress").html("Riempi tutti i campi correttamente");
	    			return false;
				}
			}
			
			function validateProvincia(input) {
				var regex = /^[A-Za-z]{2}$/;
				if(input.match(regex)){
					return true;
				} else return false; 
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
