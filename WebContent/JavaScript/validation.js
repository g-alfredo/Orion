function validateEmail() {
	var emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w+)+$/;
	$email = $("#email");

	if($email.val().match(emailRegex)) {
		display("validEmail", "OK", "");
		return true;
	} else {
		display("validEmail", "NO", "Email non valida!");
		emailOk = false;
	}
}

/*
 * Criteri per la password:
 *  - Almeno 6 caratteri;
 *  - Almeno una maiuscola;
 *  - Almeno una minuscola;
 *  - Almeno un numero;
 *  - Nessuno spazio;
 */

function validatePassword() {
	  var passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=\S+$).{6,}$/;
	  var $password = $("#password");           

	  if($password.val().match(passwordRegex)) {
	    display("validPassword", "OK", "");
	    return true;
	  } else {
	    display("validPassword", "NO", "Password non valida");
	    return false;
	  }
	}


function checkMatch() {
	var $password = $("#password");
	var $vPassword = $("#vPassword");

	if($password.val() == $vPassword.val()) {
		display("passwordMatch", "OK", "");
		return true;
	} else {
		display("passwordMatch", "NO", "Le password non corrispondono!");
		return false;
	}
}


function validateFirstName() {
	var regex = /^[A-Za-z \']*$/;
	var $firstName = $("#nome");

	if($firstName.val().match(regex)) {
		display("validFirstName", "OK", "");
		return true;
	}
	else {
		display("validFirstName", "NO", "Nome non valido!");
		return false;

	}
}


function validateLastName() {
	var regex = /^[A-Za-z \']*$/;
	var $lastName = $("#cognome");

	if($lastName.val().match(regex)) {
		display("validLastName", "OK", "");
		return true;
	}
	else {
		display("validLastName", "NO", "Cognome non valido!");
		return false;
	}
	return;
}


function validatePhone() {
	var regex = /^\d{9,}$/;
	var $phone = $("#telefono");
	
	if($phone.val().length == 0 || $phone.val().match(regex)) {
		display("validPhone", "OK", "");
		return true;
	} else {
		display("validPhone", "NO", "Numero Di Telefono non valido!");
		return false;
	}
	
}


function display(id, result, message) {
	$message = $("#" + id);

	if(result == "OK") {
		$message.html("");
		$message.css("display", "none");
	} else {
		$message.html(message);
		$message.css({
			"display" : "block",
			"color" : "red"
		});
	}
}
