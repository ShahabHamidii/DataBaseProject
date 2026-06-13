package ui;

import dao.InstructorDAO;
import model.Instructor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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

    public InstructorForm() {

        setTitle("Instructor Management");

        setSize(900, 600);

        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        JPanel panel =
                new JPanel(
                        new GridLayout(5, 2)
                );

        idField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        salaryField = new JTextField();

        panel.add(new JLabel("ID"));
        panel.add(idField);

        panel.add(new JLabel("Name"));
        panel.add(nameField);

        panel.add(new JLabel("Department"));
        panel.add(deptField);

        panel.add(new JLabel("Salary"));
        panel.add(salaryField);

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
    }

    private void addInstructor() {

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
}