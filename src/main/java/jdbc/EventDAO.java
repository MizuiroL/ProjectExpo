package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import model.Event;

public class EventDAO {
    private static final String selectEventById = "" +
    		"SELECT *\n" +
            "FROM event\n" +
            "WHERE eventId=?;";

    public Event getEventById(Integer eventId) {
        Event event = new Event();
        try {
            Connection connection = DB.getConnection();

            PreparedStatement statement = connection.prepareStatement(selectEventById);
            statement.setInt(1, eventId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String eventName = rs.getString("eventName");
                LocalDateTime eventStartDate = LocalDateTime.of(rs.getDate("eventStartDate").toLocalDate(), rs.getTime("eventStartTime").toLocalTime());
                LocalDateTime eventEndDate = LocalDateTime.of(rs.getDate("eventEndDate").toLocalDate(), rs.getTime("eventEndTime").toLocalTime());
                Integer eventTotalSeats = rs.getInt("exhibitAreaId");
                Integer eventAvailableSeats = rs.getInt("exhibitorId");
                
                event.setExhibitId(eventId);
                event.setExhibitName(eventName);
                event.setExhibitStartDate(eventStartDate.toLocalDate());
                event.setExhibitStartTime(eventStartDate.toLocalTime());
                event.setExhibitEndDate(eventEndDate.toLocalDate());
                event.setExhibitEndTime(eventEndDate.toLocalTime());
                event.setEventAvailableSeats(eventAvailableSeats);
                event.setEventTotalSeats(eventTotalSeats);
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return event;
    }
}
