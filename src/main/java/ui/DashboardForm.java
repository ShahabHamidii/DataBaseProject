package ui;

import dao.StatisticsDAO;

import javax.swing.*;
import java.awt.*;

public class DashboardForm extends JFrame {

    private JLabel studentLabel;

    private JLabel courseLabel;

    private JLabel enrollmentLabel;

    private JLabel departmentLabel;

    public DashboardForm() {

        setTitle("Dashboard");

        setSize(600, 400);

        setLocationRelativeTo(null);

        initComponents();

        loadStatistics();

        setVisible(true);
    }

    private void initComponents() {

        JPanel panel =
                new JPanel(
                        new GridLayout(4, 1, 20, 20)
                );

        studentLabel = new JLabel();

        courseLabel = new JLabel();

        enrollmentLabel = new JLabel();

        departmentLabel = new JLabel();

        studentLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        courseLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        enrollmentLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        departmentLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        panel.add(studentLabel);

        panel.add(courseLabel);

        panel.add(enrollmentLabel);

        panel.add(departmentLabel);

        add(panel);
    }

    private void loadStatistics() {

        StatisticsDAO dao =
                new StatisticsDAO();

        studentLabel.setText(
                "Students: "
                        + dao.getStudentCount()
        );

        courseLabel.setText(
                "Courses: "
                        + dao.getCourseCount()
        );

        enrollmentLabel.setText(
                "Enrollments: "
                        + dao.getEnrollmentCount()
        );

        departmentLabel.setText(
                "Departments: "
                        + dao.getDepartmentCount()
        );
    }
}