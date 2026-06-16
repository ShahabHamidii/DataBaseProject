package dao;

import database.DBConnection;
import model.TimeSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotDAO {

    public boolean addTimeSlot(
            TimeSlot slot
    ) {

        String sql =
                "INSERT INTO time_slot VALUES(?,?,?,?)";

        try (

                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)

        ) {

            pst.setString(
                    1,
                    slot.getTimeSlotId()
            );

            pst.setString(
                    2,
                    slot.getDay()
            );

            pst.setString(
                    3,
                    slot.getStartTime()
            );

            pst.setString(
                    4,
                    slot.getEndTime()
            );

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<TimeSlot> getAllTimeSlots() {

        List<TimeSlot> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM time_slot";

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

                        new TimeSlot(

                                rs.getString(
                                        "time_slot_id"
                                ),

                                rs.getString(
                                        "day"
                                ),

                                rs.getString(
                                        "start_time"
                                ),

                                rs.getString(
                                        "end_time"
                                )
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
}