package jdbc;

import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpoDataAccess extends DataAccess {
    private static final String selectExpoById = "" +
            "SELECT *\n" +
            "FROM expo\n" +
            "WHERE expoId=?;";
    public Expo getExpoById(Integer expoId) throws SQLException {
        ExpoManager expoManager = null;
        this.openConnection();

        PreparedStatement statement = getConnection().prepareStatement(selectExpoById);
        statement.setInt(1, expoId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            String province = rs.getString("province");
            String comune = rs.getString("comune");
            String address = rs.getString("address");
            String streetNumber = rs.getString("streetNumber");
            LocalDateTime startDate = rs.getDate("expoStartDate").toLocalDate().atStartOfDay();
            LocalDateTime endDate = rs.getDate("expoEndDate").toLocalDate().atStartOfDay();
            closeConnection();
            expoManager = new ExpoManager(expoId, province, comune, address, streetNumber, startDate, endDate);
        }
        closeConnection();
        return expoManager;
    }

    public List<ExhibitArea> getExhibitAreasByExpoId(Integer expoId) throws SQLException {
        List<ExhibitArea> exhibitAreaList = new ArrayList<>();
        this.openConnection();
        String query = "SELECT *\n" +
                "FROM exhibitArea\n" +
                "WHERE expoId=?;";
        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, expoId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Integer exhibitAreaId = rs.getInt("exhibitAreaId");
            exhibitAreaList.add(new FixedExhibitArea(exhibitAreaId, expoId));
        }
        closeConnection();
        return exhibitAreaList;
    }

}
