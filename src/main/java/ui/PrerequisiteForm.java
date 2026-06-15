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

    private JButton deleteButton;

    private JButton searchButton;

    private JTextField searchField;

    public PrerequisiteForm() {

        setTitle("Prerequisite Management");

        setSize(800,500);

        setLocationRelativeTo(null);

        initComponents();

        loadCourses();

        loadPrerequisites();

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

        deleteButton =
                new JButton("Delete");

        searchButton =
                new JButton("Search");

        searchField =
                new JTextField();

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
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel);

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout()
                );

        searchPanel.add(
                new JLabel("Course ID"),
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );
        searchPanel.add(
                searchButton,
                BorderLayout.EAST
        );

        add(searchPanel, BorderLayout.SOUTH);

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

        deleteButton.addActionListener(
                e -> deletePrerequisite()
        );

        searchButton.addActionListener(
                e -> searchPrerequisite()
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
        PrerequisiteDAO dao =
                new PrerequisiteDAO();

        if(
                dao.exists(
                        course.getCourseId(),
                        prereq.getCourseId()
                )
        ){
            JOptionPane.showMessageDialog(
                    this,
                    "Already exists"
            );
            return;
        }
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
    private void deletePrerequisite() {

        int row =
                table.getSelectedRow();

        if(row == -1){

            return;
        }

        String courseId =
                tableModel.getValueAt(
                        row,
                        0
                ).toString();

        String prereqId =
                tableModel.getValueAt(
                        row,
                        1
                ).toString();

        boolean deleted =
                new PrerequisiteDAO()
                        .deletePrerequisite(
                                courseId,
                                prereqId
                        );

        if(deleted){

            loadPrerequisites();
        }
    }
    private void searchPrerequisite() {

        tableModel.setRowCount(0);

        for(

                Prerequisite p :

                new PrerequisiteDAO()
                        .searchByCourse(
                                searchField.getText()
                        )

        ){

            tableModel.addRow(
                    new Object[]{
                            p.getCourseId(),
                            p.getPrereqId()
                    }
            );
        }
    }
}