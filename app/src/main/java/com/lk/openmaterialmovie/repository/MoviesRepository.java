/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.repository;

import android.arch.lifecycle.LiveData;

import com.lk.openmaterialmovie.Constants;
import com.lk.openmaterialmovie.db.MovieDatabase;
import com.lk.openmaterialmovie.db.dao.MovieDao;
import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.dto.MovieListResponse;
import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.log.Logger;
import com.lk.openmaterialmovie.network.MutableResponse;
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
    //private final Executor executor;
    private final MovieDao movieDao;

    //movieService = ServiceFactory.GET.getMovieService();

    @Inject
    public MoviesRepository(MovieService movieService) {
        this.movieService = movieService;
        // TODO: 2019-04-26 Rework to inject but injection needed
        this.movieDao = MovieDatabase.getInstance(Ui.getBaseContext()).getMovieDao();
    }

    public LiveData<List<Movie>> getMovies(int page) {
        refreshData(page);
        // Returns a LiveData object directly from the database.
        return movieDao.getAll();
    }

    private void refreshData(int page) {
        // Runs in a background thread.


        //NetworkExecutor networkExecutor = new NetworkExecutor();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        //Executor executor = command -> {};
        executor.execute(() -> {
            String s = "s";
            // Check if user data was fetched recently.
            //boolean userExists = movieDao.hasUser(FRESH_TIMEOUT);

            //if (!userExists) {
            // Refreshes the data.

            // TODO: 2019-04-26 Remove constants from here and move it to method call
            //Response<User> response =
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

            // Check for errors here.

            // Updates the database. The LiveData object automatically
            // refreshes, so we don't need to do anything else here.

            //movieDao.save(response.body());
            //}
        });
        /*executor.execute(() -> {

        });*/
    }

  /*  private void refreshUser(final String userId) {
        // Runs in a background thread.
        executor.execute(() -> {
            // Check if user data was fetched recently.
            boolean userExists = movieDao.hasUser(FRESH_TIMEOUT);
            if (!userExists) {
                // Refreshes the data.
                Response<User> response = webservice.getUser(userId).execute();

                // Check for errors here.

                // Updates the database. The LiveData object automatically
                // refreshes, so we don't need to do anything else here.
                userDao.save(response.body());
            }
        });
    }*/

    MutableResponse getPopular(int page) {
        //Cant use observe here non lyfecycle...
        return call(ServiceFactory.GET.getMovieService().getPopular(Constants.KEY_THE_MOVIE_DB, page));
    }

    MutableResponse getMoviesByGenre(int page) {
        return call(ServiceFactory.GET.getMovieService().getMoviesByGenres(Constants.DEFAULT_GENRE, Constants.KEY_THE_MOVIE_DB, page));
    }

    MutableResponse getMoviesByPopular() {
        return call(ServiceFactory.GET.getMovieService().getMoviesByPopularity(Constants.Api.KEY_BY_POPULAR_DESC, Constants.KEY_THE_MOVIE_DB));
    }

    MutableResponse getMovies(Integer page) {
        return call(ServiceFactory.GET.getMovieService().getMovies(page, Constants.KEY_THE_MOVIE_DB));
    }

}
