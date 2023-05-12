package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.Exhibit;
import model.ExhibitArea;
import model.ExpoManager;
import model.FixedExhibitArea;

public class ExpoDAO {
    private static final String selectExpoById = "" +
            "SELECT *\n" +
            "FROM expo\n" +
            "WHERE expoId=?;";

    private static final String selectExhibitAreasByExpoId = "" +
            "SELECT *\n" +
            "FROM exhibitArea\n" +
            "WHERE expoId=?;";
    
    private static final String selectExhibitInCurrentDate = ""
    		+ "SELECT exhibitId, exhibitAreaId, exhibitorId, exhibitName, exhibitStartDate, exhibitStartTime, exhibitEndDate, exhibitEndTime\n"
    		+ "FROM exhibit\n"
    		+ "JOIN exhibitArea USING (exhibitAreaId)\n"
    		+ "JOIN expo USING (expoId)\n"
    		+ "WHERE expoId=?\n"
    		+ "AND CURDATE() BETWEEN exhibitStartDate AND exhibitEndDate;";
    
    private static final String selectFutureEvents = ""
    		+ "SELECT exhibitId, exhibitAreaId, exhibitorId, exhibitName, exhibitStartDate, exhibitStartTime, exhibitEndDate, exhibitEndTime, eventTotalSeats, eventAvailableSeats\n"
    		+ "FROM event\n"
    		+ "JOIN exhibitArea USING (exhibitAreaId)\n"
    		+ "JOIN expo USING (expoId)\n"
    		+ "WHERE expo.expoId=?\n"
    		+ "AND event.exhibitStartDate > CURDATE();";
    
    private static final String selectBookedEvents = ""
    		+ "SELECT exhibitId, exhibitAreaId, exhibitorId, exhibitName, exhibitStartDate, exhibitStartTime, exhibitEndDate, exhibitEndTime, eventTotalSeats, eventAvailableSeats\n"
    		+ "FROM event\n"
    		+ "JOIN ticket USING (exhibitId)\n"
    		+ "JOIN visitor USING (fiscalCode)\n"
    		+ "JOIN visitorExpoPass USING (fiscalCode)\n"
    		+ "WHERE expoId=?\n"
    		+ "AND fiscalCode=?";

