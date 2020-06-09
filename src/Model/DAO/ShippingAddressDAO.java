package Model.DAO;

import Model.Beans.ShippingAddressBean;
import connectionPool.DBConnectionPool;

import java.util.ArrayList;
import java.io.Serializable;
import java.sql.*;

public class ShippingAddressDAO implements Serializable {
	private static final long serialVersionUID = -5192508403835820115L;
	
	private Connection connection;
	private ResultSet rs;
	private PreparedStatement ps;


	public synchronized int doSave(ShippingAddressBean ab) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "INSERT INTO Indirizzo_Di_Spedizione (ID, Preferito, Provincia, Città, CAP, Via, Civico) "
						+ " VALUES(?, ?, ?, ?, ?, ?, ?);";

				ps = connection.prepareStatement(query);
				ps.setLong(1, ab.getIDUser());
				ps.setBoolean(2, ab.isPreferred());
				ps.setString(3 ,ab.getProvincia());
				ps.setString(4, ab.getCittà());
				ps.setInt(5, ab.getCap());
				ps.setString(6, ab.getVia());
				ps.setInt(7, ab.getCivico());

				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized int doSaveOrUpdate(ShippingAddressBean ab) {
		ShippingAddressBean res = doRetrieveByKey(ab.getIDUser(), ab.getNumIndirizzo());
		if(res == null) doSave(ab);
		else {
			try {
				try {
					connection = DBConnectionPool.getConnection();
					String query = "UPDATE Indirizzo_Di_Spedizione"
							+ " SET Preferito = ?, Provincia= ?, Città = ? , CAP = ?, Via = ?, Civico= ? "
							+ " WHERE ID = ? AND Numero_Indirizzo = ?;";

					ps = connection.prepareStatement(query);

					ps.setBoolean(1, ab.isPreferred());
					ps.setString(2 ,ab.getProvincia());
					ps.setString(3, ab.getCittà());
					ps.setInt(4, ab.getCap());
					ps.setString(5, ab.getVia());
					ps.setInt(6, ab.getCivico());
					ps.setLong(7, ab.getIDUser());
					ps.setInt(8, ab.getNumIndirizzo());

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


	public synchronized int doDelete(long IDUser, int numeroIndirizzo) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "DELETE FROM Indirizzo_Di_Spedizione WHERE ID = ? AND Numero_Indirizzo = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				ps.setLong(2, numeroIndirizzo);

				return ps.executeUpdate();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public synchronized ShippingAddressBean doRetrieveByKey(long IDUser, int numeroIndirizzo) {
		ShippingAddressBean ab = null;
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Indirizzo_Di_Spedizione WHERE ID = ? AND Numero_Indirizzo = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				ps.setInt(2, numeroIndirizzo);
				rs = ps.executeQuery();

				if(rs.next()) {
					ab = new ShippingAddressBean();
					ab.setIDUser(IDUser);
					ab.setNumIndirizzo(numeroIndirizzo);
					ab.setPreferred(rs.getBoolean("Preferito"));
					ab.setProvincia(rs.getString("Provincia"));
					ab.setCittà(rs.getString("Città"));
					ab.setCap(rs.getInt("CAP"));
					ab.setVia(rs.getString("Via"));
					ab.setCivico(rs.getInt("Civico"));
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return ab;
	}


	public synchronized ArrayList<ShippingAddressBean> doRetrieveByIDUser(long IDUser) {
		ArrayList<ShippingAddressBean> addresses = new ArrayList<ShippingAddressBean>();
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "SELECT * FROM Indirizzo_Di_Spedizione WHERE ID = ?;";

				ps = connection.prepareStatement(query);
				ps.setLong(1, IDUser);
				rs = ps.executeQuery();

				ShippingAddressBean ab;
				while(rs.next()) {
					ab = new ShippingAddressBean();
					ab.setIDUser(IDUser);
					ab.setNumIndirizzo(rs.getInt("Numero_Indirizzo"));
					ab.setPreferred(rs.getBoolean("Preferito"));
					ab.setProvincia(rs.getString("Provincia"));
					ab.setCittà(rs.getString("Città"));
					ab.setCap(rs.getInt("CAP"));
					ab.setVia(rs.getString("Via"));
					ab.setCivico(rs.getInt("Civico"));

					addresses.add(ab);
				}
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return addresses;
	}


	public synchronized int doUpdateFavorite(long IDUser) {
		try {
			try {
				connection = DBConnectionPool.getConnection();
				String query = "UPDATE Indirizzo_Di_Spedizione "
						+ "SET Preferito = false "
						+ "WHERE ID = ?;";

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
}