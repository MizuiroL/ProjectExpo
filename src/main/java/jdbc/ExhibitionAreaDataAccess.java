package jdbc;

import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionAreaDataAccess extends DataAccess {
    public ExhibitionArea getExhibitionAreaById(Integer exhibitionAreaId) throws SQLException {
        ExhibitionArea exhibitionArea = null;
        this.openConnection();
        String query = "SELECT *\n" +
                "FROM exhibitionArea\n" +
                "WHERE exhibitionAreaId=?;";
        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, exhibitionAreaId);
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            //Integer expoId = rs.getInt("expoId");
            Integer expoId = rs.getInt("expoId");
            closeConnection();
            exhibitionArea = new FixedExhibitionArea(exhibitionAreaId, expoId);
        }
        closeConnection();
        return exhibitionArea;
    }

    public List<Exhibition> getExhibitionsByExhibitionAreaId(Integer exhibitionAreaId) throws SQLException {
        List<Exhibition> exhibitionList = new ArrayList<>();
        this.openConnection();
        String query = "SELECT exhibitionId AS id, exhibitionAreaId, exhibitorId, exhibitionName AS name, exhibitionStartDate AS startDate, exhibitionEndDate AS endDate\n" +
                "FROM exhibition\n" +
                "WHERE exhibitionAreaId=" + exhibitionAreaId + "\n" +
                "UNION\n" +
                "SELECT eventId AS id, exhibitionAreaId, exhibitorId, eventName AS name, eventStartDate AS startDate, eventEndDate AS endDate\n" +
                "FROM event\n" +
                "WHERE exhibitionAreaId=" + exhibitionAreaId + ";";
        PreparedStatement statement = getConnection().prepareStatement(query);
        //statement.setInt(1, exhibitionAreaId);
        //statement.setInt(2, exhibitionAreaId);
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Integer exhibitionId = rs.getInt("id");
            Integer exhibitorId = rs.getInt("exhibitorId");
            String name = rs.getString("name");
            LocalDateTime start = rs.getDate("startDate").toLocalDate().atStartOfDay();
            LocalDateTime end = rs.getDate("endDate").toLocalDate().atStartOfDay();
            exhibitionList.add(new Exhibition(exhibitionId, exhibitionAreaId, exhibitorId, name, start, end));
        }
        closeConnection();
        return exhibitionList;
    }
}
