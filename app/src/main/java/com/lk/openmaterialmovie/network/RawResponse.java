/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.network;


import com.lk.openmaterialmovie.enums.NetworkResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString(callSuper = true)
public class RawResponse<Response> extends BaseResponse {

    @Getter
    @Setter
    private Response data;

    public RawResponse(NetworkResponse networkResponseStatus) {
        super(networkResponseStatus);
    }

}
