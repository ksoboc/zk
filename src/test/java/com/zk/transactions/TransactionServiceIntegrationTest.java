package com.zk.transactions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionServiceIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void teransactionServiceShouldReturnAccountsListAsInExample() throws JsonProcessingException {
        String requestBody = """
                [
                  {
                    "debitAccount": "32309111922661937852684864",
                    "creditAccount": "06105023389842834748547303",
                    "amount": 10.90
                  },
                  {
                    "debitAccount": "31074318698137062235845814",
                    "creditAccount": "66105036543749403346524547",
                    "amount": 200.90
                  },
                  {
                    "debitAccount": "66105036543749403346524547",
                    "creditAccount": "32309111922661937852684864",
                    "amount": 50.10
                  }
                ]
                """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> request =
                new HttpEntity<>(requestBody, headers);
        ResponseEntity<? extends String> response = restTemplate.postForEntity("/transactions/report", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        String expectedResponse = """
              [
              {
                "account": "06105023389842834748547303",
                "debitCount": 0,
                "creditCount": 1,
                "balance": 10.90
              },
              {
                "account": "31074318698137062235845814",
                "debitCount": 1,
                "creditCount": 0,
                "balance": -200.90
              },
              {
                "account": "32309111922661937852684864",
                "debitCount": 1,
                "creditCount": 1,
                "balance": 39.20
              },
              {
                "account": "66105036543749403346524547",
                "debitCount": 1,
                "creditCount": 1,
                "balance": 150.80
              }
            ]
        """;

        assertEquals(objectMapper.readTree(expectedResponse), objectMapper.readTree(response.getBody()));
    }


}