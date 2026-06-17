package ui;

import util.UITheme;
import util.UIUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainDashboard extends JFrame {

    private JPanel contentPanel;
    private final Map<String, JButton> navButtons = new LinkedHashMap<>();
    private JButton activeNavButton;

    public MainDashboard() {
        setTitle("University Management System");
        setSize(1280, 820);
        setMinimumSize(new Dimension(1024, 680));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        showPanel(new DashboardPanel(), "Dashboard");
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 0));

        JPanel sidebar = buildSidebar();
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UITheme.CONTENT_BG);

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(UITheme.SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(240, 0));

        JPanel brand = new JPanel(new BorderLayout());
        brand.setBackground(UITheme.SIDEBAR_BG);
        brand.setBorder(new EmptyBorder(28, 22, 24, 22));

        JLabel brandIcon = new JLabel("\uD83C\uDFEB");
        brandIcon.setFont(new Font("SansSerif", Font.PLAIN, 28));
        brandIcon.setBorder(new EmptyBorder(0, 0, 0, 10));

        JPanel brandText = new JPanel();
        brandText.setLayout(new BoxLayout(brandText, BoxLayout.Y_AXIS));
        brandText.setOpaque(false);

        JLabel brandTitle = new JLabel("UniSys");
        brandTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        brandTitle.setForeground(Color.WHITE);

        JLabel brandSub = new JLabel("Management System");
        brandSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        brandSub.setForeground(UITheme.SIDEBAR_TEXT);

        brandText.add(brandTitle);
        brandText.add(brandSub);

        JPanel brandRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        brandRow.setOpaque(false);
        brandRow.add(brandIcon);
        brandRow.add(brandText);
        brand.add(brandRow, BorderLayout.CENTER);

        sidebar.add(brand, BorderLayout.NORTH);

        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(UITheme.SIDEBAR_BG);
        navPanel.setBorder(new EmptyBorder(0, 10, 20, 10));

        addNav(navPanel, "\u2302", "Dashboard", () -> new DashboardPanel());
        addNav(navPanel, "\uD83C\uDF93", "Students", () -> new StudentPanel());
        addNav(navPanel, "\uD83D\uDCDA", "Courses", () -> new CoursePanel());
        addNav(navPanel, "\uD83D\uDC68\u200D\uD83C\uDFEB", "Instructors", () -> new InstructorPanel());
        addNav(navPanel, "\uD83C\uDFEC", "Departments", () -> new DepartmentPanel());
        addNav(navPanel, "\uD83D\uDDD3", "Sections", () -> new SectionPanel());
        addNav(navPanel, "\u270D", "Enrollment", () -> new EnrollmentPanel());
        addNav(navPanel, "\uD83D\uDD17", "Prerequisites", () -> new PrerequisitePanel());
        addNav(navPanel, "\uD83D\uDCCA", "Reports", () -> new ReportPanel());
        addNav(navPanel, "\uD83C\uDFAF", "Advisors", () -> new AdvisorPanel());
        addNav(navPanel, "\uD83D\uDCCB", "Teaching", () -> new TeachingAssignmentPanel());

        JScrollPane navScroll = new JScrollPane(navPanel);
        navScroll.setBorder(null);
        navScroll.getVerticalScrollBar().setUnitIncrement(16);
        navScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        navScroll.getViewport().setBackground(UITheme.SIDEBAR_BG);

        sidebar.add(navScroll, BorderLayout.CENTER);

        JLabel footer = new JLabel("<html><center>University DBMS<br><span style='color:#64748B;font-size:10px'>v1.0</span></center></html>");
        footer.setForeground(UITheme.SIDEBAR_TEXT);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setBorder(new EmptyBorder(12, 0, 16, 0));
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        sidebar.add(footer, BorderLayout.SOUTH);

        return sidebar;
    }

    private void addNav(JPanel navPanel, String icon, String label,
                        java.util.function.Supplier<JPanel> panelSupplier) {
        JButton btn = UIUtil.createNavButton(icon, label);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(e -> {
            setActiveNav(btn);
            showPanel(panelSupplier.get(), label);
        });
        navButtons.put(label, btn);
        navPanel.add(btn);
        navPanel.add(Box.createVerticalStrut(2));
    }

    private void setActiveNav(JButton button) {
        if (activeNavButton != null) {
            UIUtil.setNavActive(activeNavButton, false);
        }
        activeNavButton = button;
        UIUtil.setNavActive(button, true);
    }

    private void showPanel(JPanel panel, String navLabel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();

        if (navButtons.containsKey(navLabel)) {
            setActiveNav(navButtons.get(navLabel));
        }
    }
}
