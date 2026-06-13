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

    private JButton departmentStatsButton;

    private JButton teachingReportButton;

    private JButton enrollmentStatsButton;

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

        teachingReportButton =
                new JButton(
                        "Teaching Report"
                );

        enrollmentStatsButton =
                new JButton(
                        "Course Enrollment Stats"
                );

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(loadButton);

        buttonPanel.add(teachingReportButton);

        buttonPanel.add(enrollmentStatsButton);

        add(buttonPanel, BorderLayout.NORTH);

        departmentStatsButton =
                new JButton(
                        "Students Per Department"
                );

        add(
                departmentStatsButton,
                BorderLayout.SOUTH
        );

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

        departmentStatsButton.addActionListener(
                e -> loadDepartmentStats()
        );

        teachingReportButton.addActionListener(
                e -> loadTeachingReport()
        );

        enrollmentStatsButton.addActionListener(
                e -> loadEnrollmentStats()
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

    private void loadDepartmentStats() {

        tableModel.setRowCount(0);

        tableModel.setColumnCount(0);

        tableModel.addColumn("Department");

        tableModel.addColumn("Students");

        ReportDAO dao =
                new ReportDAO();

        List<Object[]> rows =
                dao.getStudentsPerDepartment();

        for (Object[] row : rows) {

            tableModel.addRow(row);
        }
    }
    private void loadTeachingReport() {

        tableModel.setRowCount(0);

        tableModel.setColumnCount(0);

        tableModel.addColumn("Instructor");

        tableModel.addColumn("Course");

        tableModel.addColumn("Semester");

        tableModel.addColumn("Year");

        for (

                Object[] row :

                new ReportDAO()
                        .getInstructorTeachingReport()

        ) {

            tableModel.addRow(row);
        }
    }
    private void loadEnrollmentStats() {

        tableModel.setRowCount(0);

        tableModel.setColumnCount(0);

        tableModel.addColumn("Course");

        tableModel.addColumn("Enrollments");

        for (

                Object[] row :

                new ReportDAO()
                        .getCourseEnrollmentReport()

        ) {

            tableModel.addRow(row);
        }
    }
}