package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Beans.CategoryBean;
import Model.Beans.ProductBean;
import Model.DAO.CategoryDAO;
import Model.DAO.ProductDAO;

@WebServlet("/Categoria")
public class CategoryPage extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ProductBean> prodotti; 
	private ProductDAO pd = new ProductDAO();
	private CategoryDAO cd = new CategoryDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int limite=10;
		int pagina=0;
		try{
			pagina=Integer.parseInt(request.getParameter("pagina"));
		}
		// Catturo l eccezione se non è presente l attributo nella richiesta
		catch (RuntimeException e) {
			pagina=0;
		}
		String categoria = request.getParameter("categoria");
		if (categoria==null) {
			throw new OrionException("La pagina che cerchi non esiste.");
		}
		CategoryBean cb=new CategoryBean();
		cb.setNome(categoria);
		CategoryBean categoriaExist=cd.doRetrieveByKey(cb);
		if (categoriaExist==null)
			throw new OrionException("La categoria che cerchi non esiste.");

		prodotti = pd.doRetrieveByCategory(categoria,limite,pagina*limite);

		int prevPage=pagina-1;
		if(prevPage>=0){
			if(!pd.doRetrieveByCategory(categoria,limite, prevPage*limite).isEmpty()) {
				request.setAttribute("prevPage", prevPage);
		}
		}
		if(!pd.doRetrieveByCategory(categoria,limite, (pagina+1)*limite).isEmpty()) {
			request.setAttribute("nextPage", (pagina+1));
		}
	
		
			request.setAttribute("categoria", categoria);
			request.setAttribute("prodotti", prodotti);
			
			request.getRequestDispatcher("/WEB-INF/View/categoryPage.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
