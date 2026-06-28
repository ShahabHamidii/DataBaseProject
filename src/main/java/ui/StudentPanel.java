package ui;

import dao.StudentDAO;
import model.Student;
import util.UIUtil;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import util.UITheme;
import util.ValidationUtil;


public class StudentPanel extends JPanel {


    private JTextField idField;
    private JTextField nameField;
    private JTextField deptField;
    private JTextField creditField;
    private JTable table;
    private JButton updateButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;

    private JButton searchButton;
    private JButton loadButton;
    private JButton addButton;

    public StudentPanel() {

        setPreferredSize(new Dimension(600, 400));
        initComponents();

        loadStudents();
    }

    private void initComponents() {

        idField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        creditField = new JTextField();
        searchField = new JTextField();
        searchTypeCombo = new JComboBox<>(new String[]{"ID", "Department"});

        UITheme.styleTextField(idField);
        UITheme.styleTextField(nameField);
        UITheme.styleTextField(deptField);
        UITheme.styleTextField(creditField);
        UITheme.styleTextField(searchField);
        UITheme.styleComboBox(searchTypeCombo);

        JPanel formGrid = UITheme.createFormGrid(6, 2,
                UITheme.createFieldLabel("Student ID"), idField,
                UITheme.createFieldLabel("Name"), nameField,
                UITheme.createFieldLabel("Department"), deptField,
                UITheme.createFieldLabel("Total Credits"), creditField,
                UITheme.createFieldLabel("Search"), searchField,
                UITheme.createFieldLabel("Search Type"), searchTypeCombo
        );

        addButton = UIUtil.createButton("Add", UIUtil.ButtonStyle.SUCCESS);
        updateButton = UIUtil.createButton("Update", UIUtil.ButtonStyle.PRIMARY);
        deleteButton = UIUtil.createButton("Delete", UIUtil.ButtonStyle.DANGER);
        loadButton = UIUtil.createButton("Refresh", UIUtil.ButtonStyle.SECONDARY);
        searchButton = UIUtil.createButton("Search", UIUtil.ButtonStyle.GHOST);

        JPanel leftPanel = UITheme.createCard("Student Details",
                UITheme.createFormGrid(2, 1, formGrid,
                        UITheme.createButtonBar(addButton, updateButton, deleteButton, loadButton, searchButton)));

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Department", "Credits"}, 0);
        table = new JTable(tableModel);

        UITheme.wrapContent(this, "Students",
                "Manage student records and search by ID or department",
                leftPanel, table);

        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        loadButton.addActionListener(e -> loadStudents());
        searchButton.addActionListener(e -> searchStudent());
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());
    }

    private void addStudent() {

        if (

                ValidationUtil.isEmpty(
                        idField.getText()
                )

                        ||

                        ValidationUtil.isEmpty(
                                nameField.getText()
                        )

                        ||

                        ValidationUtil.isEmpty(
                                deptField.getText()
                        )

                        ||

                        ValidationUtil.isEmpty(
                                creditField.getText()
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
                        idField.getText()
                )

                        ||

                        !ValidationUtil.isInteger(
                                creditField.getText()
                        )

        ) {

            JOptionPane.showMessageDialog(

                    this,

                    "ID and Credits must be numbers."

            );

            return;
        }

        try {

            int id =
                    Integer.parseInt(
                            idField.getText()
                    );

            String name =
                    nameField.getText();

            String dept =
                    deptField.getText();

            int credits =
                    Integer.parseInt(
                            creditField.getText()
                    );

            Student student =
                    new Student(
                            id,
                            name,
                            dept,
                            credits
                    );

            StudentDAO dao =
                    new StudentDAO();

            boolean inserted =
                    dao.addStudent(student);

            if (inserted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student Added Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Insert Failed!"
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
        loadStudents();
        clearFields();
    }

    private void loadStudents() {

        tableModel.setRowCount(0);

        StudentDAO dao =
                new StudentDAO();

        List<Student> students =
                dao.getAllStudents();

        for (Student student : students) {

            Object[] row = {

                    student.getId(),

                    student.getName(),

                    student.getDeptName(),

                    student.getTotalCredits()
            };

            tableModel.addRow(row);
        }
    }

    private void updateStudent() {
        if (
                ValidationUtil.isEmpty(
                        idField.getText()
                )

                        ||

                        ValidationUtil.isEmpty(
                                nameField.getText()
                        )

                        ||

                        ValidationUtil.isEmpty(
                                deptField.getText()
                        )

                        ||

                        ValidationUtil.isEmpty(
                                creditField.getText()
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
                        idField.getText()
                )

                        ||

                        !ValidationUtil.isInteger(
                                creditField.getText()
                        )

        ) {

            JOptionPane.showMessageDialog(

                    this,

                    "ID and Credits must be numbers."

            );

            return;
        }

        try {

            Student student =
                    new Student(
                            Integer.parseInt(
                                    idField.getText()
                            ),

                            nameField.getText(),

                            deptField.getText(),

                            Integer.parseInt(
                                    creditField.getText()
                            )
                    );

            StudentDAO dao =
                    new StudentDAO();

            boolean updated =
                    dao.updateStudent(student);

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student Updated!"
                );

                loadStudents();
                clearFields();

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

    private void deleteStudent() {

        try {

            int id =
                    Integer.parseInt(
                            idField.getText()
                    );

            StudentDAO dao =
                    new StudentDAO();

            boolean deleted =
                    dao.deleteStudent(id);

            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student Deleted!"
                );

                loadStudents();
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

    private void clearFields() {

        idField.setText("");

        nameField.setText("");

        deptField.setText("");

        creditField.setText("");
    }

    private void searchStudent() {

        tableModel.setRowCount(0);

        StudentDAO dao = new StudentDAO();

        String searchText = searchField.getText().trim();

        if (searchText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a search value"
            );

            return;
        }

        String searchType = (String) searchTypeCombo.getSelectedItem();

        if ("ID".equals(searchType)) {

            if (!ValidationUtil.isInteger(searchText)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid student ID"
                );

                return;
            }

            Student student = dao.getStudentById(
                    Integer.parseInt(searchText)
            );

            if (student != null) {

                tableModel.addRow(new Object[]{
                        student.getId(),
                        student.getName(),
                        student.getDeptName(),
                        student.getTotalCredits()
                });

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Student not found"
                );
            }

        } else {

            loadStudents();

            for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {

                String dept = tableModel.getValueAt(i, 2)
                        .toString();

                if (!dept.equalsIgnoreCase(searchText)) {
                    tableModel.removeRow(i);
                }
            }

            if (tableModel.getRowCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No students found in this department"
                );
            }
        }
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

        nameField.setText(
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

        creditField.setText(
                tableModel.getValueAt(
                        row,
                        3
                ).toString()
        );
    }

}