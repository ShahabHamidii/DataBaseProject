package ui;

import dao.EnrollmentDAO;

import javax.swing.*;
import java.awt.*;

public class EnrollmentForm extends JFrame {

    private JTextField studentField;

    private JTextField courseField;

    private JTextField sectionField;

    private JTextField semesterField;

    private JTextField yearField;

    private JButton enrollButton;

    public EnrollmentForm() {

        setTitle("Enrollment System");

        setSize(500, 400);

        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        JPanel panel =
                new JPanel(
                        new GridLayout(6, 2, 10, 10)
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // Student ID
        panel.add(
                new JLabel("Student ID:")
        );

        studentField =
                new JTextField();

        panel.add(studentField);

        // Course ID
        panel.add(
                new JLabel("Course ID:")
        );

        courseField =
                new JTextField();

        panel.add(courseField);

        // Section ID
        panel.add(
                new JLabel("Section ID:")
        );

        sectionField =
                new JTextField();

        panel.add(sectionField);

        // Semester
        panel.add(
                new JLabel("Semester:")
        );

        semesterField =
                new JTextField();

        panel.add(semesterField);

        // Year
        panel.add(
                new JLabel("Year:")
        );

        yearField =
                new JTextField();

        panel.add(yearField);

        // Button
        enrollButton =
                new JButton(
                        "Enroll Student"
                );

        panel.add(enrollButton);

        add(panel);

        enrollButton.addActionListener(
                e -> enrollStudent()
        );
    }

    private void enrollStudent() {

        try {

            int studentId =
                    Integer.parseInt(
                            studentField.getText()
                    );

            String courseId =
                    courseField.getText();

            String secId =
                    sectionField.getText();

            String semester =
                    semesterField.getText();

            int year =
                    Integer.parseInt(
                            yearField.getText()
                    );

            EnrollmentDAO dao =
                    new EnrollmentDAO();

            boolean enrolled =
                    dao.enrollStudent(
                            studentId,
                            courseId,
                            secId,
                            semester,
                            year
                    );

            if (enrolled) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enrollment Successful!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Enrollment Failed!"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}