package jdbc;

import model.Event;
import model.Exhibition;

import java.sql.*;
import java.time.LocalDateTime;

public class ExhibitionDataAccess  extends DataAccess {
    public Exhibition getExhibitionById(Integer exhibitionId) throws SQLException {
        Exhibition exhibition = null;
        this.openConnection();
        String query = "SELECT *\n" +
                "FROM exhibition\n" +
                "WHERE exhibitionId=?;";
        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, exhibitionId);
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            Integer exhibitionAreaId = rs.getInt("exhibitionAreaId");
            Integer exhibitorId = rs.getInt("exhibitorId");
            String exhibitionName = rs.getString("exhibitionName");
            LocalDateTime exhibitionStartDate = LocalDateTime.of(rs.getDate("exhibitionStartDate").toLocalDate(), rs.getTime("exhibitionStartTime").toLocalTime());
            LocalDateTime exhibitionEndDate = LocalDateTime.of(rs.getDate("exhibitionEndDate").toLocalDate(), rs.getTime("exhibitionEndTime").toLocalTime());
            exhibition = new Exhibition(exhibitionId, exhibitionAreaId, exhibitorId, exhibitionName, exhibitionStartDate, exhibitionEndDate);
        }
        closeConnection();
        return exhibition;
    }

    public Exhibition newExhibition(Exhibition exhibition) throws SQLException {
        this.openConnection();
        String query = "INSERT INTO exhibition(exhibitionAreaId, exhibitorId, exhibitionName, exhibitionStartDate, exhibitionStartTime, exhibitionEndDate, exhibitionEndTime) VALUES (?,?,?,?,?,?,?);";
        ResultSet generatedKeys;
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, exhibition.getExhibitionAreaId());
            statement.setInt(2, exhibition.getExhibitorId());
            statement.setString(3, exhibition.getExhibitionName());
            statement.setDate(4, Date.valueOf(exhibition.getExhibitionStartDate().toLocalDate()));
            statement.setTime(5, Time.valueOf(exhibition.getExhibitionStartDate().toLocalTime()));
            statement.setDate(6, Date.valueOf(exhibition.getExhibitionEndDate().toLocalDate()));
            statement.setTime(7, Time.valueOf(exhibition.getExhibitionEndDate().toLocalTime()));

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
        }
        if (generatedKeys.next()) {
            exhibition.setExhibitionId(generatedKeys.getInt(1));
        }
        return exhibition;
    }
}
