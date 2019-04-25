package com.lk.openmaterialmovie.ui.fragments;

import com.lk.openmaterialmovie.Constants;
import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.model.BaseViewModel;
import com.lk.openmaterialmovie.network.MutableResponse;

public class FragmentMoviesByGenreViewModel extends BaseViewModel {

    MutableResponse getMoviesByGenre(int page) {
        return call(ServiceFactory.GET.getMovieService().getMoviesByGenres(Constants.DEFAULT_GENRE, Constants.KEY_THE_MOVIE_DB));
    }

    MutableResponse getMoviesByPopular() {
        return call(ServiceFactory.GET.getMovieService().getMoviesByPopularity(Constants.Api.KEY_BY_POPULAR_DESC, Constants.KEY_THE_MOVIE_DB));
    }

    MutableResponse getMovies(Integer page) {
        return call(ServiceFactory.GET.getMovieService().getMovies(page, Constants.KEY_THE_MOVIE_DB));
    }
}
