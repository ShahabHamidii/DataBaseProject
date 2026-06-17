package dao;

import database.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public static User login(String username, String password) {
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User.Role role = User.Role.valueOf(rs.getString("role"));
                return new User(username, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}