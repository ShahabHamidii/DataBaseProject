package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    private JButton studentButton;

    private JButton courseButton;

    private JButton reportButton;

    private JButton exitButton;

    private JButton enrollmentButton;

    private JButton dashboardButton;

    private JButton instructorButton;

    private JButton advisorButton;

    private JButton prerequisiteButton;

    private JButton teachingButton;

    private JButton sectionButton;

    private JButton chartButton;

    public MainMenu() {

        setTitle("University DBMS");

        setSize(800, 700);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new GridLayout(12, 1, 20, 20)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        40,
                        40,
                        40,
                        40
                )
        );

        dashboardButton =
                new JButton("Dashboard");

        studentButton =
                new JButton(
                        "Student Management"
                );

        courseButton =
                new JButton(
                        "Course Management"
                );

        reportButton =
                new JButton(
                        "Reports"
                );

        instructorButton =
                new JButton(
                        "Instructor Management"
                );

        advisorButton =
                new JButton(
                        "Advisor Assignment"
                );

        enrollmentButton =
                new JButton(
                        "Enrollment System"
                );

        prerequisiteButton =
                new JButton(
                        "Prerequisite Management"
                );

        teachingButton =
                new JButton(
                        "Teaching Assignments"
                );

        sectionButton =
                new JButton(
                        "Section Management"
                );

        chartButton =
                new JButton(
                        "Statistics Charts"
                );


        exitButton =
                new JButton(
                        "Exit"
                );


        panel.add(enrollmentButton);

        panel.add(studentButton);

        panel.add(courseButton);

        panel.add(reportButton);

        panel.add(exitButton);

        panel.add(dashboardButton);

        panel.add(instructorButton);

        panel.add(advisorButton);

        panel.add(prerequisiteButton);

        panel.add(teachingButton);

        panel.add(sectionButton);

        panel.add(chartButton);

        add(panel);

        // Actions

        dashboardButton.addActionListener(
                e -> new DashboardForm()
        );

        studentButton.addActionListener(
                e -> new StudentForm()
        );

        courseButton.addActionListener(
                e -> new CourseForm()
        );

        reportButton.addActionListener(
                e -> new ReportForm()
        );

        enrollmentButton.addActionListener(
                e -> new EnrollmentForm()
        );

        exitButton.addActionListener(
                e -> System.exit(0)
        );

        instructorButton.addActionListener(
                e -> new InstructorForm()
        );

        advisorButton.addActionListener(
                e -> new AdvisorForm()
        );

        prerequisiteButton.addActionListener(
                e -> new PrerequisiteForm()
        );

        teachingButton.addActionListener(
                e -> new TeachingAssignmentForm()
        );

        sectionButton.addActionListener(
                e -> new SectionForm()
        );

        chartButton.addActionListener(
                e -> new DashboardChartForm()
        );
    }
}