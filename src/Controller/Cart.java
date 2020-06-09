package Controller;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Beans.CartBean;
import Model.Beans.ProductBean;
import Model.Beans.UserBean;
import Model.DAO.CartDAO;
import Model.DAO.ProductDAO;

@WebServlet("/Cart")
public class Cart extends BaseServlet {
	private static final long serialVersionUID = 2481101957626722432L;

	private ProductDAO pd = new ProductDAO();
	private CartDAO cd = new CartDAO();
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean ub = (UserBean) session.getAttribute("user");

		LinkedHashMap<Long, CartBean> cart = (LinkedHashMap<Long, CartBean>) session.getAttribute("cart");
		if(cart == null) {
			cart = new LinkedHashMap<Long, CartBean>();
			session.setAttribute("cart", cart);
		}
		
		//Prova ad aggiungere il nuovo prodotto, in caso si arrivi dalla pagina prodotto
		String productParam = request.getParameter("Prodotto");
		if(productParam != null) {
			Long IDProdotto = Long.parseLong(productParam);
			ProductBean pb = new ProductBean();
		
			pb = pd.doRetrieveByKey(IDProdotto);
			
			CartBean cb = new CartBean();
			cb.setProdotto(pb);
			
			cb.setQuantit�(Integer.parseInt(request.getParameter("Qnt")));
			if(ub != null) {
				cb.setIdUser(ub.getIDUser()); 
				cd.doSaveOrUpdate(cb);
			}
			
			//Controlla se il prodotto � gi� nel carrello ed eventualmente ne aggiorna la quantit�
			CartBean b = cart.get(IDProdotto);
			if(b == null) {
				cart.put(IDProdotto, cb);
				session.setAttribute("cart", cart);
			}
			else {
				int quantit�1=b.getQuantit�() + cb.getQuantit�();
				int quantit�2=pd.doRetrieveByKey(IDProdotto).getQuantit�();
				if(Math.min(quantit�1, quantit�2)==quantit�2)
					request.setAttribute("notifica", "Non � possibile inserire ulteriori prodotti di questo tipo");
				b.setQuantit�(Math.min(quantit�1, quantit�2));
			}
		}
		
		String removeFlag = request.getParameter("remove");
		if(removeFlag != null) {
			Long IDProdotto = Long.parseLong(removeFlag);
			cart.remove(IDProdotto);
			if(ub != null) {
				cd.doDelete(ub.getIDUser(), IDProdotto);
			}
		}	
		request.getRequestDispatcher("/WEB-INF/View/cart.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}