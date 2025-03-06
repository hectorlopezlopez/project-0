package com.DEPI.Service;

import com.DEPI.DAO.LoanDAO;
import com.DEPI.Model.Loan;

import javax.naming.Context;
import java.util.List;

public class LoanService {
    LoanDAO loanDAO;

    public LoanService() {
        this.loanDAO = new LoanDAO();
    }

    public List<Loan> getAllLoans(){
        return this.loanDAO.getAllLoans();
    }

    public boolean createLoan(Loan loan){
        return loanDAO.createLoan(loan);
    }
}
