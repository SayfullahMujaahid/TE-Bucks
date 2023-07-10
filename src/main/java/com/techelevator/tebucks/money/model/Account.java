package com.techelevator.tebucks.money.model;

import java.util.Objects;

public class Account {

    private int accountId;

    private int userId;
    private final double INITIAL_BALANCE = 1000;
    private double balance;

    public Account() {

    }

    public Account(int accountId, int userId) {
        this.accountId = accountId;
        this.userId = userId;
        balance = INITIAL_BALANCE;


    }
    public Account(int accountId, int userId, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;


    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance ;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


}
