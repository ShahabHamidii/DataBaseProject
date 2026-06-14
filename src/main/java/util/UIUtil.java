package util;

import javax.swing.*;
import java.awt.*;

public class UIUtil {

    public static void styleButton(
            JButton button
    ) {

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );
    }
}