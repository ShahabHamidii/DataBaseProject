package ui;

import dao.SectionDAO;
import model.Section;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SectionForm extends JFrame {

    private JTextField courseIdField;
    private JTextField secIdField;
    private JTextField semesterField;
    private JTextField yearField;
    private JTextField buildingField;
    private JTextField roomField;
    private JTextField timeSlotField;

    private JButton addButton;
    private JButton loadButton;

    private JTable table;

    private DefaultTableModel tableModel;

    public SectionForm() {

        setTitle("Section Management");

        setSize(1100,700);

        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        JPanel panel =
                new JPanel(
                        new GridLayout(8,2)
                );

        courseIdField = new JTextField();
        secIdField = new JTextField();
        semesterField = new JTextField();
        yearField = new JTextField();
        buildingField = new JTextField();
        roomField = new JTextField();
        timeSlotField = new JTextField();

        panel.add(new JLabel("Course ID"));
        panel.add(courseIdField);

        panel.add(new JLabel("Section ID"));
        panel.add(secIdField);

        panel.add(new JLabel("Semester"));
        panel.add(semesterField);

        panel.add(new JLabel("Year"));
        panel.add(yearField);

        panel.add(new JLabel("Building"));
        panel.add(buildingField);

        panel.add(new JLabel("Room"));
        panel.add(roomField);

        panel.add(new JLabel("Time Slot"));
        panel.add(timeSlotField);

        addButton =
                new JButton("Add");

        loadButton =
                new JButton("Load");

        panel.add(addButton);
        panel.add(loadButton);

        add(panel, BorderLayout.NORTH);

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Course",
                                "Section",
                                "Semester",
                                "Year",
                                "Building",
                                "Room",
                                "Time Slot"
                        },
                        0
                );

        table =
                new JTable(tableModel);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        addButton.addActionListener(
                e -> addSection()
        );

        loadButton.addActionListener(
                e -> loadSections()
        );
    }

    private void addSection() {

        Section section =
                new Section(
                        courseIdField.getText(),
                        secIdField.getText(),
                        semesterField.getText(),
                        Integer.parseInt(
                                yearField.getText()
                        ),
                        buildingField.getText(),
                        roomField.getText(),
                        timeSlotField.getText()
                );

        boolean result =
                new SectionDAO()
                        .addSection(section);

        if(result) {

            loadSections();
        }
    }

    private void loadSections() {

        tableModel.setRowCount(0);

        for(
                Section section :
                new SectionDAO()
                        .getAllSections()
        ) {

            tableModel.addRow(

                    new Object[]{

                            section.getCourseId(),
                            section.getSecId(),
                            section.getSemester(),
                            section.getYear(),
                            section.getBuilding(),
                            section.getRoomNumber(),
                            section.getTimeSlotId()
                    }
            );
        }
    }
}