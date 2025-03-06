package com.DEPI.Model;

import com.DEPI.Util.LoanStatus;

import java.time.LocalDate;

public class ApplicationLoan {
    private int id_application;
    private double amount  ;
    private short interest ;
    private LocalDate date_application;
    private LoanStatus status;
    private int id_loan;
    private int id_user;

    public ApplicationLoan() {
    }

    public ApplicationLoan(int id_application, double amount, short interest, LocalDate date_application, LoanStatus status, int id_loan, int id_user) {
        this.id_application = id_application;
        this.amount = amount;
        this.interest = interest;
        this.date_application = date_application;
        this.status = status;
        this.id_loan = id_loan;
        this.id_user = id_user;
    }

    public int getId_application() {
        return id_application;
    }

    public void setId_application(int id_application) {
        this.id_application = id_application;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public short getInterest() {
        return interest;
    }

    public void setInterest(short interest) {
        this.interest = interest;
    }

    public LocalDate getDate_application() {
        return date_application;
    }

    public void setDate_application(LocalDate date_application) {
        this.date_application = date_application;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public int getId_loan() {
        return id_loan;
    }

    public void setId_loan(int id_loan) {
        this.id_loan = id_loan;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "ApplicationLoan{" +
                "id_application=" + id_application +
                ", amount=" + amount +
                ", interest=" + interest +
                ", date_application=" + date_application +
                ", id_loan=" + id_loan +
                ", id_user=" + id_user +
                '}';
    }
}
