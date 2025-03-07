package com.DEPI.Service;

import com.DEPI.DAO.ApplicationLoanDAO;
import com.DEPI.DTO.RequestApplicationLoanDTO;
import com.DEPI.Model.ApplicationLoan;
import com.DEPI.Util.LoanStatus;

import java.util.List;

public class ApplicationLoanService {
    private final ApplicationLoanDAO applicationLoanDAO;

    public ApplicationLoanService() {
        this.applicationLoanDAO = new ApplicationLoanDAO();
    }

    public List<ApplicationLoan> getAllLoansApplications() {
        return applicationLoanDAO.getAllLoansApplications();
    }

    public List<ApplicationLoan> getApplicationByUser(int userId) {
        return applicationLoanDAO.getApplicationByUser(userId);
    }

    public ApplicationLoan getApplicationById(int applicationId) {
        return applicationLoanDAO.getApplicationById(applicationId);
    }

    public String createLoan(RequestApplicationLoanDTO loanDTO, int userId) {
        if (loanDTO.getAmount() <= 0 || loanDTO.getDate_application() == null || loanDTO.getInterest() <= 0) {
            return "Missing or invalid data";
        }

        ApplicationLoan loan = new ApplicationLoan();
        loan.setAmount(loanDTO.getAmount());
        loan.setInterest(loanDTO.getInterest());
        loan.setDate_application(loanDTO.getDate_application());
        loan.setId_user(userId);
        loan.setId_loan(loanDTO.getId_loan());

        return applicationLoanDAO.createLoan(loan) ? "Loan created" : "Error creating loan";
    }

    public String updateLoanApplication(int idApplicationLoan, RequestApplicationLoanDTO loanDTO) {
        ApplicationLoan loan = applicationLoanDAO.getLoanById(idApplicationLoan);
        if (loan == null) {
            return "Loan not found";
        }

        if (loanDTO.getAmount() > 0) loan.setAmount(loanDTO.getAmount());
        if (loanDTO.getInterest() > 0) loan.setInterest(loanDTO.getInterest());
        if (loanDTO.getDate_application() != null) loan.setDate_application(loanDTO.getDate_application());

        return applicationLoanDAO.updateLoanApplication(loan) ? "Loan updated" : "Error updating loan";
    }
    public String updateLoanStatusApplication(int idApplicationLoan, LoanStatus loanStatus) {
        ApplicationLoan loan = applicationLoanDAO.getLoanById(idApplicationLoan);
        if (loan == null) {
            return "Loan not found";
        }


        return applicationLoanDAO.updateLoanStatusApplication(idApplicationLoan,loanStatus) ? "Loan updated" : "Error updating loan";
    }

    public boolean deleteLoanApplication(int idApplicationLoan) {
        return applicationLoanDAO.deleteLoanApplication(idApplicationLoan);
    }
}
