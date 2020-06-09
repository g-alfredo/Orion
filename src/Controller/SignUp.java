package Controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Beans.CartBean;
import Model.Beans.UserBean;
import Model.Beans.WishListBean;
import Model.DAO.CartDAO;
import Model.DAO.UserDAO;
import Model.DAO.WishListDAO;

@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 6766650350974206104L;

	private UserDAO ud = new UserDAO();
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
  
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String email = request.getParameter("email"),
			   password = request.getParameter("password"),
			   vPassword = request.getParameter("vPassword"),
			   nome = request.getParameter("nome"),
			   cognome = request.getParameter("cognome"),
			   telefono = request.getParameter("telefono");
    
		if(email != null && password != null && vPassword != null && nome != null && cognome != null) {
			if(validateEmail(email) && validatePassword(password) && password.equals(vPassword) && validateFullName(nome, cognome)) {
				UserBean ub = new UserBean();
				ub.setEmail(email);
				ub.setPassword(password);
				ub.setNome(nome);
				ub.setCognome(cognome);
				ub.setTelefono(telefono);
		       
				int result = ud.doSave(ub);
				if(result == -1) throw new OrionException("Qualcosa è andato storto durante la registrazione");
				else ub.setIDUser(result);
				
				HttpSession session = request.getSession();
				session.setAttribute("user", ub);
				
				LinkedHashMap<Long, CartBean> cart = (LinkedHashMap<Long, CartBean>) session.getAttribute("cart");
				if(cart != null) {
					CartDAO cd = new CartDAO();
					for(Entry<Long, CartBean> e : cart.entrySet()) {
						CartBean cb = e.getValue();
						cb.setIdUser(ud.doRetrieveByEmail(email).getIDUser());
						cd.doSave(cb);
					}
				}				
				LinkedHashMap<Long, WishListBean> wishlist = (LinkedHashMap<Long, WishListBean>) session.getAttribute("wishlist");
				if(wishlist != null) {
					WishListDAO wd = new WishListDAO();
					for(Entry<Long, WishListBean> e : wishlist.entrySet()) {
						WishListBean wb = e.getValue();
						wb.setIdUser(ud.doRetrieveByEmail(email).getIDUser());
						wd.doSave(wb);
					}
				}
				request.getRequestDispatcher("./").forward(request, response);
			}
			else request.getRequestDispatcher("/WEB-INF/View/signUp.jsp").forward(request, response);
		}
		else request.getRequestDispatcher("/WEB-INF/View/signUp.jsp").forward(request, response);
 
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
  
	private boolean validateFullName(String nome, String cognome) {
		Pattern regex = Pattern.compile("^[A-Za-z0-9 ]*$", Pattern.CASE_INSENSITIVE);
		
		if(regex.matcher(nome).matches() && regex.matcher(cognome).matches()) return true;
		else return false;
	}
}