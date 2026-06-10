package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    private JButton studentButton;

    private JButton courseButton;

    private JButton reportButton;

    private JButton exitButton;

    private JButton enrollmentButton;

    public MainMenu() {

        setTitle("University DBMS");

        setSize(500, 400);

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
                new GridLayout(5, 1, 20, 20)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        40,
                        40,
                        40,
                        40
                )
        );

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

        exitButton =
                new JButton(
                        "Exit"
                );

        panel.add(studentButton);

        panel.add(courseButton);

        panel.add(reportButton);

        panel.add(exitButton);

        add(panel);

        // Actions
        studentButton.addActionListener(
                e -> new StudentForm()
        );

        courseButton.addActionListener(
                e -> new CourseForm()
        );

        reportButton.addActionListener(
                e -> new ReportForm()
        );
        enrollmentButton =
                new JButton(
                        "Enrollment System"
                );

        panel.add(enrollmentButton);

        enrollmentButton.addActionListener(
                e -> new EnrollmentForm()
        );

        exitButton.addActionListener(
                e -> System.exit(0)
        );
    }
}