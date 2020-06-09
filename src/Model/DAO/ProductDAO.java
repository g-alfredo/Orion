package Model.DAO;

import Model.Beans.CategoryAffiliationBean;
import Model.Beans.CategoryBean;
import Model.Beans.ImageBean;
import Model.Beans.ProductBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;
import java.text.ParseException;

public class ProductDAO implements Serializable {
	private static final long serialVersionUID = 4127296057294079574L;
	
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement ps;
	private CategoryAffiliationDAO cad=new CategoryAffiliationDAO();
	private CategoryAffiliationBean cab;


	public synchronized long doSave(ProductBean pb)  {
		try{
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Prodotto (Titolo,Data_pubblicazione,Prezzo,Percentuale_Sconto,Descrizione,Quantità) "
						+ "VALUES ( ?, ?, ?, ?, ?, ?);";

				ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, pb.getTitolo());
				ps.setDate(2, pb.getDataPubblicazione());
				ps.setDouble(3, pb.getPrezzo());
				ps.setInt(4, pb.getPercSconto());
				ps.setString(5, pb.getDescrizione());
				ps.setInt(6, pb.getQuantità());

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if(rs.next()) {
					pb.setIdProdotto(rs.getLong(1));
					if(pb.getCategorie()!=null) {
						ArrayList<CategoryBean> categorie=pb.getCategorie();
						cab=new CategoryAffiliationBean();
						for(CategoryBean categoria:categorie) {
							cab.setIdProdotto(pb.getIdProdotto());
							cab.setNomeCategoria(categoria.getNome());
							cad.doSave(cab);
						}
					}
					return pb.getIdProdotto();
				}
				else return -1;

			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized int doSaveOrUpdate(ProductBean pb)  {
		ProductBean res = doRetrieveByKey(pb.getIdProdotto()); 
		if(res == null) doSave(pb);
		else {
			try{
				try {
					connection = DBConnectionPool.getConnection();
					String query = "UPDATE Prodotto"
							+ " SET Titolo = ?,"
							+ " Data_Pubblicazione = ?,"
							+ " Prezzo = ?,"
							+ " Percentuale_Sconto = ?,"
							+ " Descrizione = ?,"
							+ " Quantità = ?"
							+ " WHERE ID_Prodotto = ?;";

					ps = connection.prepareStatement(query);
					ps.setString(1, pb.getTitolo());
					ps.setString(2, pb.getDataPubblicazione().toString());
					ps.setDouble(3, pb.getPrezzo());
					ps.setInt(4, pb.getPercSconto());
					ps.setString(5, pb.getDescrizione());
					ps.setInt(6, pb.getQuantità());
					ps.setLong(7, pb.getIdProdotto());
					cad.doDeleteAll(pb.getIdProdotto());
					if(pb.getCategorie()!=null) {
						ArrayList<CategoryBean> categorie=pb.getCategorie();
						cab=new CategoryAffiliationBean();
						for(CategoryBean categoria:categorie) {
							cab.setIdProdotto(pb.getIdProdotto());
							cab.setNomeCategoria(categoria.getNome());

							cad.doSave(cab);
						}
					}
					return ps.executeUpdate();
				} finally {
					DBConnectionPool.releaseConnection(connection);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return -1;
	}

	public synchronized boolean doDelete(long IDProdotto)  {
		try{
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Prodotto WHERE ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDProdotto);
				if( ps.executeUpdate()>0)return true;
				else return false;
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized ProductBean doRetrieveByKey(long IDProdotto)  {
		ProductBean pb = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Prodotto WHERE ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDProdotto);
				rs = ps.executeQuery();

				if(rs.next()) {
					pb = new ProductBean();
					pb.setIdProdotto(rs.getLong("ID_Prodotto"));
					pb.setTitolo(rs.getString("Titolo"));
					pb.setPrezzo(rs.getDouble("Prezzo"));
					pb.setPercSconto(rs.getInt("Percentuale_Sconto"));
					pb.setDescrizione(rs.getString("Descrizione"));
					pb.setQuantità(rs.getInt("Quantità"));
					pb.setDataPubblicazione(rs.getDate("Data_Pubblicazione"));
					pb.setCategorie(getCategorie(connection,pb.getIdProdotto()));
					pb.setImmagini(getImmagini(connection,pb.getIdProdotto()));
					
				}	
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return pb;
	}

	public synchronized ArrayList<ProductBean> doRetrieveAll( int limit,int offset)  {
		ArrayList<ProductBean> products = null;	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Prodotto LIMIT ? OFFSET  ? ;";

				ps = connection.prepareStatement(query);
				ps.setInt(1, limit);
				ps.setInt(2, offset);
				rs = ps.executeQuery();			
				products = doRetrieveProducts(rs);
			}

			finally {			
				DBConnectionPool.releaseConnection(connection);
			} 
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return products;
	}
	
	public synchronized int countProduct()  {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT COUNT(*) FROM Prodotto ;";

				ps = connection.prepareStatement(query);
				rs = ps.executeQuery();	
				if (rs.next()) {
					return rs.getInt(1);
				}
				else return 0;
			}

			finally {			
				DBConnectionPool.releaseConnection(connection);
			} 
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	

	public synchronized ArrayList<ProductBean> doRetrieveByCategory(String nomeCategoria,int limite, int pagina)  {
		ArrayList<ProductBean> products = null;	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Prodotto Join Appartenenza_Categoria ON Prodotto.ID_Prodotto = Appartenenza_Categoria.ID_Prodotto "
						+ "WHERE Nome_Categoria= ? LIMIT ? OFFSET  ? ";		

				ps = connection.prepareStatement(query);
				ps.setString(1, nomeCategoria);
				ps.setInt(2, limite);
				ps.setInt(3,pagina);
				rs = ps.executeQuery();			
				products = doRetrieveProducts(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}


	public synchronized ArrayList<ProductBean> productBetweenPrices(int prezzomin, int prezzomax) throws SQLException, ParseException {
		ArrayList<ProductBean> products = null;

		try {
			connection = DBConnectionPool.getConnection();
			String query = "SELECT * FROM Prodotti_Completi WHERE Prezzo >= ? AND Prezzo <= ? ;";

			ps = connection.prepareStatement(query);
			ps.setInt(1, prezzomin);
			ps.setInt(2, prezzomax);
			rs = ps.executeQuery();

			products = doRetrieveProducts(rs);

		} finally {
			DBConnectionPool.releaseConnection(connection);
		}
		return products;
	}	


	public synchronized ArrayList<ProductBean> doRetrieveOnSale(int offset) {
		ArrayList<ProductBean> products = new ArrayList<ProductBean>();	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Prodotto WHERE Percentuale_Sconto > 0 LIMIT 10 OFFSET ?";	

				ps = connection.prepareStatement(query);
				ps.setInt(1, offset);
				rs = ps.executeQuery();			
				products = doRetrieveProducts(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return products;
	}


	public ArrayList<ProductBean> search(String input, String categoria, int offset) {
		ArrayList<ProductBean> products = new ArrayList<ProductBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();

				//Seleziona tutti gli ID dei prodotti che hanno titolo simile a quello inserito ed eventuale categoria inserita
				String query;       
				if(categoria == null || categoria.equals("null") || categoria.equals("%") || categoria.equals("")) {
					query = "SELECT DISTINCT ID_Prodotto FROM Prodotto WHERE Titolo LIKE ? LIMIT 10 OFFSET ?;";
						ps = connection.prepareStatement(query);
						ps.setString(1, input + "%"); 
						ps.setInt(2, offset);
				}
				else {
					query = "SELECT DISTINCT P.ID_Prodotto FROM Prodotto AS P JOIN Appartenenza_Categoria AS AC ON P.ID_Prodotto = AC.ID_Prodotto "
							+ "WHERE P.Titolo LIKE ? AND AC.Nome_Categoria LIKE ? LIMIT 10 OFFSET ?";	      
					ps = connection.prepareStatement(query);
					ps.setString(1, input + "%"); 
					ps.setString(2, categoria);
					ps.setInt(3, offset);
				}
				rs = ps.executeQuery();

				while(rs.next()) {
					String query2 = "SELECT * FROM Prodotto WHERE ID_Prodotto = ?;";

					ps = connection.prepareStatement(query2);
					ps.setLong(1, rs.getLong("ID_prodotto"));
					ResultSet rs2 = ps.executeQuery();

					ProductBean pb;
					if(rs2.next()) {
						pb = new ProductBean();
						pb.setIdProdotto(rs2.getLong("ID_Prodotto"));
						pb.setTitolo(rs2.getString("Titolo"));
						pb.setPrezzo(rs2.getDouble("Prezzo"));
						pb.setPercSconto(rs2.getInt("Percentuale_Sconto"));
						pb.setDescrizione(rs2.getString("Descrizione"));
						pb.setQuantità(rs2.getInt("Quantità"));
						pb.setDataPubblicazione(rs2.getDate("Data_Pubblicazione"));
						pb.setCategorie(getCategorie(connection,pb.getIdProdotto()));
						pb.setImmagini(getImmagini(connection,pb.getIdProdotto()));				

						products.add(pb);
					}

				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return products;
	}
	
	
	public synchronized ArrayList<ProductBean> doRetrieveSoldOut(int limit, int offset) {
		
		ArrayList<ProductBean> products = new ArrayList<ProductBean>();	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Prodotto WHERE Quantità=0 LIMIT ? OFFSET  ? ;";
				ps = connection.prepareStatement(query);
				ps.setInt(1, limit);
				ps.setInt(2, offset);
				rs = ps.executeQuery();	
				products = doRetrieveProducts(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return products;
	}


	private synchronized ArrayList<ProductBean> doRetrieveProducts(ResultSet rs) {
		ArrayList<ProductBean> products = new ArrayList<ProductBean>();	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				ProductBean pb;
				while(rs.next()) {
					pb = new ProductBean();
					pb.setIdProdotto(rs.getLong("ID_Prodotto"));
					pb.setTitolo(rs.getString("Titolo"));
					pb.setPrezzo(rs.getDouble("Prezzo"));
					pb.setPercSconto(rs.getInt("Percentuale_Sconto"));
					pb.setDescrizione(rs.getString("Descrizione"));
					pb.setQuantità(rs.getInt("Quantità"));
					pb.setDataPubblicazione(rs.getDate("Data_Pubblicazione"));
					pb.setCategorie(getCategorie(connection,pb.getIdProdotto()));
					pb.setImmagini(getImmagini(connection,pb.getIdProdotto()));
					products.add(pb);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}		
		return products;
	}	


	private synchronized static ArrayList<CategoryBean> getCategorie(Connection connection,long idProdotto) {
		ArrayList<CategoryBean> categorie = new ArrayList<CategoryBean>();
		try {

			String query = "select distinct Nome_categoria from Appartenenza_Categoria"
					+ " WHERE ID_Prodotto= ? ;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, idProdotto);
			ResultSet rs = ps.executeQuery();
			CategoryBean cb;
			while(rs.next()) {
				cb = new CategoryBean();
				cb.setNome(rs.getString("Nome_Categoria"));
				categorie.add(cb);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return categorie;
	}


	private synchronized static ArrayList<ImageBean> getImmagini(Connection connection,long idProdotto) {
		ArrayList<ImageBean> immagini = new ArrayList<ImageBean>();
		try {
			String query = "select Pathname from Immagine"
					+ " WHERE ID_Prodotto= ? ;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, idProdotto);
			ResultSet rs = ps.executeQuery();
			ImageBean ib;
			while(rs.next()) {
				ib = new ImageBean();
				ib.setPathname(rs.getString("Pathname"));
				ib.setIdProdotto(idProdotto);
				immagini.add(ib);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return immagini;	
	}
}