/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.repository;

import com.lk.openmaterialmovie.network.MutableResponse;

import retrofit2.Call;

public class BaseRepository {
    protected MutableResponse call(Call<?> call) {
        return com.lk.openmaterialmovie.service.BaseRepository.callStatic(call);
    }
}
