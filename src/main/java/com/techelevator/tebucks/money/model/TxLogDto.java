package com.techelevator.tebucks.money.model;

public class TxLogDto {

    private String description;
    private String username_from;
    private String username_to;
    private double amount;

    public TxLogDto() {

    }
    public TxLogDto(String description, String username_from, String username_to, double amount) {
        this.description = description;
        this.username_from = username_from;
        this.username_to = username_to;
        this.amount = amount;
    }

    public TxLogDto(Transfer transfer) {
        this.username_from = transfer.getUserFrom().getUsername();
        this.username_to = transfer.getUserTo().getUsername();
        this.amount = transfer.getAmount();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername_from() {
        return username_from;
    }

    public void setUsername_from(String username_from) {
        this.username_from = username_from;
    }

    public String getUsername_to() {
        return username_to;
    }

    public void setUsername_to(String username_to) {
        this.username_to = username_to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
