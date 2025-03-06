package com.DEPI.DTO;

import java.time.LocalDate;

public class RequestUserApplicationLoanDTO {

    private String name;
    private String lastName;
    private String address;
    private double amount;
    private short interest;
    private LocalDate date_application;


    public RequestUserApplicationLoanDTO(String name, String lastName, String address, double amount, short interest, LocalDate date_application) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.amount = amount;
        this.interest = interest;
        this.date_application = date_application;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}

