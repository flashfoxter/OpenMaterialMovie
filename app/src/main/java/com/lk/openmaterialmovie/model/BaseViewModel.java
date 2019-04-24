/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.model;

import android.arch.lifecycle.ViewModel;

import com.lk.openmaterialmovie.network.MutableResponse;
import com.lk.openmaterialmovie.service.BaseRepository;

import retrofit2.Call;

public class BaseViewModel extends ViewModel {

    protected MutableResponse call(Call<?> call) {
        return BaseRepository.callStatic(call);
    }

}
