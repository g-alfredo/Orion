package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import Model.Beans.ProductBean;
import Model.DAO.ProductDAO;

@WebServlet("/AjaxSearch")
public class AjaxSearch extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private ProductDAO pd = new ProductDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String AjaxInput = request.getParameter("input");
		String categoria = request.getParameter("categoria");
		String offset = request.getParameter("offset");
		
		if(AjaxInput == null || categoria == null) {
			response.sendRedirect("./");
		}
		else {
			int o = 0;
			if(offset != null) {
				try {
					o = Integer.parseInt(offset);
				} catch(NumberFormatException e) {
					e.printStackTrace();
				}
			}
			
			
			JSONArray suggestions = new JSONArray();
			if(AjaxInput != null) {
				ArrayList<ProductBean> products = pd.search(AjaxInput, categoria, o);
				for(ProductBean pb : products) {
					suggestions.put(pb.getTitolo());				
				}
			}
			response.setContentType("application/json");
			response.getWriter().append(suggestions.toString());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
