package Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

import Model.Beans.CategoryBean;
import Model.Beans.ImageBean;
import Model.Beans.ProductBean;
import Model.DAO.ImageDAO;
import Model.DAO.ProductDAO;


@MultipartConfig
@WebServlet("/AdminProdotto")
public class AdminProdotto extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private final ProductDAO prodottoDAO = new ProductDAO();
	private final ImageDAO immagineDAO = new ImageDAO();



	public AdminProdotto() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isAdmin(request);
		String idstr = request.getParameter("id");
		ProductBean prodottoDB=null;
		if (idstr!=null && !idstr.equals("")) {
			prodottoDB=prodottoDAO.doRetrieveByKey(Long.parseLong(idstr));
			prodottoDB.setImmagini(immagineDAO.doRetrieveByProductID(prodottoDB.getIdProdotto()));
		}
		String titolo = request.getParameter("titolo");
		String descrizione = request.getParameter("descrizione");
		Date dataPubbl=null;
		if (request.getParameter("dataPubbl")!=null)
			dataPubbl=Date.valueOf(request.getParameter("dataPubbl"));
		double prezzo=0;
		if(request.getParameter("prezzo")!=null)
			prezzo = Double.parseDouble(request.getParameter("prezzo"));

		int percSconto=0;
		if(request.getParameter("percSconto")!=null)
			percSconto=Integer.parseInt(request.getParameter("percSconto"));
		int quantità=0;
		if(request.getParameter("qnt")!=null)
			quantità=Integer.parseInt(request.getParameter("qnt"));
		String[] categorie = request.getParameterValues("categorie");

		ProductBean prodotto=new ProductBean();
		prodotto.setTitolo(titolo);
		prodotto.setDescrizione(descrizione);
		prodotto.setDataPubblicazione(dataPubbl);
		prodotto.setPrezzo(prezzo);
		prodotto.setPercSconto(percSconto);
		prodotto.setQuantità(quantità);
		if (categorie!=null) {
			ArrayList<CategoryBean> listCategorie=new ArrayList<CategoryBean>();
			for(String strcategoria :categorie) {
				CategoryBean categoria=new CategoryBean();
				categoria.setNome(strcategoria);
				listCategorie.add(categoria);
			}
			prodotto.setCategorie(listCategorie);
		}

		if (request.getParameter("Rimuovi") != null) { //sto rimuovendo il prodotto
			prodottoDAO.doDelete(prodottoDB.getIdProdotto());
			ArrayList<ImageBean> immagini=prodottoDB.getImmagini();
			if(!immagini.isEmpty()) {
				for(ImageBean immagine:immagini) {
					immagineDAO.doDelete(immagine.getPathname());
					FileUtils.deleteQuietly(new File(immagine.getPathname().substring(1)));

				}
			}
			request.setAttribute("notifica", "Prodotto rimosso con successo.");
		} 
		else if(request.getParameter("Modifica")!=null) { // sto modificando il prodotto

			if(prodotto.getTitolo()!=null) {
				prodotto.setIdProdotto(Long.parseLong(idstr));
				prodottoDAO.doSaveOrUpdate(prodotto);


				//Prendo eventuali immagini caricate dall utente in fase di modifica
				List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">
				for (Part filePart : fileParts) {
					String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
					if (!fileName.equals("")) {
						Random r=new Random();
						int randomvalue=r.nextInt(2000000);
						File uploads = new File(getServletContext().getInitParameter("imagePath"));
						File file = new File(uploads, randomvalue+fileName);
						try (InputStream input = filePart.getInputStream()) {
							Files.copy(input, file.toPath());
						}
						ImageBean immagine=new ImageBean();
						immagine.setIdProdotto(prodotto.getIdProdotto());
						immagine.setPathname("."+file.toPath().toString());
						immagineDAO.doSave(immagine);
					}
				}
				
				//cancello le immagini che l admin ha deciso di cancellare
				String[] immaginiDelete =request.getParameterValues("deleteImages");
				if(immaginiDelete!=null) {
					for(int i=0;i<immaginiDelete.length;i++) {
						if(!immaginiDelete[i].equals("")) {
							FileUtils.deleteQuietly(new File(immaginiDelete[i].substring(1)));
							immagineDAO.doDelete(immaginiDelete[i]);
						}
					}
				}

				prodottoDB.setImmagini(immagineDAO.doRetrieveByProductID(prodottoDB.getIdProdotto()));
				request.setAttribute("notifica", "Prodotto modificato con successo.");
				request.setAttribute("operazione","Modifica");
				prodotto=prodottoDAO.doRetrieveByKey(prodotto.getIdProdotto());
				request.setAttribute("prodotto",prodotto);
			}

			else {
				prodotto=prodottoDAO.doRetrieveByKey(Long.parseLong(idstr));
				request.setAttribute("prodotto",prodottoDB);

			}

		}
		else if(request.getParameter("Aggiungi")!=null) {  //voglio aggiungere
			prodottoDAO.doSave(prodotto);

			
			//prendo le immagini che l utente ha deciso di caricare e le salvo nella cartella e nel database
			List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">
			ArrayList<ImageBean> immagini=new ArrayList<ImageBean>();
			for (Part filePart : fileParts) {
				String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

				if (!fileName.equals("")) {
					Random r=new Random();
					int randomvalue=r.nextInt(2000000);
					File uploads = new File(getServletContext().getInitParameter("imagePath"));
					File file = new File(uploads, randomvalue+fileName);


					try (InputStream input = filePart.getInputStream()) {
						Files.copy(input, file.toPath());


					}
					ImageBean immagine=new ImageBean();
					immagine.setIdProdotto(prodotto.getIdProdotto());
					immagine.setPathname("."+file.toPath().toString());
					immagineDAO.doSave(immagine);
					immagini.add(immagine);
				}
			}
			if (!immagini.isEmpty())
				prodotto.setImmagini(immagini);
			request.setAttribute("notifica", "Prodotto aggiunto con successo.");
			request.setAttribute("prodotto",prodotto);

		}


		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/View/adminprodotto.jsp");
		requestDispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
