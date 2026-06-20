import database.DBConnection;
import ui.LoginFrame;
import util.UITheme;
import javax.swing.*;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {

        new Thread(() -> {
            try {
                DBConnection.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, "db-init").start();

        UITheme.init();

        SwingUtilities.invokeLater(LoginFrame::new);
    }
}