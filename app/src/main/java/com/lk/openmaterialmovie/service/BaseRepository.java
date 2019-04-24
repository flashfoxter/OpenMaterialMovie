/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.service;


import com.lk.openmaterialmovie.network.CoreCall;
import com.lk.openmaterialmovie.network.MutableResponse;

import retrofit2.Call;


public class BaseRepository {

    public static MutableResponse callStatic(Call<?> call) {
        final MutableResponse data = new MutableResponse();
        call.enqueue(new CoreCall<>(call.request(), data::postValue));
        return data;
    }

    protected MutableResponse call(Call<?> call) {
        return callStatic(call);
    }
}
