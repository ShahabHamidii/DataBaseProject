package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<Object[]> getAdvisorAssignments() {

        List<Object[]> rows =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    s.name,
                    i.name,
                    s.dept_name
    
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

                ResultSet rs =
                        pst.executeQuery()

        ) {

            while(rs.next()) {

                rows.add(
                        new Object[]{

                                rs.getString(1),

                                rs.getString(2),

                                rs.getString(3)
                        }
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return rows;
    }

    public List<Object[]> getFullAcademicReport() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT
            s.name        AS student,
            c.title       AS course,
            i.name        AS instructor,
            d.dept_name   AS department,
            tk.semester,
            tk.year,
            tk.grade
        FROM takes tk
        JOIN student    s  ON tk.id        = s.id
        JOIN section    sec ON tk.course_id = sec.course_id
                           AND tk.sec_id   = sec.sec_id
                           AND tk.semester = sec.semester
                           AND tk.year     = sec.year
        JOIN course     c  ON tk.course_id = c.course_id
        JOIN teaches    te ON sec.course_id = te.course_id
                           AND sec.sec_id   = te.sec_id
                           AND sec.semester = te.semester
                           AND sec.year     = te.year
        JOIN instructor i  ON te.id        = i.id
        JOIN department d  ON c.dept_name  = d.dept_name
        ORDER BY tk.year DESC, s.name
        """;
        try (PreparedStatement pst = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next())
                list.add(new Object[]{
                        rs.getString("student"),
                        rs.getString("course"),
                        rs.getString("instructor"),
                        rs.getString("department"),
                        rs.getString("semester"),
                        rs.getInt("year"),
                        rs.getString("grade")
                });
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Object[]> getDepartmentSummary() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT
            d.dept_name,
            d.budget,
            COUNT(DISTINCT c.course_id)  AS courses,
            COUNT(DISTINCT s.id)         AS students,
            COUNT(DISTINCT i.id)         AS instructors
        FROM department d
        LEFT JOIN course     c ON d.dept_name = c.dept_name
        LEFT JOIN student    s ON d.dept_name = s.dept_name
        LEFT JOIN instructor i ON d.dept_name = i.dept_name
        GROUP BY d.dept_name, d.budget
        ORDER BY students DESC
        """;
        try (PreparedStatement pst = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next())
                list.add(new Object[]{
                        rs.getString(1),
                        rs.getDouble(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5)
                });
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Object[]> getStudentsMissingPrereqs() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT DISTINCT
            s.id, s.name, p.course_id AS wants, p.prereq_id AS missing
        FROM student s
        JOIN takes    tk ON s.id = tk.id
        JOIN prereq   p  ON tk.course_id = p.course_id
        WHERE NOT EXISTS (
            SELECT 1 FROM takes t2
            WHERE t2.id = s.id AND t2.course_id = p.prereq_id
        )
        ORDER BY s.name
        """;
        try (PreparedStatement pst = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next())
                list.add(new Object[]{
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4)
                });
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public int[] getDashboardStats() {

        String sql = """
        SELECT
        (
          SELECT COUNT(*) FROM student
        ) students,
        (
          SELECT COUNT(*) FROM course
        ) courses,
        (
          SELECT COUNT(*) FROM instructor
        ) instructors,
        (
          SELECT COUNT(*) FROM takes
        ) enrollments
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                return new int[]{
                        rs.getInt("students"),
                        rs.getInt("courses"),
                        rs.getInt("instructors"),
                        rs.getInt("enrollments")
                };
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new int[]{0, 0, 0, 0};
    }

    public List<Object[]> getAboveAverageStudents() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT id, name, dept_name, tot_cred
        FROM student
        WHERE tot_cred > (SELECT AVG(tot_cred) FROM student)
        ORDER BY tot_cred DESC
        """;
        try (PreparedStatement pst = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next())
                list.add(new Object[]{
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4)
                });
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Object[]> getCoursesWithNoEnrollment() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT course_id, title, dept_name, credits
        FROM course c
        WHERE NOT EXISTS (
            SELECT 1 FROM takes t WHERE t.course_id = c.course_id
        )
        ORDER BY dept_name
        """;
        try (PreparedStatement pst = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next())
                list.add(new Object[]{
                        rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4)
                });
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Object[]> getHighEarningInstructors() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
        SELECT i.id, i.name, i.dept_name, i.salary
        FROM instructor i
        WHERE i.salary > (
            SELECT AVG(i2.salary)
            FROM instructor i2
            WHERE i2.dept_name = i.dept_name
        )
        ORDER BY i.dept_name, i.salary DESC
        """;
        try (PreparedStatement pst = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next())
                list.add(new Object[]{
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getDouble(4)
                });
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
