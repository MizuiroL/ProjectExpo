package jdbc;

import model.Exhibit;
import model.Exhibitor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public Exhibitor getExhibitorById(Integer exhibitorId) {
        Exhibitor exhibitor = null;
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectExhibitorById);
            statement.setInt(1, exhibitorId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String exhibitorName = rs.getString("exhibitorName");
                exhibitor = new Exhibitor(exhibitorId, exhibitorName);
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
                Integer exhibitAreaId = rs.getInt("exhibitAreaId");
                String name = rs.getString("name");
                LocalDateTime start = LocalDateTime.of(rs.getDate("startDate").toLocalDate(), rs.getTime("startTime").toLocalTime());
                LocalDateTime end = LocalDateTime.of(rs.getDate("endDate").toLocalDate(), rs.getTime("endTime").toLocalTime());
                exhibitList.add(new Exhibit(exhibitId, exhibitAreaId, exhibitorId, name, start, end));
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitList;
    }
}
