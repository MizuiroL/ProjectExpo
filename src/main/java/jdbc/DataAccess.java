package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataAccess {
    protected Connection connection;

    public DataAccess() {
        this.connection = null;
    }

    public DataAccess(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void openConnection() throws SQLException {
        connection = DriverManager.getConnection(DBData.URL, DBData.USERNAME, DBData.PASSWORD);
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
