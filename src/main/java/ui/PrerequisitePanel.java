package ui;

import dao.CourseDAO;
import dao.PrerequisiteDAO;
import model.Course;
import model.Prerequisite;
import util.UITheme;
import util.UIUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PrerequisitePanel extends JPanel {

    private JComboBox<Course> courseCombo;
    private JComboBox<Course> prereqCombo;
    private JButton addButton;
    private JButton loadButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField searchField;

    public PrerequisitePanel() {
        initComponents();
        loadCourses();
        loadPrerequisites();
    }

    private void initComponents() {

        courseCombo = new JComboBox<>();
        prereqCombo = new JComboBox<>();
        searchField = new JTextField();

        UITheme.styleComboBox(courseCombo);
        UITheme.styleComboBox(prereqCombo);
        UITheme.styleTextField(searchField);

        JPanel formGrid = UITheme.createFormGrid(2, 2,
                UITheme.createFieldLabel("Course"), courseCombo,
                UITheme.createFieldLabel("Prerequisite"), prereqCombo
        );

        addButton = UIUtil.createButton("Add", UIUtil.ButtonStyle.SUCCESS);
        loadButton = UIUtil.createButton("Refresh", UIUtil.ButtonStyle.SECONDARY);
        deleteButton = UIUtil.createButton("Delete", UIUtil.ButtonStyle.DANGER);
        searchButton = UIUtil.createButton("Search", UIUtil.ButtonStyle.GHOST);

        JPanel searchRow = new JPanel(new BorderLayout(8, 0));
        searchRow.setOpaque(false);
        searchRow.add(UITheme.createFieldLabel("Filter by Course ID"), BorderLayout.WEST);
        searchRow.add(searchField, BorderLayout.CENTER);

        JPanel leftContent = new JPanel();
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));
        leftContent.setOpaque(false);
        leftContent.add(formGrid);
        leftContent.add(Box.createVerticalStrut(12));
        leftContent.add(searchRow);
        leftContent.add(Box.createVerticalStrut(12));
        leftContent.add(UITheme.createButtonBar(addButton, deleteButton, loadButton, searchButton));

        JPanel leftPanel = UITheme.createCard("Prerequisite Rules", leftContent);

        tableModel = new DefaultTableModel(
                new String[]{"Course", "Prerequisite"}, 0);
        table = new JTable(tableModel);

        UITheme.wrapContent(this, "Prerequisites",
                "Define course prerequisite requirements",
                leftPanel, table);

        addButton.addActionListener(e -> addPrerequisite());
        loadButton.addActionListener(e -> loadPrerequisites());
        deleteButton.addActionListener(e -> deletePrerequisite());
        searchButton.addActionListener(e -> searchPrerequisite());
    }

    private void loadCourses() {
        courseCombo.removeAllItems();
        prereqCombo.removeAllItems();
        for (Course course : new CourseDAO().getAllCourses()) {
            courseCombo.addItem(course);
            prereqCombo.addItem(course);
        }
    }

    private void addPrerequisite() {

        Course course = (Course) courseCombo.getSelectedItem();
        Course prereq = (Course) prereqCombo.getSelectedItem();

        if (course == null || prereq == null) return;

        PrerequisiteDAO dao = new PrerequisiteDAO();

        if (dao.exists(course.getCourseId(), prereq.getCourseId())) {
            JOptionPane.showMessageDialog(this, "Prerequisite already exists");
            return;
        }

        if (dao.addPrerequisite(course.getCourseId(), prereq.getCourseId())) {
            JOptionPane.showMessageDialog(this, "Prerequisite added");
            loadPrerequisites();
        }
    }

    private void loadPrerequisites() {

        tableModel.setRowCount(0);

        for (Prerequisite p : new PrerequisiteDAO().getAllPrerequisites()) {
            tableModel.addRow(new Object[]{p.getCourseId(), p.getPrereqId()});
        }
    }

    private void deletePrerequisite() {

        int row = table.getSelectedRow();
        if (row == -1) return;

        String courseId = tableModel.getValueAt(row, 0).toString();
        String prereqId = tableModel.getValueAt(row, 1).toString();

        if (new PrerequisiteDAO().deletePrerequisite(courseId, prereqId)) {
            loadPrerequisites();
        }
    }

    private void searchPrerequisite() {

        tableModel.setRowCount(0);

        for (Prerequisite p : new PrerequisiteDAO().searchByCourse(searchField.getText())) {
            tableModel.addRow(new Object[]{p.getCourseId(), p.getPrereqId()});
        }
    }
}
