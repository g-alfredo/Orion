package Model.DAO;

import Model.Beans.CategoryAffiliationBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

public class CategoryAffiliationDAO implements Serializable {
	private static final long serialVersionUID = -3443857553663334698L;
	private Connection connection;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;

	
	public synchronized int doSave(CategoryAffiliationBean cb) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Appartenenza_Categoria VALUES (?, ?);";

				ps = connection.prepareStatement(query);
				ps.setLong(1, cb.getIdProdotto());
				ps.setString(2, cb.getNomeCategoria());

				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized int doDeleteAll(long IDProdotto)  {
		try{
			try {

				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Appartenenza_Categoria WHERE ID_Prodotto = ? ;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDProdotto);
				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized CategoryAffiliationBean doRetrieveByKey(long IDProdotto, String nomeCategoria) {
		CategoryAffiliationBean cb = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Appartenenza_Categoria WHERE ID_Prodotto = ? AND Nome_Categoria = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDProdotto);
				ps.setString(2, nomeCategoria);
				rs = ps.executeQuery();

				if(rs.next()) {
					cb = new CategoryAffiliationBean();
					cb.setIdProdotto(IDProdotto);
					cb.setNomeCategoria(nomeCategoria);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return cb;
	}


	public synchronized ArrayList<CategoryAffiliationBean> doRetrieveAll() {
		ArrayList<CategoryAffiliationBean> affiliations = new ArrayList<CategoryAffiliationBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Appartenenza_Categoria;";

				st = connection.createStatement();
				rs = st.executeQuery(query);

				CategoryAffiliationBean cb;
				while(rs.next()) {
					cb = new CategoryAffiliationBean();
					cb.setIdProdotto(rs.getLong("ID_Prodotto"));
					cb.setNomeCategoria(rs.getString("Nome_Categoria"));
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return affiliations;
	}
}