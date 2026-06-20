package ui;

import dao.CourseDAO;
import model.Course;
import util.UITheme;
import util.UIUtil;
import util.ValidationUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoursePanel extends JPanel {

    private JTextField idField, titleField, deptField, creditsField, searchField;
    private JTable table;
    private DefaultTableModel tableModel;
    private final CourseDAO dao = new CourseDAO();

    public CoursePanel() {
        initComponents();
        loadCourses();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UITheme.CONTENT_BG);
        setBorder(new EmptyBorder(24, 28, 24, 28));

        add(UITheme.createPageHeader("Courses", "Manage course catalog"), BorderLayout.NORTH);

        // ── فرم ────────────────────────────────────────────
        idField      = makeField();
        titleField   = makeField();
        deptField    = makeField();
        creditsField = makeField();
        searchField  = makeField();

        JPanel formGrid = UITheme.createFormGrid(5, 2,
                UITheme.createFieldLabel("Course ID"),         idField,
                UITheme.createFieldLabel("Title"),             titleField,
                UITheme.createFieldLabel("Department"),        deptField,
                UITheme.createFieldLabel("Credits"),           creditsField,
                UITheme.createFieldLabel("Search (title/dept)"), searchField
        );

        JButton addBtn    = UIUtil.createButton("Add",     UIUtil.ButtonStyle.SUCCESS);
        JButton updateBtn = UIUtil.createButton("Update",  UIUtil.ButtonStyle.PRIMARY);
        JButton deleteBtn = UIUtil.createButton("Delete",  UIUtil.ButtonStyle.DANGER);
        JButton refreshBtn= UIUtil.createButton("Refresh", UIUtil.ButtonStyle.SECONDARY);
        JButton searchBtn = UIUtil.createButton("Search",  UIUtil.ButtonStyle.GHOST);
        JButton clearBtn  = UIUtil.createButton("Clear",   UIUtil.ButtonStyle.GHOST);

        JPanel leftPanel = UITheme.createCard("Course Details",
                UITheme.createFormGrid(2, 1, formGrid,
                        UITheme.createButtonBar(addBtn, updateBtn, deleteBtn,
                                refreshBtn, searchBtn, clearBtn)));

        // ── جدول ───────────────────────────────────────────
        tableModel = new DefaultTableModel(
                new String[]{"Course ID", "Title", "Department", "Credits"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.wrapContent(this, "Courses", "Manage course catalog", leftPanel, table);

        // ── Events ─────────────────────────────────────────
        addBtn.addActionListener(e -> addCourse());
        updateBtn.addActionListener(e -> updateCourse());
        deleteBtn.addActionListener(e -> deleteCourse());
        refreshBtn.addActionListener(e -> loadCourses());
        searchBtn.addActionListener(e -> searchCourses());
        clearBtn.addActionListener(e -> clearFields());
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        // Enter توی search
        searchField.addActionListener(e -> searchCourses());
    }

    // ── Validation مشترک ────────────────────────────────────
    private boolean validateFields() {
        if (ValidationUtil.isEmpty(idField.getText())     ||
                ValidationUtil.isEmpty(titleField.getText())  ||
                ValidationUtil.isEmpty(deptField.getText())   ||
                ValidationUtil.isEmpty(creditsField.getText())) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return false;
        }
        if (!ValidationUtil.isInteger(creditsField.getText())) {
            JOptionPane.showMessageDialog(this, "Credits must be a number.");
            return false;
        }
        return true;
    }

    private Course buildCourseFromForm() {
        return new Course(
                idField.getText().trim(),
                titleField.getText().trim(),
                deptField.getText().trim(),
                Integer.parseInt(creditsField.getText().trim())
        );
    }

    // ── CRUD ────────────────────────────────────────────────
    private void addCourse() {
        if (!validateFields()) return;
        boolean ok = dao.addCourse(buildCourseFromForm());
        JOptionPane.showMessageDialog(this, ok ? "Course added!" : "Insert failed.");
        if (ok) { loadCourses(); clearFields(); }
    }

    private void updateCourse() {
        if (!validateFields()) return;
        boolean ok = dao.updateCourse(buildCourseFromForm());
        JOptionPane.showMessageDialog(this, ok ? "Course updated!" : "Update failed.");
        if (ok) loadCourses();
    }

    private void deleteCourse() {
        String id = idField.getText().trim();
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Select a course first."); return; }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete course " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = dao.deleteCourse(id);
        JOptionPane.showMessageDialog(this, ok ? "Course deleted!" : "Delete failed.");
        if (ok) { loadCourses(); clearFields(); }
    }

    private void searchCourses() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) { loadCourses(); return; }

        tableModel.setRowCount(0);
        // جستجو هم در title هم در dept_name
        List<Course> results = dao.searchByTitle(keyword);
        // اگه نتیجه‌ای نداشت، بر اساس dept امتحان کن
        if (results.isEmpty()) results = dao.searchByDepartment(keyword);

        for (Course c : results) {
            tableModel.addRow(new Object[]{
                    c.getCourseId(), c.getTitle(), c.getDeptName(), c.getCredits()
            });
        }

        if (tableModel.getRowCount() == 0)
            JOptionPane.showMessageDialog(this, "No courses found.");
    }

    private void loadCourses() {
        tableModel.setRowCount(0);
        for (Course c : dao.getAllCourses()) {
            tableModel.addRow(new Object[]{
                    c.getCourseId(), c.getTitle(), c.getDeptName(), c.getCredits()
            });
        }
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        deptField.setText("");
        creditsField.setText("");
        searchField.setText("");
        table.clearSelection();
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        idField.setText(tableModel.getValueAt(row, 0).toString());
        titleField.setText(tableModel.getValueAt(row, 1).toString());
        deptField.setText(tableModel.getValueAt(row, 2).toString());
        creditsField.setText(tableModel.getValueAt(row, 3).toString());
    }

    private JTextField makeField() {
        JTextField f = new JTextField();
        UITheme.styleTextField(f);
        return f;
    }
}