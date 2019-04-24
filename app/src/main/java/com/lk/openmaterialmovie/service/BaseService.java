/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.service;

import com.lk.openmaterialmovie.network.ApiClient;
import com.lk.openmaterialmovie.network.MutableResponse;

import retrofit2.Call;

public class BaseService {
    protected static <T> T getService(Class<T> c, boolean mock) {
        return ApiClient.getService(c);
    }

    protected MutableResponse call(Call<?> call) {
        return BaseRepository.callStatic(call);
    }
}
