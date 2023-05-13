package com.zk.transactions.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    private double balance;
    private int creditCount;
    private int debitCount;
    private final String accountNr;

    public Account(String accountNr) {
        this.accountNr = accountNr;
    }

    public Account(final String account, final int debitCount, final int creditCount, final double balance) {
        this.balance = balance;
        this.creditCount = creditCount;
        this.debitCount = debitCount;
        this.accountNr = account;
    }

    public void withDraw(double amount) {
        ++debitCount;
        balance -= amount;
    }

    public void deposit(double amount) {
        ++creditCount;
        balance += amount;
    }

    @Override
    public String toString() {
        return "{" +
                "balance=" + balance +
                ", creditCount=" + creditCount +
                ", debitCount=" + debitCount +
                ", account='" + accountNr + '\'' +
                '}';
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCreditCount() {
        return creditCount;
    }


    public int getDebitCount() {
        return debitCount;
    }

    @JsonProperty("account")
    public String getAccountNr() {
        return accountNr;
    }

}
