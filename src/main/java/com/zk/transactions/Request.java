package com.zk.transactions;

public class Request {
    private String debitAccount;
    private String creditAccount;
    private double amount;

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
