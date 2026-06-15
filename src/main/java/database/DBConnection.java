package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/university";

    private static final String USER = "root";
    private static final String PASSWORD = "shah1383";

    public static Connection getConnection() throws SQLException {
        System.out.println("Connecting to DB...");

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

}