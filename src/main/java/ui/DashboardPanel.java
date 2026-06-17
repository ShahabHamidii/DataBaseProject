package ui;

import dao.ReportDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UITheme.CONTENT_BG);
        setBorder(new EmptyBorder(24, 28, 24, 28));

        add(UITheme.createPageHeader(
                "Dashboard",
                "Overview of your university database"
        ), BorderLayout.NORTH);

        ReportDAO dao = new ReportDAO();

        JPanel statsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        statsRow.setOpaque(false);
        statsRow.setBorder(new EmptyBorder(0, 0, 20, 0));

        statsRow.add(createStatCard("Students", dao.getStudentCount(),
                new Color(99, 102, 241), "\uD83C\uDF93"));
        statsRow.add(createStatCard("Courses", dao.getCourseCount(),
                new Color(139, 92, 246), "\uD83D\uDCDA"));
        statsRow.add(createStatCard("Instructors", dao.getInstructorCount(),
                new Color(236, 72, 153), "\uD83D\uDC68\u200D\uD83C\uDFEB"));
        statsRow.add(createStatCard("Enrollments", dao.getEnrollmentCount(),
                new Color(20, 184, 166), "\uD83D\uDCCB"));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(dao.getStudentCount(), "Count", "Students");
        dataset.addValue(dao.getCourseCount(), "Count", "Courses");
        dataset.addValue(dao.getInstructorCount(), "Count", "Instructors");
        dataset.addValue(dao.getEnrollmentCount(), "Count", "Enrollments");

        JFreeChart chart = ChartFactory.createBarChart(
                null, null, null, dataset
        );
        styleChart(chart);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(new LineBorder(UITheme.CARD_BORDER, 1, true));
        chartPanel.setBackground(UITheme.CARD_BG);
        chartPanel.setPreferredSize(new Dimension(0, 320));

        JPanel chartCard = UITheme.createCard("Statistics Overview", chartPanel);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);
        center.add(statsRow, BorderLayout.NORTH);
        center.add(chartCard, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, int value, Color accent, String emoji) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UITheme.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UITheme.CARD_BORDER, 1, true),
                new EmptyBorder(20, 22, 20, 22)
        ));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));

        JPanel iconBg = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        iconBg.setOpaque(true);
        iconBg.setBackground(blend(accent, Color.WHITE, 0.85f));
        iconBg.setPreferredSize(new Dimension(44, 44));
        iconBg.add(emojiLabel);

        top.add(iconBg, BorderLayout.EAST);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UITheme.FONT_LABEL);
        titleLabel.setForeground(UITheme.TEXT_SECONDARY);

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(UITheme.FONT_STAT);
        valueLabel.setForeground(accent);
        valueLabel.setBorder(new EmptyBorder(8, 0, 0, 0));

        card.add(top, BorderLayout.NORTH);
        card.add(titleLabel, BorderLayout.CENTER);
        card.add(valueLabel, BorderLayout.SOUTH);

        return card;
    }

    private void styleChart(JFreeChart chart) {
        chart.setBackgroundPaint(UITheme.CARD_BG);
        chart.getLegend().setVisible(false);

        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(UITheme.CARD_BG);
        plot.setOutlineVisible(false);

        if (plot instanceof CategoryPlot categoryPlot) {
            categoryPlot.setDomainGridlinesVisible(false);
            categoryPlot.setRangeGridlinePaint(UITheme.CARD_BORDER);

            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            renderer.setSeriesPaint(0, UITheme.ACCENT);
            renderer.setMaximumBarWidth(0.15);
            renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
            renderer.setShadowVisible(false);
        }

        if (chart.getTitle() != null) {
            chart.getTitle().setVisible(false);
        }
    }

    private Color blend(Color base, Color overlay, float ratio) {
        return new Color(
                (int) (base.getRed() * ratio + overlay.getRed() * (1 - ratio)),
                (int) (base.getGreen() * ratio + overlay.getGreen() * (1 - ratio)),
                (int) (base.getBlue() * ratio + overlay.getBlue() * (1 - ratio))
        );
    }
}
