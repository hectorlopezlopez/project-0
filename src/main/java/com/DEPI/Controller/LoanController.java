package com.DEPI.Controller;

import com.DEPI.DTO.RequestLoanDTO;
import com.DEPI.Model.Loan;
import com.DEPI.Service.LoanService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoanController {

    public LoanService loanService;

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    public LoanController() {
        this.loanService = new LoanService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/loans", this::getAllLoans);
        app.post("/loan", this::createLoan);
    }

    public void getAllLoans (Context ctx) {
        if(AuthController.checkLogin(ctx)) {
            ctx.json(loanService.getAllLoans());
        }
    }

    public void createLoan(Context ctx){
        if(AuthController.checkLogin(ctx)) {
        RequestLoanDTO loanDTO = ctx.bodyAsClass(RequestLoanDTO.class);
        if(
                 loanDTO.getName().trim().isEmpty()
                && loanDTO.getDescription().trim().isEmpty()
                && loanDTO.getInterest_base()!=0
        ){
            logger.error("Failed to create a loan by missing data : {}");
            ctx.status(400).json("{\"error\":\"Missing data\"}");

            return;
        }

        Loan loan = new Loan();
        loan.setName(loanDTO.getName());
        loan.setDescription(loanDTO.getDescription());
        loan.setInterest_base(loanDTO.getInterest_base());
        if(loanService.createLoan(loan)){
            ctx.status(200).json("Loan created");
            logger.info("Loan created: {}",loan.getName());
        }else {
            logger.error("Failed to create a loan : {}", loan.getName());
            ctx.status(500).json("Something went wrong with creating the Loan");
        }
    }
    }
}
