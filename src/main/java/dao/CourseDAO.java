package dao;

import database.DBConnection;
import model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public boolean addCourse(Course course) {

        String sql =
                "INSERT INTO course " +
                        "(course_id, title, dept_name, credits) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, course.getCourseId());

            pst.setString(2, course.getTitle());

            pst.setString(3, course.getDeptName());

            pst.setInt(4, course.getCredits());

            int rows =
                    pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;

    }
    public List<Course> getAllCourses() {

        List<Course> courses =
                new ArrayList<>();

        String sql =
                "SELECT * FROM course";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement pst =
                     con.prepareStatement(sql);

             ResultSet rs =
                     pst.executeQuery()) {

            while (rs.next()) {

                Course course =
                        new Course(

                                rs.getString("course_id"),

                                rs.getString("title"),

                                rs.getString("dept_name"),

                                rs.getInt("credits")
                        );

                courses.add(course);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return courses;
    }
    public boolean updateCourse(Course course) {

        String sql =
                "UPDATE course " +
                        "SET title = ?, dept_name = ?, credits = ? " +
                        "WHERE course_id = ?";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, course.getTitle());

            pst.setString(2, course.getDeptName());

            pst.setInt(3, course.getCredits());

            pst.setString(4, course.getCourseId());

            int rows =
                    pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
    public boolean deleteCourse(String courseId) {

        String sql =
                "DELETE FROM course " +
                        "WHERE course_id = ?";

        try (Connection con =
                     DBConnection.getConnection();

             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, courseId);

            int rows =
                    pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

}