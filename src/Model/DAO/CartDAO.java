package Model.DAO;

import Model.Beans.CartBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.Serializable;
import java.sql.*;

public class CartDAO implements Serializable {
	private static final long serialVersionUID = -7549830198704020565L;
	
	private Connection connection;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;

	
	public synchronized int doSave(CartBean cb) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Carrello VALUES (?, ?, ?);";

				ps = connection.prepareStatement(query);
				ps.setLong(1, cb.getIdUser());
				ps.setLong(2, cb.getProdotto().getIdProdotto());
				ps.setInt(3, cb.getQuantità());

				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized int doSaveOrUpdate(CartBean cb) {
		CartBean res = doRetrieveByKey(cb.getIdUser(), cb.getProdotto().getIdProdotto());
		if(res == null) doSave(cb);
		else {
			try {
				try {
					connection = DBConnectionPool.getConnection();
					String query = "UPDATE Carrello"
							+ " SET Quantità = ?"
							+ " WHERE ID = ? AND ID_Prodotto = ?;";

					ps = connection.prepareStatement(query);
					ps.setInt(1, cb.getQuantità());
					ps.setLong(2, cb.getIdUser());
					ps.setLong(3, cb.getProdotto().getIdProdotto());

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


	public synchronized int doDelete(long IDUser, long idProdotto) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Carrello WHERE ID = ? AND ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				ps.setLong(2, idProdotto);
				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized CartBean doRetrieveByKey(long IDUser, long idProdotto) {
		CartBean cb = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Carrello WHERE ID = ? AND ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				ps.setLong(2, idProdotto);
				rs = ps.executeQuery();

				if(rs.next()) {
					ProductDAO pd = new ProductDAO();
					cb = new CartBean();
					cb.setIdUser(IDUser);;
					cb.setProdotto(pd.doRetrieveByKey(idProdotto));
					cb.setQuantità(rs.getInt("Quantità"));
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return cb;
	}


	public synchronized ArrayList<CartBean> doRetrieveAll() {
		ArrayList<CartBean> carts = new ArrayList<CartBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Carrello;";

				st = connection.createStatement();
				rs = st.executeQuery(query);

				CartBean cb;
				ProductDAO pd = new ProductDAO();
				while(rs.next()) {
					cb = new CartBean();
					cb.setIdUser(rs.getLong("ID"));
					cb.setProdotto(pd.doRetrieveByKey(rs.getLong("ID_Prodotto")));
					cb.setQuantità(rs.getInt("Quantità"));

					carts.add(cb);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return carts;
	}


	public synchronized LinkedHashMap<Long, CartBean> userCart(long IDUser){
		LinkedHashMap<Long, CartBean> cart = new LinkedHashMap<Long, CartBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Carrello WHERE ID = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				rs = ps.executeQuery();

				CartBean cb;
				ProductDAO pd = new ProductDAO();
				while(rs.next()) {
					cb = new CartBean();
					cb.setIdUser(rs.getLong("ID"));
					Long IDProdotto = rs.getLong("ID_Prodotto");
					cb.setProdotto(pd.doRetrieveByKey(IDProdotto));
					cb.setQuantità(rs.getInt("Quantità"));					
					cart.put(IDProdotto, cb);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return cart;
	}
}