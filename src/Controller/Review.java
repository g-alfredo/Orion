package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Beans.ReviewBean;
import Model.Beans.UserBean;
import Model.DAO.ReviewDAO;


@WebServlet("/Review")
public class Review extends BaseServlet {
	private static final long serialVersionUID = -435858769939380555L;

	private ReviewDAO rd = new ReviewDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isLogged(request);
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		String productParam = request.getParameter("IDProdotto");
		
		if(productParam != null) {
			long IDProdotto = Long.parseLong(productParam);
			
			String titolo = request.getParameter("titolo"),
					   punteggio = request.getParameter("punteggio"),
					   descrizione = request.getParameter("descrizione");
			
			if(titolo != null && punteggio != null && descrizione != null) {
				ReviewBean rb = new ReviewBean();
				rb.setIdUser(ub.getIDUser());			
				rb.setIdProdotto(IDProdotto);
				rb.setTitolo(titolo);
				rb.setPunteggio(Integer.parseInt(punteggio));
				rb.setDescrizione(descrizione);
				rb.setDataPubblicazione(new java.sql.Date(new java.util.Date().getTime()));
				rd.doSave(rb);
			}
		
			String deleteFlag = request.getParameter("delete");
			if(deleteFlag != null) {
				String userID = request.getParameter("userID");
				if(userID != null) {
					try {
						rd.doDelete(Long.parseLong(userID), IDProdotto);
					} catch(NumberFormatException e) {
						e.printStackTrace();
					}
				}
				else rd.doDelete(ub.getIDUser(), IDProdotto);

			}		
			request.getRequestDispatcher("ProductPage?ID=" + IDProdotto).forward(request, response);
		}
		else request.getRequestDispatcher("./").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
