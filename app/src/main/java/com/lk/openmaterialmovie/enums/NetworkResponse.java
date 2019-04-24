/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.enums;

import lombok.Getter;

public enum NetworkResponse {
    // generic cases
    SUCCESS,
    SERVER_ERROR,
    NO_CONNECTION,
    TIMEOUT,

    // custom cases
    AUTH_FAILED,
    SIGNUP_REQUEST_FAILED,
    DIFFERENT_API_VERSIONS,
    NO_ACCOUNTS,
    NOT_FOUND,

    // default case, shouldn't occur anywhere
    ERROR_UNEXPECTED;

    @Getter
    private String name;
    private boolean initialized;

    NetworkResponse() {
        name = "";
        initialized = false;
    }

    public void initName(String name) {
        if (!initialized) {
            initialized = true;
            this.name = name;
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
