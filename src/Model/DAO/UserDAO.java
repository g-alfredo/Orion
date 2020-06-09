package Model.DAO;

import Model.Beans.UserBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

public class UserDAO implements Serializable {
	private static final long serialVersionUID = 4981460112255796602L;
	
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement ps;


	public synchronized int doSave(UserBean ub) {		
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Utente (Email,User_Password,Nome,Cognome,Amministratore,Telefono) "
						+ " VALUES (?, ?, ?, ?, ?, ?);";

				ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, ub.getEmail());
				ps.setString(2, ub.getPassword());
				ps.setString(3, ub.getNome());
				ps.setString(4, ub.getCognome());
				ps.setBoolean(5, ub.getAdmin());
				ps.setString(6, ub.getTelefono());

				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if(rs.next()) return rs.getInt(1);
				else return -1;
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized int doSaveOrUpdate(UserBean ub) {
		UserBean res = doRetrieveByKey(ub.getIDUser()); 
		if(res == null) doSave(ub);
		else {
			try {
				try {
					connection = DBConnectionPool.getConnection();
					String query = "UPDATE Utente"
							+ " SET	Email = ?,"
							+ " User_Password = ?,"
							+ " Nome = ?,"
							+ " Cognome = ?,"
							+ " Amministratore = ?,"
							+ " Telefono = ?"
							+ " WHERE ID = ?;";

					ps = connection.prepareStatement(query);
					ps.setString(1, ub.getEmail());
					ps.setString(2, ub.getPassword());
					ps.setString(3, ub.getNome());
					ps.setString(4, ub.getCognome());
					ps.setBoolean(5, ub.getAdmin());
					ps.setString(6, ub.getTelefono());
					ps.setLong(7, ub.getIDUser());

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


	public synchronized int doDelete(long IDUser) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Utente WHERE ID = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized UserBean doRetrieveByKey(long IDUser) {
		UserBean ub = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Utente WHERE ID = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				rs = ps.executeQuery();

				if(rs.next()) {
					ub = new UserBean();
					ub.setIDUser(rs.getLong("ID"));
					ub.setEmail(rs.getString("Email"));
					ub.setPassword(rs.getString("User_Password"));
					ub.setNome(rs.getString("Nome"));
					ub.setCognome(rs.getString("Cognome"));
					ub.setAdmin(rs.getBoolean("Amministratore"));
					ub.setTelefono(rs.getString("Telefono"));
				}		
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return ub;
	}


	public synchronized ArrayList<UserBean> doRetrieveAll(int pagina,int limite) {
		ArrayList<UserBean> users = new ArrayList<UserBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Utente LIMIT ? , ? ;";

				ps = connection.prepareStatement(query);
				ps.setInt(1,pagina);
				ps.setInt(2,limite);
				rs = ps.executeQuery();

				UserBean ub;
				while (rs.next()) {
					ub = new UserBean();
					ub.setIDUser(rs.getLong("ID"));
					ub.setEmail(rs.getString("Email"));
					ub.setPassword(rs.getString("User_Password"));
					ub.setNome(rs.getString("Nome"));
					ub.setCognome(rs.getString("Cognome"));
					ub.setAdmin(rs.getBoolean("Amministratore"));
					ub.setTelefono(rs.getString("Telefono"));

					users.add(ub);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return users;
	}


	public synchronized int setStatus(long IDUser, boolean valore) {

		UserBean res = doRetrieveByKey(IDUser); 
		if(res == null) return -1;
		else {
			try {
				try {
					connection = DBConnectionPool.getConnection();
					String query = "UPDATE Utente"
							+ " SET Status_Utente = ?"
							+ " WHERE ID = ?;";

					ps = connection.prepareStatement(query);
					ps.setBoolean(1, valore);
					ps.setLong(2, IDUser);


					return ps.executeUpdate();
				} finally {
					DBConnectionPool.releaseConnection(connection);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}


	public synchronized UserBean doRetrieveByEmail(String email) {
		UserBean ub = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Utente WHERE Email = ?;";

				ps = connection.prepareStatement(query);
				ps.setString(1, email);
				rs = ps.executeQuery();

				if(rs.next()) {
					ub = new UserBean();
					ub.setIDUser(rs.getLong("ID"));
					ub.setEmail(rs.getString("Email"));
					ub.setPassword(rs.getString("User_Password"));
					ub.setNome(rs.getString("Nome"));
					ub.setCognome(rs.getString("Cognome"));
					ub.setAdmin(rs.getBoolean("Amministratore"));
					ub.setTelefono(rs.getString("Telefono"));
				}    
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return ub;
	}
}