package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class EnrollmentDAO {

    public boolean enrollStudent(

            int studentId,

            String courseId,

            String secId,

            String semester,

            int year
    ) {

        String sql =
                "INSERT INTO takes " +
                        "(id, course_id, sec_id, semester, year, grade) " +
                        "VALUES (?, ?, ?, ?, ?, NULL)";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            // Transaction Start
            con.setAutoCommit(false);

            pst.setInt(1, studentId);

            pst.setString(2, courseId);

            pst.setString(3, secId);

            pst.setString(4, semester);

            pst.setInt(5, year);

            int rows =
                    pst.executeUpdate();

            con.commit();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}