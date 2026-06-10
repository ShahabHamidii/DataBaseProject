package ui;

import dao.ReportDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportForm extends JFrame {

    private JTable table;

    private DefaultTableModel tableModel;

    private JButton loadButton;

    public ReportForm() {

        setTitle("Enrollment Reports");

        setSize(900, 500);

        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        loadButton =
                new JButton(
                        "Load Reports"
                );

        add(loadButton, BorderLayout.NORTH);

        String[] columns = {

                "Student Name",

                "Course Title",

                "Semester",

                "Year",

                "Grade"
        };

        tableModel =
                new DefaultTableModel(columns, 0);

        table =
                new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        loadButton.addActionListener(
                e -> loadReports()
        );
    }

    private void loadReports() {

        tableModel.setRowCount(0);

        ReportDAO dao =
                new ReportDAO();

        List<Object[]> reports =
                dao.getEnrollmentReport();

        for (Object[] row : reports) {

            tableModel.addRow(row);
        }
    }
}