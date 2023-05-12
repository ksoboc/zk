package com.zk.atmservice;

import java.util.Objects;

public class Response {
    private final int region;
    private final int atmId;

    public Response(final int region, final int atmId) {
        this.region = region;
        this.atmId = atmId;
    }

    @Override
    public String toString() {
        return "Response{" +
                "region=" + region +
                ", atmId=" + atmId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return region == response.region && atmId == response.atmId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, atmId);
    }

    public int getRegion() {
        return region;
    }

    public int getAtmId() {
        return atmId;
    }
}
