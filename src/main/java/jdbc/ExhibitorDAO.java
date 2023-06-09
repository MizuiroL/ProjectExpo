package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.Exhibit;
import model.Exhibitor;
import model.ExhibitorNotification;


public class ExhibitorDAO {
    public static final String selectExhibitorById = "" +
            "SELECT *\n" +
            "FROM exhibitor\n" +
            "WHERE exhibitorId=?;";

    public static final String selectExhibitsByExhibitorId = "" +
            "SELECT exhibitId AS id, exhibitAreaId, exhibitorId, exhibitName AS name, exhibitStartDate AS startDate, exhibitEndDate AS endDate, exhibitStartTime AS startTime, exhibitEndTime AS endTime\n" +
            "FROM exhibit\n" +
            "WHERE exhibitorId=?\n" +
            "UNION\n" +
            "SELECT eventId AS id, exhibitAreaId, exhibitorId, eventName AS name, eventStartDate AS startDate, eventEndDate AS endDate, eventStartTime AS startTime, eventEndTime AS endTimee\n" +
            "FROM event\n" +
            "WHERE exhibitorId=?\n" +
            "ORDER BY id;";
    
    public static final String createNotification = ""
    		+ "INSERT INTO exhibitorNotification(exhibitorId, message) VALUES\n"
    		+ "(?, ?);";
    
    public static final String selectNotificationsByExhibitorId = ""
    		+ "SELECT notificationId, exhibitorId, message\n"
    		+ "FROM exhibitorNotification\n"
    		+ "WHERE exhibitorId=?;";
    
    public Exhibitor getExhibitorById(Integer exhibitorId) {
        Exhibitor exhibitor = null;
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectExhibitorById);
            statement.setInt(1, exhibitorId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String exhibitorName = rs.getString("exhibitorName");
                exhibitor = new Exhibitor();
                exhibitor.setExhibitorId(exhibitorId);
                exhibitor.setExhibitorName(exhibitorName);
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitor;
    }
    public List<Exhibit> getExhibitsByExhibitorId(Integer exhibitorId) {
        List<Exhibit> exhibitList = new ArrayList<>();
        try {
            Connection connection = DB.getConnection();

            PreparedStatement statement = connection.prepareStatement(selectExhibitsByExhibitorId);
            statement.setInt(1, exhibitorId);
            statement.setInt(2, exhibitorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
            	Integer exhibitId = rs.getInt("id");
                String name = rs.getString("name");
                //LocalDateTime start = LocalDateTime.of(rs.getDate("startDate").toLocalDate(), rs.getTime("startTime").toLocalTime());
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalTime startTime = rs.getTime("startTime").toLocalTime();
                //LocalDateTime end = LocalDateTime.of(rs.getDate("endDate").toLocalDate(), rs.getTime("endTime").toLocalTime());
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                LocalTime endTime = rs.getTime("endTime").toLocalTime();
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
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitList;
    }
    
	public void newNotification(Integer exhibitorId, String message) {
		try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(createNotification);
            statement.setInt(1, exhibitorId);
            statement.setString(2, message);
            int n = statement.executeUpdate();
            if (n == 0) {
            	System.out.println("Failed notification insert in database");
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	public List<ExhibitorNotification> selectAllNotifications(Integer exhibitorId) {
		List<ExhibitorNotification> notificationList = new ArrayList<>();
	        try {
	            Connection connection = DB.getConnection();

	            PreparedStatement statement = connection.prepareStatement(selectNotificationsByExhibitorId);
	            statement.setInt(1, exhibitorId);
	            ResultSet rs = statement.executeQuery();
	            while (rs.next()) {
	                Integer notificationId = rs.getInt("notificationId");
	                String message = rs.getString("message");
	                ExhibitorNotification notification = new ExhibitorNotification();
	                notification.setExhibitor(new ExhibitorDAO().getExhibitorById(exhibitorId));
	                notification.setNotificationId(notificationId);
	                notification.setMessage(message);
	                notificationList.add(notification);
	            }
	            DB.closeConnection(connection);
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
		return notificationList;
	}
}
