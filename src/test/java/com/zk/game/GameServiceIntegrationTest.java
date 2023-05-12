package com.zk.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void gameServiceShouldReturnGroupsListAsInExample() throws JsonProcessingException {

        String requestBody = """
                {
                  "groupCount": 6,
                  "clans": [
                    {
                      "numberOfPlayers": 4,
                      "points": 50
                    },
                    {
                      "numberOfPlayers": 2,
                      "points": 70
                    },
                    {
                      "numberOfPlayers": 6,
                      "points": 60
                    },
                    {
                      "numberOfPlayers": 1,
                      "points": 15
                    },
                    {
                      "numberOfPlayers": 5,
                      "points": 40
                    },
                    {
                      "numberOfPlayers": 3,
                      "points": 45
                    },
                    {
                      "numberOfPlayers": 1,
                      "points": 12
                    },
                    {
                      "numberOfPlayers": 4,
                      "points": 40
                    }
                  ]
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> request =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<? extends String> response = restTemplate.postForEntity("/onlinegame/calculate", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String expectedResponse = """
                [
                  [
                    {
                      "numberOfPlayers": 2,
                      "points": 70
                    },
                    {
                      "numberOfPlayers": 4,
                      "points": 50
                    }
                  ],
                  [
                    {
                      "numberOfPlayers": 6,
                      "points": 60
                    }
                  ],
                  [
                    {
                      "numberOfPlayers": 3,
                      "points": 45
                    },
                    {
                      "numberOfPlayers": 1,
                      "points": 15
                    },
                    {
                      "numberOfPlayers": 1,
                      "points": 12
                    }
                  ],
                  [
                    {
                      "numberOfPlayers": 4,
                      "points": 40
                    }
                  ],
                  [
                    {
                      "numberOfPlayers": 5,
                      "points": 40
                    }
                  ]
                ]
                """;

        assertEquals(objectMapper.readTree(expectedResponse), objectMapper.readTree(response.getBody()));

    }


}
