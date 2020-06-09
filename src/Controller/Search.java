package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.DAO.CategoryDAO;
import Model.DAO.ProductDAO;


@WebServlet("/Search")
public class Search extends BaseServlet {
	private static final long serialVersionUID = -7814253409688261744L;

	private ProductDAO pd = new ProductDAO();
	private CategoryDAO cd = new CategoryDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String input = request.getParameter("search-field");
		String categoria = request.getParameter("categoria");
		String pageNumber = request.getParameter("pageNumber");
		
		int pageNum=0;
		int offset = 0;
		if (pageNumber!=null) {
			try {
				pageNum=Integer.parseInt(pageNumber);
				offset = pageNum * 10;
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}


		if(input == null || input.isEmpty()) {
			request.getRequestDispatcher("./").forward(request, response);
		}
		else {
			int prevOffset=offset-10;
			if(prevOffset>=0){
				if(!pd.search(input, categoria, prevOffset).isEmpty()) {
					request.setAttribute("prevPage", true);
				}
			}
			if(!pd.search(input, categoria, offset+10).isEmpty()) {
				request.setAttribute("nextPage", true);
			}
			request.setAttribute("prodotti", pd.search(input, categoria, offset));
			request.setAttribute("categorie", cd.doRetrieveAll());
			request.setAttribute("search-field", input);
			request.setAttribute("categoria", categoria);
			request.setAttribute("pageNumber", pageNum);
			request.getRequestDispatcher("/WEB-INF/View/search.jsp").forward(request, response);
		} 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}