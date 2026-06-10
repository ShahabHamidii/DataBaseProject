package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public List<Object[]> getEnrollmentReport() {

        List<Object[]> rows =
                new ArrayList<>();

        String sql =

                "SELECT " +

                        "s.name AS student_name, " +

                        "c.title AS course_title, " +

                        "t.semester, " +

                        "t.year, " +

                        "t.grade " +

                        "FROM takes t " +

                        "JOIN student s " +

                        "ON t.id = s.id " +

                        "JOIN course c " +

                        "ON t.course_id = c.course_id";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()

        ) {

            while (rs.next()) {

                Object[] row = {

                        rs.getString(
                                "student_name"
                        ),

                        rs.getString(
                                "course_title"
                        ),

                        rs.getString(
                                "semester"
                        ),

                        rs.getInt(
                                "year"
                        ),

                        rs.getString(
                                "grade"
                        )
                };

                rows.add(row);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rows;
    }
}
