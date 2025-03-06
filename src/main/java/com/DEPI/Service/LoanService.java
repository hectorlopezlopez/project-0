package com.DEPI.Service;

import com.DEPI.DAO.LoanDAO;
import com.DEPI.DTO.RequestLoanDTO;
import com.DEPI.Model.Loan;

import java.util.List;

public class LoanService {
    private final LoanDAO loanDAO;

    public LoanService() {
        this.loanDAO = new LoanDAO();
    }

    public List<Loan> getAllLoans() {
        return loanDAO.getAllLoans();
    }

    public String createLoan(RequestLoanDTO loanDTO) {
        if (loanDTO.getName() == null || loanDTO.getName().trim().isEmpty()) {
            return "Loan name is required";
        }
        if (loanDTO.getDescription() == null || loanDTO.getDescription().trim().isEmpty()) {
            return "Loan description is required";
        }
        if (loanDTO.getInterest_base() <= 0) {
            return "Interest base must be greater than zero";
        }

        Loan loan = new Loan();
        loan.setName(loanDTO.getName());
        loan.setDescription(loanDTO.getDescription());
        loan.setInterest_base(loanDTO.getInterest_base());

        return loanDAO.createLoan(loan) ? "Loan created" : "Error creating loan";
    }
}
