package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UITheme {

    public static final Color SIDEBAR_BG       = new Color(15, 23, 42);
    public static final Color SIDEBAR_HOVER    = new Color(30, 41, 59);
    public static final Color SIDEBAR_ACTIVE   = new Color(99, 102, 241);
    public static final Color SIDEBAR_TEXT     = new Color(148, 163, 184);
    public static final Color SIDEBAR_TEXT_ACTIVE = Color.WHITE;

    public static final Color CONTENT_BG       = new Color(241, 245, 249);
    public static final Color CARD_BG          = Color.WHITE;
    public static final Color CARD_BORDER      = new Color(226, 232, 240);
    public static final Color TEXT_PRIMARY     = new Color(15, 23, 42);
    public static final Color TEXT_SECONDARY   = new Color(100, 116, 139);
    public static final Color ACCENT           = new Color(99, 102, 241);
    public static final Color ACCENT_LIGHT     = new Color(238, 242, 255);
    public static final Color SUCCESS          = new Color(16, 185, 129);
    public static final Color DANGER           = new Color(239, 68, 68);
    public static final Color WARNING          = new Color(245, 158, 11);

    public static final Font FONT_TITLE    = new Font("SansSerif", Font.BOLD, 26);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_LABEL    = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_BODY     = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_STAT     = new Font("SansSerif", Font.BOLD, 36);

    public static void init() {
        try {
            UIManager.setLookAndFeel(
                    "com.formdev.flatlaf.FlatIntelliJLaf"
            );
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ignored) {
            }
        }

        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("ScrollBar.width", 10);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("Table.showHorizontalLines", false);
        UIManager.put("Table.showVerticalLines", false);
        UIManager.put("Table.intercellSpacing", new Dimension(0, 0));
        UIManager.put("Table.selectionBackground", ACCENT_LIGHT);
        UIManager.put("Table.selectionForeground", TEXT_PRIMARY);
        UIManager.put("Table.rowHeight", 38);
    }

    public static JPanel createPageHeader(String title, String subtitle) {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(TEXT_PRIMARY);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(FONT_SUBTITLE);
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setBorder(new EmptyBorder(4, 0, 0, 0));

        header.add(titleLabel, BorderLayout.NORTH);
        header.add(subtitleLabel, BorderLayout.CENTER);
        return header;
    }

    public static JPanel createCard(String cardTitle, JComponent content) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(18, 20, 18, 20)
        ));

        if (cardTitle != null && !cardTitle.isEmpty()) {
            JLabel label = new JLabel(cardTitle);
            label.setFont(FONT_LABEL);
            label.setForeground(TEXT_PRIMARY);
            card.add(label, BorderLayout.NORTH);
        }

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    public static JScrollPane createTableScroll(JTable table) {
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(CARD_BORDER, 1, true));
        scroll.getViewport().setBackground(CARD_BG);
        return scroll;
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(38);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(FONT_BODY);
        table.setBackground(CARD_BG);
        table.setSelectionBackground(ACCENT_LIGHT);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_LABEL);
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(TEXT_SECONDARY);
        header.setPreferredSize(new Dimension(header.getWidth(), 42));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(new EmptyBorder(0, 12, 0, 12));
        table.setDefaultRenderer(Object.class, renderer);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        t, value, isSelected, hasFocus, row, column);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                setBackground(new Color(248, 250, 252));
                setForeground(TEXT_SECONDARY);
                return c;
            }
        });
    }

    public static void styleTextField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setPreferredSize(new Dimension(200, 38));
    }

    public static void styleComboBox(JComboBox<?> combo) {
        combo.setFont(FONT_BODY);
        combo.setPreferredSize(new Dimension(200, 38));
    }

    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_SECONDARY);
        return label;
    }

    public static JPanel createFormGrid(int rows, int cols, Component... components) {
        JPanel grid = new JPanel(new GridLayout(rows, cols, 12, 10));
        grid.setOpaque(false);
        for (Component c : components) {
            grid.add(c);
        }
        return grid;
    }

    public static JPanel createButtonBar(Component... buttons) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(12, 0, 0, 0));
        for (Component b : buttons) {
            bar.add(b);
        }
        return bar;
    }

    public static JPanel wrapContent(JPanel page, String title, String subtitle,
                                     JComponent leftPanel, JTable table) {
        page.removeAll();
        page.setLayout(new BorderLayout(0, 0));
        page.setBackground(CONTENT_BG);
        page.setBorder(new EmptyBorder(24, 28, 24, 28));

        page.add(createPageHeader(title, subtitle), BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setBorder(null);
        split.setDividerSize(6);
        split.setBackground(CONTENT_BG);
        split.setOpaque(false);

        JPanel leftWrap = new JPanel(new BorderLayout());
        leftWrap.setOpaque(false);
        leftWrap.setPreferredSize(new Dimension(380, 0));
        leftWrap.setMinimumSize(new Dimension(320, 0));
        leftWrap.add(leftPanel, BorderLayout.CENTER);

        split.setLeftComponent(leftWrap);
        split.setRightComponent(createCard("Records", createTableScroll(table)));
        split.setResizeWeight(0.32);

        page.add(split, BorderLayout.CENTER);
        return page;
    }
}
