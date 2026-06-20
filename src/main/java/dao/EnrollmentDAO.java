package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {


    public boolean enrollStudent(int studentId, String courseId,
                                 String secId, String semester, int year) {
        String checkSql = "SELECT COUNT(*) FROM takes " +
                "WHERE id=? AND course_id=? AND sec_id=? " +
                "AND semester=? AND year=?";
        String insertSql = "INSERT INTO takes " +
                "(id, course_id, sec_id, semester, year, grade) " +
                "VALUES (?, ?, ?, ?, ?, NULL)";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement check = con.prepareStatement(checkSql);
            check.setInt(1, studentId);
            check.setString(2, courseId);
            check.setString(3, secId);
            check.setString(4, semester);
            check.setInt(5, year);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                con.rollback();
                return false;
            }

            PreparedStatement pst = con.prepareStatement(insertSql);
            pst.setInt(1, studentId);
            pst.setString(2, courseId);
            pst.setString(3, secId);
            pst.setString(4, semester);
            pst.setInt(5, year);
            int rows = pst.executeUpdate();

            con.commit();
            return rows > 0;

        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }


    public boolean unEnrollStudent(int studentId, String courseId,
                                   String secId, String semester, int year) {
        String sql = "DELETE FROM takes WHERE id=? AND course_id=? " +
                "AND sec_id=? AND semester=? AND year=?";
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);
            pst.setString(2, courseId);
            pst.setString(3, secId);
            pst.setString(4, semester);
            pst.setInt(5, year);
            int rows = pst.executeUpdate();

            con.commit();
            return rows > 0;

        } catch (SQLException e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) try { con.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public List<Object[]> getAllEnrollments() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT s.name, c.title, t.semester, t.year, t.grade
            FROM takes t
            JOIN student s ON t.id = s.id
            JOIN course  c ON t.course_id = c.course_id
            ORDER BY t.year DESC, t.semester
            """;
        try (PreparedStatement pst = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next())
                list.add(new Object[]{
                        rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4), rs.getString(5)
                });
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}