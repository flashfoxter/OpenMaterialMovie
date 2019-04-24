/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.network;

import com.lk.openmaterialmovie.enums.NetworkResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import okhttp3.Request;
import okhttp3.ResponseBody;

@NoArgsConstructor
@ToString
public class BaseResponse {

    //@formatter:off
    @Getter
    @Setter
    private NetworkResponse networkResponseStatus;
    @Getter
    @Setter
    private Request request;
    @Getter
    @Setter
    private ResponseBody error; // Only for retrofit, not for okHttp
    @Getter
    @Setter
    private String errorString; // Only for retrofit, not for okHttp
    //@formatter:on

    public BaseResponse(NetworkResponse networkResponseStatus) {
        this.networkResponseStatus = networkResponseStatus;
    }
}