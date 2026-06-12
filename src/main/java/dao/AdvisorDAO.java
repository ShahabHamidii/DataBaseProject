package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdvisorDAO {

    public boolean assignAdvisor(
            int studentId,
            int instructorId
    ) {

        String sql =
                "INSERT INTO advisor(s_id, i_id) VALUES(?, ?)";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setInt(1, studentId);
            pst.setInt(2, instructorId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}