package com.DEPI.Model;

public class Loan {
    private int id_loan;
    private String name;
    private String description;
    private short interest_base;

    public Loan() {
    }

    public Loan(int id_loan, String name, String description, short interest_base) {
        this.id_loan = id_loan;
        this.name = name;
        this.description = description;
        this.interest_base = interest_base;
    }

    public int getId_loan() {
        return id_loan;
    }

    public void setId_loan(int id_loan) {
        this.id_loan = id_loan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getInterest_base() {
        return interest_base;
    }

    public void setInterest_base(short interest_base) {
        this.interest_base = interest_base;
    }
}
