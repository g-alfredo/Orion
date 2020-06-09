package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Beans.ShippingAddressBean;
import Model.Beans.UserBean;
import Model.DAO.ShippingAddressDAO;

@WebServlet("/Addresses")
public class Addresses extends BaseServlet {
	private static final long serialVersionUID = 1140822978723033383L;

	private ShippingAddressDAO ad = new ShippingAddressDAO();
	private ShippingAddressBean ab;
	private ArrayList<ShippingAddressBean> addresses;	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isLogged(request);
		UserBean ub = (UserBean) request.getSession().getAttribute("user");	
		long IDUser = ub.getIDUser();
		String provincia = request.getParameter("provincia");	
		
		if(provincia != null && !provincia.isEmpty()) {
			String città = request.getParameter("citta"),
				   via = request.getParameter("via"),
				   cap = request.getParameter("cap"),
				   civico = request.getParameter("civico"),	
				   preferito = request.getParameter("preferito");
			
			ab = new ShippingAddressBean();
			ab.setIDUser(IDUser);
			ab.setProvincia(provincia);
			ab.setCittà(città);
			ab.setVia(via);
			try {
				ab.setCap(Integer.parseInt(cap));
				ab.setCivico(Integer.parseInt(civico));
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
			
			
			boolean pref = false;
			if(preferito.equals("si")) {
				pref = true;
				ad.doUpdateFavorite(IDUser);
			}
			ab.setPreferred(pref);
			
			ad.doSave(ab);
		}
		
		String updateFlag = request.getParameter("update");
		if(updateFlag != null) {
			try {
				ab = new ShippingAddressBean();
				ab.setIDUser(IDUser);
				ab.setNumIndirizzo(Integer.parseInt(updateFlag));
				ab.setProvincia(request.getParameter("UpdProvincia"));
				ab.setCittà(request.getParameter("UpdCitta"));
				ab.setVia(request.getParameter("UpdVia"));
				ab.setCap(Integer.parseInt(request.getParameter("UpdCap")));
				ab.setCivico(Integer.parseInt(request.getParameter("UpdCivico")));
				
				String  preferito = request.getParameter("UpdPreferito");
				boolean pref = false;
				if(preferito.equals("si")) {
					pref = true;
					ad.doUpdateFavorite(IDUser);
				}
				ab.setPreferred(pref);
				ad.doSaveOrUpdate(ab);			
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		String deleteFlag = request.getParameter("delete");
		if(deleteFlag != null) {
			ad.doDelete(IDUser, Integer.parseInt(deleteFlag));
		}
		
		addresses = ad.doRetrieveByIDUser(IDUser);
		if(!addresses.isEmpty()) {
			request.setAttribute("addresses", addresses);
		}
		request.getRequestDispatcher("/WEB-INF/View/addresses.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
