package com.zk.transactions;

public class Account {
    private double balance;
    private int creditCount;
    private int debitCount;
    private String account;

    public Account(String accountNr) {
        this.account = accountNr;
    }

    public Account(final String account, final int debitCount, final int creditCount, final double balance) {
        this.balance = balance;
        this.creditCount = creditCount;
        this.debitCount = debitCount;
        this.account = account;
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
                ", account='" + account + '\'' +
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


    public String getAccount() {
        return account;
    }

}
