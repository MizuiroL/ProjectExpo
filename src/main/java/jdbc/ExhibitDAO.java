package jdbc;

import model.Exhibit;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExhibitDAO {
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
        	Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectExhibitById);
            statement.setInt(1, exhibitId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Integer exhibitorId = rs.getInt("exhibitorId");
                String name = rs.getString("name");
                //LocalDateTime start = LocalDateTime.of(rs.getDate("startDate").toLocalDate(), rs.getTime("startTime").toLocalTime());
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalTime startTime = rs.getTime("startTime").toLocalTime();
                //LocalDateTime end = LocalDateTime.of(rs.getDate("endDate").toLocalDate(), rs.getTime("endTime").toLocalTime());
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                LocalTime endTime = rs.getTime("endTime").toLocalTime();
                exhibit = new Exhibit();
                exhibit.setExhibitId(exhibitId);
                exhibit.setExhibitor(new ExhibitorDAO().getExhibitorById(exhibitorId));
                exhibit.setExhibitName(name);
                exhibit.setExhibitStartDate(startDate);
                exhibit.setExhibitStartTime(startTime);
                exhibit.setExhibitEndDate(endDate);
                exhibit.setExhibitEndTime(endTime);
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibit;
    }

    public Exhibit newExhibit(Exhibit exhibit) {
        try {
        	Connection connection = DB.getConnection();
        	PreparedStatement statement = connection.prepareStatement(insertIntoExhibit, Statement.RETURN_GENERATED_KEYS);
        	ResultSet generatedKeys;
            statement.setInt(1, exhibit.getExhibitArea().getExhibitAreaId());
            statement.setInt(2, exhibit.getExhibitor().getExhibitorId());
            statement.setString(3, exhibit.getExhibitName());
            statement.setDate(4, Date.valueOf(exhibit.getExhibitStartDate()));
            statement.setTime(5, Time.valueOf(exhibit.getExhibitStartTime()));
            statement.setDate(6, Date.valueOf(exhibit.getExhibitEndDate()));
            statement.setTime(7, Time.valueOf(exhibit.getExhibitEndTime()));

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                exhibit.setExhibitId(generatedKeys.getInt(1));
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibit;
    }

    public void updateName(Integer exhibitId, String exhibitName) {
        try {
        	Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateFieldById);
            statement.setString(1, "exhibitName");
            statement.setString(2, exhibitName);
            statement.setInt(3, exhibitId);

            if (statement.executeUpdate() != 1) {
                System.out.println("Failed update");
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeExhibit(Exhibit exhibit) {
        try {
        	Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteExhibitById);
            statement.setInt(1, exhibit.getExhibitId());

            if (statement.executeUpdate() != 0) {
                System.out.println("Failed delete I think");
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStartDate(Integer exhibitId, LocalDateTime exhibitStartDate) {
        try {
        	Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateFieldById);
            statement.setString(1, "exhibitStartDate");
            statement.setString(2, exhibitStartDate.toLocalDate().toString());
            statement.setInt(3, exhibitId);

            if (statement.executeUpdate() != 1) {
                System.out.println("Failed update I think");
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
