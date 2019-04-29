/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.data;

public class NetworkState {

    public static final NetworkState LOADED;
    static final NetworkState LOADING;

    static {
        LOADED = new NetworkState(Status.SUCCESS, "Success");
        LOADING = new NetworkState(Status.RUNNING, "Running");
    }

    private final Status status;
    private final String msg;

    NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}