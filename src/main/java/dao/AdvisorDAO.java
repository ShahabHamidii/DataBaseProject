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
    public boolean advisorExists(int studentId) {

        String sql =
                "SELECT * FROM advisor WHERE s_id = ?";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setInt(1, studentId);

            return pst.executeQuery().next();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
    public boolean deleteAdvisor(int studentId) {

        String sql =
                "DELETE FROM advisor WHERE s_id = ?";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setInt(1, studentId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public java.util.List<Object[]> getAllAssignments() {

        java.util.List<Object[]> rows =
                new java.util.ArrayList<>();

        String sql =
                """
                SELECT
                    s.id,
                    s.name,
                    i.name
    
                FROM advisor a
    
                JOIN student s
                    ON a.s_id = s.id
    
                JOIN instructor i
                    ON a.i_id = i.id
                """;

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                java.sql.ResultSet rs =
                        pst.executeQuery()

        ) {

            while (rs.next()) {

                rows.add(
                        new Object[]{
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3)
                        }
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rows;
    }


}