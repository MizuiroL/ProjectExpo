package jdbc;

import model.Exhibition;
import model.Exhibitor;
import model.Expo;
import model.ExpoManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExhibitorDataAccess extends DataAccess {
    public Exhibitor getExhibitorById(Integer exhibitorId) throws SQLException {
        Exhibitor exhibitor = null;
        this.openConnection();
        String query = "SELECT *\n" +
                "FROM exhibitor\n" +
                "WHERE exhibitorId=" + exhibitorId + ";";
        PreparedStatement statement = getConnection().prepareStatement(query);
        //statement.setInt(1, exhibitorId);
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            String exhibitorName = rs.getString("exhibitorName");
            closeConnection();
            exhibitor = new Exhibitor(exhibitorId, exhibitorName);
        }
        closeConnection();
        return exhibitor;
    }
    public List<Exhibition> getExhibitionsByExhibitorId(Integer exhibitorId) throws SQLException {
        List<Exhibition> exhibitionList = new ArrayList<>();
        this.openConnection();
        String query = "SELECT exhibitionId AS id, exhibitionAreaId, exhibitorId, exhibitionName AS name, exhibitionStartDate AS startDate, exhibitionEndDate AS endDate\n" +
                "FROM exhibition\n" +
                "WHERE exhibitorId=" + exhibitorId + "\n" +
                "UNION\n" +
                "SELECT eventId AS id, exhibitionAreaId, exhibitorId, eventName AS name, eventStartDate AS startDate, eventEndDate AS endDate\n" +
                "FROM event\n" +
                "WHERE exhibitorId=" + exhibitorId + ";";
        PreparedStatement statement = getConnection().prepareStatement(query);
        //statement.setInt(1, exhibitionAreaId);
        //statement.setInt(2, exhibitionAreaId);
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Integer exhibitionId = rs.getInt("id");
            Integer exhibitionAreaId = rs.getInt("exhibitionAreaId");
            String name = rs.getString("name");
            LocalDateTime start = rs.getDate("startDate").toLocalDate().atStartOfDay();
            LocalDateTime end = rs.getDate("endDate").toLocalDate().atStartOfDay();
            exhibitionList.add(new Exhibition(exhibitionId, exhibitionAreaId, exhibitorId, name, start, end));
        }
        closeConnection();
        return exhibitionList;
    }
}
