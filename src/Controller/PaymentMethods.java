package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Beans.CardBean;
import Model.Beans.UserBean;
import Model.DAO.CardDAO;

@WebServlet("/PaymentMethods")
public class PaymentMethods extends BaseServlet {
	private static final long serialVersionUID = -8754220416320891220L;
	
	private CardDAO cd = new CardDAO();
	private CardBean cb;
	private ArrayList<CardBean> cards;	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isLogged(request);
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		long IDUser = ub.getIDUser();
		String numeroCarta= request.getParameter("nrCarta");
		
		if(numeroCarta != null && !numeroCarta.isEmpty()) {
			String nome = request.getParameter("nome"),
				   cognome = request.getParameter("cognome"),
				   preferito = request.getParameter("preferito");
			
			cb = new CardBean();
			cb.setIdUser(IDUser);
			cb.setNrCarta(numeroCarta);
			cb.setNome(nome);
			cb.setCognome(cognome);
			
			boolean pref = false;
			if(preferito.equals("si")) {
				pref = true;
				cd.doUpdateFavourite(IDUser);
			}
			cb.setPreferred(pref);		
			cd.doSave(cb);
		}
			
		String updateFlag = request.getParameter("update");
		if(updateFlag != null) {
			cb = new CardBean();
			cb.setIdUser(IDUser);
			cb.setNrCarta(updateFlag);
			cb.setNome(request.getParameter("UpdNome"));
			cb.setCognome(request.getParameter("UpdCognome"));
			cd.doSaveOrUpdate(cb);
		}
		
		String deleteFlag = request.getParameter("delete");
		if(deleteFlag != null) {
			cd.doDelete(deleteFlag, IDUser);
		}
		
		cards = cd.doRetrieveByIDUser(IDUser);
		if(!cards.isEmpty()) {
			request.setAttribute("cards", cards);
		}
		request.getRequestDispatcher("/WEB-INF/View/paymentMethods.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}