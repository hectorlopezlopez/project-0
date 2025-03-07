package com.DEPI.Controller;

import com.DEPI.DTO.RequestApplicationLoanDTO;
import com.DEPI.Model.ApplicationLoan;
import com.DEPI.Service.ApplicationLoanService;
import com.DEPI.Util.LoanStatus;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationLoanController {
    AuthController authController;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationLoanController.class);
    private final ApplicationLoanService applicationLoanService;

    public ApplicationLoanController() {
        this.authController = new AuthController();
        this.applicationLoanService = new ApplicationLoanService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/applicationloan", this::getAllLoansApplications);
        app.get("/applicationloan/{id_applicationloan}", this::getApplicationById);
        app.get("/applicationloansbyuser", this::getApplicationByUser);
        app.post("/applicationloan", this::createLoanApplication);
        app.put("/updateapplicationloan/{id_applicationloan}", this::updateLoanApplication);
        app.put("/updateapplicationloanbyuser/{id_applicationloan}", this::updateLoanApplicationByUser);
        app.patch("/updatestatusapplicationloan/{id_applicationloan}", this::updateLoanStatusApplication);
        app.delete("/applicationloan/{id_applicationloan}", this::deleteLoanApplication);
    }

    public void getAllLoansApplications(Context ctx) {
        if (!authController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }
        if(authController.getRole(ctx)==1) {
            ctx.json(applicationLoanService.getAllLoansApplications());
        }else {
            ctx.status(401).json("{\"error\": \"You dont have permission to get all the loans applications\"}");
        }
    }

    public void getApplicationByUser(Context ctx) {
        if (!authController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }
        int userId = authController.getUserId(ctx);
        if(applicationLoanService.getApplicationByUser(userId).size()>0) {
            ctx.json(applicationLoanService.getApplicationByUser(userId));
        }else {
            ctx.status(200).json("{\"error\": \"Not applications found\"}");
        }
    }

    public void getApplicationById(Context ctx) {
        if (!authController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }
        if(authController.getRole(ctx)==1) {

        int idApplication =  Integer.parseInt(ctx.pathParam("id_applicationloan"));
        ApplicationLoan applicationLoan = applicationLoanService.getApplicationById(idApplication);
        if(applicationLoan==null){
            ctx.status(404).json("{\"error\": \"Application loan not found\"}");
            return;
        }
        ctx.json(applicationLoan);
        }else {
            ctx.status(401).json("{\"error\": \"You dont have permission to get a loan application that its not yours\"}");
        }
    }
    public void getApplicationByIdValidation(Context ctx) {
        if (!authController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }
        int idApplication =  Integer.parseInt(ctx.pathParam("id_applicationloan"));
        ApplicationLoan applicationLoan = applicationLoanService.getApplicationById(idApplication);
        if(applicationLoan==null){
            ctx.status(404).json("{\"error\": \"Application loan not found\"}");
            return;
        }
        ctx.json(applicationLoan);
    }

    public void createLoanApplication(Context ctx) {
        if (!authController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }

        RequestApplicationLoanDTO applicationLoanDTO = ctx.bodyAsClass(RequestApplicationLoanDTO.class);
        String result = applicationLoanService.createLoan(applicationLoanDTO, authController.getUserId(ctx));

        if (result.equals("Loan created")) {
            ctx.status(201).json("{\"message\": \"Loan created\"}");
        } else {
            ctx.status(400).json("{\"error\": result}");
        }
    }

    public void updateLoanApplication(Context ctx) {
        if (!authController.checkLogin(ctx) || authController.getRole(ctx) != 1) {
            ctx.status(403).json("{\"error\": \"You don't have permissions to do this\"}");
            return;
        }

        int idApplicationLoan = Integer.parseInt(ctx.pathParam("id_applicationloan"));
        RequestApplicationLoanDTO applicationLoanDTO = ctx.bodyAsClass(RequestApplicationLoanDTO.class);

        String result = applicationLoanService.updateLoanApplication(idApplicationLoan, applicationLoanDTO);
        ctx.status(result.equals("Loan updated") ? 200 : 400).json("{\"message\": \"" + result + "\"}");
    }
    public void updateLoanApplicationByUser(Context ctx) {
        if (authController.checkLogin(ctx) ) {
        int idApplicationLoan = Integer.parseInt(ctx.pathParam("id_applicationloan"));
        int idUserInSession = authController.getUserId(ctx);
        int idApplicactionOwner = (applicationLoanService.getApplicationById(idApplicationLoan)).getId_application();
        if(idApplicactionOwner==idUserInSession) {
            RequestApplicationLoanDTO applicationLoanDTO = ctx.bodyAsClass(RequestApplicationLoanDTO.class);

            String result = applicationLoanService.updateLoanApplication(idApplicationLoan, applicationLoanDTO);
            ctx.status(result.equals("Loan updated") ? 200 : 400).json("{\"message\": \"" + result + "\"}");
        }else {
            ctx.status(400).json("{\"error\": \"YOU CANNOT UPDATE AN APPLICATION THAT ITS NOT YOURS\"}");
        }
        }else {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
        }
    }


    public void updateLoanStatusApplication(Context ctx) {
        if (!authController.checkLogin(ctx) || authController.getRole(ctx) != 1) {
            ctx.status(403).json("{\"error\": \"You don't have permissions to do this\"}");
            return;
        }

        int idApplicationLoan = Integer.parseInt(ctx.pathParam("id_applicationloan"));

        String statusStr = ctx.body().trim().replace("\"", "");

        try {
            LoanStatus statusLoan = LoanStatus.valueOf(statusStr.toUpperCase());
            String result = applicationLoanService.updateLoanStatusApplication(idApplicationLoan, statusLoan);
            ctx.status(result.equals("Loan updated") ? 200 : 400).json("{\"message\": \"" + result + "\"}");
        } catch (IllegalArgumentException e) {
            ctx.status(400).json("{\"error\": \"Invalid status value. Allowed: PAYED, PROCESS, DEFEATED, CURRENT\"}");
        }
    }


    public void deleteLoanApplication(Context ctx) {
        if (!authController.checkLogin(ctx) || authController.getRole(ctx) != 1) {
            ctx.status(403).json("{\"error\": \"You don't have permissions to do this\"}");
            return;
        }

        int idApplicationLoan = Integer.parseInt(ctx.pathParam("id_applicationloan"));
        boolean success = applicationLoanService.deleteLoanApplication(idApplicationLoan);

        ctx.status(success ? 200 : 400).json(success ? "Application loan deleted" : "Failed to delete application loan");
    }
}
