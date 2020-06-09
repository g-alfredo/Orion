<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="CSS/stile-signup.css">
    <link rel="icon" href="images/icon.png">
    <title>Registrazione</title>
  </head>
  
  <body>
 	<div style="margin: 20px;">
 		<a href="./"><img src="images/logo.PNG"></a>
 	</div>
    
    	<form id="signUpForm" name="signUpForm" action="SignUp" method="post" onsubmit="return validateForm()">       	
        	<h3 style="text-align:center;">Crea un account</h3>
        	<span id="validSubmit"></span>
        	<span id="validEmail"></span>
        	<label for="email">E-mail</label><br>
        	<input id="email" class="form-field" type="text" name="email" required onblur="validateSignUpEmail()"><br>
          
        	<span id="validPassword"></span>
        	<label for="password">Password</label>
        	<input id="password" class="form-field" type="password" name="password" required onblur="passwordOk = validatePassword()"><br>
          
        	<span id="passwordMatch"></span>
        	<label for="vPassword">Conferma password </label><br>
        	<input id="vPassword" class="form-field" type="password" name="vPassword" required onblur="passwordMatch = checkMatch()"><br>
          
          	<span id="validFirstName"></span>
          	<label for="nome">Nome</label><br>
          	<input id="nome" class="form-field" type="text" name="nome" required onblur="firstNameOk = validateFirstName()"><br>
          
          	<span id="validLastName"></span>
          	<label for="cognome">Cognome</label><br>
          	<input id="cognome" class="form-field" type="text" name="cognome" required onblur="lastNameOk = validateLastName(); phoneOk = validatePhone()"><br>
          
          	<span id="validPhone"></span>
          	<label for="telefono">Telefono*</label><br>
          	<input id="telefono" class="form-field" type="text" name="telefono" onblur = "phoneOk = validatePhone()"><br>
          
          	<button id="submitButton" type="submit" class="btn">Registrati</button>
        </form>
        
        <script>
	        var emailOk = false;
	    	var passwordOk = false;
	    	var passwordMatch = false;
	    	var firstNameOk = false;
	    	var lastNameOk = false;
	    	var phoneOk = false;
	    	
	    	function validateForm() {
	    		if(emailOk && passwordOk && passwordMatch && firstNameOk && lastNameOk && phoneOk) return true;
	    		else {
	    			$("#validSubmit").html("Riempi tutti i campi correttamente");
	    			return false;
	    		}
	    	}
	    	
	    	function validateSignUpEmail() {
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
        </script>
        <script type="text/javascript" src="JavaScript/jquery.js"></script>
    <script type="text/javascript" src ="JavaScript/validation.js"></script>
	</body>
</html>