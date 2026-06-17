package ui;

import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.StudentDAO;
import model.Course;
import model.Student;
import javax.swing.*;
import java.awt.*;

public class EnrollmentPanel extends JPanel {

    private JComboBox<Student> studentCombo;

    private JComboBox<Course> courseCombo;

    private JTextField sectionField;

    private JTextField semesterField;

    private JTextField yearField;

    private JButton enrollButton;

    public EnrollmentPanel() {

        initComponents();

        loadStudents();

        loadCourses();

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

        studentCombo =
                new JComboBox<>();

        panel.add(studentCombo);
        // Course ID
        panel.add(
                new JLabel("Course ID:")
        );

        courseCombo =
                new JComboBox<>();

        panel.add(courseCombo);

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

            Student student =
                    (Student) studentCombo.getSelectedItem();

            int studentId =
                    student.getId();

            Course course =
                    (Course) courseCombo.getSelectedItem();

            String courseId =
                    course.getCourseId();

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

    private void loadStudents() {

        StudentDAO dao =
                new StudentDAO();

        for (Student student :
                dao.getAllStudents()) {

            studentCombo.addItem(student);
        }
    }
    private void loadCourses() {

        CourseDAO dao =
                new CourseDAO();

        for (Course course :
                dao.getAllCourses()) {

            courseCombo.addItem(course);
        }
    }
}