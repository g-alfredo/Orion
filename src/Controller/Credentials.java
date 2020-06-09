package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Beans.UserBean;
import Model.DAO.UserDAO;


@WebServlet("/Credentials")
public class Credentials extends BaseServlet {
	private static final long serialVersionUID = -6058503328121612738L;

	private final UserDAO ud = new UserDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isLogged(request);
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		boolean formAdmin=false;
		String userModifica=request.getParameter("userModify");
		if (userModifica!=null && ub.getAdmin()) {

			formAdmin=true;
			request.setAttribute("userModifica",userModifica);

			long userModify=Long.parseLong(userModifica);
			ub = ud.doRetrieveByKey(userModify);
			request.setAttribute("userModify", ub);

		}
		if(request.getParameter("rimuovi")!=null) {
			ud.doDelete(ub.getIDUser());
			response.sendRedirect("AdminUsers"); 
		}
		else {

			String email = request.getParameter("email");
			if(email != null && !email.isEmpty()) {
				ub.setEmail(email);
				ud.doSaveOrUpdate(ub);
			}

			String password = request.getParameter("password");
			if(password != null && !password.isEmpty()) {
				ub.setPassword(password);
				ud.doSaveOrUpdate(ub);
			}

			String nome = request.getParameter("nome");
			if(nome != null && !nome.isEmpty()) {
				ub.setNome(nome);
				ud.doSaveOrUpdate(ub);
			}

			String cognome = request.getParameter("cognome");
			if(cognome != null && !cognome.isEmpty()) {
				ub.setCognome(cognome);
				ud.doSaveOrUpdate(ub);
			}

			String telefono = request.getParameter("telefono");
			if(telefono != null && !telefono.isEmpty()) {
				ub.setTelefono(telefono);
				ud.doSaveOrUpdate(ub);
			}
			if(formAdmin) {
				String admin = request.getParameter("admin");
				if(admin != null && !admin.isEmpty()) {
					ub.setAdmin((admin.equals("true")) ? true : false);
					ud.doSaveOrUpdate(ub);
				}
			}

			request.getRequestDispatcher("/WEB-INF/View/credentialsUpdate.jsp").forward(request, response);
		}
	}
}
