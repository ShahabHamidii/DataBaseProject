package ui;

import dao.ReportDAO;
import util.CSVExporter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportPanel extends JPanel {

    private JTable table;

    private DefaultTableModel tableModel;

    private JButton loadButton;

    private JButton departmentStatsButton;

    private JButton teachingReportButton;

    private JButton enrollmentStatsButton;

    private JButton exportButton;

    private JButton topStudentsButton;

    private JButton advisorReportButton;

    public ReportPanel() {

        initComponents();

        loadReports();
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

        exportButton =
                new JButton(
                        "Export CSV"
                );

        topStudentsButton =
                new JButton(
                        "Top Students"
                );

        advisorReportButton =
                new JButton(
                        "Advisor Report"
                );

        departmentStatsButton =
                new JButton(
                        "Students Per Department"
                );

        add(
                departmentStatsButton,
                BorderLayout.SOUTH
        );

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(loadButton);

        buttonPanel.add(teachingReportButton);

        buttonPanel.add(enrollmentStatsButton);

        buttonPanel.add(exportButton);

        buttonPanel.add(topStudentsButton);

        buttonPanel.add(advisorReportButton);

        add(buttonPanel, BorderLayout.NORTH);


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

        table.setRowHeight(30);

        table.setAutoCreateRowSorter(true);

        table.getTableHeader()
                .setReorderingAllowed(false);

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

        exportButton.addActionListener(
                e -> exportReport()
        );

        topStudentsButton.addActionListener(
                e -> loadTopStudents()
        );

        advisorReportButton.addActionListener(
                e -> loadAdvisorReport()
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

    private void exportReport() {

        JFileChooser chooser =
                new JFileChooser();

        int result =
                chooser.showSaveDialog(this);

        if(result != JFileChooser.APPROVE_OPTION) {

            return;
        }

        String path =
                chooser
                        .getSelectedFile()
                        .getAbsolutePath();

        if(!path.endsWith(".csv")) {

            path += ".csv";
        }

        CSVExporter.exportTable(
                table,
                path
        );

        JOptionPane.showMessageDialog(

                this,

                "CSV exported successfully."
        );
    }
    private void loadTopStudents() {

        tableModel.setRowCount(0);

        tableModel.setColumnCount(0);

        tableModel.addColumn("ID");

        tableModel.addColumn("Name");

        tableModel.addColumn("Department");

        tableModel.addColumn("Credits");

        for(

                Object[] row :

                new ReportDAO()
                        .getTopStudentsByCredits()

        ){

            tableModel.addRow(row);
        }
    }

    private void loadAdvisorReport() {

        tableModel.setRowCount(0);

        tableModel.setColumnCount(0);

        tableModel.addColumn("Student");

        tableModel.addColumn("Advisor");

        tableModel.addColumn("Department");

        for(

                Object[] row :

                new ReportDAO()
                        .getAdvisorAssignments()

        ){

            tableModel.addRow(row);
        }
    }
}