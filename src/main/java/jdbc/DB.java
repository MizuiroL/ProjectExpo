package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/projectexpo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mizuiro";
    
    public static Connection getConnection() throws SQLException {
		Connection connection = null;
		connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		return connection;
	}

	public static void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}

	public static void closeStatement(Statement statement) throws SQLException {
		statement.close();
	}

	public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.close();
	}

	public static void closeResultSet(ResultSet resultSet) throws SQLException {
		resultSet.close();
	}
}
