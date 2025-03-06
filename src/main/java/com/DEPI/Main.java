package com.DEPI;

import com.DEPI.Controller.*;
import com.DEPI.Util.ConnectionUtil;
import io.javalin.Javalin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create();

//        CONTROLLERS SETUP
        new AuthController().registerRoutes(app);
        new UserController().registerRoutes(app);
        new LoanController().registerRoutes(app);
        new ApplicationLoanController().registerRoutes(app);

//        START
        app.start(7070);


    }
}