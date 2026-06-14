package ui;

import dao.ReportDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class DashboardChartForm extends JFrame {

    public DashboardChartForm() {

        setTitle("Dashboard Statistics");

        setSize(900,600);

        setLocationRelativeTo(null);

        createChart();

        setVisible(true);
    }

    private void createChart() {

        ReportDAO dao =
                new ReportDAO();

        DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        dataset.addValue(
                dao.getStudentCount(),
                "Count",
                "Students"
        );

        dataset.addValue(
                dao.getCourseCount(),
                "Count",
                "Courses"
        );

        dataset.addValue(
                dao.getInstructorCount(),
                "Count",
                "Instructors"
        );

        dataset.addValue(
                dao.getEnrollmentCount(),
                "Count",
                "Enrollments"
        );

        JFreeChart chart =
                ChartFactory.createBarChart(
                        "University Statistics",
                        "Entity",
                        "Count",
                        dataset
                );

        ChartPanel panel =
                new ChartPanel(chart);

        setContentPane(panel);
    }
}