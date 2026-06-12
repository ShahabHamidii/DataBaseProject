package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatisticsDAO {

    public int getStudentCount() {

        return getCount(
                "SELECT COUNT(*) FROM student"
        );
    }

    public int getCourseCount() {

        return getCount(
                "SELECT COUNT(*) FROM course"
        );
    }

    public int getEnrollmentCount() {

        return getCount(
                "SELECT COUNT(*) FROM takes"
        );
    }

    public int getDepartmentCount() {

        return getCount(
                "SELECT COUNT(*) FROM department"
        );
    }

    private int getCount(String sql) {

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()

        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
}