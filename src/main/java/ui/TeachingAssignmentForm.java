package ui;

import dao.CourseDAO;
import dao.InstructorDAO;
import dao.TeachingAssignmentDAO;
import model.Course;
import model.Instructor;
import model.TeachingAssignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TeachingAssignmentForm extends JFrame {

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

    public TeachingAssignmentForm() {

        setTitle("Teaching Assignments");

        setSize(900, 600);

        setLocationRelativeTo(null);

        initComponents();

        loadData();

        loadAssignments();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        JPanel panel =
                new JPanel(
                        new GridLayout(8, 2, 10, 10)
                );

        instructorCombo =
                new JComboBox<>();

        courseCombo =
                new JComboBox<>();

        sectionField =
                new JTextField();

        semesterField =
                new JTextField();

        yearField =
                new JTextField();

        panel.add(new JLabel("Instructor"));
        panel.add(instructorCombo);

        panel.add(new JLabel("Course"));
        panel.add(courseCombo);

        panel.add(new JLabel("Section"));
        panel.add(sectionField);

        panel.add(new JLabel("Semester"));
        panel.add(semesterField);

        panel.add(new JLabel("Year"));
        panel.add(yearField);

        assignButton =
                new JButton("Assign");

        loadButton =
                new JButton("Load");

        deleteButton =
                new JButton("Delete");

        searchButton =
                new JButton("Search");

        searchField =
                new JTextField();

        panel.add(assignButton);
        panel.add(loadButton);
        panel.add(deleteButton);
        panel.add(searchButton);

        add(panel, BorderLayout.NORTH);

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout()
                );

        searchPanel.add(
                new JLabel("Instructor ID"),
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        add(
                searchPanel,
                BorderLayout.SOUTH
        );

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Instructor ID",
                                "Course",
                                "Section",
                                "Semester",
                                "Year"
                        },
                        0
                );

        table =
                new JTable(tableModel);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        assignButton.addActionListener(
                e -> assignInstructor()
        );

        loadButton.addActionListener(
                e -> loadAssignments()
        );

        deleteButton.addActionListener(
                e -> deleteAssignment()
        );

        searchButton.addActionListener(
                e -> searchAssignments()
        );
    }

    private void loadData() {

        for (
                Instructor instructor :
                new InstructorDAO()
                        .getAllInstructors()
        ) {

            instructorCombo.addItem(
                    instructor
            );
        }

        for (
                Course course :
                new CourseDAO()
                        .getAllCourses()
        ) {

            courseCombo.addItem(
                    course
            );
        }
    }

    private void assignInstructor() {

        Instructor instructor =
                (Instructor)
                        instructorCombo.getSelectedItem();

        Course course =
                (Course)
                        courseCombo.getSelectedItem();

        TeachingAssignmentDAO dao =
                new TeachingAssignmentDAO();

        if (
                dao.exists(
                        instructor.getId(),
                        course.getCourseId(),
                        sectionField.getText(),
                        semesterField.getText(),
                        Integer.parseInt(
                                yearField.getText()
                        )
                )
        ) {
            JOptionPane.showMessageDialog(
                    this,
                    "Assignment already exists"
            );
            return;
        }

        boolean result =
                new TeachingAssignmentDAO()
                        .assignInstructor(
                                instructor.getId(),
                                course.getCourseId(),
                                sectionField.getText(),
                                semesterField.getText(),
                                Integer.parseInt(
                                        yearField.getText()
                                )
                        );

        if (result) {

            loadAssignments();

            clearFields();

        }
    }

    private void loadAssignments() {

        tableModel.setRowCount(0);

        for (
                TeachingAssignment assignment :
                new TeachingAssignmentDAO()
                        .getAllAssignments()
        ) {

            tableModel.addRow(
                    new Object[]{
                            assignment.getInstructorId(),
                            assignment.getCourseId(),
                            assignment.getSecId(),
                            assignment.getSemester(),
                            assignment.getYear()
                    }
            );
        }
    }

    private void deleteAssignment() {

        int row =
                table.getSelectedRow();

        if(row == -1){

            return;
        }

        boolean deleted =
                new TeachingAssignmentDAO()
                        .deleteAssignment(

                                Integer.parseInt(
                                        tableModel.getValueAt(
                                                row,
                                                0
                                        ).toString()
                                ),

                                tableModel.getValueAt(
                                        row,
                                        1
                                ).toString(),

                                tableModel.getValueAt(
                                        row,
                                        2
                                ).toString(),

                                tableModel.getValueAt(
                                        row,
                                        3
                                ).toString(),

                                Integer.parseInt(
                                        tableModel.getValueAt(
                                                row,
                                                4
                                        ).toString()
                                )
                        );

        if(deleted){

            loadAssignments();

            clearFields();

        }
    }

    private void searchAssignments() {

        String instructorId =
                searchField.getText();

        if(instructorId.isEmpty()) {

            loadAssignments();

            return;
        }

        tableModel.setRowCount(0);

        for (

                TeachingAssignment assignment :

                new TeachingAssignmentDAO()
                        .getAllAssignments()

        ) {

            if(

                    String.valueOf(
                                    assignment.getInstructorId()
                            )
                            .equals(instructorId)

            ){

                tableModel.addRow(
                        new Object[]{
                                assignment.getInstructorId(),
                                assignment.getCourseId(),
                                assignment.getSecId(),
                                assignment.getSemester(),
                                assignment.getYear()
                        }
                );
            }
        }
    }

    private void clearFields() {

        sectionField.setText("");

        semesterField.setText("");

        yearField.setText("");

    }
}