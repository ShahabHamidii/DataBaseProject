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

    public TeachingAssignmentForm() {

        setTitle("Teaching Assignments");

        setSize(900,600);

        setLocationRelativeTo(null);

        initComponents();

        loadData();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        JPanel panel =
                new JPanel(
                        new GridLayout(6,2,10,10)
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

        panel.add(assignButton);
        panel.add(loadButton);

        add(panel, BorderLayout.NORTH);

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

        if(result) {

            loadAssignments();
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
}