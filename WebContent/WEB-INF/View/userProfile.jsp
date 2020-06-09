<%@
	page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="Model.Beans.*"
%>

		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="Il mio account"/>
		<jsp:param name="stylesheet" value="CSS/stile-userProfile.css"/>
		
		</jsp:include>
		<jsp:include page="navbar.jsp"/>

	<h2 style="text-align:center;">Il mio account</h2>
	
	
	<div class="info">
		
			<a href="Orders">
				<div>
					<img src="images/ordini.png">
					<p><b>I miei ordini</b><br><br>Visualizza gli ordini passati</p>
				</div>
			</a>
		
			<a href="Credentials">
				<div> 
					<img src="images/accesso.png">
					<p><b>Impostazioni accesso</b><br><br>Modifica le credenziali di accesso, il nome ed il telefono</p>
				</div>
			</a>
			
		
			<a href="Addresses">
				<div>
					<img src="images/indirizzi.png">
					<p><b>Indirizzi di spedizione</b><br><br>Aggiungi o rimuovi indirizzi di spedizione</p>
				</div>
			</a>
		
		
			<a href="PaymentMethods">
				<div>
					<img src="images/pagamenti.png">
					<p><b>Opzioni di pagamento</b><br><br>Modifica o aggiungi metodi di pagamento</p>
				</div>
			</a>
		<% UserBean utente=(UserBean)session.getAttribute("user"); 
		if (utente!=null && utente.getAdmin()) {%>
		
			<a href="AdminProdotto">
				<div>
					<img src="images/aggiungi.png">
					<p><b>Aggiungi prodotto</b><br><br>Aggiungi un prodotto al magazzino</p>
				</div>
			</a>
		
			<a href="AdminProdotti">
				<div> 
					<img src="images/modifica-prodotti.png">
					<p><b>Modifica o rimuovi prodotti</b><br><br>Modifica o rimuovi i prodotti dal magazzino</p>
				</div>
			</a>
			
		
			<a href="AdminUsers">
				<div>
					<img src="images/modifica-utenti.png">
					<p><b>Modifica o rimuovi utenti</b><br><br>Modifica o rimuovi il profilo di utente</p>
				</div>
			</a>
		
		
			<a href="AdminCategoria">
				<div>
					<img src="images/aggiungi-categoria.png">
					<p><b>Aggiungi categoria</b><br><br>Aggiungi una categoria al sito</p>
				</div>
			</a>
		
		<% } %>
	</div>
		
		<jsp:include page="footer.jsp"/>