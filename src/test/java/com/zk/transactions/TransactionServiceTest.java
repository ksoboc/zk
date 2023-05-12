package com.zk.transactions;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Test
    void transactionServiceShouldReturnListOfAccountsAsInExample()  {
        List<Request> requests = List.of(
                new Request("32309111922661937852684864", "06105023389842834748547303", 10.9),
                new Request("31074318698137062235845814", "66105036543749403346524547", 200.9),
                new Request("66105036543749403346524547", "32309111922661937852684864", 50.1));

        List<Account> expectedResponse = List.of(
                new Account("06105023389842834748547303", 0, 1, 10.9),
                new Account("31074318698137062235845814", 1, 0, -200.9),
                new Account("32309111922661937852684864", 1, 1, 39.2),
                new Account("66105036543749403346524547", 1, 1, 150.8)
        );
        var response = transactionService.report(requests);

        for (int i=0;i< expectedResponse.size();++i) {
            assertEquals(expectedResponse.get(i).getAccount(), response.get(i).getAccount());
            assertEquals(expectedResponse.get(i).getDebitCount(), response.get(i).getDebitCount());
            assertEquals(expectedResponse.get(i).getCreditCount(), response.get(i).getCreditCount());
            assertEquals(expectedResponse.get(i).getBalance(), response.get(i).getBalance(), 0.001);
        }

    }

    @Test
    void transactionServiceShouldReturnListOfAccountsAnotherExample() {
        // Given
        List<Request> requests = List.of(
                new Request("123", "456", 100.0),
                new Request("456", "789", 200.0),
                new Request("123", "789", 50.0),
                new Request("456", "123", 75.0)
        );

        // When
        List<Account> accounts = transactionService.report(requests);

        // Then
        assertNotNull(accounts);
        assertEquals(3, accounts.size());

        Account account123 = accounts.get(0);
        Account account456 = accounts.get(1);
        Account account789 = accounts.get(2);

        assertEquals("123", account123.getAccount());
        assertEquals(-75.0, account123.getBalance(), 0.001);

        assertEquals("456", account456.getAccount());
        assertEquals(-175.0, account456.getBalance(), 0.001);

        assertEquals("789", account789.getAccount());
        assertEquals(250.0, account789.getBalance(), 0.001);
    }

    @Test
    void transactionServiceShouldReturnEmptyListForEmptyRequestList() {
        // Given
        List<Request> requests = List.of();

        // When
        List<Account> accounts = transactionService.report(requests);

        // Then
        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
    }

    @Test
    void transactionServiceShouldThrowProperExceptionOnNull() {

        assertThrows(ConstraintViolationException.class, () -> transactionService.report(null));
    }
}