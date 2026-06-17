package ui;

import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {

    private JPanel contentPanel;

    public MainDashboard() {

        setTitle("University System");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();

        setVisible(true);
    }

    private void initUI() {

        setLayout(new BorderLayout());

        // ===== Menu Panel =====
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(10, 1, 10, 10));

        JButton studentBtn = new JButton("Students");
        JButton courseBtn = new JButton("Courses");
        JButton instructorBtn = new JButton("Instructors");
        JButton sectionBtn = new JButton("Sections");
        JButton reportBtn = new JButton("Reports");
        JButton prerequisiteBtn = new JButton("Prerequisite");
        JButton advisorBtn = new JButton("Advisor");
        JButton enrollmentBtn = new JButton("Enrollment");
        JButton teachingAssignmentBtn = new JButton("Teaching Assignment");
        JButton departmentBtn = new JButton("Department");

        menuPanel.add(studentBtn);
        menuPanel.add(courseBtn);
        menuPanel.add(instructorBtn);
        menuPanel.add(sectionBtn);
        menuPanel.add(reportBtn);
        menuPanel.add(prerequisiteBtn);
        menuPanel.add(advisorBtn);
        menuPanel.add(enrollmentBtn);
        menuPanel.add(teachingAssignmentBtn);
        menuPanel.add(departmentBtn);

        // ===== Content Panel =====
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // ===== Actions =====

        studentBtn.addActionListener(e -> showPanel(new StudentPanel()));
        courseBtn.addActionListener(e -> showPanel(new CoursePanel()));
        instructorBtn.addActionListener(e -> showPanel(new InstructorPanel()));
        sectionBtn.addActionListener(e -> showPanel(new SectionPanel()));
        reportBtn.addActionListener(e -> showPanel(new ReportPanel()));
        prerequisiteBtn.addActionListener(e -> showPanel(new PrerequisitePanel()));
        advisorBtn.addActionListener(e -> showPanel(new AdvisorPanel()));
        enrollmentBtn.addActionListener(e -> showPanel(new EnrollmentPanel()));
        teachingAssignmentBtn.addActionListener(e -> showPanel(new TeachingAssignmentPanel()));
        departmentBtn.addActionListener(e -> showPanel(new DepartmentPanel()));

    }

    private void showPanel(JPanel panel) {

        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}