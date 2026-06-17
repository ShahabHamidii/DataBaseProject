package ui;

import dao.AdvisorDAO;
import dao.InstructorDAO;
import dao.StudentDAO;
import model.Instructor;
import model.Student;
import util.UITheme;
import util.UIUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdvisorPanel extends JPanel {

    private JComboBox<Student> studentCombo;
    private JComboBox<Instructor> instructorCombo;
    private JButton assignButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton;
    private JButton deleteButton;

    public AdvisorPanel() {
        initComponents();
        loadData();
        loadAssignments();
    }

    private void initComponents() {

        studentCombo = new JComboBox<>();
        instructorCombo = new JComboBox<>();

        UITheme.styleComboBox(studentCombo);
        UITheme.styleComboBox(instructorCombo);

        JPanel formGrid = UITheme.createFormGrid(2, 2,
                UITheme.createFieldLabel("Student"), studentCombo,
                UITheme.createFieldLabel("Advisor"), instructorCombo
        );

        assignButton = UIUtil.createButton("Assign Advisor", UIUtil.ButtonStyle.PRIMARY);
        loadButton = UIUtil.createButton("Refresh", UIUtil.ButtonStyle.SECONDARY);
        deleteButton = UIUtil.createButton("Remove", UIUtil.ButtonStyle.DANGER);

        JPanel leftPanel = UITheme.createCard("Assign Advisor",
                UITheme.createFormGrid(2, 1, formGrid,
                        UITheme.createButtonBar(assignButton, deleteButton, loadButton)));

        tableModel = new DefaultTableModel(
                new String[]{"Student ID", "Student", "Advisor"}, 0);
        table = new JTable(tableModel);

        UITheme.wrapContent(this, "Advisors",
                "Assign faculty advisors to students",
                leftPanel, table);

        assignButton.addActionListener(e -> assignAdvisor());
        loadButton.addActionListener(e -> loadAssignments());
        deleteButton.addActionListener(e -> deleteAssignment());
    }

    private void loadData() {
        for (Student student : new StudentDAO().getAllStudents()) {
            studentCombo.addItem(student);
        }
        for (Instructor instructor : new InstructorDAO().getAllInstructors()) {
            instructorCombo.addItem(instructor);
        }
    }

    private void assignAdvisor() {

        Student student = (Student) studentCombo.getSelectedItem();
        Instructor instructor = (Instructor) instructorCombo.getSelectedItem();

        if (student == null || instructor == null) {
            JOptionPane.showMessageDialog(this, "Select both a student and advisor");
            return;
        }

        AdvisorDAO dao = new AdvisorDAO();

        if (dao.advisorExists(student.getId())) {
            JOptionPane.showMessageDialog(this, "Student already has an advisor");
            return;
        }

        if (dao.assignAdvisor(student.getId(), instructor.getId())) {
            JOptionPane.showMessageDialog(this, "Advisor assigned successfully");
            loadAssignments();
        }
    }

    private void loadAssignments() {

        tableModel.setRowCount(0);

        for (Object[] row : new AdvisorDAO().getAllAssignments()) {
            tableModel.addRow(row);
        }
    }

    private void deleteAssignment() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first");
            return;
        }

        int studentId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());

        int choice = JOptionPane.showConfirmDialog(
                this, "Remove this advisor assignment?",
                "Confirm", JOptionPane.YES_NO_OPTION);

        if (choice != JOptionPane.YES_OPTION) return;

        if (new AdvisorDAO().deleteAdvisor(studentId)) {
            JOptionPane.showMessageDialog(this, "Assignment removed");
            loadAssignments();
        }
    }
}
