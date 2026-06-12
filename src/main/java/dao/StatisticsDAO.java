package dao;

import database.DBConnection;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticsDAO {

    public int getStudentCount() {
        return getCount("SELECT COUNT(*) FROM student");
    }

    public int getCourseCount() {
        return getCount("SELECT COUNT(*) FROM course");
    }

    public int getEnrollmentCount() {
        return getCount("SELECT COUNT(*) FROM takes");
    }

    public int getDepartmentCount() {
        return getCount("SELECT COUNT(*) FROM department");
    }

    private int getCount(String sql) {

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Student> getAllStudents() {

        List<Student> students =
                new ArrayList<>();

        String sql =
                "SELECT * FROM student";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

                students.add(
                        new Student(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("dept_name"),
                                rs.getInt("tot_cred")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return students;
    }
}