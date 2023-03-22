package jdbc;

import model.Visitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitorDataAccess  extends DataAccess {
    private static final String selectVisitorByFiscalCode = "" +
            "SELECT *\n" +
            "FROM visitor\n" +
            "WHERE fiscalCode=?;";
    public Visitor getVisitorByFiscalCode(String fiscalCode) {
        Visitor visitor = null;
        try {
            this.openConnection();
            PreparedStatement statement = getConnection().prepareStatement(selectVisitorByFiscalCode);
            statement.setString(1, fiscalCode);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Integer expoId = rs.getInt("expoId");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                visitor = new Visitor(fiscalCode, expoId, name, surname, email);
            }
            this.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return visitor;
    }
}
