package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel sidebar;
    private JPanel contentPanel;

    private CardLayout cardLayout;

    private JLabel statusLabel;

    public MainFrame() {

        setTitle("University Database Management System");

        setSize(1400, 850);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        createSidebar();

        createContent();

        createStatusBar();

        add(sidebar, BorderLayout.WEST);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void createSidebar() {

        sidebar = new JPanel();

        sidebar.setPreferredSize(
                new Dimension(250, 0)
        );

        sidebar.setLayout(
                new GridLayout(12,1)
        );

        JButton dashboardBtn =
                new JButton("Dashboard");

        JButton studentBtn =
                new JButton("Students");

        JButton courseBtn =
                new JButton("Courses");

        JButton instructorBtn =
                new JButton("Instructors");

        JButton sectionBtn =
                new JButton("Sections");

        JButton enrollmentBtn =
                new JButton("Enrollment");

        JButton reportBtn =
                new JButton("Reports");

        sidebar.add(dashboardBtn);
        sidebar.add(studentBtn);
        sidebar.add(courseBtn);
        sidebar.add(instructorBtn);
        sidebar.add(sectionBtn);
        sidebar.add(enrollmentBtn);
        sidebar.add(reportBtn);

        dashboardBtn.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "dashboard"
                )
        );

        studentBtn.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "students"
                )
        );

        courseBtn.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "courses"
                )
        );

        instructorBtn.addActionListener(
                e -> cardLayout.show(
                        contentPanel,
                        "instructors"
                )
        );
    }

    private void createContent() {

        cardLayout =
                new CardLayout();

        contentPanel =
                new JPanel(cardLayout);

        contentPanel.add(
                new DashboardPanel(),
                "dashboard"
        );

        contentPanel.add(
                new JPanel(),
                "students"
        );

        contentPanel.add(
                new JPanel(),
                "courses"
        );

        contentPanel.add(
                new JPanel(),
                "instructors"
        );
    }

    private void createStatusBar() {

        JPanel statusBar =
                new JPanel(
                        new BorderLayout()
                );

        statusLabel =
                new JLabel(
                        "Database Connected"
                );

        statusBar.add(
                statusLabel,
                BorderLayout.WEST
        );

        add(
                statusBar,
                BorderLayout.SOUTH
        );
    }
}