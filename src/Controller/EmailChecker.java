package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.DAO.UserDAO;

@WebServlet("/EmailChecker")
public class EmailChecker extends HttpServlet {
	private static final long serialVersionUID = 351884748223820291L;
	
	private final UserDAO ud = new UserDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		PrintWriter out = response.getWriter();
		
		if(email == null) {
			response.sendRedirect("./");
		}
		else {
			if(ud.doRetrieveByEmail(email) != null) {
				out.append("NO");
			}
			else {
				out.append("OK");
			}
		}
	}
}
