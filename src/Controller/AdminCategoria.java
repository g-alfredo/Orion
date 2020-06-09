package Controller;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Beans.CategoryBean;
import Model.DAO.CategoryDAO;


@WebServlet("/AdminCategoria")
public class AdminCategoria extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private final CategoryDAO categoriaDAO = new CategoryDAO();


	public AdminCategoria() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isAdmin(request);
		String strHiddenCategoria = request.getParameter("hiddenCategoria");
		String strCategoria = request.getParameter("categoria");
		CategoryBean categoria=new CategoryBean();
		categoria.setNome(strCategoria);
		CategoryBean hiddenCategoria=new CategoryBean();
		hiddenCategoria.setNome(strHiddenCategoria);
		
		
		if (request.getParameter("Rimuovi")!=null) { // voglio rimuovere 
			categoriaDAO.doDelete(hiddenCategoria);
			request.setAttribute("notifica", "Categoria rimossa con successo.");
			request.removeAttribute("categorie");
			request.setAttribute("categorie", categoriaDAO.doRetrieveAll());
			
			

		}

		else if(request.getParameter("Modifica")!=null) {  //voglio modificare
			if(categoria.getNome().equals(hiddenCategoria.getNome())) {
				request.setAttribute("categoria", categoria);

			}
			else if(categoriaDAO.doRetrieveByKey(categoria)!=null) {
				request.setAttribute("notifica", "Errore: categoria già presente nel database.");
			}
			else {
				categoriaDAO.doUpdate(categoria, hiddenCategoria);
				request.setAttribute("notifica", "Categoria modificata con successo.");
				request.setAttribute("operazione","Modifica");
				request.removeAttribute("categorie");
				request.setAttribute("categorie", categoriaDAO.doRetrieveAll());
				request.setAttribute("categoria", categoria);
				
			}
		}

		else if(request.getParameter("Aggiungi")!=null) {  //voglio aggiungere
			if(categoriaDAO.doRetrieveByKey(categoria)!=null) {
				request.setAttribute("notifica", "Errore: categoria già presente nel database.");
				request.setAttribute("operazione","Aggiungi");

			}
			else {
				categoriaDAO.doSave(categoria);
				request.setAttribute("notifica", "Categoria aggiunta con successo.");
				request.setAttribute("operazione","Aggiungi");
				request.removeAttribute("categorie");
				request.setAttribute("categorie", categoriaDAO.doRetrieveAll());
				request.setAttribute("categoria", categoria);
			}
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/View/admincategoria.jsp");
		requestDispatcher.forward(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
