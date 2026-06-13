package dao;

import database.DBConnection;
import model.Instructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorDAO {

    public List<Instructor> getAllInstructors() {

        List<Instructor> instructors =
                new ArrayList<>();

        String sql =
                "SELECT * FROM instructor";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            while (rs.next()) {

                instructors.add(
                        new Instructor(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("dept_name"),
                                rs.getDouble("salary")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return instructors;
    }

    public boolean addInstructor(
            Instructor instructor
    ) {

        String sql =
                "INSERT INTO instructor VALUES(?,?,?,?)";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(
                    1,
                    instructor.getId()
            );

            pst.setString(
                    2,
                    instructor.getName()
            );

            pst.setString(
                    3,
                    instructor.getDeptName()
            );

            pst.setDouble(
                    4,
                    instructor.getSalary()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean updateInstructor(
            Instructor instructor
    ) {

        String sql =
                """
                UPDATE instructor
                SET
                    name = ?,
                    dept_name = ?,
                    salary = ?
                WHERE id = ?
                """;

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setString(
                    1,
                    instructor.getName()
            );

            pst.setString(
                    2,
                    instructor.getDeptName()
            );

            pst.setDouble(
                    3,
                    instructor.getSalary()
            );

            pst.setInt(
                    4,
                    instructor.getId()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteInstructor(
            int id
    ) {

        String sql =
                "DELETE FROM instructor WHERE id = ?";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setInt(1, id);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}