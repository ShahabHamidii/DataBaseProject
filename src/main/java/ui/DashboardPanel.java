package ui;

import model.User;
import dao.ReportDAO;
import util.UITheme;
import util.UIUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;

public class DashboardPanel extends JPanel {

    private final User currentUser;

    public DashboardPanel(User user) {
        this.currentUser = user;
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

        JLabel subtitle = new JLabel("Welcome, " + capitalize(currentUser.getRole().name())
               );
        subtitle.setForeground(UITheme.TEXT_SECONDARY);

        left.add(title);
        left.add(subtitle);

        JLabel dateLabel = new JLabel(LocalDate.now().toString());
        dateLabel.setForeground(UITheme.TEXT_SECONDARY);

        header.add(left,      BorderLayout.WEST);
        header.add(dateLabel, BorderLayout.EAST);
        return header;
    }


    private JPanel buildDashboard(int[] s) {
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setOpaque(false);

        JPanel stats = new JPanel(new GridLayout(1, 4, 16, 0));
        stats.setOpaque(false);
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        stats.add(statCard("Students",    s[0], new Color(99,  102, 241), "🎓"));
        stats.add(statCard("Courses",     s[1], new Color(139,  92, 246), "📚"));
        stats.add(statCard("Instructors", s[2], new Color(236,  72, 153), "👨‍🏫"));
        stats.add(statCard("Enrollments", s[3], new Color(20,  184, 166), "📋"));

        JPanel status = new JPanel(new GridLayout(1, 4, 16, 0));
        status.setOpaque(false);
        status.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        status.add(infoCard("Role",     capitalize(currentUser.getRole().name()), roleColor(currentUser.getRole())));
        status.add(infoCard("User",     currentUser.getUsername(),                new Color(0x0369A1)));
        status.add(infoCard("Database", "Connected ✓",                           new Color(0x16A34A)));
        status.add(infoCard("Date",     LocalDate.now().toString(),               new Color(0x92400E)));

        root.add(stats);
        root.add(Box.createVerticalStrut(16));
        root.add(status);

        if (currentUser.getRole() == User.Role.ADMIN) {
            root.add(Box.createVerticalStrut(16));
            root.add(buildQuickActions());
        }

        return root;
    }

    private JPanel statCard(String title, int value, Color accent, String emoji) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(UITheme.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UITheme.CARD_BORDER, 1, true),
                new EmptyBorder(18, 20, 18, 20)
        ));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel emojiLbl = new JLabel(emoji, SwingConstants.CENTER);
        emojiLbl.setFont(new Font("SansSerif", Font.PLAIN, 22));
        emojiLbl.setOpaque(true);
        emojiLbl.setBackground(blend(accent, Color.WHITE, 0.85f));
        emojiLbl.setPreferredSize(new Dimension(44, 44));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(UITheme.FONT_LABEL);
        titleLbl.setForeground(UITheme.TEXT_SECONDARY);

        top.add(titleLbl,  BorderLayout.WEST);
        top.add(emojiLbl,  BorderLayout.EAST);

        JLabel valueLbl = new JLabel(String.valueOf(value));
        valueLbl.setFont(UITheme.FONT_STAT);
        valueLbl.setForeground(accent);

        card.add(top,      BorderLayout.NORTH);
        card.add(valueLbl, BorderLayout.CENTER);
        return card;
    }

    private JPanel infoCard(String label, String value, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 4));
        card.setBackground(UITheme.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(accent, 1, true),
                new EmptyBorder(12, 16, 12, 16)
        ));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(UITheme.TEXT_SECONDARY);

        JLabel val = new JLabel(value);
        val.setFont(new Font("SansSerif", Font.BOLD, 14));
        val.setForeground(accent);

        card.add(lbl, BorderLayout.NORTH);
        card.add(val, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildQuickActions() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel title = new JLabel("Quick Actions");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setBorder(new EmptyBorder(0, 0, 8, 0));

        JPanel buttons = new JPanel(new GridLayout(1, 4, 12, 0));
        buttons.setOpaque(false);

        JButton s = UIUtil.createButton("➕ Add Student",    UIUtil.ButtonStyle.PRIMARY);
        JButton c = UIUtil.createButton("➕ Add Course",     UIUtil.ButtonStyle.PRIMARY);
        JButton i = UIUtil.createButton("➕ Add Instructor", UIUtil.ButtonStyle.PRIMARY);
        JButton e = UIUtil.createButton("✍ Enroll Student", UIUtil.ButtonStyle.SUCCESS);


        s.addActionListener(ev -> navigateTo("Students"));
        c.addActionListener(ev -> navigateTo("Courses"));
        i.addActionListener(ev -> navigateTo("Instructors"));
        e.addActionListener(ev -> navigateTo("Enrollment"));

        buttons.add(s);
        buttons.add(c);
        buttons.add(i);
        buttons.add(e);

        panel.add(title,   BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.CENTER);
        return panel;
    }

    private void navigateTo(String panelName) {
        Window win = SwingUtilities.getWindowAncestor(this);
        if (win instanceof MainDashboard md) {
            md.navigateTo(panelName);
        }
    }

    private Color roleColor(User.Role role) {
        return switch (role) {
            case ADMIN      -> new Color(0x7C3AED);
            case INSTRUCTOR -> new Color(0x0369A1);
            case STUDENT    -> new Color(0x15803D);
        };
    }

    private Color blend(Color base, Color overlay, float ratio) {
        return new Color(
                (int)(base.getRed()   * ratio + overlay.getRed()   * (1 - ratio)),
                (int)(base.getGreen() * ratio + overlay.getGreen() * (1 - ratio)),
                (int)(base.getBlue()  * ratio + overlay.getBlue()  * (1 - ratio))
        );
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.charAt(0) + s.substring(1).toLowerCase();
    }
}