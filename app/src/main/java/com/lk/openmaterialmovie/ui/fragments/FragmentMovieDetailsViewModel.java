/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.fragments;

import com.lk.openmaterialmovie.Constants;
import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.model.BaseViewModel;
import com.lk.openmaterialmovie.network.MutableResponse;

public class FragmentMovieDetailsViewModel extends BaseViewModel {

    public int movieId;

    public FragmentMovieDetailsViewModel(int movieId) {
        this.movieId = movieId;
    }

    MutableResponse getTrailers() {
        return call(ServiceFactory.GET.getMovieService().getTrailers(movieId, Constants.KEY_THE_MOVIE_DB));
    }

}
