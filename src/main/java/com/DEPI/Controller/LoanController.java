package com.DEPI.Controller;

import com.DEPI.DTO.RequestLoanDTO;
import com.DEPI.Service.LoanService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoanController {
    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    private final LoanService loanService;

    public LoanController() {
        this.loanService = new LoanService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/loans", this::getAllLoans);
        app.post("/loan", this::createLoan);
    }

    public void getAllLoans(Context ctx) {
        if (!AuthController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }
        ctx.json(loanService.getAllLoans());
    }

    public void createLoan(Context ctx) {
        if (!AuthController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }

        RequestLoanDTO loanDTO = ctx.bodyAsClass(RequestLoanDTO.class);
        String result = loanService.createLoan(loanDTO);

        if (result.equals("Loan created")) {
            ctx.status(201).json("{\"message\": \"Loan created\"}");
        } else {
            ctx.status(400).json("{\"error\": result}");
        }
    }
}
