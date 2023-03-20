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

    private static final String updateFieldById = "" +
            "UPDATE exhibit\n" +
            "SET ?=?\n" +
            "WHERE exhibitId=?;";

    private static final String deleteExhibitById = "" +
            "DELETE FROM exhibit\n" +
            "WHERE exhibitId=?;";

    public Exhibit getExhibitById(Integer exhibitId) {
        Exhibit exhibit = null;
        try {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibit;
    }

    public Exhibit newExhibit(Exhibit exhibit) {
        try {
            this.openConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibit;
    }

    public void updateName(Integer exhibitId, String exhibitName) {
        try {
            this.openConnection();
            PreparedStatement statement = getConnection().prepareStatement(updateFieldById);
            statement.setString(1, "exhibitName");
            statement.setString(2, exhibitName);
            statement.setInt(3, exhibitId);

            if (statement.executeUpdate() != 1) {
                System.out.println("Failed update");
            }
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeExhibit(Exhibit exhibit) {
        try {
            this.openConnection();
            PreparedStatement statement = getConnection().prepareStatement(deleteExhibitById);
            statement.setInt(1, exhibit.getExhibitId());

            if (statement.executeUpdate() != 0) {
                System.out.println("Failed delete I think");
            }
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStartDate(Integer exhibitId, LocalDateTime exhibitStartDate) {
        try {
            this.openConnection();
            PreparedStatement statement = getConnection().prepareStatement(updateFieldById);
            statement.setString(1, "exhibitStartDate");
            statement.setString(2, exhibitStartDate.toLocalDate().toString());
            statement.setInt(3, exhibitId);

            if (statement.executeUpdate() != 1) {
                System.out.println("Failed update I think");
            }
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
