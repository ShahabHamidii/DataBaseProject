package dao;

import database.DBConnection;
import model.TeachingAssignment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeachingAssignmentDAO {

    public boolean assignInstructor(
            int instructorId,
            String courseId,
            String secId,
            String semester,
            int year
    ) {

        String sql =
                "INSERT INTO teaches(id, course_id, sec_id, semester, year) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, instructorId);
            pst.setString(2, courseId);
            pst.setString(3, secId);
            pst.setString(4, semester);
            pst.setInt(5, year);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<TeachingAssignment> getAllAssignments() {

        List<TeachingAssignment> assignments =
                new ArrayList<>();

        String sql =
                "SELECT * FROM teaches";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

                assignments.add(
                        new TeachingAssignment(
                                rs.getInt("id"),
                                rs.getString("course_id"),
                                rs.getString("sec_id"),
                                rs.getString("semester"),
                                rs.getInt("year")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return assignments;
    }
}