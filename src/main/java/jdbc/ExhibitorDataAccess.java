package jdbc;

import model.Exhibitor;
import model.Expo;
import model.ExpoManager;

import java.sql.*;
import java.time.LocalDateTime;

public class ExhibitorDataAccess  extends DataAccess {
    public Exhibitor getExhibitorById(Integer exhibitorId) throws SQLException {
        Exhibitor exhibitor = null;
        this.openConnection();
        String query = "SELECT *\n" +
                "FROM exhibitor\n" +
                "WHERE exhibitorId=?;";
        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setInt(1, exhibitorId);
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            //Integer expoId = rs.getInt("expoId");
            String exhibitorName = rs.getString("exhibitorName");
            closeConnection();
            exhibitor = new Exhibitor(exhibitorId, exhibitorName);
        }
        closeConnection();
        return exhibitor;
    }
}
