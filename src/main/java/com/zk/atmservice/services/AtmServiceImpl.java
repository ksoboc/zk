package com.zk.atmservice.services;

import com.zk.atmservice.entities.Request;
import com.zk.atmservice.entities.RequestType;
import com.zk.atmservice.entities.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AtmServiceImpl implements AtmService {
    Logger logger = LoggerFactory.getLogger(AtmServiceImpl.class);
    private Comparator<Request> requestComparator() {
        return Comparator.comparingInt(Request::getRegion)
                .thenComparingInt(r -> r.getRequestType().getVal())
                .thenComparingInt(Request::getNumber);
    }
    @Override
    public List<Response> calculateOrder(List<Request> requests) {
        logger.info("calculateOrder called");
        Map<Request, RequestType> requestMap = new HashMap<>();
        int reqNr = 0;
        for (var request : requests) {
            request.setNumber(reqNr++);
            RequestType existingType = requestMap.get(request);
            if (existingType == null || existingType.getVal() < request.getRequestType().getVal()) {
                requestMap.put(request, request.getRequestType());
            }
        }

        List<Request> requestList = new ArrayList<>(requestMap.keySet());
        requestList.sort(requestComparator());

        List<Response> responses = new ArrayList<>(requestList.size());
        for (var request : requestList) {
            responses.add(new Response(request.getRegion(), request.getAtmId()));
        }

        return responses;
    }
}
