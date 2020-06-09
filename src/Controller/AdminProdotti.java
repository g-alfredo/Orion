package Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.Beans.ProductBean;
import Model.DAO.ProductDAO;

@WebServlet("/AdminProdotti")
public class AdminProdotti extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private ProductDAO prodottoDAO=new ProductDAO();

    public AdminProdotti() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isAdmin(request);
		int limite=10;
		int pagina=0;
		try{
			pagina=Integer.parseInt(request.getParameter("pagina"));
		}
		// Catturo l eccezione se non è presente l attributo nella richiesta
		catch (RuntimeException e) {
			pagina=0;
		}
		ArrayList<ProductBean>prodotti;
		if(request.getParameter("Esauriti")!=null) {
			prodotti=prodottoDAO.doRetrieveSoldOut(limite, pagina);
			int prevPage=pagina-1;
			if(prevPage>=0){
				if(!prodottoDAO.doRetrieveSoldOut(limite, prevPage*limite).isEmpty()) {
					request.setAttribute("prevPage", prevPage);
			}
			}
			if(!prodottoDAO.doRetrieveSoldOut(limite, (pagina+1)*limite).isEmpty()) {
				request.setAttribute("nextPage", (pagina+1));
			}
		}
		else {
			prodotti=prodottoDAO.doRetrieveAll(limite, pagina*limite);
			int prevPage=pagina-1;
			if(prevPage>=0){
				if(!prodottoDAO.doRetrieveAll(limite, prevPage*limite).isEmpty()) {
					request.setAttribute("prevPage", prevPage);
			}
			}
			if(!prodottoDAO.doRetrieveAll(limite, (pagina+1)*limite).isEmpty()) {
				request.setAttribute("nextPage", (pagina+1));
			}
		}

		request.setAttribute("prodotti", prodotti);
		request.setAttribute("pagina",pagina);
		request.setAttribute("limite", limite);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/View/adminprodotti.jsp");
		requestDispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
