package ui;

import dao.CourseDAO;
import dao.PrerequisiteDAO;
import model.Course;
import model.Prerequisite;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PrerequisiteForm extends JFrame {

    private JComboBox<Course> courseCombo;

    private JComboBox<Course> prereqCombo;

    private JButton addButton;

    private JButton loadButton;

    private JTable table;

    private DefaultTableModel tableModel;

    public PrerequisiteForm() {

        setTitle("Prerequisite Management");

        setSize(800,500);

        setLocationRelativeTo(null);

        initComponents();

        loadCourses();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        JPanel panel =
                new JPanel(
                        new GridLayout(2,3,10,10)
                );

        courseCombo =
                new JComboBox<>();

        prereqCombo =
                new JComboBox<>();

        addButton =
                new JButton("Add");

        loadButton =
                new JButton("Load");

        panel.add(
                new JLabel("Course")
        );

        panel.add(
                new JLabel("Prerequisite")
        );

        panel.add(
                new JLabel("")
        );

        panel.add(courseCombo);

        panel.add(prereqCombo);

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(loadButton);

        panel.add(buttonPanel);

        add(panel, BorderLayout.NORTH);

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Course",
                                "Prerequisite"
                        },
                        0
                );

        table =
                new JTable(tableModel);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        addButton.addActionListener(
                e -> addPrerequisite()
        );

        loadButton.addActionListener(
                e -> loadPrerequisites()
        );
    }

    private void loadCourses() {

        for (
                Course course :
                new CourseDAO()
                        .getAllCourses()
        ) {

            courseCombo.addItem(course);
            prereqCombo.addItem(course);
        }
    }

    private void addPrerequisite() {

        Course course =
                (Course)
                        courseCombo.getSelectedItem();

        Course prereq =
                (Course)
                        prereqCombo.getSelectedItem();

        boolean result =
                new PrerequisiteDAO()
                        .addPrerequisite(
                                course.getCourseId(),
                                prereq.getCourseId()
                        );

        if(result) {

            JOptionPane.showMessageDialog(
                    this,
                    "Prerequisite Added"
            );

            loadPrerequisites();
        }
    }

    private void loadPrerequisites() {

        tableModel.setRowCount(0);

        for (
                Prerequisite prerequisite :
                new PrerequisiteDAO()
                        .getAllPrerequisites()
        ) {

            tableModel.addRow(
                    new Object[]{
                            prerequisite.getCourseId(),
                            prerequisite.getPrereqId()
                    }
            );
        }
    }
}