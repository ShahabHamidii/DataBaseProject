package dao;

import database.DBConnection;
import model.Prerequisite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PrerequisiteDAO {

    public boolean addPrerequisite(
            String courseId,
            String prereqId
    ) {

        String sql =
                "INSERT INTO prereq(course_id, prereq_id) VALUES (?, ?)";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setString(1, courseId);
            pst.setString(2, prereqId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<Prerequisite> getAllPrerequisites() {

        List<Prerequisite> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM prereq";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()

        ) {

            while (rs.next()) {

                list.add(
                        new Prerequisite(
                                rs.getString("course_id"),
                                rs.getString("prereq_id")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }

    public boolean deletePrerequisite(
            String courseId,
            String prereqId
    ) {

        String sql =
                """
                DELETE FROM prereq
                WHERE course_id = ?
                AND prereq_id = ?
                """;

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setString(1, courseId);
            pst.setString(2, prereqId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean exists(
            String courseId,
            String prereqId
    ) {

        String sql =
                """
                SELECT *
                FROM prereq
                WHERE course_id = ?
                AND prereq_id = ?
                """;

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setString(1, courseId);
            pst.setString(2, prereqId);

            return pst.executeQuery().next();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<Prerequisite> searchByCourse(
            String courseId
    ) {

        List<Prerequisite> list =
                new ArrayList<>();

        String sql =
                """
                SELECT *
                FROM prereq
                WHERE course_id = ?
                """;

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setString(1, courseId);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                list.add(
                        new Prerequisite(
                                rs.getString("course_id"),
                                rs.getString("prereq_id")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
}