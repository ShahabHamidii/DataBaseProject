package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/university" +
                    "?useSSL=false" +
                    "&allowPublicKeyRetrieval=true" +
                    "&connectTimeout=3000" +
                    "&socketTimeout=10000" +
                    "&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "shah1383";

    private static Connection connection;

    public static void init() {
        try {
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("DB connected.");
        } catch (SQLException e) {
            System.err.println("DB connection failed: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            init();
        }
        return connection;
    }
}