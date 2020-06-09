package Model.DAO;

import Model.Beans.OrderedProductBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

public class OrderedProductDAO implements Serializable {
	private static final long serialVersionUID = -5427383033756102441L;
	
	private Connection connection;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;


	public synchronized int doSave(OrderedProductBean ob) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Prodotti_Ordinati VALUES (?, ?, ?, ?, ?);";

				ps = connection.prepareStatement(query);
				ps.setString(1, ob.getTitolo());
				ps.setInt(2, ob.getIDOrdine());
				ps.setLong(3, ob.getIDProdotto());
				ps.setInt(4, ob.getQuantità());
				ps.setDouble(5, ob.getPrezzo());

				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized int doDelete(int IDOrdine, String IDProdotto) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Prodotti_Ordinati WHERE ID_Ordine = ? AND ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setInt(1, IDOrdine);
				ps.setString(2, IDProdotto);
				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized OrderedProductBean doRetrieveByKey(int IDOrdine, long IDProdotto) {
		OrderedProductBean ob = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Prodotti_Ordinati WHERE ID_Ordine = ? AND ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setInt(1, IDOrdine);
				ps.setLong(2, IDProdotto);
				rs = ps.executeQuery();

				if(rs.next()) {
					ob = new OrderedProductBean();
					ob.setIDOrdine(IDOrdine);
					ob.setIDProdotto(IDProdotto);
					ob.setQuantità(rs.getInt("Quantità"));
					ob.setPrezzo(rs.getDouble("Prezzo"));
					ob.setTitolo(rs.getString("Titolo"));
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return ob;
	}


	public synchronized ArrayList<OrderedProductBean> doRetrieveAll() {
		ArrayList<OrderedProductBean> orderedProducts = new ArrayList<OrderedProductBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Prodotti_Ordinati;";

				st = connection.createStatement();
				rs = st.executeQuery(query);
				orderedProducts = doRetrieveOrderedProducts(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return orderedProducts;
	}


	public synchronized ArrayList<OrderedProductBean> userOrderedProduct(String Email) {
		ArrayList<OrderedProductBean> orderedProducts = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Prodotti_Ordinati WHERE Email= ? ;";

				ps = connection.prepareStatement(query);
				ps.setString(1, Email);
				rs = ps.executeQuery();
				orderedProducts = doRetrieveOrderedProducts(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return orderedProducts;
	}


	public synchronized ArrayList<OrderedProductBean> doRetrieveByOrderID(int IDOrdine) {
		ArrayList<OrderedProductBean> orderedProducts = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Prodotti_Ordinati WHERE ID_Ordine = ? ;";

				ps = connection.prepareStatement(query);
				ps.setInt(1, IDOrdine);
				rs = ps.executeQuery();
				orderedProducts = doRetrieveOrderedProducts(rs);
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return orderedProducts;
	}


	private synchronized ArrayList<OrderedProductBean> doRetrieveOrderedProducts(ResultSet rs) {
		ArrayList<OrderedProductBean> orderedProducts = new ArrayList<OrderedProductBean>();	
		try {
			try {
				connection = DBConnectionPool.getConnection();
				OrderedProductBean ob;
				while(rs.next()) {
					ob = new OrderedProductBean();
					ob.setIDOrdine(rs.getInt("ID_Ordine"));
					ob.setIDProdotto(rs.getLong("ID_Prodotto"));
					ob.setQuantità(rs.getInt("Quantità"));
					ob.setPrezzo(rs.getDouble("Prezzo"));
					ob.setTitolo(rs.getString("Titolo"));

					ProductDAO pd = new ProductDAO();
					ob.setProdotto(pd.doRetrieveByKey(ob.getIDProdotto()));
					orderedProducts.add(ob);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}		
		return orderedProducts;
	}
}