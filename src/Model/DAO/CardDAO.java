package Model.DAO;

import Model.Beans.CardBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

public class CardDAO implements Serializable {
	private static final long serialVersionUID = -4307947034099221193L;
	private Connection connection;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;
	
	
	public synchronized int doSave(CardBean cb) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Carta_Di_Credito VALUES (?, ?, ?, ?, ?);";

				ps = connection.prepareStatement(query);
				ps.setString(1, cb.getNrCarta());
				ps.setLong(2, cb.getIdUser());
				ps.setBoolean(3, cb.isPreferred());
				ps.setString(4, cb.getNome());
				ps.setString(5, cb.getCognome());				
				return ps.executeUpdate();				
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized int doSaveOrUpdate(CardBean cb) {
		CardBean res = doRetrieveByKey(cb.getNrCarta(), cb.getIdUser());
		if(res == null) doSave(cb);
		else {
			try {
				try {
					connection = DBConnectionPool.getConnection();
					String query = "UPDATE Carta_Di_Credito"
							+ " SET Nome_Titolare = ?,"
							+ " Cognome_Titolare = ?, Preferito=? "
							+ " WHERE Numero_Carta = ? AND ID = ?;";

					ps = connection.prepareStatement(query);
					ps.setString(1, cb.getNome());
					ps.setString(2, cb.getCognome());
					ps.setBoolean(3, cb.isPreferred());
					ps.setString(4, cb.getNrCarta());
					ps.setLong(5, cb.getIdUser());				
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


	public synchronized int doUpdateFavourite(long IDUser) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "UPDATE Carta_Di_Credito "
						+ "SET Preferito = false "
						+ "WHERE ID = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				return ps.executeUpdate();				
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}	
	}


	public synchronized int doDelete(String numeroCarta, long IDUser) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Carta_Di_Credito "
						+ "WHERE Numero_Carta = ? AND ID = ?;";

				ps = connection.prepareStatement(query);
				ps.setString(1, numeroCarta);
				ps.setLong(2, IDUser);			
				return ps.executeUpdate();			
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized CardBean doRetrieveByKey(String numeroCarta, long IDUser) {
		CardBean cb = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Carta_Di_Credito WHERE Numero_Carta = ? AND ID = ?;";

				ps = connection.prepareStatement(query);
				ps.setString(1, numeroCarta);
				ps.setLong(2, IDUser);				
				rs = ps.executeQuery();

				if(rs.next()) {
					cb = new CardBean();
					cb.setNrCarta(numeroCarta);
					cb.setIdUser(IDUser);
					cb.setNome(rs.getString("Nome_Titolare"));
					cb.setCognome(rs.getString("Cognome_Titolare"));
					cb.setPreferred(rs.getBoolean("Preferito"));
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return cb;
	}


	public ArrayList<CardBean> doRetrieveAll() {
		ArrayList<CardBean> cards = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Carta_Di_Credito;";		
				st = connection.createStatement();
				rs = st.executeQuery(query);
				cards = doRetrieveCards(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return cards;
	}


	public synchronized ArrayList<CardBean> doRetrieveByIDUser(long IDUser){
		ArrayList<CardBean> cards = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Carta_Di_Credito WHERE ID = ?;";			
				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);		
				rs = ps.executeQuery();
				cards = doRetrieveCards(rs);			
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}	
		return cards;
	}


	private synchronized ArrayList<CardBean> doRetrieveCards(ResultSet rs) {
		ArrayList<CardBean> cards = new ArrayList<CardBean>();	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				CardBean cb;
				while(rs.next()) {
					cb = new CardBean();
					cb.setNrCarta(rs.getString("Numero_Carta"));
					cb.setIdUser(rs.getLong("ID"));
					cb.setNome(rs.getString("Nome_Titolare"));
					cb.setCognome(rs.getString("Cognome_Titolare"));
					cb.setPreferred(rs.getBoolean("Preferito"));
					cards.add(cb);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}		
		return cards;
	}
}