package com.zk.transactions.services;

import com.zk.transactions.entities.Account;
import com.zk.transactions.entities.Request;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    public List<Account> report(List<Request> requestList) {


        Map<String, Account> accounts = new HashMap<>();
        for (var request : requestList) {
            String debitAccount = request.getDebitAccount();
            String creditAccount = request.getCreditAccount();
            double amount = request.getAmount();

            accounts.computeIfAbsent(debitAccount, x -> new Account(debitAccount)).withDraw(amount);
            accounts.computeIfAbsent(creditAccount, x -> new Account(creditAccount)).deposit(amount);
        }
        List<Account> accountList = new ArrayList<>(accounts.size());
        accountList.addAll(accounts.values());
        accountList.sort(Comparator.comparing(Account::getAccountNr));
        return accountList;
    }

}
