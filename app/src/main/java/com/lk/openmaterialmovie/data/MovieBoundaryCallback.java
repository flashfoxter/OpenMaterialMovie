/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.lk.openmaterialmovie.Constants;
import com.lk.openmaterialmovie.data.db.dao.MovieDao;
import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.dto.MovieListResponse;
import com.lk.openmaterialmovie.service.MovieService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieBoundaryCallback extends PagedList.BoundaryCallback<Movie> implements Callback<MovieListResponse> {
    private MovieService movieService;
    private MovieDao movieDao;
    private Executor executor;
    private MutableLiveData<NetworkState> networkState;
    private int lastPage = 1;

    public MovieBoundaryCallback(MovieService newsService, MovieDao movieDao) {
        this.movieService = newsService;
        this.movieDao = movieDao;
        this.executor = Executors.newFixedThreadPool(2);
        // TODO: 2019-04-29 Temp solution for pre_calc items in db and calc last page
        executor.execute(() -> {
            List<Movie> allSync = movieDao.getAllSync();
            lastPage = allSync.size() / Constants.DB_PAGE_SIZE + ((allSync.size() % Constants.DB_PAGE_SIZE == 0) ? 0 : 1);
        });
        networkState = new MutableLiveData<>();
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        executor.execute(() -> fetchMovies(lastPage));
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Movie itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);

        executor.execute(() -> fetchMovies(lastPage));
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    private void fetchMovies(int page) {
        networkState.postValue(NetworkState.LOADING);
        movieService.getMoviesByGenres(Constants.DEFAULT_GENRE, Constants.KEY_THE_MOVIE_DB, page).enqueue(this);
    }

    @Override
    public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
        if (response.isSuccessful()) {
            lastPage++;
            networkState.postValue(NetworkState.LOADED);
            executor.execute(() -> {
                // TODO: 2019-04-29 Create generic safety response
                movieDao.insertCollection(response.body().getResults());
            });
        } else {
            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
        }
    }

    @Override
    public void onFailure(Call<MovieListResponse> call, Throwable t) {
        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, t.getMessage()));
    }
}