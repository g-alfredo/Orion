<%@ 
	page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.ArrayList, Model.Beans.UserBean"
%>

	<body>
		<jsp:include page="header.jsp">
		<jsp:param name="pageName" value="Informazioni account"/>
		<jsp:param name="stylesheet" value="CSS/stile-credenziali.css"/>
		
		</jsp:include>
		<jsp:include page="navbar.jsp"/>
		
		<section style="margin:15px;">
		
		<% 	UserBean ub = (UserBean) session.getAttribute("user"); 
			UserBean userModify = (UserBean) request.getAttribute("userModify");
			boolean formAdmin=ub.getAdmin();
			
			if (userModify!=null) 
				ub=userModify;
				
			
			if (userModify==null){
		%>
		<h2>Gestisci e modifica le informazioni associate al tuo account</h2>
			<% } 
			else  {  %>
				<form action="AdminUsers" method="get">
				<input type="submit" value="torna indietro">
				</form>
				<h2>Gestisci e modifica le informazioni associate all account di <%=ub.getNome() %> con ID <%=ub.getIDUser() %></h2>	
				
				<% } %>
			
		
		
		<div id="tabella">
		
		   <div>
			<span>Email: <%= ub.getEmail() %></span>
			<button class="btn modifybutton">Modifica</button>
			<div style="display: none;">
				<form action="Credentials" method="post" onsubmit="validateEmailChange(); return emailOk;">
					<span id="validEmail"></span>
          			<span id="usedEmail"></span>
          			<label for="email">Email</label><br>
          			<input id="email" class="form-field" type="text" name="email" required><br>
          			<% if(userModify!=null){ %> <input type="hidden" name="userModify" value="<%=ub.getIDUser() %>"> <% } %>
					<input class="btn" type="submit" value="Salva modifiche">
				</form>
			</div>
		   </div>
		   
		   <br>
		
		<div>
			<%
			String lunghezza = ub.getPassword();
			String pw = "";
			for(int i=0; i<lunghezza.length(); i++){
				pw = pw + "*";
			}
			%>
			
			<span>Password: <%= pw %></span>
			<button class="btn modifybutton">Modifica</button>
			<div style="display: none;">
				<form action="Credentials" method="post" onsubmit="return validatePassword() && checkMatch()">
					<span id="validPassword"></span>
          			<label for="password">Password</label><br>
          			<input id="password" class="form-field" type="password" name="password" required><br>
          
          			<span id="passwordMatch"></span>
          			<label for="vPassword">Conferma password </label><br>
          			<input id="vPassword" class="form-field" type="password" name="vPassword" required><br>
          			<% if(userModify!=null){ %> <input type="hidden" name="userModify" value="<%=ub.getIDUser() %>"> <% } %>
					<input class="btn" type="submit" value="Salva modifiche">
				</form>
			</div>
		</div>
		
		<br>
		
		<div>
			<span>Nome: <%= ub.getNome() %> <%= ub.getCognome() %></span>
			<button class="btn modifybutton">Modifica</button>
			<div style="display: none;">
				<form action="Credentials" method="post" onsubmit="return validateFirstName() &6 validateLastName()">
					<span id="validName"></span>
          			<label for="nome">Nome</label><br>
         			<input id="nome" class="form-field" type="text" name="nome" required><br>
     
          			<label for="cognome">Cognome</label><br>
          			<input id="cognome" class="form-field" type="text" name="cognome" required><br>
          			<% if(userModify!=null){ %> <input type="hidden" name="userModify" value="<%=ub.getIDUser() %>"> <% } %>

          			
					<input class="btn" type="submit" value="Salva modifiche">
				</form>
			</div>
		</div>
		
		<br>
		
		<div>
			<%
				String telefono = ub.getTelefono();
				if (telefono == null || telefono.isEmpty()) telefono = "Non hai mai inserito un numero di telefono";
			%>
			<span>Telefono: <%=telefono %></span>
			<button class="btn modifybutton">Modifica</button>
			<div style="display: none;">
				<form action="Credentials" method="post">
					<span id="validPhone"></span>
          			<label for="telefono">Telefono*</label><br>
          			<input id="telefono" class="form-field" type="text" name="telefono"><br>
          			
          			<% if(userModify!=null){ %> <input type="hidden" name="userModify" value="<%=ub.getIDUser() %>"> <% } %>
					<input class="btn" type="submit" value="Salva modifiche">
				</form>
			</div>
		</div>
		
		<br>
		
		
		<% if(formAdmin){ %>
			<div>
			<span>Admin: <%= ub.getAdmin() ? "Si" : "No" %></span>
			<button class="btn modifybutton">Modifica</button>
			<div style="display: none;">
				<form action="Credentials" method="post">
					<input type="radio" name="admin" value="true" <%= ub.getAdmin()? "checked" : "" %>> Si<br>
  					<input type="radio" name="admin" value="false" <%= ub.getAdmin()? "" : "checked" %>> No<br>
          			<% if(userModify!=null){ %> <input type="hidden" name="userModify" value="<%=ub.getIDUser() %>"> <% } %>

  					<input class="btn" type="submit" value="Salva modifiche">
				</form>
			</div>
			</div>
		<% } %>
		
	</div>
		</section>

		
		<script type="text/javascript">
		var emailOk; 
		
		function validateEmailChange() {
    		var emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w+)+$/;
    		var $email = $("#email");

    		if($email.val().match(emailRegex)) {
    			display("validEmail", "OK", "");

    			$.ajax({
    				type : "POST",
    				url : "EmailChecker",
    				data : "email=" + $email.val(),
    				async: true,		    
    				success : function(msg) {
    					if(msg == "OK") {    
    						display("validEmail", "OK", "");
    						 emailOk = true;
    					} else {      
    						display("validEmail", "NO", "Email già in uso!");
    						emailOk = false;
    					}
    				}
    			});
    		} else {
    			display("validEmail", "NO", "Email non valida!");
    			emailOk = false;
    		}
    		return;
    	}
		
			$("button.modifybutton").click(function() {
				$(this).next().toggle();
			});
		</script>
	
	<br>	
	
	<jsp:include page="footer.jsp"/>
