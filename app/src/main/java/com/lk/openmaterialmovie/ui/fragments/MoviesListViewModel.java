package com.lk.openmaterialmovie.ui.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.lk.openmaterialmovie.arch.SingleLiveEvent;
import com.lk.openmaterialmovie.db.MovieDatabase;
import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.repository.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

public class MoviesListViewModel extends ViewModel {

    public final SingleLiveEvent taskNavigation = new SingleLiveEvent();
    private MoviesRepository moviesRepository;
    private LiveData<List<Movie>> moviesList;

    @Inject
    public MoviesListViewModel() {
        this.moviesRepository = new MoviesRepository(ServiceFactory.GET.getMovieService());
        moviesList = MovieDatabase.getInstance(null).getMovieDao().getAll();
    }

    public LiveData<List<Movie>> getMoviesList(int page) {
        moviesRepository.getMovies(page);
        return moviesList;
    }

}
