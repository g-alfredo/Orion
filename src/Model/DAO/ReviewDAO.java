package Model.DAO;

import Model.Beans.ReviewBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;;

public class ReviewDAO implements Serializable {
	private static final long serialVersionUID = -560809538771505590L;
	
	private Connection connection;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;


	public synchronized int doSave(ReviewBean rb) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Recensione VALUES (?, ?, ?, ?, ?, ?);";

				ps = connection.prepareStatement(query);
				ps.setLong(1, rb.getIdUser());
				ps.setLong(2, rb.getIdProdotto());
				ps.setString(3, rb.getTitolo());
				ps.setString(4, rb.getDescrizione());
				ps.setDate(5, rb.getDataPubblicazione());
				ps.setInt(6, rb.getPunteggio());

				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized int doSaveOrUpdate(ReviewBean rb) {
		ReviewBean res = doRetrieveByKey(rb.getIdUser(), rb.getIdProdotto());
		if(res == null) doSave(rb);
		else {
			try {
				try {
					connection = DBConnectionPool.getConnection();
					String query = "UPDATE Recensione"
							+ " SET Titolo = ?,"
							+ " Descrizione = ?,"
							+ " Data_Pubblicazione = ?,"
							+ " Punteggio = ?"
							+ " WHERE ID = ? AND ID_Prodotto = ?;";

					ps = connection.prepareStatement(query);
					ps.setString(1, rb.getTitolo());
					ps.setString(2, rb.getDescrizione());
					ps.setDate(3, rb.getDataPubblicazione());
					ps.setString(4, Integer.toString(rb.getPunteggio()));
					ps.setLong(5, rb.getIdUser());
					ps.setLong(6, rb.getIdProdotto());

					return ps.executeUpdate();
				} finally {
					DBConnectionPool.releaseConnection(connection);
				}
			} catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return -1;
	}


	public synchronized int doDelete(long IDUser, long IDProdotto) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Recensione WHERE ID = ? AND ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				ps.setLong(2, IDProdotto);

				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized ReviewBean doRetrieveByKey(long IDUser, long IDProdotto) {
		ReviewBean rb = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Recensione WHERE ID = ? AND ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				ps.setLong(2, IDProdotto);
				rs = ps.executeQuery();

				if(rs.next()) {
					rb = new ReviewBean();
					rb.setIdUser(IDUser);
					rb.setIdProdotto(IDProdotto);
					rb.setTitolo(rs.getString("Titolo"));
					rb.setDescrizione(rs.getString("Descrizione"));
					rb.setPunteggio(rs.getInt("Punteggio"));
					rb.setNome(getNome(connection,IDUser));
					rb.setCognome(getCognome(connection,IDUser));	
			        rb.setDataPubblicazione(rs.getDate("Data_Pubblicazione"));
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return rb;
	}


	public synchronized ArrayList<ReviewBean> doRetrieveAll() {
		ArrayList<ReviewBean> reviews = new ArrayList<ReviewBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Recensione;";

				st = connection.createStatement();
				rs = st.executeQuery(query);
				reviews = doRetrieveReviews(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return reviews;
	}


	public synchronized ArrayList<ReviewBean> doRetrieveByID(long IDProdotto) {
		ArrayList<ReviewBean> reviews = new ArrayList<ReviewBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Recensione WHERE ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDProdotto);
				rs = ps.executeQuery();
				reviews = doRetrieveReviews(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return reviews;
	}


	public synchronized ArrayList<ReviewBean> doRetrieveByIDAndPunteggio(long IDProdotto, int punteggio) {
		ArrayList<ReviewBean> reviews = new ArrayList<ReviewBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Recensione WHERE ID_Prodotto = ? AND Punteggio = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDProdotto);
				ps.setInt(2, punteggio);
				rs = ps.executeQuery();
				reviews = doRetrieveReviews(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return reviews;
	}


	public synchronized boolean reviewOk (long IDUser, long IDProdotto) {		
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Recensione WHERE ID = ? AND ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				ps.setLong(2, IDProdotto);
				rs = ps.executeQuery();
				if(!rs.next()) return true;
				else return false;

			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	private synchronized ArrayList<ReviewBean> doRetrieveReviews(ResultSet rs) {
		ArrayList<ReviewBean> reviews = new ArrayList<ReviewBean>();	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				ReviewBean rb;
				while(rs.next()) {
					rb = new ReviewBean();
					rb.setIdUser(rs.getLong("ID"));
					rb.setIdProdotto(rs.getLong("ID_Prodotto"));
					rb.setTitolo(rs.getString("Titolo"));
					rb.setDescrizione(rs.getString("Descrizione"));
					rb.setPunteggio(rs.getInt("Punteggio"));
					rb.setNome(getNome(connection,rb.getIdUser()));
					rb.setCognome(getCognome(connection,rb.getIdUser()));
					rb.setDataPubblicazione(rs.getDate("Data_Pubblicazione"));				
					reviews.add(rb);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}		
		return reviews;
	}


	private static String getNome(Connection connection,long idUser) {
		try {
			String query = "select Nome from Utente"
					+ " WHERE ID= ? ;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, idUser);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) 
				return rs.getString("Nome");
			else return "Nome non trovato";
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}	
	}


	private static String getCognome(Connection connection,long idUser) {
		try {
			String query = "select Cognome from Utente"
					+ " WHERE ID= ? ;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, idUser);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) 
				return rs.getString("Cognome");
			else return "Cognome non trovato";
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}