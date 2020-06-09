package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Beans.ProductBean;
import Model.DAO.ProductDAO;


@WebServlet("")
public class HomePage extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private ProductDAO productDAO= new ProductDAO();
	private ArrayList<ProductBean> prodotti;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			prodotti = productDAO.doRetrieveAll(10,0);
			request.setAttribute("prodotti", prodotti);
			request.getRequestDispatcher("/WEB-INF/View/homepage.jsp").forward(request, response);
		} catch (Exception e) {
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
