package ui;

import dao.ReportDAO;
import util.CSVExporter;
import util.UITheme;
import util.UIUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public ReportPanel() {
        initComponents();
        loadReports();
    }

    private void initComponents() {

        setLayout(new BorderLayout(0, 0));
        setBackground(UITheme.CONTENT_BG);
        setBorder(new EmptyBorder(24, 28, 24, 28));

        add(UITheme.createPageHeader("Reports",
                "Generate and export university analytics"), BorderLayout.NORTH);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        toolbar.setOpaque(false);
        toolbar.setBorder(new EmptyBorder(0, 0, 16, 0));

        toolbar.add(createReportButton("All Enrollments", this::loadReports));
        toolbar.add(createReportButton("By Department", this::loadDepartmentStats));
        toolbar.add(createReportButton("Teaching Report", this::loadTeachingReport));
        toolbar.add(createReportButton("Enrollment Stats", this::loadEnrollmentStats));
        toolbar.add(createReportButton("Top Students", this::loadTopStudents));
        toolbar.add(createReportButton("Advisor Report", this::loadAdvisorReport));

        JButton exportButton = UIUtil.createButton("Export CSV", UIUtil.ButtonStyle.SUCCESS);
        exportButton.addActionListener(e -> exportReport());
        toolbar.add(exportButton);

        tableModel = new DefaultTableModel(
                new String[]{"Student Name", "Course Title", "Semester", "Year", "Grade"}, 0);
        table = new JTable(tableModel);

        JPanel center = new JPanel(new BorderLayout(0, 0));
        center.setOpaque(false);
        center.add(toolbar, BorderLayout.NORTH);
        center.add(UITheme.createCard("Report Data", UITheme.createTableScroll(table)), BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private JButton createReportButton(String label, Runnable action) {
        JButton btn = UIUtil.createButton(label, UIUtil.ButtonStyle.GHOST);
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private void loadReports() {
        resetColumns("Student Name", "Course Title", "Semester", "Year", "Grade");
        for (Object[] row : new ReportDAO().getEnrollmentReport()) {
            tableModel.addRow(row);
        }
    }

    private void loadDepartmentStats() {
        resetColumns("Department", "Students");
        for (Object[] row : new ReportDAO().getStudentsPerDepartment()) {
            tableModel.addRow(row);
        }
    }

    private void loadTeachingReport() {
        resetColumns("Instructor", "Course", "Semester", "Year");
        for (Object[] row : new ReportDAO().getInstructorTeachingReport()) {
            tableModel.addRow(row);
        }
    }

    private void loadEnrollmentStats() {
        resetColumns("Course", "Enrollments");
        for (Object[] row : new ReportDAO().getCourseEnrollmentReport()) {
            tableModel.addRow(row);
        }
    }

    private void loadTopStudents() {
        resetColumns("ID", "Name", "Department", "Credits");
        for (Object[] row : new ReportDAO().getTopStudentsByCredits()) {
            tableModel.addRow(row);
        }
    }

    private void loadAdvisorReport() {
        resetColumns("Student", "Advisor", "Department");
        for (Object[] row : new ReportDAO().getAdvisorAssignments()) {
            tableModel.addRow(row);
        }
    }

    private void resetColumns(String... columns) {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        for (String col : columns) {
            tableModel.addColumn(col);
        }
    }

    private void exportReport() {

        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        String path = chooser.getSelectedFile().getAbsolutePath();
        if (!path.endsWith(".csv")) path += ".csv";

        CSVExporter.exportTable(table, path);
        JOptionPane.showMessageDialog(this, "CSV exported successfully");
    }
}
