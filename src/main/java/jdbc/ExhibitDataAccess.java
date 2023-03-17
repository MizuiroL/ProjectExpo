package jdbc;

import model.Exhibit;

import java.sql.*;
import java.time.LocalDateTime;

public class ExhibitDataAccess extends DataAccess {
    private static final String selectExhibitById = "" +
            "SELECT *\n" +
            "FROM exhibit\n" +
            "WHERE exhibitId=?;";

    private static final String insertIntoExhibit = "" +
            "INSERT INTO exhibit(exhibitAreaId, exhibitorId, exhibitName, exhibitStartDate, exhibitStartTime, exhibitEndDate, exhibitEndTime) \n" +
            "VALUES (?,?,?,?,?,?,?);";
    public Exhibit getExhibitById(Integer exhibitId) throws SQLException {
        Exhibit exhibit = null;
        this.openConnection();
        PreparedStatement statement = getConnection().prepareStatement(selectExhibitById);
        statement.setInt(1, exhibitId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            Integer exhibitAreaId = rs.getInt("exhibitAreaId");
            Integer exhibitorId = rs.getInt("exhibitorId");
            String exhibitName = rs.getString("exhibitName");
            LocalDateTime exhibitStartDate = LocalDateTime.of(rs.getDate("exhibitStartDate").toLocalDate(), rs.getTime("exhibitStartTime").toLocalTime());
            LocalDateTime exhibitEndDate = LocalDateTime.of(rs.getDate("exhibitEndDate").toLocalDate(), rs.getTime("exhibitEndTime").toLocalTime());
            exhibit = new Exhibit(exhibitId, exhibitAreaId, exhibitorId, exhibitName, exhibitStartDate, exhibitEndDate);
        }
        closeConnection();
        return exhibit;
    }

    public Exhibit newExhibit(Exhibit exhibit) throws SQLException {
        this.openConnection();
        ResultSet generatedKeys;
        try (PreparedStatement statement = getConnection().prepareStatement(insertIntoExhibit, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, exhibit.getExhibitAreaId());
            statement.setInt(2, exhibit.getExhibitorId());
            statement.setString(3, exhibit.getExhibitName());
            statement.setDate(4, Date.valueOf(exhibit.getExhibitStartDate().toLocalDate()));
            statement.setTime(5, Time.valueOf(exhibit.getExhibitStartDate().toLocalTime()));
            statement.setDate(6, Date.valueOf(exhibit.getExhibitEndDate().toLocalDate()));
            statement.setTime(7, Time.valueOf(exhibit.getExhibitEndDate().toLocalTime()));

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                exhibit.setExhibitId(generatedKeys.getInt(1));
            }
        }
        return exhibit;
    }
}
