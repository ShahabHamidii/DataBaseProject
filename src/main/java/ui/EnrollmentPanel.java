package ui;

import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.StudentDAO;
import model.Course;
import model.Student;
import util.UITheme;
import util.UIUtil;
import util.ValidationUtil;

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

        studentCombo = new JComboBox<>();
        courseCombo = new JComboBox<>();
        sectionField = new JTextField();
        semesterField = new JTextField();
        yearField = new JTextField();

        UITheme.styleComboBox(studentCombo);
        UITheme.styleComboBox(courseCombo);
        UITheme.styleTextField(sectionField);
        UITheme.styleTextField(semesterField);
        UITheme.styleTextField(yearField);

        JPanel formGrid = UITheme.createFormGrid(5, 2,
                UITheme.createFieldLabel("Student"), studentCombo,
                UITheme.createFieldLabel("Course"), courseCombo,
                UITheme.createFieldLabel("Section ID"), sectionField,
                UITheme.createFieldLabel("Semester"), semesterField,
                UITheme.createFieldLabel("Year"), yearField
        );

        enrollButton = UIUtil.createButton("Enroll Student", UIUtil.ButtonStyle.PRIMARY);

        JPanel leftPanel = UITheme.createCard("New Enrollment",
                UITheme.createFormGrid(2, 1, formGrid,
                        UITheme.createButtonBar(enrollButton)));

        setLayout(new BorderLayout());
        setBackground(UITheme.CONTENT_BG);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        add(UITheme.createPageHeader("Enrollment",
                "Register students for course sections"), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(leftPanel);
        add(center, BorderLayout.CENTER);

        enrollButton.addActionListener(e -> enrollStudent());
    }

    private void enrollStudent() {

        if (studentCombo.getSelectedItem() == null || courseCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a student and course");
            return;
        }

        if (ValidationUtil.isEmpty(sectionField.getText())
                || ValidationUtil.isEmpty(semesterField.getText())
                || ValidationUtil.isEmpty(yearField.getText())) {
            JOptionPane.showMessageDialog(this, "All fields are required");
            return;
        }

        if (!ValidationUtil.isValidSemester(semesterField.getText())) {
            JOptionPane.showMessageDialog(this, "Semester must be Fall, Spring, or Summer");
            return;
        }

        if (!ValidationUtil.isValidYear(yearField.getText())) {
            JOptionPane.showMessageDialog(this, "Enter a valid year (2000–2100)");
            return;
        }

        try {
            Student student = (Student) studentCombo.getSelectedItem();
            Course course = (Course) courseCombo.getSelectedItem();

            boolean enrolled = new EnrollmentDAO().enrollStudent(
                    student.getId(),
                    course.getCourseId(),
                    sectionField.getText(),
                    semesterField.getText(),
                    Integer.parseInt(yearField.getText())
            );

            if (enrolled) {
                JOptionPane.showMessageDialog(this, "Enrollment successful!");
                sectionField.setText("");
                semesterField.setText("");
                yearField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Enrollment failed");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadStudents() {
        studentCombo.removeAllItems();
        for (Student student : new StudentDAO().getAllStudents()) {
            studentCombo.addItem(student);
        }
    }

    private void loadCourses() {
        courseCombo.removeAllItems();
        for (Course course : new CourseDAO().getAllCourses()) {
            courseCombo.addItem(course);
        }
    }
}
