/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.network;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.function.Consumer;
import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.enums.NetworkResponse;
import com.lk.openmaterialmovie.helpers.Dialogue;

/**
 * Created by Levashkin Konstantin on 11/27/18
 */
public class MutableResponse extends MutableLiveData<RawResponse> {

    public void onChangeOnce(@NonNull LifecycleOwner owner, Consumer<RawResponse> responseSuccessConsumer) {
        observe(owner, r -> onBaseResponse(r, responseSuccessConsumer));
    }

    private void onBaseResponse(RawResponse response, Consumer<RawResponse> responseSuccessConsumer) {
        onBaseResponse(response, responseSuccessConsumer, null);
    }

    public void onBaseResponse(RawResponse response, Consumer<RawResponse> responseSuccessConsumer, @Nullable Consumer<RawResponse> withCustomError) {
        if (response != null) {
            if (response.getNetworkResponseStatus() == NetworkResponse.SUCCESS) {
                responseSuccessConsumer.accept(response);
            } else {
                if (withCustomError != null) {
                    withCustomError.accept(response);
                } else {
                    Dialogue.showError(response.getErrorString());
                }
            }
        } else {
            Dialogue.showError(R.string.common_unexpectedError);
        }
    }
}
