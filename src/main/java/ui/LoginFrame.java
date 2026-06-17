package ui;

import dao.UserDAO;
import database.DBConnection;
import model.User;
import util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;
    private JLabel loadingLabel;

    public LoginFrame() {
        setTitle("University Management System — Login");
        setSize(420, 520);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.CONTENT_BG);
        setContentPane(root);

        // ── Header ──────────────────────────────────────────
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(UITheme.SIDEBAR_BG);
        header.setBorder(new EmptyBorder(40, 30, 30, 30));

        JLabel icon = new JLabel("🎓", SwingConstants.CENTER);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 48));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("UniSys", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("University Management System", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(UITheme.SIDEBAR_TEXT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(icon);
        header.add(Box.createVerticalStrut(8));
        header.add(title);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitle);

        root.add(header, BorderLayout.NORTH);

        // ── Form ────────────────────────────────────────────
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(UITheme.CONTENT_BG);
        form.setBorder(new EmptyBorder(36, 40, 20, 40));

        form.add(makeLabel("Username"));
        form.add(Box.createVerticalStrut(6));
        usernameField = makeTextField();
        form.add(usernameField);

        form.add(Box.createVerticalStrut(18));

        form.add(makeLabel("Password"));
        form.add(Box.createVerticalStrut(6));
        passwordField = new JPasswordField();
        styleField(passwordField);
        form.add(passwordField);

        form.add(Box.createVerticalStrut(28));

        loginButton = new JButton("Sign In");
        styleLoginButton(loginButton);
        form.add(loginButton);

        form.add(Box.createVerticalStrut(14));

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(0xEF4444));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(statusLabel);

        loadingLabel = new JLabel("Connecting...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        loadingLabel.setForeground(new Color(0x94A3B8));
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadingLabel.setVisible(false);
        form.add(loadingLabel);

        root.add(form, BorderLayout.CENTER);

        // ── Footer ──────────────────────────────────────────
        JLabel footer = new JLabel("v1.0  •  University DBMS", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 10));
        footer.setForeground(new Color(0x94A3B8));
        footer.setBorder(new EmptyBorder(0, 0, 16, 0));
        root.add(footer, BorderLayout.SOUTH);

        // ── Actions ─────────────────────────────────────────
        loginButton.addActionListener(e -> doLogin());

        // Enter کلید
        KeyAdapter enter = new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) doLogin();
            }
        };
        usernameField.addKeyListener(enter);
        passwordField.addKeyListener(enter);

        usernameField.requestFocusInWindow();
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter username and password.");
            return;
        }

        loginButton.setEnabled(false);
        loadingLabel.setVisible(true);
        statusLabel.setText("");

        // لاگین رو توی thread جدا انجام بده تا UI فریز نکنه
        SwingWorker<User, Void> worker = new SwingWorker<>() {
            @Override
            protected User doInBackground() {
                return UserDAO.login(username, password);
            }

            @Override
            protected void done() {
                try {
                    User user = get();
                    loadingLabel.setVisible(false);
                    loginButton.setEnabled(true);

                    if (user != null) {
                        dispose();
                        new MainDashboard(user).setVisible(true);
                    } else {
                        statusLabel.setText("Invalid username or password.");
                        passwordField.setText("");
                        passwordField.requestFocusInWindow();
                    }
                } catch (Exception ex) {
                    loadingLabel.setVisible(false);
                    loginButton.setEnabled(true);
                    statusLabel.setText("Connection error. Try again.");
                }
            }
        };
        worker.execute();
    }

    // ── Helpers ─────────────────────────────────────────────
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(new Color(0x374151));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField makeTextField() {
        JTextField field = new JTextField();
        styleField(field);
        return field;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(0x1F2937));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD1D5DB), 1, true),
                new EmptyBorder(10, 14, 10, 14)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleLoginButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(UITheme.ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        btn.addMouseListener(new MouseAdapter() {
            final Color normal = UITheme.ACCENT;
            final Color hover  = UITheme.ACCENT.darker();
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            @Override public void mouseExited (MouseEvent e) { btn.setBackground(normal); }
        });
    }
}