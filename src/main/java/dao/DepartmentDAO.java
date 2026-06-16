package dao;

import database.DBConnection;
import model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    public boolean addDepartment(
            Department department
    ) {

        String sql =
                """
                INSERT INTO department
                VALUES(?,?,?)
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setString(
                    1,
                    department.getDeptName()
            );

            pst.setString(
                    2,
                    department.getBuilding()
            );

            pst.setDouble(
                    3,
                    department.getBudget()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<Department> getAllDepartments() {

        List<Department> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM department";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ) {

            while(rs.next()) {

                list.add(
                        new Department(
                                rs.getString("dept_name"),
                                rs.getString("building"),
                                rs.getDouble("budget")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }

    public boolean updateDepartment(
            Department department
    ) {

        String sql =
                """
                UPDATE department
                SET
                    building = ?,
                    budget = ?
                WHERE dept_name = ?
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setString(
                    1,
                    department.getBuilding()
            );

            pst.setDouble(
                    2,
                    department.getBudget()
            );

            pst.setString(
                    3,
                    department.getDeptName()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteDepartment(
            String deptName
    ) {

        String sql =
                """
                DELETE FROM department
                WHERE dept_name = ?
                """;

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setString(
                    1,
                    deptName
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}