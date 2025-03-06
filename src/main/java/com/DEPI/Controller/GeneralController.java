package com.DEPI.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;

public class GeneralController {

    LoanController loanController;
    UserController userController;
    ApplicationLoanController applicationLoanController;

    public GeneralController() {
         loanController = new LoanController();
         userController = new UserController();
         applicationLoanController = new ApplicationLoanController();
    }
    public Javalin startAPI(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Javalin app = Javalin.create();

        app.get("/test", ctx -> ctx.result("Hello World"));


//------------------------------------------------------------------REQUIRED ENDPOINTS-------------------------------------------------------------------------------------------------------------------------------------------------------------------
//------CREATE NEW USER ACCOUNTS-------------------------------------------------------------------------------------------------------------------------------------------------------------------
        app.post("/user", userController::createUser);
//------LOGIN AND LOGOUT----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        app.post("/login", AuthController::login);
        app.post("/logout", AuthController::logOut);
//------REGULAR USER MANAGE AND VIEW THEIR OWN LOANS----------------------------------------------------------------------------------------------------------------------------------------------------------------
        app.get("/applicationloansbyuser",applicationLoanController::getApplicationByUser);
//------MANAGE ROLES---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//        TODO:OBTENER ROLES Y PODER CREAR Y MODIFICARLOS
// ------USER UPDATE THEIR OWN PROFILE----------------------------------------------------------------------------------------------------------------------------------------------------------------
        app.patch("/updatemyuser", userController::updateMyUser);
// ------LOANS-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// ------REGULAR USER ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// ------CREATE NEW APPLICATION AND APPLY FOR A LOAN-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
        app.post("/applicationloan", applicationLoanController::createLoanApplication);
        //app.post("/applicationloanbyuser", applicationLoanController::createLoanApplicationByUser);
// ------MANAGER USER ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// ------VIEW ALL LOANS APPLICATTIONS AND APROVE OR REJECT AN APPLICATION----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        app.get("/applicationloan", applicationLoanController::getAllLoansApplications);
//        TODO: CAMBIAR ESTRUCTURA DE CAMBIAR STATUS
        app.put("/updateapplicationloan/{id_applicationloan}", applicationLoanController::changeLoanStatus);


        app.get("/users", userController::getUsers);
        app.get("/user/{id_user}", userController::getUserById);
        app.get("/userloanapplication/{id_user}", userController::getLoansByUser);
        app.delete("/user/{id_user}", userController::deleteUser);
        app.put("/updateuser/{id_user}", userController::updateUser);
        app.get("/loans", loanController::getAllLoans);
        app.post("/loan", loanController::createLoan);
//        app.put("/updateapplicationloan/{id_applicationloan}", applicationLoanController::updateLoanApplication);
        app.delete("/applicationloan/{id_applicationloan}", applicationLoanController::deleteLoanApplication);

        return app;
    }



}
