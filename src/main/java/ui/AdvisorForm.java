package ui;

import dao.AdvisorDAO;
import dao.InstructorDAO;
import dao.StudentDAO;
import model.Instructor;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdvisorForm extends JFrame {

    private JComboBox<Student> studentCombo;

    private JComboBox<Instructor> instructorCombo;

    private JButton assignButton;

    private JTable table;

    private javax.swing.table.DefaultTableModel tableModel;

    private JButton loadButton;

    private JButton deleteButton;

    public AdvisorForm() {

        setTitle("Advisor Assignment");

        setSize(500,300);

        setLocationRelativeTo(null);

        initComponents();

        loadData();

        loadAssignments();

        setVisible(true);
    }
    private void initComponents() {

        JPanel panel =
                new JPanel(
                        new GridLayout(3,2,10,10)
                );

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Student ID",
                                "Student",
                                "Advisor"
                        },
                        0
                );

        table =
                new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        panel.add(
                new JLabel("Student")
        );

        studentCombo =
                new JComboBox<>();

        panel.add(studentCombo);

        panel.add(
                new JLabel("Instructor")
        );

        instructorCombo =
                new JComboBox<>();

        panel.add(instructorCombo);

        assignButton =
                new JButton(
                        "Assign Advisor"
                );

        loadButton =
                new JButton(
                        "Load Assignments"
                );

        deleteButton =
                new JButton(
                        "Delete Assignment"
                );

        panel.add(loadButton);

        panel.add(deleteButton);

        panel.add(assignButton);

        add(panel);

        assignButton.addActionListener(
                e -> assignAdvisor()
        );

        loadButton.addActionListener(
                e -> loadAssignments()
        );

        deleteButton.addActionListener(
                e -> deleteAssignment()
        );
    }

    private void loadData() {

        for (
                Student student :
                new StudentDAO()
                        .getAllStudents()
        ) {

            studentCombo.addItem(student);
        }

        for (
                Instructor instructor :
                new InstructorDAO()
                        .getAllInstructors()
        ) {

            instructorCombo.addItem(instructor);
        }
    }

    private void assignAdvisor() {

        Student student =
                (Student)
                        studentCombo.getSelectedItem();

        Instructor instructor =
                (Instructor)
                        instructorCombo.getSelectedItem();
        AdvisorDAO dao =
                new AdvisorDAO();

        if(dao.advisorExists(student.getId())){
            JOptionPane.showMessageDialog(
                    this,
                    "Student already has an advisor."
            );
            return;
        }
        boolean result =
                new AdvisorDAO()
                        .assignAdvisor(
                                student.getId(),
                                instructor.getId()
                        );

        if(result) {

            JOptionPane.showMessageDialog(
                    this,
                    "Advisor Assigned"
            );
        }
    }

    private void loadAssignments() {

        tableModel.setRowCount(0);

        for(

                Object[] row :

                new AdvisorDAO()
                        .getAllAssignments()

        ){

            tableModel.addRow(row);
        }
    }

    private void deleteAssignment() {

        int row =
                table.getSelectedRow();

        if(row == -1){

            return;
        }

        int studentId =
                Integer.parseInt(
                        tableModel.getValueAt(
                                row,
                                0
                        ).toString()
                );

        boolean deleted =
                new AdvisorDAO()
                        .deleteAdvisor(studentId);

        if(deleted){

            loadAssignments();
        }
    }
}