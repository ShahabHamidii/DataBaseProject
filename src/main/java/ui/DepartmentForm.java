package ui;

import dao.DepartmentDAO;
import model.Department;
import util.ValidationUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DepartmentForm extends JFrame {

    private JTextField nameField;
    private JTextField buildingField;
    private JTextField budgetField;

    private JTable table;

    private DefaultTableModel tableModel;

    private JButton addButton;
    private JButton loadButton;
    private JButton updateButton;
    private JButton deleteButton;

    public DepartmentForm() {

        setTitle("Department Management");

        setSize(900,600);

        setLocationRelativeTo(null);

        initComponents();

        loadDepartments();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        JPanel panel =
                new JPanel(
                        new GridLayout(4,2,10,10)
                );

        nameField =
                new JTextField();

        buildingField =
                new JTextField();

        budgetField =
                new JTextField();

        panel.add(
                new JLabel("Department")
        );

        panel.add(nameField);

        panel.add(
                new JLabel("Building")
        );

        panel.add(buildingField);

        panel.add(
                new JLabel("Budget")
        );

        panel.add(budgetField);

        addButton =
                new JButton("Add");

        loadButton =
                new JButton("Load");

        updateButton =
                new JButton("Update");

        deleteButton =
                new JButton("Delete");

        panel.add(addButton);
        panel.add(loadButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(
                panel,
                BorderLayout.NORTH
        );

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Department",
                                "Building",
                                "Budget"
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
                e -> addDepartment()
        );

        loadButton.addActionListener(
                e -> loadDepartments()
        );

        updateButton.addActionListener(
                e -> updateDepartment()
        );

        deleteButton.addActionListener(
                e -> deleteDepartment()
        );

        table.getSelectionModel()
                .addListSelectionListener(
                        e -> fillForm()
                );
    }

    private void addDepartment() {

        if(
                ValidationUtil.isEmpty(
                        nameField.getText()
                )
                        ||
                        ValidationUtil.isEmpty(
                                buildingField.getText()
                        )
                        ||
                        ValidationUtil.isEmpty(
                                budgetField.getText()
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "All fields required"
            );

            return;
        }

        if(
                !ValidationUtil.isDouble(
                        budgetField.getText()
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid budget"
            );

            return;
        }

        Department department =
                new Department(
                        nameField.getText(),
                        buildingField.getText(),
                        Double.parseDouble(
                                budgetField.getText()
                        )
                );

        if(
                new DepartmentDAO()
                        .addDepartment(department)
        ) {

            loadDepartments();
        }
    }

    private void loadDepartments() {

        tableModel.setRowCount(0);

        for(
                Department department :
                new DepartmentDAO()
                        .getAllDepartments()
        ) {

            tableModel.addRow(
                    new Object[]{
                            department.getDeptName(),
                            department.getBuilding(),
                            department.getBudget()
                    }
            );
        }
    }

    private void updateDepartment() {

        Department department =
                new Department(
                        nameField.getText(),
                        buildingField.getText(),
                        Double.parseDouble(
                                budgetField.getText()
                        )
                );

        if(
                new DepartmentDAO()
                        .updateDepartment(
                                department
                        )
        ) {

            loadDepartments();
        }
    }

    private void deleteDepartment() {

        if(
                new DepartmentDAO()
                        .deleteDepartment(
                                nameField.getText()
                        )
        ) {

            loadDepartments();
        }
    }

    private void fillForm() {

        int row =
                table.getSelectedRow();

        if(row == -1) {

            return;
        }

        nameField.setText(
                table.getValueAt(
                        row,
                        0
                ).toString()
        );

        buildingField.setText(
                table.getValueAt(
                        row,
                        1
                ).toString()
        );

        budgetField.setText(
                table.getValueAt(
                        row,
                        2
                ).toString()
        );
    }
}