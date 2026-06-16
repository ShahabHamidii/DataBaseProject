package ui;

import dao.TimeSlotDAO;
import model.TimeSlot;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TimeSlotForm extends JFrame {

    private JTextField idField;
    private JTextField dayField;
    private JTextField startField;
    private JTextField endField;

    private JTable table;
    private DefaultTableModel tableModel;

    public TimeSlotForm() {

        setTitle("Time Slot Management");

        setSize(800,600);

        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        JPanel panel =
                new JPanel(
                        new GridLayout(5,2,10,10)
                );

        idField = new JTextField();
        dayField = new JTextField();
        startField = new JTextField();
        endField = new JTextField();

        JButton addButton =
                new JButton("Add");

        JButton loadButton =
                new JButton("Load");

        panel.add(new JLabel("Time Slot ID"));
        panel.add(idField);

        panel.add(new JLabel("Day"));
        panel.add(dayField);

        panel.add(new JLabel("Start Time"));
        panel.add(startField);

        panel.add(new JLabel("End Time"));
        panel.add(endField);

        panel.add(addButton);
        panel.add(loadButton);

        add(panel, BorderLayout.NORTH);

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "ID",
                                "Day",
                                "Start",
                                "End"
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
                e -> addTimeSlot()
        );

        loadButton.addActionListener(
                e -> loadTimeSlots()
        );
    }

    private void addTimeSlot() {

        TimeSlot slot =
                new TimeSlot(
                        idField.getText(),
                        dayField.getText(),
                        startField.getText(),
                        endField.getText()
                );

        boolean result =
                new TimeSlotDAO()
                        .addTimeSlot(slot);

        if(result) {

            loadTimeSlots();
        }
    }

    private void loadTimeSlots() {

        tableModel.setRowCount(0);

        for (

                TimeSlot slot :

                new TimeSlotDAO()
                        .getAllTimeSlots()

        ) {

            tableModel.addRow(
                    new Object[]{
                            slot.getTimeSlotId(),
                            slot.getDay(),
                            slot.getStartTime(),
                            slot.getEndTime()
                    }
            );
        }
    }
}