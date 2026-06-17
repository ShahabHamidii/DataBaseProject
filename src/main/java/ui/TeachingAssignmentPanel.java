package ui;

import dao.CourseDAO;
import dao.InstructorDAO;
import dao.TeachingAssignmentDAO;
import model.Course;
import model.Instructor;
import model.TeachingAssignment;
import util.UITheme;
import util.UIUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TeachingAssignmentPanel extends JPanel {

    private JComboBox<Instructor> instructorCombo;
    private JComboBox<Course> courseCombo;
    private JTextField sectionField;
    private JTextField semesterField;
    private JTextField yearField;
    private JButton assignButton;
    private JButton loadButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton deleteButton;
    private JTextField searchField;
    private JButton searchButton;

    public TeachingAssignmentPanel() {
        initComponents();
        loadData();
        loadAssignments();
    }

    private void initComponents() {

        instructorCombo = new JComboBox<>();
        courseCombo = new JComboBox<>();
        sectionField = new JTextField();
        semesterField = new JTextField();
        yearField = new JTextField();
        searchField = new JTextField();

        UITheme.styleComboBox(instructorCombo);
        UITheme.styleComboBox(courseCombo);
        UITheme.styleTextField(sectionField);
        UITheme.styleTextField(semesterField);
        UITheme.styleTextField(yearField);
        UITheme.styleTextField(searchField);

        JPanel formGrid = UITheme.createFormGrid(5, 2,
                UITheme.createFieldLabel("Instructor"), instructorCombo,
                UITheme.createFieldLabel("Course"), courseCombo,
                UITheme.createFieldLabel("Section"), sectionField,
                UITheme.createFieldLabel("Semester"), semesterField,
                UITheme.createFieldLabel("Year"), yearField
        );

        assignButton = UIUtil.createButton("Assign", UIUtil.ButtonStyle.PRIMARY);
        loadButton = UIUtil.createButton("Refresh", UIUtil.ButtonStyle.SECONDARY);
        deleteButton = UIUtil.createButton("Delete", UIUtil.ButtonStyle.DANGER);
        searchButton = UIUtil.createButton("Search", UIUtil.ButtonStyle.GHOST);

        JPanel searchRow = new JPanel(new BorderLayout(8, 0));
        searchRow.setOpaque(false);
        searchRow.add(UITheme.createFieldLabel("Filter by Instructor ID"), BorderLayout.WEST);
        searchRow.add(searchField, BorderLayout.CENTER);

        JPanel leftContent = new JPanel();
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));
        leftContent.setOpaque(false);
        leftContent.add(formGrid);
        leftContent.add(Box.createVerticalStrut(12));
        leftContent.add(searchRow);
        leftContent.add(Box.createVerticalStrut(12));
        leftContent.add(UITheme.createButtonBar(assignButton, deleteButton, loadButton, searchButton));

        JPanel leftPanel = UITheme.createCard("Teaching Assignment", leftContent);

        tableModel = new DefaultTableModel(
                new String[]{"Instructor ID", "Course", "Section", "Semester", "Year"}, 0);
        table = new JTable(tableModel);

        UITheme.wrapContent(this, "Teaching Assignments",
                "Assign instructors to course sections",
                leftPanel, table);

        assignButton.addActionListener(e -> assignInstructor());
        loadButton.addActionListener(e -> loadAssignments());
        deleteButton.addActionListener(e -> deleteAssignment());
        searchButton.addActionListener(e -> searchAssignments());
    }

    private void loadData() {
        for (Instructor instructor : new InstructorDAO().getAllInstructors()) {
            instructorCombo.addItem(instructor);
        }
        for (Course course : new CourseDAO().getAllCourses()) {
            courseCombo.addItem(course);
        }
    }

    private void assignInstructor() {

        Instructor instructor = (Instructor) instructorCombo.getSelectedItem();
        Course course = (Course) courseCombo.getSelectedItem();

        if (instructor == null || course == null) return;

        TeachingAssignmentDAO dao = new TeachingAssignmentDAO();

        int year = Integer.parseInt(yearField.getText());

        if (dao.exists(instructor.getId(), course.getCourseId(),
                sectionField.getText(), semesterField.getText(), year)) {
            JOptionPane.showMessageDialog(this, "Assignment already exists");
            return;
        }

        if (dao.assignInstructor(instructor.getId(), course.getCourseId(),
                sectionField.getText(), semesterField.getText(), year)) {
            loadAssignments();
            clearFields();
        }
    }

    private void loadAssignments() {

        tableModel.setRowCount(0);

        for (TeachingAssignment a : new TeachingAssignmentDAO().getAllAssignments()) {
            tableModel.addRow(new Object[]{
                    a.getInstructorId(), a.getCourseId(),
                    a.getSecId(), a.getSemester(), a.getYear()
            });
        }
    }

    private void deleteAssignment() {

        int row = table.getSelectedRow();
        if (row == -1) return;

        boolean deleted = new TeachingAssignmentDAO().deleteAssignment(
                Integer.parseInt(tableModel.getValueAt(row, 0).toString()),
                tableModel.getValueAt(row, 1).toString(),
                tableModel.getValueAt(row, 2).toString(),
                tableModel.getValueAt(row, 3).toString(),
                Integer.parseInt(tableModel.getValueAt(row, 4).toString())
        );

        if (deleted) {
            loadAssignments();
            clearFields();
        }
    }

    private void searchAssignments() {

        String instructorId = searchField.getText();

        if (instructorId.isEmpty()) {
            loadAssignments();
            return;
        }

        tableModel.setRowCount(0);

        for (TeachingAssignment a : new TeachingAssignmentDAO().getAllAssignments()) {
            if (String.valueOf(a.getInstructorId()).equals(instructorId)) {
                tableModel.addRow(new Object[]{
                        a.getInstructorId(), a.getCourseId(),
                        a.getSecId(), a.getSemester(), a.getYear()
                });
            }
        }
    }

    private void clearFields() {
        sectionField.setText("");
        semesterField.setText("");
        yearField.setText("");
    }
}
