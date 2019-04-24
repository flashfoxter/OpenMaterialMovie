/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.factories;

import com.lk.openmaterialmovie.network.ApiClient;
import com.lk.openmaterialmovie.service.MovieService;

import lombok.Getter;

public enum ServiceFactory {

    GET;

    // @formatter:off
    @Getter
    private MovieService movieService;
    // @formatter:on

    ServiceFactory() {
        init();
    }

    private static <T> T getService(Class<T> c, boolean mock) {
        return ApiClient.getService(c);
    }

    public void init() {
        movieService = getService(MovieService.class, false);
    }

}
