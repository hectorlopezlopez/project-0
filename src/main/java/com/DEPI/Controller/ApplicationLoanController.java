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
    private static final Logger logger = LoggerFactory.getLogger(ApplicationLoanController.class);
    private final ApplicationLoanService applicationLoanService;

    public ApplicationLoanController() {
        this.applicationLoanService = new ApplicationLoanService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/applicationloan", this::getAllLoansApplications);
        app.get("/applicationloan/{id_applicationloan}", this::getApplicationById);
        app.get("/applicationloansbyuser", this::getApplicationByUser);
        app.post("/applicationloan", this::createLoanApplication);
        app.put("/updateapplicationloan/{id_applicationloan}", this::updateLoanApplication);
        app.patch("/updatestatusapplicationloan/{id_applicationloan}", this::updateLoanStatusApplication);
        app.delete("/applicationloan/{id_applicationloan}", this::deleteLoanApplication);
    }

    public void getAllLoansApplications(Context ctx) {
        if (!AuthController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }
        ctx.json(applicationLoanService.getAllLoansApplications());
    }

    public void getApplicationByUser(Context ctx) {
        if (!AuthController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }
        int userId = AuthController.getUserId(ctx);
        ctx.json(applicationLoanService.getApplicationByUser(userId));
    }

    public void getApplicationById(Context ctx) {
        if (!AuthController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }
        int idApplication =  Integer.parseInt(ctx.pathParam("id_applicationloan"));
        System.out.println("Desde controler->"+idApplication);
        ApplicationLoan applicationLoan = applicationLoanService.getApplicationById(idApplication);
        if(applicationLoan==null){
            ctx.status(404).json("{\"error\": \"Application loan not found\"}");
            return;
        }
        ctx.json(applicationLoan);
    }

    public void createLoanApplication(Context ctx) {
        if (!AuthController.checkLogin(ctx)) {
            ctx.status(401).json("{\"error\": \"Not logged in\"}");
            return;
        }

        RequestApplicationLoanDTO applicationLoanDTO = ctx.bodyAsClass(RequestApplicationLoanDTO.class);
        String result = applicationLoanService.createLoan(applicationLoanDTO, AuthController.getUserId(ctx));

        if (result.equals("Loan created")) {
            ctx.status(201).json("{\"message\": \"Loan created\"}");
        } else {
            ctx.status(400).json("{\"error\": result}");
        }
    }

    public void updateLoanApplication(Context ctx) {
        if (!AuthController.checkLogin(ctx) || AuthController.getRole(ctx) != 1) {
            ctx.status(403).json("{\"error\": \"You don't have permissions to do this\"}");
            return;
        }

        int idApplicationLoan = Integer.parseInt(ctx.pathParam("id_applicationloan"));
        RequestApplicationLoanDTO applicationLoanDTO = ctx.bodyAsClass(RequestApplicationLoanDTO.class);

        String result = applicationLoanService.updateLoanApplication(idApplicationLoan, applicationLoanDTO);
        ctx.status(result.equals("Loan updated") ? 200 : 400).json("{\"message\": \"" + result + "\"}");
    }

    public void updateLoanStatusApplication(Context ctx) {
        if (!AuthController.checkLogin(ctx) || AuthController.getRole(ctx) != 1) {
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
        if (!AuthController.checkLogin(ctx) || AuthController.getRole(ctx) != 1) {
            ctx.status(403).json("{\"error\": \"You don't have permissions to do this\"}");
            return;
        }

        int idApplicationLoan = Integer.parseInt(ctx.pathParam("id_applicationloan"));
        boolean success = applicationLoanService.deleteLoanApplication(idApplicationLoan);

        ctx.status(success ? 200 : 400).json(success ? "Application loan deleted" : "Failed to delete application loan");
    }
}
