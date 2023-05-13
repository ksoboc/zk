package com.zk.atmservice.services;

import com.zk.atmservice.entities.Request;
import com.zk.atmservice.entities.RequestType;
import com.zk.atmservice.entities.Response;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
public class AtmServiceImpl implements AtmService {
    private Comparator<Request> requestComparator() {
        return (o1, o2) -> {
            if (o1.getRegion() != o2.getRegion()) {
                return Integer.compare(o1.getRegion(), o2.getRegion());
            }
            if (o1.getRequestType() != o2.getRequestType())
                return Integer.compare(o1.getRequestType().getVal(), o2.getRequestType().getVal());
            return Integer.compare(o1.getNumber(), o2.getNumber());
        };
    }
    @Override
    public List<Response> calculateOrder(List<Request> requests) {

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
