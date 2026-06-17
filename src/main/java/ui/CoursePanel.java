package ui;

import dao.CourseDAO;
import model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import util.ValidationUtil;
import util.UITheme;
import util.UIUtil;

public class CoursePanel extends JPanel {

    private JTextField idField;

    private JTextField titleField;

    private JTextField deptField;

    private JTextField creditsField;

    private JTextField searchField;

    private JButton addButton;

    private JButton updateButton;

    private JButton deleteButton;

    private JButton loadButton;

    private JButton searchButton;

    private JTable table;

    private DefaultTableModel tableModel;

    public CoursePanel() {

        initComponents();

        loadCourses();
    }

    private void initComponents() {

        idField = new JTextField();
        titleField = new JTextField();
        deptField = new JTextField();
        creditsField = new JTextField();
        searchField = new JTextField();

        UITheme.styleTextField(idField);
        UITheme.styleTextField(titleField);
        UITheme.styleTextField(deptField);
        UITheme.styleTextField(creditsField);
        UITheme.styleTextField(searchField);

        JPanel formGrid = UITheme.createFormGrid(5, 2,
                UITheme.createFieldLabel("Course ID"), idField,
                UITheme.createFieldLabel("Title"), titleField,
                UITheme.createFieldLabel("Department"), deptField,
                UITheme.createFieldLabel("Credits"), creditsField,
                UITheme.createFieldLabel("Search Department"), searchField
        );

        addButton = UIUtil.createButton("Add", UIUtil.ButtonStyle.SUCCESS);
        updateButton = UIUtil.createButton("Update", UIUtil.ButtonStyle.PRIMARY);
        deleteButton = UIUtil.createButton("Delete", UIUtil.ButtonStyle.DANGER);
        loadButton = UIUtil.createButton("Refresh", UIUtil.ButtonStyle.SECONDARY);
        searchButton = UIUtil.createButton("Search", UIUtil.ButtonStyle.GHOST);

        JPanel leftPanel = UITheme.createCard("Course Details",
                UITheme.createFormGrid(2, 1, formGrid,
                        UITheme.createButtonBar(addButton, updateButton, deleteButton, loadButton, searchButton)));

        tableModel = new DefaultTableModel(
                new String[]{"Course ID", "Title", "Department", "Credits"}, 0);
        table = new JTable(tableModel);

        UITheme.wrapContent(this, "Courses",
                "Manage course catalog and filter by department",
                leftPanel, table);

        addButton.addActionListener(e -> addCourse());
        updateButton.addActionListener(e -> updateCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        loadButton.addActionListener(e -> loadCourses());
        searchButton.addActionListener(e -> searchCourses());
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());
    }

    private void addCourse() {

        if (

                ValidationUtil.isEmpty(
                        idField.getText()
                )

                        ||

                        ValidationUtil.isEmpty(
                                titleField.getText()
                        )

                        ||

                        ValidationUtil.isEmpty(
                                deptField.getText()
                        )

                        ||

                        ValidationUtil.isEmpty(
                                creditsField.getText()
                        )

        ) {

            JOptionPane.showMessageDialog(

                    this,

                    "All fields are required."

            );

            return;
        }

        if (

                !ValidationUtil.isInteger(
                        creditsField.getText()
                )

        ) {

            JOptionPane.showMessageDialog(

                    this,

                    "Credits must be numeric."

            );

            return;
        }

        try {

            Course course =
                    new Course(
                            idField.getText(),

                            titleField.getText(),

                            deptField.getText(),

                            Integer.parseInt(
                                    creditsField.getText()
                            )
                    );

            CourseDAO dao =
                    new CourseDAO();

            boolean inserted =
                    dao.addCourse(course);

            if (inserted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Course Added!"
                );

                loadCourses();

                clearFields();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Insert Failed!"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void loadCourses() {

        tableModel.setRowCount(0);

        CourseDAO dao =
                new CourseDAO();

        List<Course> courses =
                dao.getAllCourses();

        for (Course course : courses) {

            Object[] row = {

                    course.getCourseId(),

                    course.getTitle(),

                    course.getDeptName(),

                    course.getCredits()
            };

            tableModel.addRow(row);
        }
    }

    private void updateCourse() {

        if (

                ValidationUtil.isEmpty(
                        idField.getText()
                )

                        ||

                        ValidationUtil.isEmpty(
                                titleField.getText()
                        )

                        ||

                        ValidationUtil.isEmpty(
                                deptField.getText()
                        )

                        ||

                        ValidationUtil.isEmpty(
                                creditsField.getText()
                        )

        ) {

            JOptionPane.showMessageDialog(

                    this,

                    "All fields are required."

            );

            return;
        }

        if (

                !ValidationUtil.isInteger(
                        creditsField.getText()
                )

        ) {

            JOptionPane.showMessageDialog(

                    this,

                    "Credits must be numeric."

            );

            return;
        }

        try {

            Course course =
                    new Course(
                            idField.getText(),

                            titleField.getText(),

                            deptField.getText(),

                            Integer.parseInt(
                                    creditsField.getText()
                            )
                    );

            CourseDAO dao =
                    new CourseDAO();

            boolean updated =
                    dao.updateCourse(course);

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Course Updated!"
                );

                loadCourses();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Update Failed!"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void deleteCourse() {

        try {

            CourseDAO dao =
                    new CourseDAO();

            boolean deleted =
                    dao.deleteCourse(
                            idField.getText()
                    );

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Course Deleted!"
                );

                loadCourses();

                clearFields();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Delete Failed!"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void searchCourses() {

        tableModel.setRowCount(0);

        String dept =
                searchField.getText();

        List<Course> courses =
                new CourseDAO()
                        .searchByDepartment(
                                searchField.getText()
                        );

        for (Course course : courses) {

            if (course.getDeptName()
                    .equalsIgnoreCase(dept)) {

                Object[] row = {

                        course.getCourseId(),

                        course.getTitle(),

                        course.getDeptName(),

                        course.getCredits()
                };

                tableModel.addRow(row);
            }
        }
    }

    private void clearFields() {

        idField.setText("");

        titleField.setText("");

        deptField.setText("");

        creditsField.setText("");
    }

    private void fillFormFromTable() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            return;
        }

        idField.setText(
                tableModel.getValueAt(
                        row,
                        0
                ).toString()
        );

        titleField.setText(
                tableModel.getValueAt(
                        row,
                        1
                ).toString()
        );

        deptField.setText(
                tableModel.getValueAt(
                        row,
                        2
                ).toString()
        );

        creditsField.setText(
                tableModel.getValueAt(
                        row,
                        3
                ).toString()
        );
    }
}