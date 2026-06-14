package dao;

import database.DBConnection;
import model.Student;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentDAO {

    public boolean addStudent(Student student) {

        String sql =
                "INSERT INTO student " +
                        "(id, name, dept_name, tot_cred) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setInt(1, student.getId());
            pst.setString(2, student.getName());
            pst.setString(3, student.getDeptName());
            pst.setInt(4, student.getTotalCredits());

            int rows = pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public Student getStudentById(int id) {

        String sql =
                "SELECT * FROM student WHERE id = ?";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setInt(1, id);

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {

                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("dept_name"),
                        rs.getInt("tot_cred")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public List<Student> getAllStudents() {

        List<Student> students =
                new ArrayList<>();

        String sql =
                "SELECT * FROM student";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement pst =
                     con.prepareStatement(sql);

             ResultSet rs =
                     pst.executeQuery()) {

            while (rs.next()) {

                Student student =
                        new Student(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("dept_name"),
                                rs.getInt("tot_cred")
                        );

                students.add(student);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return students;
    }

    public boolean updateStudent(Student student) {

        String sql =
                "UPDATE student " +
                        "SET name = ?, dept_name = ?, tot_cred = ? " +
                        "WHERE id = ?";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, student.getName());
            pst.setString(2, student.getDeptName());
            pst.setInt(3, student.getTotalCredits());
            pst.setInt(4, student.getId());

            int rows =
                    pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
    public boolean deleteStudent(int id) {

        String sql =
                "DELETE FROM student WHERE id = ?";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setInt(1, id);

            int rows =
                    pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
    public List<Student> searchByDepartment(
            String deptName
    ) {

        List<Student> students =
                new ArrayList<>();

        String sql =
                "SELECT * FROM student " +
                        "WHERE dept_name = ?";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, deptName);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Student student =
                        new Student(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("dept_name"),
                                rs.getInt("tot_cred")
                        );

                students.add(student);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return students;
    }
}
