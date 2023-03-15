package jdbc;

import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpoDataAccess extends DataAccess {
    public Expo getExpoById(Integer expoId) throws SQLException {
        ExpoManager expoManager = null;
        this.openConnection();
        String query = "SELECT *\n" +
                "FROM expo\n" +
                "WHERE expoId=?;";
        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, expoId);
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            //Integer expoId = rs.getInt("expoId");
            String province = rs.getString("province");
            String comune = rs.getString("comune");
            String address = rs.getString("address");
            String streetNumber = rs.getString("streetNumber");
            LocalDateTime startDate = rs.getDate("startDate").toLocalDate().atStartOfDay();
            LocalDateTime endDate = rs.getDate("endDate").toLocalDate().atStartOfDay();
            closeConnection();
            expoManager = new ExpoManager(expoId, province, comune, address, streetNumber, startDate, endDate);
        }
        closeConnection();
        return expoManager;
    }

    public List<ExhibitionArea> getExhibitionAreasByExpoId(Integer expoId) throws SQLException {
        List<ExhibitionArea> exhibitionAreaList = new ArrayList<>();
        this.openConnection();
        String query = "SELECT *\n" +
                "FROM exhibitionArea\n" +
                "WHERE expoId=?;";
        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, expoId);
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Integer exhibitionAreaId = rs.getInt("exhibitionAreaId");
            exhibitionAreaList.add(new FixedExhibitionArea(exhibitionAreaId, expoId));
        }
        closeConnection();
        return exhibitionAreaList;
    }

}
