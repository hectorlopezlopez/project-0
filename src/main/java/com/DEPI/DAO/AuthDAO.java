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
import org.slf4j.LoggerFactory;

public class AuthDAO {
    private static final Logger logger = LoggerFactory.getLogger(AuthDAO.class);

    public static void login(Context ctx) {

    User requestUser = ctx.bodyAsClass(User.class);

        if (requestUser.getMail() == null || requestUser.getPassword() == null) {
        ctx.status(400).json("{\"error\":\"Missing username or password\"}");
        return;
    }

    // Check credentials. dbUser makes it clear we got this data from the db after verifying with the requestUser.
    User dbUser = getUserFromDB(requestUser.getMail());
        if (dbUser == null) {
        ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
            logger.info("User tried to login but invalida credentials: {}", requestUser.getMail());
        return;
    }


        if ( (!Hashing.comparePasswords(requestUser.getPassword() , dbUser.getPassword()) ) && ( !requestUser.getPassword().equals(dbUser.getPassword())) ) {
        ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
        return;
    }

    // If valid, start a session
    HttpSession session = ctx.req().getSession(true);
        session.setAttribute("user", dbUser);
        session.setAttribute("role", dbUser.getRol());
        session.setAttribute("id", dbUser.getId());
        logger.info("User logged: {}", requestUser.getMail());
        ctx.status(200).json("{\"message\":\"Login successful\"}");
    }

    private static User getUserFromDB(String mail) {
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

    public static boolean checkLogin(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            ctx.status(200).json("{\"message\":\"You are logged in\"}");
            return true;
        } else {
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
            return false;
        }
    }


    public static void logout(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        ctx.status(200).json("{\"message\":\"Logged out\"}");
    }

    public static int getRole(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null && session.getAttribute("role") != null ) {
            return (int) session.getAttribute("role");
        }
        return -1;
    }

    public static int getUserId(Context ctx) {

        HttpSession session = ctx.req().getSession(false);
        if (session != null && session.getAttribute("id") != null ) {
            //System.out.println("El id desde get user id es->"+session.getAttribute("id"));
            return (int) session.getAttribute("id");
        }
        return -1;
    }

}
