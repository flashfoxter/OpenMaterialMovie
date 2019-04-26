/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.lk.openmaterialmovie.Constants;
import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.network.MutableResponse;
import com.lk.openmaterialmovie.ui.BaseViewModel;

import lombok.Getter;
import lombok.Setter;

public class MovieDetailsViewModel extends BaseViewModel {

    @Getter
    @Setter
    private Movie movie;

    public MovieDetailsViewModel(Movie movie) {
        this.movie = movie;
    }

    MutableResponse getTrailers() {
        return call(ServiceFactory.GET.getMovieService().getTrailers(movie.getId(), Constants.KEY_THE_MOVIE_DB));
    }

    public static class ViewModelDetailsFactory implements ViewModelProvider.Factory {
        private Movie movie;

        public ViewModelDetailsFactory(Movie movie) {
            this.movie = movie;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new MovieDetailsViewModel(movie);
        }
    }
}
