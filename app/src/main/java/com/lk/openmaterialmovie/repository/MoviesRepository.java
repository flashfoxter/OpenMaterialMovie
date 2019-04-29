/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.lk.openmaterialmovie.Constants;
import com.lk.openmaterialmovie.data.MovieBoundaryCallback;
import com.lk.openmaterialmovie.data.NetworkState;
import com.lk.openmaterialmovie.data.db.MovieDatabase;
import com.lk.openmaterialmovie.data.db.dao.MovieDao;
import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.dto.MovieListResponse;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.log.Logger;
import com.lk.openmaterialmovie.service.MovieService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Response;

public class MoviesRepository extends BaseRepository {

    private final MovieService movieService;
    private final MovieDao movieDao;
    private MutableLiveData<NetworkState> networkState;

    @Inject
    public MoviesRepository(MovieService movieService) {
        this.movieService = movieService;
        // TODO: 2019-04-26 Rework to inject but injection needed
        this.movieDao = MovieDatabase.getInstance(Ui.getBaseContext()).getMovieDao();
    }

    public LiveData<List<Movie>> getMovies(int page) {
        refreshData(page);
        return movieDao.getAll();
    }

    public LiveData<PagedList<Movie>> getPagedMovies() {
        DataSource.Factory<Integer, Movie> dataSource = movieDao.getAllPaged();
        MovieBoundaryCallback callback = new MovieBoundaryCallback(movieService, movieDao);
        networkState = callback.getNetworkState();
        return new LivePagedListBuilder<>(dataSource, Constants.DB_PAGE_SIZE)
                .setBoundaryCallback(callback)
                .build();
    }

    private void refreshData(int page) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                Response<MovieListResponse> movieListResponseResponse = movieService.getMoviesByGenres(Constants.DEFAULT_GENRE, Constants.KEY_THE_MOVIE_DB, page).execute();
                List<Movie> results = new ArrayList<>();
                if (movieListResponseResponse.body() != null) {
                    results = movieListResponseResponse.body().getResults();
                }
                if (!results.isEmpty()) {
                    movieDao.insertCollection(results);
                }
            } catch (IOException e) {
                Logger.e(e);
            }
        });
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
