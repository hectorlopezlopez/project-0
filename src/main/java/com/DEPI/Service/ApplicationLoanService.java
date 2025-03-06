package com.DEPI.Service;

import com.DEPI.DAO.ApplicationLoanDAO;
import com.DEPI.Model.ApplicationLoan;
import com.DEPI.Model.Loan;
import java.util.List;

public class ApplicationLoanService {

    ApplicationLoanDAO applicationLoanDAO;

    public ApplicationLoanService() {
        this.applicationLoanDAO = new ApplicationLoanDAO();
    }

    public List<ApplicationLoan> getAllLoansApplications(){
        return this.applicationLoanDAO.getAllLoansApplications();
    }

    public List<ApplicationLoan> getApplicationByUser(int idUser){
        return applicationLoanDAO.getApplicationByUser(idUser);
    }

    public boolean createLoan(ApplicationLoan loan){
        return applicationLoanDAO.createLoan(loan);
    }

    public boolean updateLoanApplication(ApplicationLoan loan){
        return this.applicationLoanDAO.updateLoanApplication(loan);
    }
    public boolean deleteLoanApplication(ApplicationLoan applicationLoan){
        return this.applicationLoanDAO.deleteLoanApplication(applicationLoan);
    }
}
