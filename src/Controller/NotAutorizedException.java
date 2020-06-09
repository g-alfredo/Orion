package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class NotAutorizedException
 */
@WebServlet("/NotAutorizedException")
public class NotAutorizedException extends ServletException{

	private static final long serialVersionUID = 1L;

    public NotAutorizedException() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see ServletException#ServletException(String)
     */
    public NotAutorizedException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see ServletException#ServletException(String, Throwable)
     */
    public NotAutorizedException(String message, Throwable rootCause) {
        super(message, rootCause);
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see ServletException#ServletException(Throwable)
     */
    public NotAutorizedException(Throwable rootCause) {
        super(rootCause);
        // TODO Auto-generated constructor stub
    }


}
