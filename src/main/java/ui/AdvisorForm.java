package ui;

import dao.AdvisorDAO;
import dao.InstructorDAO;
import dao.StudentDAO;
import model.Instructor;
import model.Student;

import javax.swing.*;
import java.awt.*;

public class AdvisorForm extends JFrame {

    private JComboBox<Student> studentCombo;

    private JComboBox<Instructor> instructorCombo;

    private JButton assignButton;

    public AdvisorForm() {

        setTitle("Advisor Assignment");

        setSize(500,300);

        setLocationRelativeTo(null);

        initComponents();

        loadData();

        setVisible(true);
    }

    private void initComponents() {

        JPanel panel =
                new JPanel(
                        new GridLayout(3,2,10,10)
                );

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

        panel.add(assignButton);

        add(panel);

        assignButton.addActionListener(
                e -> assignAdvisor()
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
}