package ui;

import dao.InstructorDAO;
import model.Instructor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import util.ValidationUtil;
import util.UITheme;
import util.UIUtil;

public class InstructorPanel extends JPanel {

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


    public InstructorPanel() {

        initComponents();

        loadInstructors();
    }

    private void initComponents() {

        idField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        salaryField = new JTextField();
        searchField = new JTextField();

        UITheme.styleTextField(idField);
        UITheme.styleTextField(nameField);
        UITheme.styleTextField(deptField);
        UITheme.styleTextField(salaryField);
        UITheme.styleTextField(searchField);

        JPanel formGrid = UITheme.createFormGrid(5, 2,
                UITheme.createFieldLabel("ID"), idField,
                UITheme.createFieldLabel("Name"), nameField,
                UITheme.createFieldLabel("Department"), deptField,
                UITheme.createFieldLabel("Salary"), salaryField,
                UITheme.createFieldLabel("Search (ID or Name)"), searchField
        );

        addButton = UIUtil.createButton("Add", UIUtil.ButtonStyle.SUCCESS);
        loadButton = UIUtil.createButton("Refresh", UIUtil.ButtonStyle.SECONDARY);
        updateButton = UIUtil.createButton("Update", UIUtil.ButtonStyle.PRIMARY);
        deleteButton = UIUtil.createButton("Delete", UIUtil.ButtonStyle.DANGER);
        searchButton = UIUtil.createButton("Search", UIUtil.ButtonStyle.GHOST);

        JPanel leftPanel = UITheme.createCard("Instructor Details",
                UITheme.createFormGrid(2, 1, formGrid,
                        UITheme.createButtonBar(addButton, updateButton, deleteButton, loadButton, searchButton)));

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Department", "Salary"}, 0);
        table = new JTable(tableModel);

        UITheme.wrapContent(this, "Instructors",
                "Manage faculty members and their departments",
                leftPanel, table);

        addButton.addActionListener(e -> addInstructor());
        loadButton.addActionListener(e -> loadInstructors());
        updateButton.addActionListener(e -> updateInstructor());
        deleteButton.addActionListener(e -> deleteInstructor());
        searchButton.addActionListener(e -> searchInstructor());
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());
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

        if(result){

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Successful"
            );

            loadInstructors();

            clearFields();

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Failed"
            );
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

        if(result){

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Successful"
            );

            loadInstructors();

            clearFields();

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Failed"
            );
        }
    }

    private void deleteInstructor() {

        if(
                !ValidationUtil.isInteger(
                        idField.getText()
                )
        ){
            JOptionPane.showMessageDialog(
                    this,
                    "Select an instructor first."
            );
            return;
        }

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

        if(result){

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Successful"
            );

            loadInstructors();

            clearFields();

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Failed"
            );
        }
    }

    private void searchInstructor() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) { loadInstructors(); return; }

        tableModel.setRowCount(0);

        if (ValidationUtil.isInteger(keyword)) {
            Instructor ins = new InstructorDAO()
                    .getInstructorById(Integer.parseInt(keyword));
            if (ins != null) {
                tableModel.addRow(new Object[]{
                        ins.getId(), ins.getName(), ins.getDeptName(), ins.getSalary()
                });
            } else {
                JOptionPane.showMessageDialog(this, "Instructor not found.");
            }
        }
        else {
            var list = new InstructorDAO().searchByName(keyword);
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No instructors found.");
            } else {
                for (Instructor ins : list) {
                    tableModel.addRow(new Object[]{
                            ins.getId(), ins.getName(), ins.getDeptName(), ins.getSalary()
                    });
                }
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

        salaryField.setText(
                tableModel.getValueAt(
                        row,
                        3
                ).toString()
        );
    }

    private void clearFields() {

        idField.setText("");

        nameField.setText("");

        deptField.setText("");

        salaryField.setText("");
    }

}