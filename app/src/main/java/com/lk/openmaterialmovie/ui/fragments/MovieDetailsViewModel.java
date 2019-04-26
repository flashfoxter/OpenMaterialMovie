/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.fragments;

import com.lk.openmaterialmovie.Constants;
import com.lk.openmaterialmovie.dto.MovieDto;
import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.model.BaseViewModel;
import com.lk.openmaterialmovie.network.MutableResponse;

public class MovieDetailsViewModel extends BaseViewModel {

    private MovieDto movieDto;

    public MovieDetailsViewModel(MovieDto movieDto) {
        this.movieDto = movieDto;
    }

    MutableResponse getTrailers() {
        return call(ServiceFactory.GET.getMovieService().getTrailers(movieDto.getId(), Constants.KEY_THE_MOVIE_DB));
    }
}
