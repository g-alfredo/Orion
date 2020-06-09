package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.DAO.OrderDAO;
import Model.DAO.ProductDAO;
import Model.DAO.ReviewDAO;
import Model.Beans.ProductBean;
import Model.Beans.ReviewBean;
import Model.Beans.UserBean;

@WebServlet("/ProductPage")
public class ProductPage extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private ProductDAO pd = new ProductDAO();
	private ReviewDAO rd = new ReviewDAO();
	
	private ProductBean pb = new ProductBean();
	private ArrayList<ReviewBean> reviews; 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("ID")==null) {
			throw new OrionException("Il prodotto che cerchi non è presente sul database.");
		}
		long IDProdotto = Long.parseLong(request.getParameter("ID"));
		pb=null;
		try {
			pb = pd.doRetrieveByKey(IDProdotto);
			reviews = rd.doRetrieveByID(IDProdotto);
			
			request.setAttribute("prodotto", pb);
			request.setAttribute("recensioni", reviews);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		if (pb==null) {
			throw new OrionException("Il prodotto che cerchi non è presente sul database.");
		}
		
		boolean reviewFlag = false;
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		if(ub != null) {
			long IDUser = ub.getIDUser();
			OrderDAO od = new OrderDAO();
			ReviewDAO rd = new ReviewDAO();
			
			if(od.orderOk(IDUser, IDProdotto) && rd.reviewOk(IDUser, IDProdotto)) reviewFlag = true;
			else reviewFlag = false;	
		}
		request.setAttribute("reviewFlag", reviewFlag);
		request.getRequestDispatcher("/WEB-INF/View/productPage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}