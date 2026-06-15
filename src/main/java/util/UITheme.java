package util;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UITheme {

    public static void styleTable(JTable table){

        table.setRowHeight(36);

        table.setShowGrid(false);

        table.setIntercellSpacing(
                new Dimension(0,0)
        );

        JTableHeader header =
                table.getTableHeader();

        header.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );
    }

    public static void styleTextField(
            JTextField field
    ){
        field.setPreferredSize(
                new Dimension(
                        200,
                        35
                )
        );
    }

    public static void styleFrame(
            JFrame frame
    ){
        frame.getRootPane()
                .setBorder(
                        BorderFactory.createEmptyBorder(
                                10,
                                10,
                                10,
                                10
                        )
                );
    }
}