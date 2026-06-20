package ui;

import model.User;
import util.UITheme;
import util.UIUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MainDashboard extends JFrame {

    private JPanel contentPanel;
    private final Map<String, JButton> navButtons = new LinkedHashMap<>();
    private JButton activeNavButton;
    private final User currentUser;

    public MainDashboard(User user) {
        this.currentUser = user;
        setTitle("UniSys — " + capitalize(user.getRole().name())
                + "  |  " + user.getUsername());
        setSize(1280, 820);
        setMinimumSize(new Dimension(1024, 680));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        SwingUtilities.invokeLater(() -> showPanel(new DashboardPanel(currentUser), "Dashboard"));
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 0));
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UITheme.CONTENT_BG);
        add(buildSidebar(), BorderLayout.WEST);
        add(contentPanel,   BorderLayout.CENTER);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(UITheme.SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(240, 0));

        sidebar.add(buildBrand(),   BorderLayout.NORTH);
        sidebar.add(buildNav(),     BorderLayout.CENTER);
        sidebar.add(buildFooter(),  BorderLayout.SOUTH);

        return sidebar;
    }

    private JPanel buildBrand() {
        JPanel brand = new JPanel(new BorderLayout());
        brand.setBackground(UITheme.SIDEBAR_BG);
        brand.setBorder(new EmptyBorder(28, 22, 24, 22));

        JLabel icon = new JLabel("🎓");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 28));
        icon.setBorder(new EmptyBorder(0, 0, 0, 10));

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setOpaque(false);

        JLabel name = new JLabel("UniSys");
        name.setFont(new Font("SansSerif", Font.BOLD, 20));
        name.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Management System");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(UITheme.SIDEBAR_TEXT);

        text.add(name);
        text.add(sub);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setOpaque(false);
        row.add(icon);
        row.add(text);
        brand.add(row, BorderLayout.CENTER);

        JLabel roleBadge = new JLabel(currentUser.getRole().name(), SwingConstants.CENTER);
        roleBadge.setFont(new Font("SansSerif", Font.BOLD, 10));
        roleBadge.setForeground(Color.WHITE);
        roleBadge.setOpaque(true);
        roleBadge.setBackground(roleColor(currentUser.getRole()));
        roleBadge.setBorder(new EmptyBorder(3, 10, 3, 10));
        roleBadge.setPreferredSize(new Dimension(80, 20));

        JPanel badgeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 6));
        badgeRow.setOpaque(false);
        badgeRow.add(roleBadge);
        brand.add(badgeRow, BorderLayout.SOUTH);

        return brand;
    }

    private JScrollPane buildNav() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(UITheme.SIDEBAR_BG);
        navPanel.setBorder(new EmptyBorder(0, 10, 20, 10));

        User.Role role = currentUser.getRole();

        addNav(navPanel, "⌂", "Dashboard", () -> new DashboardPanel(currentUser));

        if (role == User.Role.ADMIN || role == User.Role.STUDENT) {
            addNav(navPanel, "🎓", "Students", StudentPanel::new);
        }
        if (role == User.Role.ADMIN || role == User.Role.INSTRUCTOR) {
            addNav(navPanel, "📚", "Courses", CoursePanel::new);
            addNav(navPanel, "👨‍🏫", "Instructors", InstructorPanel::new);
        }
        if (role == User.Role.ADMIN) {
            addNav(navPanel, "🏤", "Departments",  DepartmentPanel::new);
            addNav(navPanel, "🗓", "Sections",     SectionPanel::new);
            addNav(navPanel, "✍", "Enrollment",   EnrollmentPanel::new);
            addNav(navPanel, "🔗", "Prerequisites", PrerequisitePanel::new);
            addNav(navPanel, "📊", "Reports",      ReportPanel::new);
            addNav(navPanel, "🎯", "Advisors",     AdvisorPanel::new);
            addNav(navPanel, "📋", "Teaching",     TeachingAssignmentPanel::new);
        }
        if (role == User.Role.STUDENT) {
            addNav(navPanel, "✍", "My Enrollment", EnrollmentPanel::new);
            addNav(navPanel, "📊", "My Grades",    ReportPanel::new);
        }
        if (role == User.Role.INSTRUCTOR) {
            addNav(navPanel, "🗓", "My Sections",  SectionPanel::new);
            addNav(navPanel, "📋", "My Teaching",  TeachingAssignmentPanel::new);
        }

        navPanel.add(Box.createVerticalStrut(16));
        navPanel.add(makeSeparator());
        navPanel.add(Box.createVerticalStrut(8));
        addLogout(navPanel);

        JScrollPane scroll = new JScrollPane(navPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getViewport().setBackground(UITheme.SIDEBAR_BG);
        return scroll;
    }

    private JSeparator makeSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0x334155));
        sep.setBackground(UITheme.SIDEBAR_BG);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    private JLabel buildFooter() {
        JLabel footer = new JLabel(
                "<html><center>University DBMS<br>"
                        + "<span style='color:#64748B;font-size:10px'>v2.1  •  "
                        + currentUser.getUsername() + "</span></center></html>",
                SwingConstants.CENTER
        );
        footer.setForeground(UITheme.SIDEBAR_TEXT);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setBorder(new EmptyBorder(12, 0, 16, 0));
        return footer;
    }

    private void addNav(JPanel panel, String icon, String label,
                        Supplier<JPanel> supplier) {
        JButton btn = UIUtil.createNavButton(icon, label);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(e -> {
            setActiveNav(btn);
            loadPanel(supplier, label);
        });
        navButtons.put(label, btn);
        panel.add(btn);
        panel.add(Box.createVerticalStrut(2));
    }

    private void addLogout(JPanel panel) {
        JButton btn = UIUtil.createNavButton("⏻", "Logout");
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setForeground(new Color(0xFCA5A5));
        btn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        });
        panel.add(btn);
    }

    private void setActiveNav(JButton button) {
        if (activeNavButton != null) UIUtil.setNavActive(activeNavButton, false);
        activeNavButton = button;
        UIUtil.setNavActive(button, true);
    }

    private void loadPanel(Supplier<JPanel> supplier, String label) {
        JPanel loading = new JPanel(new GridBagLayout());
        loading.setBackground(UITheme.CONTENT_BG);
        JLabel lbl = new JLabel("Loading...");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setForeground(new Color(0x94A3B8));
        loading.add(lbl);
        showPanel(loading, null);

        SwingWorker<JPanel, Void> worker = new SwingWorker<>() {
            @Override protected JPanel doInBackground() { return supplier.get(); }
            @Override protected void done() {
                try { showPanel(get(), label); }
                catch (Exception ex) { ex.printStackTrace(); }
            }
        };
        worker.execute();
    }

    private void showPanel(JPanel panel, String navLabel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        if (navLabel != null && navButtons.containsKey(navLabel))
            setActiveNav(navButtons.get(navLabel));
    }

    private Color roleColor(User.Role role) {
        return switch (role) {
            case ADMIN      -> new Color(0x7C3AED);
            case INSTRUCTOR -> new Color(0x0369A1);
            case STUDENT    -> new Color(0x15803D);
        };
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.charAt(0) + s.substring(1).toLowerCase();
    }

    public void navigateTo(String label) {
        JButton btn = navButtons.get(label);
        if (btn != null) btn.doClick();
    }
}