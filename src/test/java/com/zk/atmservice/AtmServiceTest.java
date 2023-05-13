package com.zk.atmservice;

import static org.junit.jupiter.api.Assertions.*;


import com.zk.atmservice.entities.Request;
import com.zk.atmservice.entities.RequestType;
import com.zk.atmservice.entities.Response;
import com.zk.atmservice.services.AtmService;
import com.zk.atmservice.services.AtmServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AtmServiceTest {

    @Autowired
    private AtmService atmService;

    @Test
    void atmServiceShouldReturnListOfAtmsToVisitFirstExample() {
        List<Request> requests = List.of(
                new Request(4, RequestType.STANDARD, 1),
                new Request(1, RequestType.STANDARD, 1),
                new Request(2, RequestType.STANDARD, 1),
                new Request(3, RequestType.PRIORITY, 2),
                new Request(3, RequestType.STANDARD, 1),
                new Request(2, RequestType.SIGNAL_LOW, 1),
                new Request(5, RequestType.STANDARD, 1),
                new Request(5, RequestType.STANDARD, 2),
                new Request(5, RequestType.FAILURE_RESTART, 1));

        List<Response> expected = List.of(
                new Response(1, 1),
                new Response(2, 1),
                new Response(3, 2),
                new Response(3, 1),
                new Response(4, 1),
                new Response(5, 1),
                new Response(5, 2));

        var res = atmService.calculateOrder(requests);
        assertEquals(expected, res);
    }


    @Test
    void atmServiceShouldReturnListOfAtmsToVisitSecondExample()  {
        final AtmService atmService = new AtmServiceImpl();
        List<Request> requests = List.of(
                new Request(1, RequestType.STANDARD, 2),
                new Request(1, RequestType.STANDARD, 1),
                new Request(2, RequestType.PRIORITY, 3),
                new Request(3, RequestType.STANDARD, 4),
                new Request(4, RequestType.STANDARD, 5),
                new Request(5, RequestType.PRIORITY, 2),
                new Request(5, RequestType.STANDARD, 1),
                new Request(3, RequestType.SIGNAL_LOW, 2),
                new Request(2, RequestType.SIGNAL_LOW, 1),
                new Request(3, RequestType.FAILURE_RESTART, 1));

        List<Response> expected = List.of(new Response(1, 2),
                new Response(1, 1),
                new Response(2, 3),
                new Response(2, 1),
                new Response(3, 1),
                new Response(3, 2),
                new Response(3, 4),
                new Response(4, 5),
                new Response(5, 2),
                new Response(5, 1));

        var res = atmService.calculateOrder(requests);
        assertEquals(expected, res);
    }

    @Test
    void atmServiceShouldReturnEmptyListWhenRequestsAreEmpty() {
        // given
        List<Request> requests = List.of();

        // when
        List<Response> responses = atmService.calculateOrder(requests);

        // then
        assertEquals(0, responses.size());
    }

    @Test
    void atmServiceShouldReturnSameNumberOfResponsesForUniqueRequests() {
        List<Request> requests = List.of(new Request(1, RequestType.STANDARD, 2),
                new Request(1, RequestType.STANDARD, 3),
                new Request(2, RequestType.PRIORITY, 4));
        List<Response> responses = atmService.calculateOrder(requests);
        assertEquals(requests.size(), responses.size());
    }

    @Test
    void atmServiceShouldNotReturnDuplicates() {
        List<Request> requests = List.of(new Request(1, RequestType.STANDARD, 2),
                new Request(1, RequestType.STANDARD, 2),
                new Request(2, RequestType.PRIORITY, 4),
                new Request(2, RequestType.STANDARD, 4));
        List<Response> responses = atmService.calculateOrder(requests);
        assertEquals(2, responses.size());
        assertEquals(2, responses.get(0).getAtmId());
        assertEquals(4, responses.get(1).getAtmId());
    }

    @Test
    void atmServiceShouldVisitInOrderOfRegions() {
        List<Request> requests = List.of(new Request(1, RequestType.STANDARD, 2),
                new Request(2, RequestType.PRIORITY, 4),
                new Request(1, RequestType.STANDARD, 3),
                new Request(3, RequestType.PRIORITY, 5),
                new Request(2, RequestType.STANDARD, 6));
        List<Response> responses = atmService.calculateOrder(requests);
        assertArrayEquals(new int[]{1,1,2,2,3}, responses.stream().mapToInt(Response::getRegion).toArray());
    }

    @Test
    void atmServiceShouldHonourPriorityWithinRegion() {
        List<Request> requests = List.of(new Request(1, RequestType.STANDARD, 2),
                new Request(1, RequestType.SIGNAL_LOW, 4),
                new Request(1, RequestType.PRIORITY, 3),
                new Request(1, RequestType.FAILURE_RESTART, 5)
                );
        List<Response> responses = atmService.calculateOrder(requests);
        assertArrayEquals(new int[]{5,3,4,2}, responses.stream().mapToInt(Response::getAtmId).toArray());
    }

    @Test
    void atmServiceShouldThrowProperExceptionOnNull() {
        assertThrows(ConstraintViolationException.class, () -> atmService.calculateOrder(null));
    }

}