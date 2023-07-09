package com.techelevator.tebucks.money.model;

import com.techelevator.tebucks.money.model.Account;
import com.techelevator.tebucks.security.model.User;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class Transfer {


    Account account = new Account();
    @NotEmpty
    private int transferId;
    @NotEmpty
    private String transferType;
    @NotEmpty
    private String transferStatus;
    @NotEmpty
    private User userFrom;
    @NotEmpty
    private User userTo;
    @Positive
    private double amount;
    public Transfer () {

    }

    public Transfer(int transferId, String transferType, String transferStatus, double amount) {
        this.account = account;
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.amount = amount;
    }

    @AssertTrue
    public boolean enoughMoney() {
        if (amount > account.getBalance()) {
            return true;
        } else {
            return false;
        }
    }

    @AssertTrue
    public boolean cantSendMoneyToSelf() {
        if (userFrom != userTo) {
            return true;
        } else {
            return false;
        }
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
