package com.zk.transactions;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface TransactionService {
    List<Account> report(@NotNull List<Request> requests);
}
