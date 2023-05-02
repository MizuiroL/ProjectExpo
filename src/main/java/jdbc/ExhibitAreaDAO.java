package jdbc;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ExhibitAreaDAO {
    private static final String selectExhibitAreaById = "" +
            "SELECT *\n" +
            "FROM exhibitArea\n" +
            "WHERE exhibitAreaId=?;";

    private static final String selectExhibitsByExhibitAreaId = "" +
            "SELECT exhibitId AS id, exhibitAreaId, exhibitorId, exhibitName AS name, exhibitStartDate AS startDate, exhibitEndDate AS endDate, exhibitStartTime AS startTime, exhibitEndTime AS endTime\n" +
            "FROM exhibit\n" +
            "WHERE exhibitAreaId=?\n" +
            "UNION\n" +
            "SELECT eventId AS id, exhibitAreaId, exhibitorId, eventName AS name, eventStartDate AS startDate, eventEndDate AS endDate, eventStartTime AS startTime, eventEndTime AS endTime\n" +
            "FROM event\n" +
            "WHERE exhibitAreaId=?;";

    public ExhibitArea getExhibitAreaById(Integer exhibitAreaId) throws SQLException {
        FixedExhibitArea exhibitArea = null;
        Connection connection = DB.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectExhibitAreaById);
        statement.setInt(1, exhibitAreaId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            //Integer expoId = rs.getInt("expoId");
            Integer expoId = rs.getInt("expoId");
            exhibitArea = new FixedExhibitArea();
            exhibitArea.setExhibitAreaId(exhibitAreaId);
            exhibitArea.setExpo(new ExpoDAO().getExpoById(expoId));
        }
        DB.closeConnection(connection);
        return exhibitArea;
    }

    public List<Exhibit> getExhibitsByExhibitAreaId(Integer exhibitAreaId) {
        List<Exhibit> exhibitList = new ArrayList<>();
        try {
        	Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectExhibitsByExhibitAreaId);
            statement.setInt(1, exhibitAreaId);
            statement.setInt(2, exhibitAreaId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Integer exhibitId = rs.getInt("id");
                Integer exhibitorId = rs.getInt("exhibitorId");
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
                //exhibitList.add(new Exhibit(exhibitId, exhibitAreaId, exhibitorId, name, start, end));
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitList;
    }
}
