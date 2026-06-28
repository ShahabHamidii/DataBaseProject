package ui;

import dao.DepartmentDAO;
import model.Department;
import util.UITheme;
import util.UIUtil;
import util.ValidationUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DepartmentPanel extends JPanel {

    private JTextField nameField;
    private JTextField buildingField;
    private JTextField budgetField;

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton addButton;
    private JButton loadButton;
    private JButton updateButton;
    private JButton deleteButton;

    public DepartmentPanel() {
        initComponents();
        loadDepartments();
    }

    private void initComponents() {

        nameField = new JTextField();
        buildingField = new JTextField();
        budgetField = new JTextField();

        UITheme.styleTextField(nameField);
        UITheme.styleTextField(buildingField);
        UITheme.styleTextField(budgetField);

        JPanel formGrid = UITheme.createFormGrid(3, 2,
                UITheme.createFieldLabel("Department"), nameField,
                UITheme.createFieldLabel("Building"), buildingField,
                UITheme.createFieldLabel("Budget"), budgetField
        );

        addButton = UIUtil.createButton("Add", UIUtil.ButtonStyle.SUCCESS);
        loadButton = UIUtil.createButton("Refresh", UIUtil.ButtonStyle.SECONDARY);
        updateButton = UIUtil.createButton("Update", UIUtil.ButtonStyle.PRIMARY);
        deleteButton = UIUtil.createButton("Delete", UIUtil.ButtonStyle.DANGER);

        JPanel leftPanel = UITheme.createCard("Department Details",
                UITheme.createFormGrid(2, 1, formGrid,
                        UITheme.createButtonBar(addButton, updateButton, deleteButton, loadButton)));

        tableModel = new DefaultTableModel(
                new String[]{"Department", "Building", "Budget"}, 0);
        table = new JTable(tableModel);

        UITheme.wrapContent(this, "Departments",
                "Manage academic departments and budgets",
                leftPanel, table);

        addButton.addActionListener(e -> addDepartment());
        loadButton.addActionListener(e -> loadDepartments());
        updateButton.addActionListener(e -> updateDepartment());
        deleteButton.addActionListener(e -> deleteDepartment());
        table.getSelectionModel().addListSelectionListener(e -> fillForm());
    }

    private void addDepartment() {

        if (ValidationUtil.isEmpty(nameField.getText())
                || ValidationUtil.isEmpty(buildingField.getText())
                || ValidationUtil.isEmpty(budgetField.getText())) {

            JOptionPane.showMessageDialog(this, "All fields required");
            return;
        }

        if (!ValidationUtil.isDouble(budgetField.getText())) {
            JOptionPane.showMessageDialog(this, "Invalid budget");
            return;
        }

        Department department = new Department(
                nameField.getText(),
                buildingField.getText(),
                Double.parseDouble(budgetField.getText())
        );

        if (new DepartmentDAO().addDepartment(department)) {
            JOptionPane.showMessageDialog(this, "Department added successfully");
            loadDepartments();
            clearFields();
        }
    }

    private void loadDepartments() {

        tableModel.setRowCount(0);

        for (Department department : new DepartmentDAO().getAllDepartments()) {
            tableModel.addRow(new Object[]{
                    department.getDeptName(),
                    department.getBuilding(),
                    department.getBudget()
            });
        }
    }

    private void updateDepartment() {

        if (ValidationUtil.isEmpty(nameField.getText())
                || ValidationUtil.isEmpty(buildingField.getText())
                || ValidationUtil.isEmpty(budgetField.getText())) {

            JOptionPane.showMessageDialog(this, "All fields required");
            return;
        }

        Department department = new Department(
                nameField.getText(),
                buildingField.getText(),
                Double.parseDouble(budgetField.getText())
        );

        if (new DepartmentDAO().updateDepartment(department)) {
            JOptionPane.showMessageDialog(this, "Department updated");
            loadDepartments();
        }
    }

    private void deleteDepartment() {

        if (ValidationUtil.isEmpty(nameField.getText())) {
            JOptionPane.showMessageDialog(this, "Select a department first");
            return;
        }

        if (new DepartmentDAO().deleteDepartment(nameField.getText())) {
            JOptionPane.showMessageDialog(this, "Department deleted");
            loadDepartments();
            clearFields();
        }
    }

    private void clearFields() {
        nameField.setText("");
        buildingField.setText("");
        budgetField.setText("");
    }

    private void fillForm() {

        int row = table.getSelectedRow();
        if (row == -1) return;

        nameField.setText(table.getValueAt(row, 0).toString());
        buildingField.setText(table.getValueAt(row, 1).toString());
        budgetField.setText(table.getValueAt(row, 2).toString());
    }
}
