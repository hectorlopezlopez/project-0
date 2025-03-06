package com.DEPI.Controller;

import com.DEPI.DAO.AuthDAO;
import com.DEPI.Model.User;
import com.DEPI.Service.AuthService;
import com.DEPI.Util.Hashing;
import io.javalin.Javalin;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class AuthController {

    AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController() {
        this.authService = new AuthService();
    }

    public void registerRoutes(Javalin app) {
        app.post("/login", this::login);
        app.post("/logout", this::logOut);
    }


    public void login(Context ctx) {

        User requestUser = ctx.bodyAsClass(User.class);

        if (requestUser.getMail() == null || requestUser.getPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }

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

        HttpSession session = ctx.req().getSession(true);
        session.setAttribute("user", dbUser);
        session.setAttribute("role", dbUser.getRol());
        session.setAttribute("id", dbUser.getId());
        logger.info("User logged: {}", requestUser.getMail());
        ctx.status(200).json("{\"message\":\"Login successful\"}");
    }

    private User getUserFromDB(String mail) {
    return this.authService.getUserFromDB(mail);
    }

    public boolean checkLogin(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            ctx.status(200).json("{\"message\":\"You are logged in\"}");
            return true;
        } else {
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
            return false;
        }
    }


    public void logOut(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null ) {
            session.invalidate();
        }
        ctx.status(200).json("{\"message\":\"Logged out\"}");
    }

    public int getRole(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null && session.getAttribute("role") != null ) {
            return (int) session.getAttribute("role");
        }
        return -1;
    }

    public int getUserId(Context ctx) {

        HttpSession session = ctx.req().getSession(false);
        if (session != null && session.getAttribute("id") != null ) {
            //System.out.println("El id desde get user id es->"+session.getAttribute("id"));
            return (int) session.getAttribute("id");
        }
        return -1;
    }

}