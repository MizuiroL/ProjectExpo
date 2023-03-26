package jdbc;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    
    private static final String selectExhibitInCurrentDate = ""
    		+ "SELECT exhibitId, exhibitAreaId, exhibitorId, exhibitName, exhibitStartDate, exhibitStartTime, exhibitEndDate, exhibitEndTime\n"
    		+ "FROM exhibit\n"
    		+ "JOIN exhibitArea USING (exhibitAreaId)\n"
    		+ "JOIN expo USING (expoId)\n"
    		+ "WHERE expoId=?\n"
    		+ "AND CURDATE() BETWEEN exhibitStartDate AND exhibitEndDate;";

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
    
    public List<Exhibit> getStartedExhibits(Integer expoId) {
    	List<Exhibit> exhibitList = new ArrayList<>();
    	try {
    		Connection connection = DB.getConnection();
    		PreparedStatement statement = connection.prepareStatement(selectExhibitInCurrentDate);
    		statement.setInt(1, expoId);
    		ResultSet rs = statement.executeQuery();
    		while (rs.next()) {
    			Integer exhibitId = rs.getInt("exhibitId");
    			Integer exhibitAreaId = rs.getInt("exhibitId");
    			Integer exhibitorId = rs.getInt("exhibitorId");
    			String exhibitName = rs.getString("exhibitName");
    			LocalDateTime exhibitStartDate = LocalDateTime.of(rs.getDate("exhibitStartDate").toLocalDate(), rs.getTime("exhibitStartTime").toLocalTime());
                LocalDateTime exhibitEndDate = LocalDateTime.of(rs.getDate("exhibitEndDate").toLocalDate(), rs.getTime("exhibitEndTime").toLocalTime());
                Exhibit exhibit = new Exhibit(exhibitId, exhibitAreaId, exhibitorId, exhibitName, exhibitStartDate, exhibitEndDate);
                exhibitList.add(exhibit);
    		}
    	} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    	return exhibitList;
    }

}
