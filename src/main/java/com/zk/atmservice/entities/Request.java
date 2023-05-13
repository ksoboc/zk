package com.zk.atmservice.entities;

import java.util.Objects;

public class Request {
    private final int region;
    private final RequestType requestType;
    private final int atmId;
    private int number;

    public Request(final int region, final RequestType requestType, final int atmId) {
        this.region = region;
        this.requestType = requestType;
        this.atmId = atmId;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return region == request.region && atmId == request.atmId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, atmId);
    }

    public int getRegion() {
        return region;
    }


    public RequestType getRequestType() {
        return requestType;
    }


    public int getAtmId() {
        return atmId;
    }


    public int getNumber() {
        return number;
    }
}
