package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentPanel extends JPanel {

    private JTextField idField;
    private JTextField nameField;
    private JTextField deptField;
    private JTextField creditField;
    private JTextField searchField;

    private JTable table;
    private DefaultTableModel tableModel;

    public StudentPanel() {

        setLayout(new BorderLayout(15,15));

        createTopPanel();
        createTable();

        loadStudents();
    }

    private void createTopPanel() {

        JPanel panel = new JPanel(
                new GridLayout(0,4,10,10)
        );

        idField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        creditField = new JTextField();
        searchField = new JTextField();

        JButton addBtn = new JButton("➕ Add");
        JButton updateBtn = new JButton("✏ Edit");
        JButton deleteBtn = new JButton("🗑 Delete");
        JButton refreshBtn = new JButton("🔄 Refresh");
        JButton searchBtn = new JButton("🔍 Search");

        panel.add(new JLabel("ID"));
        panel.add(idField);

        panel.add(new JLabel("Name"));
        panel.add(nameField);

        panel.add(new JLabel("Department"));
        panel.add(deptField);

        panel.add(new JLabel("Credits"));
        panel.add(creditField);

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);
        panel.add(refreshBtn);

        panel.add(searchField);
        panel.add(searchBtn);

        add(panel, BorderLayout.NORTH);

        addBtn.addActionListener(e -> addStudent());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        refreshBtn.addActionListener(e -> loadStudents());
        searchBtn.addActionListener(e -> searchStudent());
    }

    private void createTable() {

        tableModel = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Name",
                        "Department",
                        "Credits"
                },
                0
        );

        table = new JTable(tableModel);

        table.setRowHeight(35);
        table.setAutoCreateRowSorter(true);
        table.setShowGrid(false);

        table.getSelectionModel()
                .addListSelectionListener(
                        e -> fillForm()
                );

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );
    }

    private void fillForm() {

        int row = table.getSelectedRow();

        if(row == -1) return;

        idField.setText(
                table.getValueAt(row,0).toString()
        );

        nameField.setText(
                table.getValueAt(row,1).toString()
        );

        deptField.setText(
                table.getValueAt(row,2).toString()
        );

        creditField.setText(
                table.getValueAt(row,3).toString()
        );
    }

    private void loadStudents() {

        tableModel.setRowCount(0);

        for(Student s :
                new StudentDAO().getAllStudents()) {

            tableModel.addRow(
                    new Object[]{
                            s.getId(),
                            s.getName(),
                            s.getDeptName(),
                            s.getTotalCredits()
                    }
            );
        }
    }

    private void addStudent() {}
    private void updateStudent() {}
    private void deleteStudent() {}
    private void searchStudent() {}
}