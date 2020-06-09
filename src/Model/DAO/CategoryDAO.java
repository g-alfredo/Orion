package Model.DAO;

import Model.Beans.CategoryBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

public class CategoryDAO implements Serializable {
	private static final long serialVersionUID = 7317940987140259208L;
	
	private Connection connection;
	private ResultSet rs;
	private Statement st;
	private PreparedStatement ps;

	public synchronized int doSave(CategoryBean cb){
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Categoria VALUES (?);";

				ps = connection.prepareStatement(query);
				ps.setString(1, cb.getNome());

				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		}	catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	public synchronized int doDelete(CategoryBean categoria)  {
		try{
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Categoria WHERE Nome = ?;";

				ps = connection.prepareStatement(query);
				ps.setString(1, categoria.getNome());
				return ps.executeUpdate();
			} 
			finally {
				DBConnectionPool.releaseConnection(connection);
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}


	public synchronized void doUpdate(CategoryBean nuovo, CategoryBean old) {
		try { try {
			connection = DBConnectionPool.getConnection();
			String query = "UPDATE categoria SET Nome = ? WHERE Nome = ?;";

			ps = connection.prepareStatement(query);
			ps.setString(1, nuovo.getNome());
			ps.setString(2, old.getNome());
			ps.executeUpdate();
		} finally {
			DBConnectionPool.releaseConnection(connection);
		}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	public synchronized CategoryBean doRetrieveByKey(CategoryBean categoria)  {
		CategoryBean gb = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Categoria WHERE Nome = ?;";

				ps = connection.prepareStatement(query);
				ps.setString(1, categoria.getNome());
				rs = ps.executeQuery();

				if(rs.next()) {
					gb = new CategoryBean();
					gb.setNome(rs.getString("Nome"));
				}			
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return gb;
	}

	
	public ArrayList<CategoryBean> doRetrieveAll() {
		ArrayList<CategoryBean> categories = new ArrayList<CategoryBean>();
		try{
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Categoria;";

				st = connection.createStatement();
				rs = st.executeQuery(query);

				CategoryBean cb = null;
				while(rs.next()) {
					cb = new CategoryBean();
					cb.setNome(rs.getString("Nome"));

					categories.add(cb);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return categories;
	}
}