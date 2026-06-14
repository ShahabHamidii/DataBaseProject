package ui;

import dao.InstructorDAO;
import model.Instructor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import util.ValidationUtil;

public class InstructorForm extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField deptField;
    private JTextField salaryField;
    private JButton addButton;
    private JButton loadButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField searchField;

    private JButton searchButton;
    public InstructorForm() {

        setTitle("Instructor Management");

        setSize(900, 600);

        setLocationRelativeTo(null);

        initComponents();
        loadInstructors();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        JPanel panel =
                new JPanel(
                        new GridLayout(8, 2, 10, 10)
                );

        idField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        salaryField = new JTextField();
        searchField =
                new JTextField();


        panel.add(new JLabel("ID"));
        panel.add(idField);

        panel.add(new JLabel("Name"));
        panel.add(nameField);

        panel.add(new JLabel("Department"));
        panel.add(deptField);

        panel.add(new JLabel("Salary"));
        panel.add(salaryField);

        panel.add(new JLabel("Search By ID"));
        panel.add(searchField);

        addButton =
                new JButton("Add");

        loadButton =
                new JButton("Load");

        updateButton =
                new JButton("Update");

        deleteButton =
                new JButton("Delete");

        searchButton =
                new JButton("Search");
        searchButton.addActionListener(
                e -> searchInstructor()
        );

        panel.add(addButton);
        panel.add(loadButton);

        panel.add(updateButton);
        panel.add(deleteButton);

        panel.add(searchButton);
        panel.add(new JLabel());

        add(panel, BorderLayout.NORTH);

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "ID",
                                "Name",
                                "Department",
                                "Salary"
                        },
                        0
                );

        table =
                new JTable(tableModel);

        table.setRowHeight(30);

        table.setAutoCreateRowSorter(true);

        table.getTableHeader()
                .setReorderingAllowed(false);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        addButton.addActionListener(
                e -> addInstructor()
        );

        loadButton.addActionListener(
                e -> loadInstructors()
        );

        updateButton.addActionListener(
                e -> updateInstructor()
        );

        deleteButton.addActionListener(
                e -> deleteInstructor()
        );

        table.getSelectionModel()
                .addListSelectionListener(
                        e -> fillFormFromTable()
                );
    }

    private void addInstructor() {
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
                                salaryField.getText()
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

                        !ValidationUtil.isDouble(
                                salaryField.getText()
                        )

        ) {

            JOptionPane.showMessageDialog(

                    this,

                    "Invalid numeric values."

            );

            return;
        }

        Instructor instructor =
                new Instructor(
                        Integer.parseInt(idField.getText()),
                        nameField.getText(),
                        deptField.getText(),
                        Double.parseDouble(
                                salaryField.getText()
                        )
                );

        boolean result =
                new InstructorDAO()
                        .addInstructor(instructor);

        if (result) {

            loadInstructors();
        }
    }

    private void loadInstructors() {

        tableModel.setRowCount(0);

        for (Instructor instructor :
                new InstructorDAO()
                        .getAllInstructors()) {

            tableModel.addRow(
                    new Object[]{
                            instructor.getId(),
                            instructor.getName(),
                            instructor.getDeptName(),
                            instructor.getSalary()
                    }
            );
        }
    }

    private void updateInstructor() {

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
                                salaryField.getText()
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

                        !ValidationUtil.isDouble(
                                salaryField.getText()
                        )

        ) {

            JOptionPane.showMessageDialog(

                    this,

                    "Invalid numeric values."

            );

            return;
        }

        Instructor instructor =
                new Instructor(

                        Integer.parseInt(
                                idField.getText()
                        ),

                        nameField.getText(),

                        deptField.getText(),

                        Double.parseDouble(
                                salaryField.getText()
                        )
                );

        boolean result =

                new InstructorDAO()
                        .updateInstructor(
                                instructor
                        );

        if (result) {

            loadInstructors();
        }
    }

    private void deleteInstructor() {

        int id =
                Integer.parseInt(
                        idField.getText()
                );

        int choice =

                JOptionPane.showConfirmDialog(

                        this,

                        "Delete Instructor?",

                        "Confirm",

                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        boolean result =

                new InstructorDAO()
                        .deleteInstructor(id);

        if (result) {

            loadInstructors();
        }
    }

    private void searchInstructor() {

        if (!ValidationUtil.isInteger(searchField.getText())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid instructor ID"
            );

            return;
        }

        Instructor instructor =
                new InstructorDAO()
                        .getInstructorById(
                                Integer.parseInt(
                                        searchField.getText()
                                )
                        );

        tableModel.setRowCount(0);

        if (instructor != null) {

            tableModel.addRow(
                    new Object[]{
                            instructor.getId(),
                            instructor.getName(),
                            instructor.getDeptName(),
                            instructor.getSalary()
                    }
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Instructor not found"
            );
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

        salaryField.setText(
                tableModel.getValueAt(
                        row,
                        3
                ).toString()
        );
    }
}