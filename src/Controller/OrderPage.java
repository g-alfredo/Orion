package Controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Beans.CartBean;
import Model.Beans.OrderBean;
import Model.Beans.OrderedProductBean;
import Model.Beans.ProductBean;
import Model.Beans.UserBean;
import Model.DAO.CardDAO;
import Model.DAO.OrderDAO;
import Model.DAO.OrderedProductDAO;
import Model.DAO.ProductDAO;
import Model.DAO.ShippingAddressDAO;

@WebServlet("/OrderPage")
public class OrderPage extends BaseServlet {
	private static final long serialVersionUID = -8055871902338401129L;

	private ShippingAddressDAO ad = new ShippingAddressDAO();
	private CardDAO cd = new CardDAO();
	private ProductDAO pd=new ProductDAO();

	@SuppressWarnings({ "unchecked" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isLogged(request);
		HttpSession session = request.getSession();
		UserBean ub = (UserBean) session.getAttribute("user");
		LinkedHashMap<Long, CartBean> cart = (LinkedHashMap<Long, CartBean>) session.getAttribute("cart");

		if(ub != null) {
			long IDUser = ub.getIDUser();
			request.setAttribute("addresses", ad.doRetrieveByIDUser(IDUser));
			request.setAttribute("cards", cd.doRetrieveByIDUser(IDUser));
			request.setAttribute("totale", request.getParameter("totale"));

			String completeOrder = request.getParameter("ordina");
			if(completeOrder != null && cart!= null) {
				String numCarta = request.getParameter("card");
				int numIndirizzo = Integer.parseInt(request.getParameter("address"));

				boolean excess = false;
				Iterator<Entry<Long, CartBean>> it = cart.entrySet().iterator();
				while(it.hasNext()) {
					Entry<Long, CartBean> e = (Entry<Long, CartBean>) it.next();
					CartBean cb = e.getValue();

					int cartQnt = cb.getQuantità();
					int dbQnt = pd.doRetrieveByKey(e.getKey()).getQuantità();

					if(dbQnt - cartQnt < 0) {
						it.remove();
						excess = true;
					}
				}

				if(excess) {
					request.setAttribute("excess", excess);
					request.getRequestDispatcher("Cart").forward(request, response);
				}
				else {
					if(!cart.isEmpty()) {
						//Salva il nuovo ordine, se restano prodotti nel carrello
						OrderBean ob = new OrderBean();
						ob.setIDUser(ub.getIDUser());
						ob.setNrIndirizzo(numIndirizzo);
						ob.setNrCarta(numCarta);
						ob.setDataOrdine(new java.sql.Date(new java.util.Date().getTime()));
						ob.setCostoSpedizione(10);		
						OrderDAO od = new OrderDAO();
						ob.setIDOrdine(od.doSave(ob));

						//Aggiorna le quantità dei prodotti, salva i prodotti ordinati nel DB ed elimina il carrello dalla sessione

						it = cart.entrySet().iterator();
						while(it.hasNext()) {
							Entry<Long, CartBean> e = (Entry<Long, CartBean>) it.next();
							ProductBean pb = e.getValue().getProdotto();

							OrderedProductBean opb = new OrderedProductBean();
							opb.setIDOrdine(ob.getIDOrdine());
							opb.setIDProdotto(pb.getIdProdotto());
							opb.setTitolo(pb.getTitolo());
							opb.setQuantità(e.getValue().getQuantità());
							double prezzo=pb.getPrezzo() - (pb.getPrezzo() * pb.getPercSconto() / 100);		
							double prezzofinale=(Math.floor(prezzo*100))/100;

							opb.setPrezzo(prezzofinale);					

							OrderedProductDAO opd = new OrderedProductDAO();
							opd.doSave(opb);

							int quantità = e.getValue().getQuantità();
							pb.setQuantità(pb.getQuantità() - quantità);

							ProductDAO pd = new ProductDAO();		
							pd.doSaveOrUpdate(pb);
						}
					}
					cart = new LinkedHashMap<Long, CartBean>();
					session.setAttribute("cart", cart);

					request.setAttribute("success", true);
					request.getRequestDispatcher("/WEB-INF/View/orderPage.jsp").forward(request, response);
				}
			}
			else request.getRequestDispatcher("/WEB-INF/View/orderPage.jsp").forward(request, response);			
		}
		else request.getRequestDispatcher("./").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}