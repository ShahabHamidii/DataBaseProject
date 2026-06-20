package ui;

import dao.ReportDAO;
import util.UITheme;
import util.UIUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(UITheme.CONTENT_BG);
        setBorder(new EmptyBorder(24, 28, 24, 28));

        add(buildHeader(), BorderLayout.NORTH);

        JPanel loading = new JPanel(new GridBagLayout());
        loading.setOpaque(false);
        JLabel lbl = new JLabel("Loading dashboard...");
        lbl.setForeground(UITheme.TEXT_SECONDARY);
        loading.add(lbl);

        add(loading, BorderLayout.CENTER);

        new SwingWorker<int[], Void>() {
            @Override
            protected int[] doInBackground() {
                return new ReportDAO().getDashboardStats();
            }

            @Override
            protected void done() {
                try {
                    int[] stats = get();
                    remove(loading);
                    add(buildDashboard(stats), BorderLayout.CENTER);
                    revalidate();
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private JPanel buildHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("University Management System");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("Academic Administration Dashboard");
        subtitle.setForeground(UITheme.TEXT_SECONDARY);

        left.add(title);
        left.add(subtitle);

        JLabel dateLabel = new JLabel(LocalDate.now().toString());
        dateLabel.setForeground(UITheme.TEXT_SECONDARY);

        header.add(left, BorderLayout.WEST);
        header.add(dateLabel, BorderLayout.EAST);

        return header;
    }

    private JPanel buildDashboard(int[] s) {

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setOpaque(false);

        JPanel stats = new JPanel(new GridLayout(1, 4, 16, 0));
        stats.setOpaque(false);

        stats.add(stat("Students", s[0]));
        stats.add(stat("Courses", s[1]));
        stats.add(stat("Instructors", s[2]));
        stats.add(stat("Enrollments", s[3]));

        JPanel welcome = new JPanel(new GridLayout(5, 1));
        welcome.setBorder(new LineBorder(UITheme.CARD_BORDER, 1, true));
        welcome.setBackground(UITheme.CARD_BG);

        welcome.add(new JLabel("Welcome Admin"));
        welcome.add(new JLabel("Total Students: " + s[0]));
        welcome.add(new JLabel("Total Courses: " + s[1]));
        welcome.add(new JLabel("Total Instructors: " + s[2]));
        welcome.add(new JLabel("System Status: Online | DB: Connected"));

        JPanel actions = new JPanel(new GridLayout(1, 4, 15, 0));
        actions.setOpaque(false);

        actions.add(UIUtil.createActionButton("Add Student"));
        actions.add(UIUtil.createActionButton("Add Course"));
        actions.add(UIUtil.createActionButton("Add Instructor"));
        actions.add(UIUtil.createActionButton("Enroll Student"));

//        JList<String> activity = new JList<>(new String[]{
//                "Student Zhang added",
//                "Course CS-347 updated",
//                "Enrollment completed",
//                "Instructor assigned"
//        });
//
//        JScrollPane activityScroll = new JScrollPane(activity);
//
//        JPanel activityPanel = new JPanel(new BorderLayout());
//        activityPanel.setBorder(new LineBorder(UITheme.CARD_BORDER, 1, true));
//        activityPanel.setBackground(UITheme.CARD_BG);
//        activityPanel.add(new JLabel("Recent Activity"), BorderLayout.NORTH);
//        activityPanel.add(activityScroll, BorderLayout.CENTER);
//

        root.add(stats);
        root.add(Box.createVerticalStrut(16));
        root.add(welcome);
        root.add(Box.createVerticalStrut(16));
        root.add(actions);
        root.add(Box.createVerticalStrut(16));
//        root.add(activityPanel);

        return root;
    }

    private JPanel stat(String title, int value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(UITheme.CARD_BG);
        p.setBorder(new LineBorder(UITheme.CARD_BORDER, 1, true));

        JLabel t = new JLabel(title);
        JLabel v = new JLabel(String.valueOf(value));

        t.setForeground(UITheme.TEXT_SECONDARY);
        v.setFont(UITheme.FONT_STAT);
        v.setForeground(UITheme.ACCENT);

        p.add(t, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);

        return p;
    }
}