/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.domain;

public interface UseCase<P> {

    void execute(P parameter, Callback callback);

    interface Callback {

        void onSuccess();

        void onError(Throwable throwable);
    }
}
