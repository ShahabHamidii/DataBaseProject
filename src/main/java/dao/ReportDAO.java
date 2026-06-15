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
    public List<Object[]> getStudentsPerDepartment() {

        List<Object[]> rows =
                new ArrayList<>();

        String sql =

                "SELECT dept_name, COUNT(*) AS total_students " +

                        "FROM student " +

                        "GROUP BY dept_name";

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

                        rs.getString("dept_name"),

                        rs.getInt("total_students")
                };

                rows.add(row);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rows;
    }
    public List<Object[]> getInstructorTeachingReport() {

        List<Object[]> rows =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    i.name,
                    c.title,
                    t.semester,
                    t.year
    
                FROM teaches t
    
                JOIN instructor i
                    ON t.id = i.id
    
                JOIN course c
                    ON t.course_id = c.course_id
                """;

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()

        ) {

            while (rs.next()) {

                rows.add(

                        new Object[]{

                                rs.getString(1),

                                rs.getString(2),

                                rs.getString(3),

                                rs.getInt(4)
                        }
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rows;
    }
    public List<Object[]> getCourseEnrollmentReport() {

        List<Object[]> rows =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    c.title,
                    COUNT(*) AS total
    
                FROM takes t
    
                JOIN course c
                    ON t.course_id = c.course_id
    
                GROUP BY c.title
    
                ORDER BY total DESC
                """;

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()

        ) {

            while (rs.next()) {

                rows.add(

                        new Object[]{

                                rs.getString(1),

                                rs.getInt(2)
                        }
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rows;
    }

    public int getStudentCount() {

        String sql =
                "SELECT COUNT(*) FROM student";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()

        ) {

            if(rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    public int getCourseCount() {

        String sql =
                "SELECT COUNT(*) FROM course";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()

        ) {

            if(rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
    public int getInstructorCount() {

        String sql =
                "SELECT COUNT(*) FROM instructor";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()

        ) {

            if(rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
    public int getEnrollmentCount() {

        String sql =
                "SELECT COUNT(*) FROM takes";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()

        ) {

            if(rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    public List<Object[]> getTopStudentsByCredits() {

        List<Object[]> rows =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    id,
                    name,
                    dept_name,
                    tot_cred
                FROM student
                ORDER BY tot_cred DESC
                LIMIT 10
                """;

        try(
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()
        ){

            while(rs.next()){

                rows.add(
                        new Object[]{
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4)
                        }
                );
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return rows;
    }
}
