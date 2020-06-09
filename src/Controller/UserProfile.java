package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Beans.UserBean;

@WebServlet("/UserProfile")
public class UserProfile extends BaseServlet {
	private static final long serialVersionUID = 6389521846496460448L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if(ub != null) request.getRequestDispatcher("/WEB-INF/View/userProfile.jsp").forward(request, response);
		else request.getRequestDispatcher(".").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
