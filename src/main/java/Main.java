import database.DBConnection;
import ui.LoginFrame;
import util.UITheme;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        new Thread(() -> DBConnection.init(), "db-init").start();

        UITheme.init();

        SwingUtilities.invokeLater(LoginFrame::new);
    }
}