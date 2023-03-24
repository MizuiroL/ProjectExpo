package jdbc;

import model.Visitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitorDAO {
	private static final String selectVisitorByFiscalCode = "" +
			"SELECT *\n" +
			"FROM visitor\n" +
			"WHERE fiscalCode=?;";

	public Visitor getVisitorByFiscalCode(String fiscalCode) {
		Visitor visitor = null;
		try {
			Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(selectVisitorByFiscalCode);
			statement.setString(1, fiscalCode);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Integer expoId = rs.getInt("expoId");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String email = rs.getString("email");
				visitor = new Visitor(fiscalCode, expoId, name, surname, email);
			}
			DB.closeConnection(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return visitor;
	}
}
