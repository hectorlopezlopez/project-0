package com.DEPI.Controller;

import com.DEPI.DAO.AuthDAO;
import io.javalin.http.Context;

public class AuthController {
    public AuthController() {

    }

    public static void login(Context ctx) {
        AuthDAO.login(ctx);
    }

    public static boolean checkLogin(Context ctx){
        return AuthDAO.checkLogin(ctx);
    }

    public static void logOut(Context ctx){
        AuthDAO.logout(ctx);
    }

    public static int getRole(Context ctx){
        return AuthDAO.getRole(ctx);
    }
    public static int getUserId(Context ctx){
        return AuthDAO.getUserId(ctx);
    }



}
