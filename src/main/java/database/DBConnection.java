package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/university";

    private static final String USER = "root";
    private static final String PASSWORD = "shah1383";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            System.out.println("Connecting to DB...");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }

        return connection;
    }
}