    public ExpoManager getExpoById(Integer expoId) {
        ExpoManager expoManager = null;
        try {
            Connection connection = DB.getConnection();

            PreparedStatement statement = connection.prepareStatement(selectExpoById);
            statement.setInt(1, expoId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String province = rs.getString("province");
                String comune = rs.getString("comune");
                String address = rs.getString("address");
                String streetNumber = rs.getString("streetNumber");
                LocalDate startDate = rs.getDate("expoStartDate").toLocalDate();
                LocalDate endDate = rs.getDate("expoEndDate").toLocalDate();
                expoManager = new ExpoManager(expoId, province, comune, address, streetNumber, startDate, endDate);
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expoManager;
    }

    public List<ExhibitArea> getExhibitAreasByExpoId(Integer expoId) {
        List<ExhibitArea> exhibitAreaList = new ArrayList<>();
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectExhibitAreasByExpoId);
            statement.setInt(1, expoId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Integer exhibitAreaId = rs.getInt("exhibitAreaId");
                FixedExhibitArea exhibitArea = new FixedExhibitArea();
                exhibitArea.setExhibitAreaId(exhibitAreaId);
                exhibitArea.setExpo(new ExpoDAO().getExpoById(expoId));
                exhibitAreaList.add(exhibitArea);
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitAreaList;
    }
    
    public List<Exhibit> getStartedExhibits(Integer expoId) {
    	List<Exhibit> exhibitList = new ArrayList<>();
    	try {
    		Connection connection = DB.getConnection();
    		PreparedStatement statement = connection.prepareStatement(selectExhibitInCurrentDate);
    		statement.setInt(1, expoId);
    		ResultSet rs = statement.executeQuery();
    		
    		while (rs.next()) {
    			Integer exhibitId = rs.getInt("exhibitId");
                Integer exhibitorId = rs.getInt("exhibitorId");
                String name = rs.getString("exhibitName");
                //LocalDateTime start = LocalDateTime.of(rs.getDate("startDate").toLocalDate(), rs.getTime("startTime").toLocalTime());
                LocalDate startDate = rs.getDate("exhibitStartDate").toLocalDate();
                LocalTime startTime = rs.getTime("exhibitStartTime").toLocalTime();
                //LocalDateTime end = LocalDateTime.of(rs.getDate("endDate").toLocalDate(), rs.getTime("endTime").toLocalTime());
                LocalDate endDate = rs.getDate("exhibitEndDate").toLocalDate();
                LocalTime endTime = rs.getTime("exhibitEndTime").toLocalTime();
                Exhibit exhibit = new Exhibit();
                exhibit.setExhibitId(exhibitId);
                exhibit.setExhibitor(new ExhibitorDAO().getExhibitorById(exhibitorId));
                exhibit.setExhibitName(name);
                exhibit.setExhibitStartDate(startDate);
                exhibit.setExhibitStartTime(startTime);
                exhibit.setExhibitEndDate(endDate);
                exhibit.setExhibitEndTime(endTime);
                exhibitList.add(exhibit);
                
    		}
            rs.close();
            connection.close();
    	} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    	return exhibitList;
    }

	public List<Event> getFutureEvents(Integer expoId) {
		List<Event> eventList = new ArrayList<>();
    	try {
    		Connection connection = DB.getConnection();
    		PreparedStatement statement = connection.prepareStatement(selectFutureEvents);
    		statement.setInt(1, expoId);
    		ResultSet rs = statement.executeQuery();
    		while (rs.next()) {
    			Integer exhibitId = rs.getInt("exhibitId");
                Integer exhibitorId = rs.getInt("exhibitorId");
                String name = rs.getString("exhibitName");
                //LocalDateTime start = LocalDateTime.of(rs.getDate("startDate").toLocalDate(), rs.getTime("startTime").toLocalTime());
                LocalDate startDate = rs.getDate("exhibitStartDate").toLocalDate();
                LocalTime startTime = rs.getTime("exhibitStartTime").toLocalTime();
                //LocalDateTime end = LocalDateTime.of(rs.getDate("endDate").toLocalDate(), rs.getTime("endTime").toLocalTime());
                LocalDate endDate = rs.getDate("exhibitEndDate").toLocalDate();
                LocalTime endTime = rs.getTime("exhibitEndTime").toLocalTime();
                Integer eventTotalSeats = rs.getInt("eventAvailableSeats");
                Integer eventAvailableSeats = rs.getInt("eventAvailableSeats");
                Event event = new Event();
                event.setExhibitId(exhibitId);
                event.setExhibitor(new ExhibitorDAO().getExhibitorById(exhibitorId));
                event.setExhibitName(name);
                event.setExhibitStartDate(startDate);
                event.setExhibitStartTime(startTime);
                event.setExhibitEndDate(endDate);
                event.setExhibitEndTime(endTime);
                event.setEventTotalSeats(eventTotalSeats);
                event.setEventAvailableSeats(eventAvailableSeats);
                eventList.add(event);
    		}
    	} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    	return eventList;
	}

	public List<Event> getBookedEvents(Integer expoId, String fiscalCode) {
		List<Event> eventList = new ArrayList<>();
    	try {
    		Connection connection = DB.getConnection();
    		PreparedStatement statement = connection.prepareStatement(selectBookedEvents);
    		statement.setInt(1, expoId);
    		statement.setString(2, fiscalCode);
    		ResultSet rs = statement.executeQuery();
    		while (rs.next()) {
    			Integer exhibitId = rs.getInt("exhibitId");
                Integer exhibitorId = rs.getInt("exhibitorId");
                String name = rs.getString("exhibitName");
                //LocalDateTime start = LocalDateTime.of(rs.getDate("startDate").toLocalDate(), rs.getTime("startTime").toLocalTime());
                LocalDate startDate = rs.getDate("exhibitStartDate").toLocalDate();
                LocalTime startTime = rs.getTime("exhibitStartTime").toLocalTime();
                //LocalDateTime end = LocalDateTime.of(rs.getDate("endDate").toLocalDate(), rs.getTime("endTime").toLocalTime());
                LocalDate endDate = rs.getDate("exhibitEndDate").toLocalDate();
                LocalTime endTime = rs.getTime("exhibitEndTime").toLocalTime();
                Event event = new Event();
                event.setExhibitId(exhibitId);
                event.setExhibitor(new ExhibitorDAO().getExhibitorById(exhibitorId));
                event.setExhibitName(name);
                event.setExhibitStartDate(startDate);
                event.setExhibitStartTime(startTime);
                event.setExhibitEndDate(endDate);
                event.setExhibitEndTime(endTime);
                //Event event = new Event(eventId, exhibitAreaId, exhibitorId, eventName, eventStartDate, eventEndDate, eventTotalSeats, eventAvailableSeats);
                eventList.add(event);
    		}
    	} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    	return eventList;
	}

}
