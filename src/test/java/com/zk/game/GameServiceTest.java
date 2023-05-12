package com.zk.game;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    void gameServiceShouldReturnGroupsListAsInExample() throws Exception {

        List<Clan> list = List.of(
                new Clan(4, 50),
                new Clan(2, 70),
                new Clan(6, 60),
                new Clan(1, 15),
                new Clan(5, 40),
                new Clan(3, 45),
                new Clan(1, 12),
                new Clan(4, 40));

        var res = gameService.calculate(6, list);
        var expectedResponse = List.of(
                List.of(new Clan(2, 70), new Clan(4, 50)),
                List.of(new Clan(6, 60)),
                List.of(new Clan(3, 45), new Clan(1, 15), new Clan(1, 12)),
                List.of(new Clan(4, 40)),
                List.of(new Clan(5, 40))
        );
        assertEquals(expectedResponse, res);
    }

    @Test
    void gameServiceShouldReturnEmptyListForEmptyClanList() {
        // Given
        List<Clan> clans = List.of();
        int groupCount = 10;

        // When
        List<List<Clan>> groupsOfClans = gameService.calculate(groupCount, clans);

        // Then
        assertNotNull(groupsOfClans);
        assertTrue(groupsOfClans.isEmpty());
    }

    @Test
    void gameServiceShouldThrowProperExceptionOnNullClanList() {
        assertThrows(ConstraintViolationException.class, () -> gameService.calculate(1,null));
    }

    @Test
    void gameServiceShouldThrowProperExceptionOnBadGroupSize() {
        assertThrows(ConstraintViolationException.class, () -> gameService.calculate(0,null));
    }


}