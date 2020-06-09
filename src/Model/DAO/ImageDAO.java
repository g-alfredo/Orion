package Model.DAO;

import Model.Beans.ImageBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

public class ImageDAO implements Serializable {
	private static final long serialVersionUID = -8145492187468240024L;
	
	private Connection connection;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;


	public synchronized int doSave(ImageBean ib) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Immagine VALUES (?, ?);";

				ps = connection.prepareStatement(query);
				ps.setString(1, ib.getPathname());
				ps.setLong(2, ib.getIdProdotto());

				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized int doSaveOrUpdate(ImageBean ib) {
		ImageBean res = doRetrieveByKey(ib.getPathname());
		if(res == null) doSave(ib);
		else {
			try {
				try {
					connection = DBConnectionPool.getConnection();
					String query = "UPDATE Immagine"
							+ " SET ID_Prodotto = ?"
							+ " WHERE Pathname = ?;";

					ps = connection.prepareStatement(query);
					ps.setLong(1, ib.getIdProdotto());
					ps.setString(2, ib.getPathname());

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


	public synchronized int doDelete(String pathname) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Immagine WHERE Pathname = ?;";

				ps = connection.prepareStatement(query);
				ps.setString(1, pathname);
				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized ImageBean doRetrieveByKey(String pathname) {
		ImageBean ib = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Immagine WHERE Pathname = ?;";

				ps = connection.prepareStatement(query);
				ps.setString(1, pathname);
				rs = ps.executeQuery();

				if(rs.next()) {
					ib = new ImageBean();
					ib.setPathname(rs.getString("Nome"));
				}			
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return ib;
	}


	public ArrayList<ImageBean> doRetrieveAll() {
		ArrayList<ImageBean> images = new ArrayList<ImageBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Immagine;";

				st = connection.createStatement();
				rs = st.executeQuery(query);

				ImageBean ib = null;
				while(rs.next()) {
					ib = new ImageBean();
					ib.setPathname(rs.getString("Pathname"));
					ib.setIdProdotto(rs.getLong("ID_Prodotto"));

					images.add(ib);
				}

			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return images;
	}


	public ArrayList<ImageBean> doRetrieveByProductID(long IDProdotto) {
		ArrayList<ImageBean> images = new ArrayList<ImageBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Immagine WHERE ID_Prodotto = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDProdotto);
				rs = ps.executeQuery();

				ImageBean ib = null;
				while(rs.next()) {
					ib = new ImageBean();
					ib.setPathname(rs.getString("Pathname"));
					ib.setIdProdotto(IDProdotto);

					images.add(ib);
				}

			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return images;
	}
}