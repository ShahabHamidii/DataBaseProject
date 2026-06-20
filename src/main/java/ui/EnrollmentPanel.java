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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EnrollmentPanel extends JPanel {

    private JComboBox<Student> studentCombo;
    private JComboBox<Course> courseCombo;
    private JTextField sectionField, semesterField, yearField;
    private JTable table;
    private DefaultTableModel tableModel;
    private final EnrollmentDAO dao = new EnrollmentDAO();

    public EnrollmentPanel() {
        initComponents();
        loadStudents();
        loadCourses();
        loadEnrollments();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 16));
        setBackground(UITheme.CONTENT_BG);
        setBorder(new EmptyBorder(24, 28, 24, 28));

        add(UITheme.createPageHeader("Enrollment",
                "Register or remove students from course sections"), BorderLayout.NORTH);

        studentCombo  = new JComboBox<>();
        courseCombo   = new JComboBox<>();
        sectionField  = new JTextField();
        semesterField = new JTextField();
        yearField     = new JTextField();

        UITheme.styleComboBox(studentCombo);
        UITheme.styleComboBox(courseCombo);
        UITheme.styleTextField(sectionField);
        UITheme.styleTextField(semesterField);
        UITheme.styleTextField(yearField);

        JPanel formGrid = UITheme.createFormGrid(5, 2,
                UITheme.createFieldLabel("Student"),    studentCombo,
                UITheme.createFieldLabel("Course"),     courseCombo,
                UITheme.createFieldLabel("Section ID"), sectionField,
                UITheme.createFieldLabel("Semester"),   semesterField,
                UITheme.createFieldLabel("Year"),       yearField
        );

        JButton enrollBtn   = UIUtil.createButton("Enroll",   UIUtil.ButtonStyle.SUCCESS);
        JButton unenrollBtn = UIUtil.createButton("Unenroll", UIUtil.ButtonStyle.DANGER);
        JButton refreshBtn  = UIUtil.createButton("Refresh",  UIUtil.ButtonStyle.SECONDARY);

        JPanel leftPanel = UITheme.createCard("Enrollment Form",
                UITheme.createFormGrid(2, 1, formGrid,
                        UITheme.createButtonBar(enrollBtn, unenrollBtn, refreshBtn)));

        tableModel = new DefaultTableModel(
                new String[]{"Student", "Course", "Semester", "Year", "Grade"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                leftPanel,
                UITheme.createCard("Current Enrollments", UITheme.createTableScroll(table))
        );
        split.setDividerLocation(380);
        split.setOpaque(false);
        split.setBorder(null);

        add(split, BorderLayout.CENTER);

        enrollBtn.addActionListener(e -> enrollStudent());
        unenrollBtn.addActionListener(e -> unenrollStudent());
        refreshBtn.addActionListener(e -> loadEnrollments());

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());
    }

    private void enrollStudent() {
        if (!validateForm()) return;

        Student student = (Student) studentCombo.getSelectedItem();
        Course  course  = (Course)  courseCombo.getSelectedItem();

        boolean ok = dao.enrollStudent(
                student.getId(),
                course.getCourseId(),
                sectionField.getText().trim(),
                semesterField.getText().trim(),
                Integer.parseInt(yearField.getText().trim())
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Enrollment successful!");
            clearFields();
            loadEnrollments();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Enrollment failed.\nStudent may already be enrolled in this section.");
        }
    }

    private void unenrollStudent() {
        if (!validateForm()) return;

        Student student = (Student) studentCombo.getSelectedItem();
        Course  course  = (Course)  courseCombo.getSelectedItem();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Remove " + student.getName() + " from " + course.getTitle() + "?",
                "Confirm Unenroll", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = dao.unEnrollStudent(
                student.getId(),
                course.getCourseId(),
                sectionField.getText().trim(),
                semesterField.getText().trim(),
                Integer.parseInt(yearField.getText().trim())
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Student unenrolled successfully.");
            clearFields();
            loadEnrollments();
        } else {
            JOptionPane.showMessageDialog(this, "Unenroll failed. Record not found.");
        }
    }

    private void loadEnrollments() {
        tableModel.setRowCount(0);
        for (Object[] row : dao.getAllEnrollments()) {
            tableModel.addRow(row);
        }
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String studentName = tableModel.getValueAt(row, 0).toString();
        for (int i = 0; i < studentCombo.getItemCount(); i++) {
            if (studentCombo.getItemAt(i).getName().equals(studentName)) {
                studentCombo.setSelectedIndex(i);
                break;
            }
        }

        String courseTitle = tableModel.getValueAt(row, 1).toString();
        for (int i = 0; i < courseCombo.getItemCount(); i++) {
            if (courseCombo.getItemAt(i).getTitle().equals(courseTitle)) {
                courseCombo.setSelectedIndex(i);
                break;
            }
        }

        semesterField.setText(tableModel.getValueAt(row, 2).toString());
        yearField.setText(tableModel.getValueAt(row, 3).toString());
    }

    private boolean validateForm() {
        if (studentCombo.getSelectedItem() == null ||
                courseCombo.getSelectedItem()  == null) {
            JOptionPane.showMessageDialog(this, "Please select a student and course.");
            return false;
        }
        if (ValidationUtil.isEmpty(sectionField.getText())  ||
                ValidationUtil.isEmpty(semesterField.getText()) ||
                ValidationUtil.isEmpty(yearField.getText())) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return false;
        }
        if (!ValidationUtil.isValidSemester(semesterField.getText())) {
            JOptionPane.showMessageDialog(this, "Semester must be Fall, Spring, or Summer.");
            return false;
        }
        if (!ValidationUtil.isValidYear(yearField.getText())) {
            JOptionPane.showMessageDialog(this, "Enter a valid year (2000–2100).");
            return false;
        }
        return true;
    }

    private void loadStudents() {
        studentCombo.removeAllItems();
        for (Student s : new StudentDAO().getAllStudents())
            studentCombo.addItem(s);
    }

    private void loadCourses() {
        courseCombo.removeAllItems();
        for (Course c : new CourseDAO().getAllCourses())
            courseCombo.addItem(c);
    }

    private void clearFields() {
        sectionField.setText("");
        semesterField.setText("");
        yearField.setText("");
        table.clearSelection();
    }
}