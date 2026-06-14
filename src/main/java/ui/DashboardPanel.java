package ui;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {

        setLayout(
                new BorderLayout()
        );

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

        cards.add(
                createCard(
                        "Students",
                        "0"
                )
        );

        cards.add(
                createCard(
                        "Courses",
                        "0"
                )
        );

        cards.add(
                createCard(
                        "Instructors",
                        "0"
                )
        );

        cards.add(
                createCard(
                        "Enrollments",
                        "0"
                )
        );

        add(
                cards,
                BorderLayout.CENTER
        );
    }

    private JPanel createCard(
            String title,
            String value
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BorderLayout()
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        40
                )
        );

        valueLabel.setHorizontalAlignment(
                SwingConstants.CENTER
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
}