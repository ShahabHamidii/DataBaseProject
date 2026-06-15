package ui;

import dao.StatisticsDAO;

import javax.swing.*;
import java.awt.*;

public class DashboardForm extends JFrame {

    private JLabel studentValue;
    private JLabel courseValue;
    private JLabel instructorValue;
    private JLabel enrollmentValue;

    public DashboardForm() {

        setTitle("University Dashboard");

        setSize(900,600);

        setLocationRelativeTo(null);

        initComponents();

        loadStatistics();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        JLabel title =
                new JLabel(
                        "University Dashboard"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        add(
                title,
                BorderLayout.NORTH
        );

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                20,
                                20
                        )
                );

        cards.setBorder(
                BorderFactory.createEmptyBorder(
                        40,
                        40,
                        40,
                        40
                )
        );

        studentValue =
                new JLabel("0");

        courseValue =
                new JLabel("0");

        instructorValue =
                new JLabel("0");

        enrollmentValue =
                new JLabel("0");

        cards.add(
                createCard(
                        "Students",
                        studentValue
                )
        );

        cards.add(
                createCard(
                        "Courses",
                        courseValue
                )
        );

        cards.add(
                createCard(
                        "Instructors",
                        instructorValue
                )
        );

        cards.add(
                createCard(
                        "Enrollments",
                        enrollmentValue
                )
        );

        add(
                cards,
                BorderLayout.CENTER
        );
    }

    private JPanel createCard(
            String title,
            JLabel valueLabel
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Color.GRAY
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        valueLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        42
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }

    private void loadStatistics() {

        StatisticsDAO dao =
                new StatisticsDAO();

        studentValue.setText(
                String.valueOf(
                        dao.getStudentCount()
                )
        );

        courseValue.setText(
                String.valueOf(
                        dao.getCourseCount()
                )
        );

        instructorValue.setText(
                String.valueOf(
                        dao.getDepartmentCount()
                )
        );

        enrollmentValue.setText(
                String.valueOf(
                        dao.getEnrollmentCount()
                )
        );
    }
}