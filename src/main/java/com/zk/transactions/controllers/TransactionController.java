package com.zk.transactions.controllers;

import com.zk.transactions.entities.Account;
import com.zk.transactions.entities.Request;
import com.zk.transactions.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/report")
    public List<Account> report(@RequestBody List<Request> requests) {
        return transactionService.report(requests);
    }
}
