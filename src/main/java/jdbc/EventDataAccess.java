package jdbc;

import model.Event;

import java.sql.*;
import java.time.LocalDateTime;

public class EventDataAccess  extends DataAccess {
    private static final String selectEventById = "SELECT *\n" +
            "FROM event\n" +
            "WHERE eventId=?;";

    public Event getEventById(Integer eventId) throws SQLException {
        Event event = null;
        this.openConnection();
        String query = "SELECT *\n" +
                "FROM event\n" +
                "WHERE eventId=?;";
        PreparedStatement statement = getConnection().prepareStatement(selectEventById);
        statement.setInt(1, eventId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            //Integer expoId = rs.getInt("expoId");
            Integer exhibitAreaId = rs.getInt("exhibitAreaId");
            Integer exhibitorId = rs.getInt("exhibitorId");
            String eventName = rs.getString("eventName");
            LocalDateTime eventStartDate = LocalDateTime.of(rs.getDate("eventStartDate").toLocalDate(), rs.getTime("eventStartTime").toLocalTime());
            LocalDateTime eventEndDate = LocalDateTime.of(rs.getDate("eventEndDate").toLocalDate(), rs.getTime("eventEndTime").toLocalTime());
            Integer eventTotalSeats = rs.getInt("exhibitAreaId");
            Integer eventAvailableSeats = rs.getInt("exhibitorId");
            event = new Event(eventId, exhibitAreaId, exhibitorId, eventName, eventStartDate, eventEndDate, eventTotalSeats, eventAvailableSeats);
        }
        closeConnection();
        return event;
    }
}
