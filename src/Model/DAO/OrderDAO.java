package Model.DAO;

import Model.Beans.OrderBean;
import Model.Beans.OrderedProductBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

public class OrderDAO implements Serializable {
	private static final long serialVersionUID = -2751062752955339172L;
	
	private Connection connection;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;


	public synchronized int doSave(OrderBean ob) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Ordine(ID, Numero_Indirizzo, Numero_Carta, Data_Ordine, Costo_Spedizione) VALUES (?, ?, ?, ?, ?);";

				ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, ob.getIDUser());
				ps.setInt(2, ob.getNrIndirizzo());
				ps.setString(3, ob.getNrCarta());
				ps.setDate(4, ob.getDataOrdine());
				ps.setDouble(5, ob.getCostoSpedizione());

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if(rs.next()) return rs.getInt(1);
				else return -1;

			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized int doDelete(int IDOrdine) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Ordine WHERE ID_Ordine = ?;";

				ps = connection.prepareStatement(query);
				ps.setInt(1, IDOrdine);
				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized OrderBean doRetrieveByKey(int IDOrdine) {
		OrderBean ob = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Ordine WHERE ID_Ordine = ?;";

				ps = connection.prepareStatement(query);
				ps.setInt(1, IDOrdine);
				rs = ps.executeQuery();

				if(rs.next()) {
					ob = new OrderBean();
					ob.setIDOrdine(IDOrdine);
					ob.setIDUser(rs.getLong("ID"));
					ob.setNrIndirizzo(rs.getInt("Numero_Indirizzo"));
					ob.setNrCarta(rs.getString("Numero_Carta"));
					ob.setCostoSpedizione(rs.getDouble("Costo_Spedizione"));
					ob.setDataOrdine(rs.getDate("Data_Ordine"));
					ob.setProdottiOrdinati(getOrderedProduct(connection,IDOrdine));
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			} 
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return ob;
	}


	public synchronized ArrayList<OrderBean> doRetrieveAll() {
		ArrayList<OrderBean> orders = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Ordine;";

				st = connection.createStatement();
				rs = st.executeQuery(query);
				orders = doRetrieveOrders(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return orders;
	}

	public synchronized ArrayList<OrderBean> doRetriveByUserID(long IDUser) {
		ArrayList<OrderBean> orders = null;	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Ordine WHERE ID = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				rs = ps.executeQuery();		
				orders = doRetrieveOrders(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return orders;
	}



	public synchronized boolean orderOk (long IDUser, long IDProdotto) {	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Ordine JOIN Prodotti_Ordinati ON Ordine.ID_Ordine = Prodotti_Ordinati.ID_Ordine  WHERE ID = ? AND ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				ps.setLong(2, IDProdotto);
				rs = ps.executeQuery();
				if(rs.next()) return true;
				else return false;

			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	private synchronized ArrayList<OrderBean> doRetrieveOrders(ResultSet rs) {
		ArrayList<OrderBean> orders = new ArrayList<OrderBean>();	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				OrderBean ob;
				while(rs.next()) {
					ob = new OrderBean();
					ob.setIDOrdine(rs.getInt("ID_Ordine"));
					ob.setIDUser(rs.getLong("ID"));
					ob.setNrIndirizzo(rs.getInt("Numero_Indirizzo"));
					ob.setNrCarta(rs.getString("Numero_Carta"));
					ob.setCostoSpedizione(rs.getDouble("Costo_Spedizione"));
					ob.setDataOrdine(rs.getDate("Data_Ordine"));
					ob.setProdottiOrdinati(getOrderedProduct(connection,ob.getIDOrdine()));
					orders.add(ob);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}		
		return orders;
	}

	private static ArrayList<OrderedProductBean> getOrderedProduct(Connection connection,int idOrdine){
		ArrayList<OrderedProductBean> prodottiOrdinati = new ArrayList<OrderedProductBean>();
		try {
			String query = "select * from Prodotti_Ordinati"
					+ " WHERE ID_Ordine= ? ;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, idOrdine);
			ResultSet rs = ps.executeQuery();
			OrderedProductBean ob ;
			while(rs.next()) {
				ob=new OrderedProductBean();
				ob.setIDOrdine(rs.getInt("ID_Ordine"));
				ob.setIDProdotto(rs.getLong("ID_Prodotto"));
				ob.setPrezzo(rs.getDouble("Prezzo"));
				ob.setQuantità(rs.getInt("Quantità"));
				ob.setTitolo(rs.getString("Titolo"));
				prodottiOrdinati.add(ob);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}	
		return prodottiOrdinati;	
	}
}