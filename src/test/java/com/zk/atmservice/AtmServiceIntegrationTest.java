package com.zk.atmservice;

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
class AtmServiceIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnAtmsListAsInFirstExample() throws JsonProcessingException {
        String requestBody = """
                [
                  {
                    "region": 4,
                    "requestType": "STANDARD",
                    "atmId": 1
                  },
                  {
                    "region": 1,
                    "requestType": "STANDARD",
                    "atmId": 1
                  },
                  {
                    "region": 2,
                    "requestType": "STANDARD",
                    "atmId": 1
                  },
                  {
                    "region": 3,
                    "requestType": "PRIORITY",
                    "atmId": 2
                  },
                  {
                    "region": 3,
                    "requestType": "STANDARD",
                    "atmId": 1
                  },
                  {
                    "region": 2,
                    "requestType": "SIGNAL_LOW",
                    "atmId": 1
                  },
                  {
                    "region": 5,
                    "requestType": "STANDARD",
                    "atmId": 2
                  },
                  {
                    "region": 5,
                    "requestType": "FAILURE_RESTART",
                    "atmId": 1
                  }
                ]
                """;

        ResponseEntity<? extends String> response = getResponseEntity(requestBody);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        String expectedResponse = """
            [
              {
                "region": 1,
                "atmId": 1
              },
              {
                "region": 2,
                "atmId": 1
              },
              {
                "region": 3,
                "atmId": 2
              },
              {
                "region": 3,
                "atmId": 1
              },
              {
                "region": 4,
                "atmId": 1
              },
              {
                "region": 5,
                "atmId": 1
              },
              {
                "region": 5,
                "atmId": 2
              }
            ]
            """;



        assertEquals(objectMapper.readTree(expectedResponse), objectMapper.readTree(response.getBody()));
    }


    @Test
    void shouldReturnAtmsListAsInSecondExample() throws JSONException, JsonProcessingException {
        String requestBody = """
                [
                   {
                     "region": 1,
                     "requestType": "STANDARD",
                     "atmId": 2
                   },
                   {
                     "region": 1,
                     "requestType": "STANDARD",
                     "atmId": 1
                   },
                   {
                     "region": 2,
                     "requestType": "PRIORITY",
                     "atmId": 3
                   },
                   {
                     "region": 3,
                     "requestType": "STANDARD",
                     "atmId": 4
                   },
                   {
                     "region": 4,
                     "requestType": "STANDARD",
                     "atmId": 5
                   },
                   {
                     "region": 5,
                     "requestType": "PRIORITY",
                     "atmId": 2
                   },
                   {
                     "region": 5,
                     "requestType": "STANDARD",
                     "atmId": 1
                   },
                   {
                     "region": 3,
                     "requestType": "SIGNAL_LOW",
                     "atmId": 2
                   },
                   {
                     "region": 2,
                     "requestType": "SIGNAL_LOW",
                     "atmId": 1
                   },
                   {
                     "region": 3,
                     "requestType": "FAILURE_RESTART",
                     "atmId": 1
                   }
                 ]
                """;

        ResponseEntity<? extends String> response = getResponseEntity(requestBody);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        String expectedResponse = """
            [
              {
                "region": 1,
                "atmId": 2
              },
              {
                "region": 1,
                "atmId": 1
              },
              {
                "region": 2,
                "atmId": 3
              },
              {
                "region": 2,
                "atmId": 1
              },
              {
                "region": 3,
                "atmId": 1
              },
              {
                "region": 3,
                "atmId": 2
              },
              {
                "region": 3,
                "atmId": 4
              },
              {
                "region": 4,
                "atmId": 5
              },
              {
                "region": 5,
                "atmId": 2
              },
              {
                "region": 5,
                "atmId": 1
              }
            ]
            """;

        assertEquals(objectMapper.readTree(expectedResponse), objectMapper.readTree(response.getBody()));
    }

    private ResponseEntity<? extends String> getResponseEntity(String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> request =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<? extends String> response = restTemplate.postForEntity("/atms/calculateOrder", request, String.class);
        return response;
    }

}