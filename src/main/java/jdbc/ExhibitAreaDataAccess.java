package jdbc;

import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExhibitAreaDataAccess extends DataAccess {
    private static final String selectExhibitAreaById = "" +
            "SELECT *\n" +
            "FROM exhibitArea\n" +
            "WHERE exhibitAreaId=?;";

    private static final String selectExhibitsByExhibitAreaId = "" +
            "SELECT exhibitId AS id, exhibitAreaId, exhibitorId, exhibitName AS name, exhibitStartDate AS startDate, exhibitEndDate AS endDate\n" +
            "FROM exhibit\n" +
            "WHERE exhibitAreaId=?\n" +
            "UNION\n" +
            "SELECT eventId AS id, exhibitAreaId, exhibitorId, eventName AS name, eventStartDate AS startDate, eventEndDate AS endDate\n" +
            "FROM event\n" +
            "WHERE exhibitAreaId=?;";

    public ExhibitArea getExhibitAreaById(Integer exhibitAreaId) throws SQLException {
        ExhibitArea exhibitArea = null;
        this.openConnection();
        PreparedStatement statement = getConnection().prepareStatement(selectExhibitAreaById);
        statement.setInt(1, exhibitAreaId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            //Integer expoId = rs.getInt("expoId");
            Integer expoId = rs.getInt("expoId");
            exhibitArea = new FixedExhibitArea(exhibitAreaId, expoId);
        }
        closeConnection();
        return exhibitArea;
    }

    public List<Exhibit> getExhibitsByExhibitAreaId(Integer exhibitAreaId) {
        List<Exhibit> exhibitList = new ArrayList<>();
        try {
            this.openConnection();
            PreparedStatement statement = getConnection().prepareStatement(selectExhibitsByExhibitAreaId);
            statement.setInt(1, exhibitAreaId);
            statement.setInt(2, exhibitAreaId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Integer exhibitId = rs.getInt("id");
                Integer exhibitorId = rs.getInt("exhibitorId");
                String name = rs.getString("name");
                LocalDateTime start = LocalDateTime.of(rs.getDate("exhibitStartDate").toLocalDate(), rs.getTime("exhibitStartTime").toLocalTime());
                LocalDateTime end = LocalDateTime.of(rs.getDate("exhibitEndDate").toLocalDate(), rs.getTime("exhibitEndTime").toLocalTime());
                exhibitList.add(new Exhibit(exhibitId, exhibitAreaId, exhibitorId, name, start, end));
            }
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitList;
    }
}
