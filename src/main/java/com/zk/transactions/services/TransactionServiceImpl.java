package com.zk.transactions.services;

import com.zk.transactions.entities.Account;
import com.zk.transactions.entities.Request;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    public List<Account> report(List<Request> requestList) {


        Map<String, Account> accountsMap = new HashMap<>();
        requestList.forEach(request -> {
            String debitAccount = request.getDebitAccount();
            String creditAccount = request.getCreditAccount();
            double amount = request.getAmount();
            accountsMap.computeIfAbsent(debitAccount, accountNr -> new Account(debitAccount)).withDraw(amount);
            accountsMap.computeIfAbsent(creditAccount, accountNr -> new Account(creditAccount)).deposit(amount);
        });
        List<Account> accountList = new ArrayList<>(accountsMap.size());
        accountList.addAll(accountsMap.values());
        accountList.sort(Comparator.comparing(Account::getAccountNr));
        return accountList;
    }

}
