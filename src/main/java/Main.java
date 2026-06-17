import ui.MainDashboard;
import util.UITheme;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        UITheme.init();

        SwingUtilities.invokeLater(() -> {
            MainDashboard dashboard = new MainDashboard();
            dashboard.setVisible(true);
        });
    }
}
