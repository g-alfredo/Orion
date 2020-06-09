package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.DAO.ProductDAO;

@WebServlet("/OnSale")
public class OnSale extends BaseServlet {
	private static final long serialVersionUID = -2476505990426847414L;

	private ProductDAO pd = new ProductDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNumber = request.getParameter("pageNumber");
		
		int pageNum = 0;
		int offset = 0;
		if(pageNumber != null) {
			try {
				pageNum = Integer.parseInt(pageNumber);
				offset = pageNum * 10;
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}
		int prevOffset = offset-10;

		
		if(prevOffset >= 0) {
			if(!pd.doRetrieveOnSale(prevOffset).isEmpty()) {
				request.setAttribute("prevPage", true);
			}
		}
		
		if(!pd.doRetrieveOnSale(offset + 10).isEmpty()) {
			request.setAttribute("nextPage", true);
		}
		
		
		request.setAttribute("prodotti", pd.doRetrieveOnSale(offset));
		request.setAttribute("pageNumber", pageNum);
		request.getRequestDispatcher("/WEB-INF/View/onSale.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}