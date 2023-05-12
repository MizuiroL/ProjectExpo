package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.UserNotFoundException;
import model.Visitor;

public class LoginDAO {
	private static final String matchAccount = "" + "SELECT fiscalCode, name, surname, visitor.email\n"
			+ "FROM visitorAccount\n" + "JOIN visitor USING(fiscalCode)\n" + "WHERE visitorAccount.email=?\n"
			+ "AND visitorAccount.password=?\n";
	
	private static final String createVisitor = ""
			+ "INSERT INTO visitor (fiscalCode, name, surname, email) VALUES\n"
			+ "(?, ?, ?, ?);";
	
	private static final String createVisitorAccount = ""
			+ "INSERT INTO visitorAccount (fiscalCode, email, password) VALUES\n"
			+ "(?, ?, ?);";

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
	
	public Visitor createVisitor(String fiscalCode, String name, String surname, String email, String password) {
		Visitor visitor = new Visitor();
		try {
			Connection connection = DB.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(createVisitor);
			PreparedStatement statement2 = connection.prepareStatement(createVisitorAccount);
			
			statement.setString(1, fiscalCode);
			statement.setString(2, name);
			statement.setString(3, surname);
			statement.setString(4, email);

			statement.execute();
			// TODO throws exception if the above is false
			
			statement2.setString(1, fiscalCode);
			statement2.setString(2, email);
			statement2.setString(3, password);
			
			statement2.execute();
			
			connection.commit();
			
			DB.closeConnection(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		visitor.setFiscalCode(fiscalCode);
		visitor.setName(name);
		visitor.setSurname(surname);
		visitor.setEmail(email);
		return visitor;
	}
}
