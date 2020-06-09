package Model.DAO;

import Model.Beans.WishListBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.Serializable;
import java.sql.*;

public class WishListDAO implements Serializable {
	private static final long serialVersionUID = -4609119285328248778L;
	
	private Connection connection;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;


	public synchronized int doSave(WishListBean wb) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Wishlist VALUES (?, ?);";

				ps = connection.prepareStatement(query);
				ps.setLong(1, wb.getIdUser());
				ps.setLong(2, wb.getProdotto().getIdProdotto());

				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized int doDelete(long IDUser, long IDProdotto) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Wishlist WHERE ID = ? AND ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				ps.setLong(2, IDProdotto);
				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized WishListBean doRetrieveByKey(long IDUser, long IDProdotto) {
		WishListBean wb = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM WishList WHERE ID = ? AND ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				ps.setLong(2, IDProdotto);
				rs = ps.executeQuery();

				if(rs.next()) {
					ProductDAO pd = new ProductDAO();
					wb = new WishListBean();
					wb.setIdUser(IDUser);
					wb.setProdotto(pd.doRetrieveByKey(IDProdotto));
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			} 
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return wb;
	}


	public synchronized ArrayList<WishListBean> doRetrieveAll() {
		ArrayList<WishListBean> carts = new ArrayList<WishListBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Wishlist;";

				st = connection.createStatement();
				rs = st.executeQuery(query);

				WishListBean wb;
				ProductDAO pd = new ProductDAO();
				while(rs.next()) {
					wb = new WishListBean();
					wb.setIdUser(rs.getLong("ID"));
					wb.setProdotto(pd.doRetrieveByKey(rs.getLong("ID_Prodotto")));		
					carts.add(wb);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return carts;
	}


	public synchronized LinkedHashMap<Long, WishListBean> userWishlist(long IDUser) {
		LinkedHashMap<Long, WishListBean> wishlist = new LinkedHashMap<Long, WishListBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Carrello WHERE ID = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				rs = ps.executeQuery();

				WishListBean wb;
				ProductDAO pd = new ProductDAO();
				while(rs.next()) {
					wb = new WishListBean();
					wb.setIdUser(rs.getLong("ID"));
					Long IDProdotto = rs.getLong("ID_Prodotto");
					wb.setProdotto(pd.doRetrieveByKey(IDProdotto));

					wishlist.put(IDProdotto, wb);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return wishlist;
	}
}