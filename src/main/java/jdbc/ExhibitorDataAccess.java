package jdbc;

import model.Exhibit;
import model.Exhibitor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExhibitorDataAccess extends DataAccess {
    public static final String selectExhibitorById = "" +
            "SELECT *\n" +
            "FROM exhibitor\n" +
            "WHERE exhibitorId=?;";

    public static final String selectExhibitsByExhibitorId = "" +
            "SELECT exhibitId AS id, exhibitAreaId, exhibitorId, exhibitName AS name, exhibitStartDate AS startDate, exhibitEndDate AS endDate\n" +
            "FROM exhibit\n" +
            "WHERE exhibitorId=?\n" +
            "UNION\n" +
            "SELECT eventId AS id, exhibitAreaId, exhibitorId, eventName AS name, eventStartDate AS startDate, eventEndDate AS endDate\n" +
            "FROM event\n" +
            "WHERE exhibitorId=?\n" +
            "ORDER BY id;";
    public Exhibitor getExhibitorById(Integer exhibitorId) throws SQLException {
        Exhibitor exhibitor = null;
        this.openConnection();
        PreparedStatement statement = getConnection().prepareStatement(selectExhibitorById);
        statement.setInt(1, exhibitorId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            String exhibitorName = rs.getString("exhibitorName");
            closeConnection();
            exhibitor = new Exhibitor(exhibitorId, exhibitorName);
        }
        closeConnection();
        return exhibitor;
    }
    public List<Exhibit> getExhibitsByExhibitorId(Integer exhibitorId) throws SQLException {
        List<Exhibit> exhibitList = new ArrayList<>();
        this.openConnection();
        String query = "SELECT exhibitId AS id, exhibitAreaId, exhibitorId, exhibitName AS name, exhibitStartDate AS startDate, exhibitEndDate AS endDate\n" +
                "FROM exhibit\n" +
                "WHERE exhibitorId=?\n" +
                "UNION\n" +
                "SELECT eventId AS id, exhibitAreaId, exhibitorId, eventName AS name, eventStartDate AS startDate, eventEndDate AS endDate\n" +
                "FROM event\n" +
                "WHERE exhibitorId=?\n" +
                "ORDER BY id;";
        PreparedStatement statement = getConnection().prepareStatement(selectExhibitsByExhibitorId);
        statement.setInt(1, exhibitorId);
        statement.setInt(2, exhibitorId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Integer exhibitId = rs.getInt("id");
            Integer exhibitAreaId = rs.getInt("exhibitAreaId");
            String name = rs.getString("name");
            LocalDateTime start = rs.getDate("startDate").toLocalDate().atStartOfDay();
            LocalDateTime end = rs.getDate("endDate").toLocalDate().atStartOfDay();
            exhibitList.add(new Exhibit(exhibitId, exhibitAreaId, exhibitorId, name, start, end));
        }
        closeConnection();
        return exhibitList;
    }
}
