package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Beans.UserBean;
import Model.DAO.UserDAO;

@WebServlet("/AdminUsers")
public class AdminUsers extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO ud=new UserDAO();

    public AdminUsers() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isAdmin(request);
		int limite=10;
		int pagina;
		try{
			pagina=Integer.parseInt(request.getParameter("pagina"));
		}
		// Catturo l eccezione se non è presente l attributo nella richiesta
		catch (RuntimeException e) {
			pagina=0;
		}

		ArrayList<UserBean>utenti = ud.doRetrieveAll(pagina*limite, limite);
		int prevPage=pagina-1;
		if(prevPage>=0){
			if(!ud.doRetrieveAll(prevPage*limite, limite).isEmpty()) {
				request.setAttribute("prevPage", prevPage);
		}
		}
		if(!ud.doRetrieveAll((pagina+1)*limite, limite).isEmpty()) {
			request.setAttribute("nextPage", (pagina+1));
		}
		request.setAttribute("utenti", utenti);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/View/adminusers.jsp");
		requestDispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
