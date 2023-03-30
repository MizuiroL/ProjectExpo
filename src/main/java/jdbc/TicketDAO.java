package jdbc;

import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TicketDAO {
	private static final String selectTicketByCode = ""
			+ "SELECT *\n"
			+ "FROM ticket\n"
			+ "WHERE ticketCode=?";

	private static final String insertTicket = ""
			+ "INSERT INTO ticket(eventId, fiscalCode)\n"
			+ "VALUES (?,?);";

	public Ticket getTicketByCode(Integer ticketCode) {
		Ticket ticket = null;
		try {
			Connection connection = DB.getConnection();
			PreparedStatement statement = connection.prepareStatement(selectTicketByCode);
			statement.setInt(1, ticketCode);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Integer eventId = rs.getInt("eventId");
				String fiscalCode = rs.getString("fiscalCode");
				ticket = new Ticket(ticketCode, eventId, fiscalCode);
			}
			DB.closeConnection(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return ticket;
	}

	public Ticket newTicket(Ticket ticket) {
		try {
			Connection connection = DB.getConnection();
			ResultSet generatedKeys;
			PreparedStatement statement = connection.prepareStatement(insertTicket, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, ticket.getEventId());
			statement.setString(2, ticket.getFiscalCode());

			if (statement.executeUpdate() == 0) {
				throw new SQLException("Ticket purchase failed");
			}
			generatedKeys = statement.getGeneratedKeys();
			//generatedKeys.last();
			//System.out.println("THIS ONE HOW MANY GENERATEDKEYS? " + generatedKeys.getRow());

			if (generatedKeys.next()) {
				ticket.setTicketCode(generatedKeys.getInt(1));
			}
			DB.closeConnection(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return ticket;
	}
}
