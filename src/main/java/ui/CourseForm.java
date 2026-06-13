package ui;

import dao.CourseDAO;
import model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import util.ValidationUtil;

public class CourseForm extends JFrame {

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

    public CourseForm() {

        setTitle("Course Management");

        setSize(900, 600);

        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        JPanel formPanel =
                new JPanel(
                        new GridLayout(9, 2, 10, 10)
                );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        // Course ID
        formPanel.add(
                new JLabel("Course ID:")
        );

        idField =
                new JTextField();

        formPanel.add(idField);

        // Title
        formPanel.add(
                new JLabel("Title:")
        );

        titleField =
                new JTextField();

        formPanel.add(titleField);

        // Department
        formPanel.add(
                new JLabel("Department:")
        );

        deptField =
                new JTextField();

        formPanel.add(deptField);

        // Credits
        formPanel.add(
                new JLabel("Credits:")
        );

        creditsField =
                new JTextField();

        formPanel.add(creditsField);

        // Search
        formPanel.add(
                new JLabel("Search Department:")
        );

        searchField =
                new JTextField();

        formPanel.add(searchField);

        // Buttons
        addButton =
                new JButton("Add");

        updateButton =
                new JButton("Update");

        deleteButton =
                new JButton("Delete");

        loadButton =
                new JButton("Load");

        searchButton =
                new JButton("Search");

        formPanel.add(addButton);

        formPanel.add(updateButton);

        formPanel.add(deleteButton);

        formPanel.add(loadButton);

        formPanel.add(searchButton);

        // Table
        String[] columns = {
                "Course ID",
                "Title",
                "Department",
                "Credits"
        };

        tableModel =
                new DefaultTableModel(columns, 0);

        table =
                new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(formPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);

        // Actions
        addButton.addActionListener(
                e -> addCourse()
        );

        updateButton.addActionListener(
                e -> updateCourse()
        );

        deleteButton.addActionListener(
                e -> deleteCourse()
        );

        loadButton.addActionListener(
                e -> loadCourses()
        );

        searchButton.addActionListener(
                e -> searchCourses()
        );

        table.getSelectionModel()
                .addListSelectionListener(
                        e -> fillFormFromTable()
                );
        // Table Selection
        table.getSelectionModel()
                .addListSelectionListener(e -> {

                    int selectedRow =
                            table.getSelectedRow();

                    if (selectedRow != -1) {

                        idField.setText(
                                tableModel.getValueAt(
                                        selectedRow,
                                        0
                                ).toString()
                        );

                        titleField.setText(
                                tableModel.getValueAt(
                                        selectedRow,
                                        1
                                ).toString()
                        );

                        deptField.setText(
                                tableModel.getValueAt(
                                        selectedRow,
                                        2
                                ).toString()
                        );

                        creditsField.setText(
                                tableModel.getValueAt(
                                        selectedRow,
                                        3
                                ).toString()
                        );
                    }
                });
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