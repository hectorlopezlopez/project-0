package com.DEPI.Controller;

import com.DEPI.DTO.RequestApplicationLoanDTO;
import com.DEPI.Model.ApplicationLoan;
import com.DEPI.Service.ApplicationLoanService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationLoanController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationLoanController.class);

    public ApplicationLoanService applicationLoanService;

    public ApplicationLoanController() {
        this.applicationLoanService = new ApplicationLoanService();
    }

    public void registerRoutes(Javalin app) {
        app.get("/applicationloan", this::getAllLoansApplications);
        app.get("/applicationloansbyuser",this::getApplicationByUser);
        app.post("/applicationloan", this::createLoanApplication);
        app.put("/updateapplicationloan/{id_applicationloan}", this::updateLoanApplication);
        app.post("/applicationloanbyuser", this::createLoanApplicationByUser);
        app.put("/updateapplicationloan/{id_applicationloan}", this::updateLoanApplication);
        app.put("/updateapplicationloan/{id_applicationloan}", this::changeLoanStatus);
        app.delete("/applicationloan/{id_applicationloan}", this::deleteLoanApplication);
    }

    public void getAllLoansApplications(Context ctx) {
        if(AuthController.checkLogin(ctx)) {
            ctx.json(applicationLoanService.getAllLoansApplications());
        }
    }

    public void getApplicationByUser(Context ctx){
        if(AuthController.checkLogin(ctx)){
            ctx.json(applicationLoanService.getApplicationByUser(AuthController.getUserId(ctx)));
        }
    }

    public void createLoanApplication(Context ctx) {
            if (AuthController.checkLogin(ctx)) {
            //    if (AuthController.getUserId(ctx) == 1) {
                RequestApplicationLoanDTO applicationLoanDTO = ctx.bodyAsClass(RequestApplicationLoanDTO.class);
                if (
                        applicationLoanDTO.getAmount() == 0 &&
                                applicationLoanDTO.getDate_application() == null &&
                                applicationLoanDTO.getInterest() == 0 &&
                                applicationLoanDTO.getId_user() == 0
                ) {
                    logger.warn("Failed to create an loan application : {}", AuthController.getUserId(ctx));
                    ctx.status(400).json("{\"error\":\"Missing data\"}");

                    return;
                }

                ApplicationLoan applicationLoan = new ApplicationLoan();
                applicationLoan.setAmount(applicationLoanDTO.getAmount());
                applicationLoan.setInterest(applicationLoanDTO.getInterest());
                applicationLoan.setDate_application(applicationLoanDTO.getDate_application());
                applicationLoan.setId_loan(applicationLoanDTO.getId_loan());
                applicationLoan.setId_user(AuthController.getUserId(ctx));
                if (applicationLoanService.createLoan(applicationLoan)) {
                    ctx.status(200).json("Loan created");
                } else {
                    logger.warn("Failed to create an loan application : {}", AuthController.getUserId(ctx));
                    ctx.status(500).json("Something went wrong with creating the Loan");
                }
           /* }
                else {
                    ctx.status(200).json("You dont have permissions to do this :(");
                }*/
        }
    }
    public void createLoanApplicationByUser(Context ctx) {
            if (AuthController.checkLogin(ctx)) {
                //if (AuthController.getRole(ctx) == 1) {
                RequestApplicationLoanDTO applicationLoanDTO = ctx.bodyAsClass(RequestApplicationLoanDTO.class);
                if (
                        applicationLoanDTO.getAmount() == 0 &&
                                applicationLoanDTO.getDate_application() == null &&
                                applicationLoanDTO.getInterest() == 0
                ) {
                    ctx.status(400).json("{\"error\":\"Missing data\"}");

                    return;
                }

                ApplicationLoan applicationLoan = new ApplicationLoan();
                applicationLoan.setAmount(applicationLoanDTO.getAmount());
                applicationLoan.setInterest(applicationLoanDTO.getInterest());
                applicationLoan.setDate_application(applicationLoanDTO.getDate_application());
                applicationLoan.setId_loan(applicationLoanDTO.getId_loan());

                applicationLoan.setId_user(AuthController.getUserId(ctx));
                System.out.println("el id desde loan controller es "+AuthController.getUserId(ctx));

                if (applicationLoanService.createLoan(applicationLoan)) {
                    ctx.status(200).json("Loan created");
                } else {
                    ctx.status(500).json("Something went wrong with creating the Loan");
                }
            }
               /* else {
                    ctx.status(200).json("You dont have permissions to do this :(");
                }*/
        }


        public void updateLoanApplication(Context ctx) {
            int id_applicationloan = Integer.parseInt(ctx.pathParam("id_applicationloan"));
                if (AuthController.checkLogin(ctx)) {
                    if (AuthController.getRole(ctx) == 1) {
                    RequestApplicationLoanDTO applicationLoanDTO = ctx.bodyAsClass(RequestApplicationLoanDTO.class);
                    if (
                            applicationLoanDTO.getId_application() == 0 &&
                            applicationLoanDTO.getAmount() == 0 &&
                                    applicationLoanDTO.getDate_application() == null &&
                                    applicationLoanDTO.getInterest() == 0 &&
                                    applicationLoanDTO.getId_user() == 0
                    ) {
                        ctx.status(400).json("{\"error\":\"Missing data\"}");

                        return;
                    }

                    ApplicationLoan applicationLoan = new ApplicationLoan();
                    applicationLoan.setId_loan(applicationLoanDTO.getId_loan());
                    applicationLoan.setAmount(applicationLoanDTO.getAmount());
                    applicationLoan.setInterest(applicationLoanDTO.getInterest());
                    applicationLoan.setDate_application(applicationLoanDTO.getDate_application());
                    applicationLoan.setId_application(id_applicationloan);
                    applicationLoan.setId_user(applicationLoanDTO.getId_user());
                    if (applicationLoanService.updateLoanApplication(applicationLoan)) {
                        ctx.status(200).json("Loan updated");
                    } else {
                        logger.warn("Failed to create an loan application : {}", AuthController.getUserId(ctx));
                        ctx.status(500).json("Something went wrong with updating the Loan");
                    }
                    } else {
                        logger.warn("User tried to update a loan but didnt have permissions : {}", AuthController.getUserId(ctx));
                        ctx.status(200).json("You dont have permissions to do this :(");
                    }
                }

        }

    public void changeLoanStatus(Context ctx) {
        int id_applicationloan = Integer.parseInt(ctx.pathParam("id_applicationloan"));
        if (AuthController.checkLogin(ctx)) {
            if (AuthController.getRole(ctx) == 1) {
                RequestApplicationLoanDTO applicationLoanDTO = ctx.bodyAsClass(RequestApplicationLoanDTO.class);
                if (
                        applicationLoanDTO.getId_application() == 0 &&
                                applicationLoanDTO.getAmount() == 0 &&
                                applicationLoanDTO.getDate_application() == null &&
                                applicationLoanDTO.getInterest() == 0 &&
                                applicationLoanDTO.getId_user() == 0
                ) {
                    ctx.status(400).json("{\"error\":\"Missing data\"}");
                    logger.error("Failed to change status of a loan: {}", AuthController.getUserId(ctx));
                    return;
                }

                ApplicationLoan applicationLoan = new ApplicationLoan();
                applicationLoan.setId_loan(applicationLoanDTO.getId_loan());
                applicationLoan.setAmount(applicationLoanDTO.getAmount());
                applicationLoan.setInterest(applicationLoanDTO.getInterest());
                applicationLoan.setDate_application(applicationLoanDTO.getDate_application());
                applicationLoan.setId_application(id_applicationloan);
                applicationLoan.setId_user(applicationLoanDTO.getId_user());
                if (applicationLoanService.updateLoanApplication(applicationLoan)) {
                    ctx.status(200).json("Loan updated");
                } else {
                    ctx.status(500).json("Something went wrong with updating the Loan");
                    logger.warn("Failed to change loan status : {}", AuthController.getUserId(ctx));
                }
            } else {
                ctx.status(200).json("You dont have permissions to do this :(");
                logger.warn("User tried to change the status of a loan but didnt have permissions: {}", AuthController.getUserId(ctx));
            }
        }

    }

        public void deleteLoanApplication(Context ctx) {
            if(AuthController.checkLogin(ctx)) {
                if (AuthController.getRole(ctx) == 1) {
                int id_applicationloan = Integer.parseInt(ctx.pathParam("id_applicationloan"));

                ApplicationLoan applicationLoan = new ApplicationLoan();
                applicationLoan.setId_application(id_applicationloan);
                //System.out.println(applicationLoan);
                if(applicationLoanService.deleteLoanApplication(applicationLoan)){
                    ctx.status(200).json("Applciation loan deleted");
                }
                else {
                    logger.warn("Failed to delete a loan but failed : {}", AuthController.getUserId(ctx));
                ctx.status(500).json("Something went wrong with deleting the Loan application");
              }
            }
            } else {
                logger.warn("User tried to change the status of a loan but didnt have permissions: {}", AuthController.getUserId(ctx));
                ctx.status(200).json("You dont have permissions to do this :(");
            }
        }

}
