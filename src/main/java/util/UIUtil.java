package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIUtil {

    public enum ButtonStyle {
        PRIMARY, SUCCESS, DANGER, SECONDARY, GHOST
    }

    public static JButton createButton(String text, ButtonStyle style) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(UITheme.FONT_LABEL);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 18, 10, 18));

        switch (style) {
            case PRIMARY -> {
                button.setBackground(UITheme.ACCENT);
                button.setForeground(Color.WHITE);
                button.setOpaque(true);
                button.setBorderPainted(false);
            }
            case SUCCESS -> {
                button.setBackground(UITheme.SUCCESS);
                button.setForeground(Color.WHITE);
                button.setOpaque(true);
                button.setBorderPainted(false);
            }
            case DANGER -> {
                button.setBackground(UITheme.DANGER);
                button.setForeground(Color.WHITE);
                button.setOpaque(true);
                button.setBorderPainted(false);
            }
            case SECONDARY -> {
                button.setBackground(new Color(226, 232, 240));
                button.setForeground(UITheme.TEXT_PRIMARY);
                button.setOpaque(true);
                button.setBorderPainted(false);
            }
            case GHOST -> {
                button.setBackground(UITheme.CARD_BG);
                button.setForeground(UITheme.TEXT_SECONDARY);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(UITheme.CARD_BORDER, 1, true));
            }
        }

        button.addMouseListener(new MouseAdapter() {
            Color original = button.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(darken(original, 0.12f));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(original);
            }
        });

        return button;
    }

    public static JButton createNavButton(String icon, String text) {
        JButton button = new JButton("<html><span style='font-size:16px'>" + icon
                + "</span>&nbsp;&nbsp;" + text + "</html>");
        button.setFocusPainted(false);
        button.setFont(UITheme.FONT_BODY);
        button.setForeground(UITheme.SIDEBAR_TEXT);
        button.setBackground(UITheme.SIDEBAR_BG);
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!Boolean.TRUE.equals(button.getClientProperty("active"))) {
                    button.setBackground(UITheme.SIDEBAR_HOVER);
                    button.setForeground(Color.WHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!Boolean.TRUE.equals(button.getClientProperty("active"))) {
                    button.setBackground(UITheme.SIDEBAR_BG);
                    button.setForeground(UITheme.SIDEBAR_TEXT);
                }
            }
        });

        return button;
    }

    public static void setNavActive(JButton button, boolean active) {
        button.putClientProperty("active", active);
        if (active) {
            button.setBackground(UITheme.SIDEBAR_ACTIVE);
            button.setForeground(UITheme.SIDEBAR_TEXT_ACTIVE);
        } else {
            button.setBackground(UITheme.SIDEBAR_BG);
            button.setForeground(UITheme.SIDEBAR_TEXT);
        }
    }

    private static Color darken(Color color, float factor) {
        return new Color(
                Math.max((int) (color.getRed() * (1 - factor)), 0),
                Math.max((int) (color.getGreen() * (1 - factor)), 0),
                Math.max((int) (color.getBlue() * (1 - factor)), 0)
        );
    }
}
