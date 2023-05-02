package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Visitor;
import exceptions.UserNotFoundException;

public class LoginDAO {
	private static final String matchAccount = "" + "SELECT fiscalCode, name, surname, visitor.email\n"
			+ "FROM visitorAccount\n" + "JOIN visitor USING(fiscalCode)\n" + "WHERE visitorAccount.email=?\n"
			+ "AND visitorAccount.password=?\n";
	
	private static final String createVisitor = ""
			+ "INSERT INTO visitor (fiscalCode, name, surname, email) VALUES\n"
			+ "(?, ?, ?, ?);";

	public Visitor login(String email, String password) throws UserNotFoundException {
		System.out.println("LoginDAO: email " + email + " password " + password);
		Visitor visitor = new Visitor();
		try {
			Connection connection = DB.getConnection();

			PreparedStatement statement = connection.prepareStatement(matchAccount);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				String fiscalCode = rs.getString("fiscalCode");
				String name = rs.getString("name");
				String surname = rs.getString("surname");

				visitor.setFiscalCode(fiscalCode);
				visitor.setName(name);
				visitor.setSurname(surname);
				visitor.setEmail(email);
			} else {
				throw new UserNotFoundException("The login data for user " + email + "hasn't been found");
			}
			DB.closeConnection(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return visitor;
	}
	
	public Visitor createVisitor(String fiscalCode, String name, String surname, String email) {
		Visitor visitor = new Visitor();
		try {
			Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(createVisitor);
			
			statement.setString(1, fiscalCode);
			statement.setString(2, name);
			statement.setString(3, surname);
			statement.setString(4, email);
			
			statement.execute();
			// TODO throws exception if the above is false
			visitor.setFiscalCode(fiscalCode);
			visitor.setName(name);
			visitor.setSurname(surname);
			visitor.setEmail(email);
			
			DB.closeConnection(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return visitor;
	}
}
