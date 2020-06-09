package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Beans.OrderBean;
import Model.Beans.UserBean;
import Model.DAO.OrderDAO;
import Model.DAO.UserDAO;

@WebServlet("/Orders")
public class Orders extends BaseServlet {
	private static final long serialVersionUID = -2809920245179537855L;

	private final OrderDAO od = new OrderDAO();
	private final UserDAO ud=new UserDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isLogged(request);
		HttpSession session = request.getSession();
		UserBean utente=(UserBean)session.getAttribute("user");
		if (utente.getAdmin()) {
			long userAdmin=-1;
			try {
				userAdmin=Long.parseLong(request.getParameter("userAdmin"));
			}
			catch (NumberFormatException e){
				
			}
			if (userAdmin>=0) {
				ArrayList<OrderBean> orders = od.doRetriveByUserID(userAdmin);
				request.setAttribute("userOrders", orders);
				UserBean utenteAdmin=ud.doRetrieveByKey(userAdmin);
				request.setAttribute("utenteAdmin", utenteAdmin);
				request.getRequestDispatcher("/WEB-INF/View/orders.jsp").forward(request, response);
			}
			else {
				ArrayList<OrderBean> orders = od.doRetriveByUserID(utente.getIDUser());
				request.setAttribute("userOrders", orders);			
				request.getRequestDispatcher("/WEB-INF/View/orders.jsp").forward(request, response);
			}
		} else {

			ArrayList<OrderBean> orders = od.doRetriveByUserID(utente.getIDUser());
			request.setAttribute("userOrders", orders);			
			request.getRequestDispatcher("/WEB-INF/View/orders.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}