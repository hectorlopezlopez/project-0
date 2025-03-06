package com.DEPI.Service;

import com.DEPI.DAO.LoanDAO;
import com.DEPI.Model.Loan;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class LoanServiceTest extends TestCase {

    private LoanService loanService;
    private LoanDAO loanDAO;

    @BeforeEach
    public void setUp() {
        loanDAO = Mockito.mock(LoanDAO.class);
        loanService = new LoanService();
        loanService.loanDAO = loanDAO;
    }

    @Test
    public void createLoanTest(){
        LoanService loanService = new LoanService();
        Loan loan = new Loan(1, "example name","example of desc", (short) 2);
        assertTrue(loanService.createLoan(loan));
    }
  
}