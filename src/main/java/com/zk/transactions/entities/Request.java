package com.zk.transactions.entities;

public class Request {
    private final String debitAccount;
    private final String creditAccount;
    private final double amount;

    public Request(String debitAccount, String creditAccount, double amount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public double getAmount() {
        return amount;
    }

}
