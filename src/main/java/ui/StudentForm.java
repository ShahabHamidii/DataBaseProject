package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class StudentForm extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField deptField;
    private JTextField creditField;
    private JTable table;
    private JButton updateButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    private JButton searchButton;
    private JButton loadButton;
    private JButton addButton;

    public StudentForm() {

        setTitle("Student Management");

        setSize(900, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        formPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        formPanel.add(idField);


        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);


        formPanel.add(new JLabel("Department:"));
        deptField = new JTextField();


        formPanel.add(deptField);
        formPanel.add(new JLabel("Total Credits:"));
        creditField = new JTextField();
        formPanel.add(creditField);


        formPanel.add(new JLabel("Search By Department:"));
        searchField =
                new JTextField();
        formPanel.add(searchField);

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

        formPanel.add(searchButton);

        formPanel.add(addButton);

        formPanel.add(updateButton);

        formPanel.add(deleteButton);

        formPanel.add(loadButton);

        // Table
        String[] columns = {
                "ID",
                "Name",
                "Department",
                "Credits"
        };

        tableModel =
                new DefaultTableModel(columns, 0);

        table =
                new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(table);

        // Add to frame
        add(formPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);

        // Actions
        addButton.addActionListener(
                e -> addStudent()
        );

        updateButton.addActionListener(
                e -> updateStudent()
        );

        deleteButton.addActionListener(
                e -> deleteStudent()
        );

        loadButton.addActionListener(
                e -> loadStudents()
        );
        searchButton.addActionListener(
                e -> searchStudents()
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

                        nameField.setText(
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

                        creditField.setText(
                                tableModel.getValueAt(
                                        selectedRow,
                                        3
                                ).toString()
                        );
                    }
                });
    }

    private void addStudent() {

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

    private void searchStudents() {

        tableModel.setRowCount(0);

        String dept =
                searchField.getText();

        StudentDAO dao =
                new StudentDAO();

        List<Student> students =
                dao.searchByDepartment(dept);

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
}