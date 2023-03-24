package jdbc;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpoDAO {
    private static final String selectExpoById = "" +
            "SELECT *\n" +
            "FROM expo\n" +
            "WHERE expoId=?;";

    private static final String selectExhibitAreasByExpoId = "" +
            "SELECT *\n" +
            "FROM exhibitArea\n" +
            "WHERE expoId=?;";

    public Expo getExpoById(Integer expoId) {
        ExpoManager expoManager = null;
        try {
            Connection connection = DB.getConnection();

            PreparedStatement statement = connection.prepareStatement(selectExpoById);
            statement.setInt(1, expoId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String province = rs.getString("province");
                String comune = rs.getString("comune");
                String address = rs.getString("address");
                String streetNumber = rs.getString("streetNumber");
                LocalDate startDate = rs.getDate("expoStartDate").toLocalDate();
                LocalDate endDate = rs.getDate("expoEndDate").toLocalDate();
                expoManager = new ExpoManager(expoId, province, comune, address, streetNumber, startDate, endDate);
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return expoManager;
    }

    public List<ExhibitArea> getExhibitAreasByExpoId(Integer expoId) {
        List<ExhibitArea> exhibitAreaList = new ArrayList<>();
        try {
            Connection connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectExhibitAreasByExpoId);
            statement.setInt(1, expoId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Integer exhibitAreaId = rs.getInt("exhibitAreaId");
                exhibitAreaList.add(new FixedExhibitArea(exhibitAreaId, expoId));
            }
            DB.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitAreaList;
    }

}
