package Controller;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Beans.ProductBean;
import Model.Beans.UserBean;
import Model.Beans.WishListBean;
import Model.DAO.ProductDAO;
import Model.DAO.WishListDAO;

@WebServlet("/WishList")
public class WishList extends BaseServlet {
	private static final long serialVersionUID = -3607620504354698270L;

	private ProductDAO pd = new ProductDAO();
	private WishListDAO wd = new WishListDAO(); 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean ub = (UserBean) session.getAttribute("user");

		@SuppressWarnings("unchecked")
		LinkedHashMap<Long, WishListBean> wishlist = (LinkedHashMap<Long, WishListBean>) session.getAttribute("wishlist");
		//Utente non registrato
		if(wishlist == null) {
			wishlist = new LinkedHashMap<Long, WishListBean>();
			session.setAttribute("wishlist", wishlist);
		}
		
		//Prova ad aggiungere il nuovo prodotto, in caso si arrivi dalla pagina prodotto
		String productParam = request.getParameter("Prodotto");
		if(productParam != null) {
			Long IDProdotto = Long.parseLong(productParam);
			ProductBean pb = new ProductBean();
			
			pb = pd.doRetrieveByKey(IDProdotto);
			
			WishListBean wb = new WishListBean();
			wb.setProdotto(pb);
			if(ub != null) {
				wb.setIdUser(ub.getIDUser());
				wd.doSave(wb);
			}
			
			//Controlla se il prodotto è già nella wishlist
			WishListBean b = wishlist.get(IDProdotto);
			if(b == null) {
				wishlist.put(IDProdotto, wb);
				session.setAttribute("wishlist", wishlist);
			}
		}
		
		String removeFlag = request.getParameter("remove");
		if(removeFlag != null) {
			Long IDProdotto = Long.parseLong(removeFlag);
			wishlist.remove(IDProdotto);
		}
		
		request.getRequestDispatcher("/WEB-INF/View/wishlist.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
