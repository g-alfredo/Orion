<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
  	     import="java.util.ArrayList, Model.Beans.*, Model.DAO.*" 
%>	     

<!DOCTYPE html>
<html>
 	<head>
 	<meta http-equiv="Content-type" content="text/html;charset=utf-8" />
 		<title><%=request.getParameter("pageName")==null?"Orion":request.getParameter("pageName") %></title>
 		<% if(request.getParameter("stylesheet")!=null){ %>
 		<link type="text/css" rel="stylesheet" href="<%=request.getParameter("stylesheet") %>">
 		<% } %>
    	<link type="text/css" rel="stylesheet" href="CSS/stile-header.css">
    	<link type="text/css" rel="stylesheet" href="CSS/stile-login.css">
    	<link type="text/css" rel="stylesheet" href="CSS/stile-navbar.css">
    	<script type="text/javascript" src="JavaScript/jquery.js"></script>
    	<script type="text/javascript" src="JavaScript/validation.js"></script>
    	<link rel="icon" href="images/icon.png">
  	</head>
  	
  	<body>
  		<div class="topnav">
    		<a href="./"><img id="logoImage" src="images/logo.PNG" alt="logo"></a>
    		
	  		<form class="search-container" name="searchForm" action="Search" method="get" onsubmit=""> 
				<select id="categorie" name="categoria">
					<option value="" selected> Tutte le categorie</option>
					<% ArrayList<CategoryBean> categorie=(ArrayList<CategoryBean>) request.getAttribute("categorie");
						if(categorie!=null){
						for(CategoryBean categoria: categorie) {
					%>
							<option value="<%=categoria.getNome()%>" > <%=categoria.getNome()%> </option>
					<% } }%>
				</select>
				
				<input id="search-field" type="text" name="search-field" list="search-datalist" placeholder="Cerca prodotti" autocomplete="off" onkeyup="getSuggestions(this.value)">	
				<datalist id="search-datalist"></datalist>	
				<input id="search-button" type="submit" value="Cerca" >
			</form>
      
    		<div class="dropdown">
      			<% UserBean ub = (UserBean) session.getAttribute("user");
      				if(ub!= null) {
      					if(ub.getAdmin()) {     			
     			%>
      						<button class="admin-button">Admin</button>
      			<% } else { %>
      					<button class="ciao-button">Ciao <%= ub.getNome() %>!</button>
      					<% } %>
      					<div id="user-block" class="user-popup">
      						<form action="UserProfile" style="display: flex; flex-direction: row; align-items: center;">
      							<img src="images/profilo.png" width="50">
      							<input class="btn" type="submit" value="Profilo">
      						</form>
      						
      			
      			
			      			<form action="Logout" style="display: flex; flex-direction: row; align-items: center;">
			      				<img src="images/logout.png" width="50">
			      				<input class="btn" type="submit" value="Esci">
			      			</form>
			      		</div>
      		
      			<% } else { %>
			      	   	<button class="login-button">Accedi</button>
			      		
			      		<div id="form-block" class="form-popup">
			      			<form id="signInForm" class="form-container" name="signInForm" action="SignIn" method="post">
			      				Hai un account?
			        			<span id="validEmail"></span>
			          			<input id="email" class="form-field" type="text" name="email" placeholder="Email" style="padding:8px;" onblur="emailOk = validateEmail()">
			          			
				        		<span id="validPassword"></span>
				        		<input id="password" class="form-field" type="password" name="password" placeholder="Password" style="padding:8px;" onblur="passwordOk = validatePassword()">
				          
				        		<button type="submit" class="btn" onclick="return validateForm()">Accedi</button>
				        		<br>Prima volta su Orion?<br>
				        		<button type="submit" class="btn" formaction="SignUp" formnovalidate onclick="clearForm()">Registrati</button>
			        		</form>
			      		</div>
	  			<% } %>
	  			
		      	<%	ArrayList<String> errors = (ArrayList<String>) request.getAttribute("error");
		      		if(errors != null && !errors.isEmpty()) {
		      			for(String s : errors){
		      	%>
		      				<span><%= s %></span>
		      	<% } } %>   
       		</div>
      </div>
    
    <script> 
    	var emailOk = false;
    	var passwordOk = false;
    
    	function validateForm() {
    		if(emailOk && passwordOk) return true;
    		else return false;
    	}
    	
    	
    	/* AJAX per la ricerca */
    	function getSuggestions(input) {
    		var $datalist = $("#search-datalist");
    		if(input.length == 0) {
    			$datalist.html("");
    			return;
    		}
    		
    		$.ajax({
    			type:"POST",
    			url: "AjaxSearch",
    			data : {
    				"input": input,
    				"categoria": $("#categorie").val()
    			},
    			dataType: "json",
    			async: true,
    			success: function(response) {
    				$datalist.html("");
    				
    				for(var i in response) {
    					var option = document.createElement("option");
    					option.value = response[i];
    					$datalist.append(option);
    				}
    			}
    		});
    	}
  
    	
    	$(document).click(function(){
            $(".admin-button, .ciao-button, .login-button").next().hide();
        });

          $(".dropdown").click(function(e){
            e.stopPropagation();
          });
         
          $(".admin-button, .ciao-button, .login-button").click(function(){
           $(this).next().toggle();
          });

    	
      function clearForm() {
        var form = document.forms["logInForm"];      
        form.email.value = null;
        form.password.value = null;
      }
    </script>