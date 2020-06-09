package Controller;

import java.util.ArrayList;
import java.util.regex.Pattern;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.*;

import Model.Beans.UserBean;
import Model.DAO.CartDAO;
import Model.DAO.UserDAO;
import Model.DAO.WishListDAO;

@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 6371110858082757544L;

	private UserDAO ud = new UserDAO();
	private UserBean ub;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
    
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    ArrayList<String> errors = new ArrayList<String>();
    
	    if(email != null && password != null) {
	    	if(validateEmail(email) && validatePassword(password)) {
	    		ub = ud.doRetrieveByEmail(email);
				if(ub != null) {
					String dbPassword = ub.getPassword();
					//Dati corretti
					if(dbPassword.equals(password)) {
						HttpSession session = request.getSession();
						session.setAttribute("user", ub);
						
						CartDAO cd = new CartDAO();
						session.setAttribute("cart", cd.userCart(ub.getIDUser()));
						WishListDAO wd = new WishListDAO();
						session.setAttribute("wishlist", wd.userWishlist(ub.getIDUser()));
					}
					//Utente esistente ma password sbagliata
					else errors.add("Password errata");
				}
				//Utente inesistente
				else errors.add("Utente non registrato");
    	    }
    	    //Parametri non validi
	    	else errors.add("Parametri non validi");
    	 
	    	if(!errors.isEmpty()) request.setAttribute("error", errors);
	    	String dest = request.getHeader("referer");
			if (dest == null || dest.contains("/SignIn") || dest.trim().isEmpty()) {
				dest = ".";
			}
			response.sendRedirect(dest);
	    }
	    else request.getRequestDispatcher(".").forward(request, response);
	}
  
  private boolean validateEmail(String email) {
    Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    if(emailRegex.matcher(email).matches()) return true;
    else return false;
  }
  
  private boolean validatePassword(String password) {
    Pattern passwordRegex = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=\\S+$).{6,}$");
    
    if(passwordRegex.matcher(password).matches()) return true;
    else return false;
  }
}