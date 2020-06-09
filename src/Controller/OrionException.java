package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;


@WebServlet("/OrionException")
public class OrionException extends ServletException {
	private static final long serialVersionUID = -189425972677654705L;

	public OrionException() {
		super();
	}

	public OrionException(String message) {
		super(message);
	}

	public OrionException(String message, Throwable rootCause) {
		super(message, rootCause);
	}


	public OrionException(Throwable rootCause) {
		super(rootCause);
	}
}
