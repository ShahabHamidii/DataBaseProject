package dao;

import database.DBConnection;
import model.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {

    public boolean addSection(
            Section section
    ) {

        String sql =
                """
                INSERT INTO section
                VALUES (?,?,?,?,?,?,?)
                """;

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setString(1, section.getCourseId());
            pst.setString(2, section.getSecId());
            pst.setString(3, section.getSemester());
            pst.setInt(4, section.getYear());
            pst.setString(5, section.getBuilding());
            pst.setString(6, section.getRoomNumber());
            pst.setString(7, section.getTimeSlotId());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<Section> getAllSections() {

        List<Section> sections =
                new ArrayList<>();

        String sql =
                "SELECT * FROM section";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs =
                        pst.executeQuery()

        ) {

            while (rs.next()) {

                sections.add(

                        new Section(
                                rs.getString("course_id"),
                                rs.getString("sec_id"),
                                rs.getString("semester"),
                                rs.getInt("year"),
                                rs.getString("building"),
                                rs.getString("room_number"),
                                rs.getString("time_slot_id")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return sections;
    }
}