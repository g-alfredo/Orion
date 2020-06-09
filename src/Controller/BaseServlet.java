package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Beans.CategoryBean;
import Model.Beans.UserBean;
import Model.DAO.CategoryDAO;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CategoryDAO categoryDAO= new CategoryDAO();

	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {

		
			ArrayList<CategoryBean> categorie =categoryDAO.doRetrieveAll();
			request.setAttribute("categorie", categorie);
			super.service(request, response);
		
	}
	protected void isLogged(HttpServletRequest request) throws ServletException {
		UserBean utente = (UserBean) request.getSession().getAttribute("user");
		if (utente == null) {
			throw new OrionException("Devi aver effettuato l'accesso per visualizzare questa pagina!");
		}
	}
	
	
	protected void isAdmin(HttpServletRequest request) throws ServletException {
		UserBean utente = (UserBean) request.getSession().getAttribute("user");
		if (utente == null || utente.getAdmin()==false) {
			throw new NotAutorizedException("Non hai i permessi per visualizzare questa pagina");
		}
	}
	
}
