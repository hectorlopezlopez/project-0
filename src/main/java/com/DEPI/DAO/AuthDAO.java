package com.DEPI.DAO;

import com.DEPI.Model.User;
import com.DEPI.Util.ConnectionUtil;
import com.DEPI.Util.Hashing;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;

public class AuthDAO {
    private static final Logger logger = LoggerFactory.getLogger(AuthDAO.class);

    public static void login(Context ctx) {

    }

    public User getUserFromDB(String mail) {
        String sql = "SELECT * FROM profile WHERE mail = ?";
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id_user"));
                user.setMail(rs.getString("mail"));
                user.setPassword(rs.getString("password_user"));
                user.setRol(rs.getInt("id_rol"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}