package jdbc;

import model.Expo;
import model.ExpoManager;
import model.Visitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class VisitorDataAccess  extends DataAccess {
    public Visitor getVisitorByFiscalCode(String fiscalCode) throws SQLException {
        Visitor visitor = null;
        this.openConnection();
        String query = "SELECT *\n" +
                "FROM visitor\n" +
                "WHERE fiscalCode=?;";
        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, fiscalCode);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            //Integer expoId = rs.getInt("expoId");
            Integer expoId = rs.getInt("expoId");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String email = rs.getString("email");
            closeConnection();
            visitor = new Visitor(fiscalCode, expoId, name, surname, email);
        }
        closeConnection();
        return visitor;
    }
}
