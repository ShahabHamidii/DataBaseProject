package ui;

import dao.SectionDAO;
import model.Section;
import util.ValidationUtil;
import util.UITheme;
import util.UIUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class SectionPanel extends JPanel {

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

    private JButton updateButton;
    private JButton deleteButton;

    private JTextField searchField;

    private JButton searchButton;

    public SectionPanel() {

        initComponents();

        loadSections();

    }

    private void initComponents() {

        courseIdField = new JTextField();
        secIdField = new JTextField();
        semesterField = new JTextField();
        yearField = new JTextField();
        buildingField = new JTextField();
        roomField = new JTextField();
        timeSlotField = new JTextField();
        searchField = new JTextField();

        UITheme.styleTextField(courseIdField);
        UITheme.styleTextField(secIdField);
        UITheme.styleTextField(semesterField);
        UITheme.styleTextField(yearField);
        UITheme.styleTextField(buildingField);
        UITheme.styleTextField(roomField);
        UITheme.styleTextField(timeSlotField);
        UITheme.styleTextField(searchField);

        JPanel formGrid = UITheme.createFormGrid(8, 2,
                UITheme.createFieldLabel("Course ID"), courseIdField,
                UITheme.createFieldLabel("Section ID"), secIdField,
                UITheme.createFieldLabel("Semester"), semesterField,
                UITheme.createFieldLabel("Year"), yearField,
                UITheme.createFieldLabel("Building"), buildingField,
                UITheme.createFieldLabel("Room"), roomField,
                UITheme.createFieldLabel("Time Slot"), timeSlotField,
                UITheme.createFieldLabel("Search Course"), searchField
        );

        addButton = UIUtil.createButton("Add", UIUtil.ButtonStyle.SUCCESS);
        loadButton = UIUtil.createButton("Refresh", UIUtil.ButtonStyle.SECONDARY);
        updateButton = UIUtil.createButton("Update", UIUtil.ButtonStyle.PRIMARY);
        deleteButton = UIUtil.createButton("Delete", UIUtil.ButtonStyle.DANGER);
        searchButton = UIUtil.createButton("Search", UIUtil.ButtonStyle.GHOST);

        JPanel leftPanel = UITheme.createCard("Section Details",
                UITheme.createFormGrid(2, 1, formGrid,
                        UITheme.createButtonBar(addButton, updateButton, deleteButton, loadButton, searchButton)));

        tableModel = new DefaultTableModel(
                new String[]{"Course", "Section", "Semester", "Year", "Building", "Room", "Time Slot"}, 0);
        table = new JTable(tableModel);

        UITheme.wrapContent(this, "Sections",
                "Manage course sections, rooms, and schedules",
                leftPanel, table);

        addButton.addActionListener(e -> addSection());
        loadButton.addActionListener(e -> loadSections());
        updateButton.addActionListener(e -> updateSection());
        deleteButton.addActionListener(e -> deleteSection());
        searchButton.addActionListener(e -> searchSections());
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());
    }

    private void addSection() {

        if (

                ValidationUtil.isEmpty(
                        courseIdField.getText()
                )

                        ||

                        ValidationUtil.isEmpty(
                                secIdField.getText()
                        )

                        ||

                        ValidationUtil.isEmpty(
                                semesterField.getText()
                        )

                        ||

                        ValidationUtil.isEmpty(
                                yearField.getText()
                        )

        ) {

            JOptionPane.showMessageDialog(

                    this,

                    "Required fields missing."

            );

            return;
        }
        if (

                !ValidationUtil.isInteger(
                        yearField.getText()
                )

        ) {

            JOptionPane.showMessageDialog(

                    this,

                    "Year must be numeric."

            );

            return;
        }

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
        SectionDAO dao =
                new SectionDAO();

        if(
                dao.exists(
                        courseIdField.getText(),
                        secIdField.getText(),
                        semesterField.getText(),
                        Integer.parseInt(
                                yearField.getText()
                        )
                )
        ){
            JOptionPane.showMessageDialog(
                    this,
                    "Section already exists"
            );
            return;
        }

        if(result){

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Successful"
            );

            loadSections();

            clearFields();

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Failed"
            );
        }
    }

    private void loadSections() {

        tableModel.setRowCount(0);

        for (
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

    private void fillFormFromTable() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            return;
        }

        courseIdField.setText(
                tableModel.getValueAt(row, 0)
                        .toString()
        );

        secIdField.setText(
                tableModel.getValueAt(row, 1)
                        .toString()
        );

        semesterField.setText(
                tableModel.getValueAt(row, 2)
                        .toString()
        );

        yearField.setText(
                tableModel.getValueAt(row, 3)
                        .toString()
        );

        buildingField.setText(
                tableModel.getValueAt(row, 4)
                        .toString()
        );

        roomField.setText(
                tableModel.getValueAt(row, 5)
                        .toString()
        );

        timeSlotField.setText(
                tableModel.getValueAt(row, 6)
                        .toString()
        );
    }

    private void deleteSection() {

        int choice =

                JOptionPane.showConfirmDialog(

                        this,

                        "Delete Section?",

                        "Confirm",

                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        boolean result =

                new SectionDAO()
                        .deleteSection(

                                courseIdField.getText(),

                                secIdField.getText(),

                                semesterField.getText(),

                                Integer.parseInt(
                                        yearField.getText()
                                )
                        );

        if(result){

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Successful"
            );

            loadSections();

            clearFields();

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Failed"
            );
        }
    }

    private void updateSection() {

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
                        .updateSection(
                                section
                        );

        if(result){

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Successful"
            );

            loadSections();

            clearFields();

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "Operation Failed"
            );
        }
    }

    private void searchSections() {

        tableModel.setRowCount(0);

        for (

                Section section :

                new SectionDAO()
                        .searchSections(
                                searchField.getText()
                        )

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

    private void clearFields() {

        courseIdField.setText("");
        secIdField.setText("");
        semesterField.setText("");
        yearField.setText("");
        buildingField.setText("");
        roomField.setText("");
        timeSlotField.setText("");
    }
}