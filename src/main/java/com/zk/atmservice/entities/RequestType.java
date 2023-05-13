package com.zk.atmservice.entities;

public enum RequestType {
    STANDARD(3),
    PRIORITY(1),
    SIGNAL_LOW(2),
    FAILURE_RESTART(0);

    private final int val;

    RequestType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

}
