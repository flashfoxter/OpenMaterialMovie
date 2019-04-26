package com.lk.openmaterialmovie.ui.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.lk.openmaterialmovie.db.MovieDatabase;
import com.lk.openmaterialmovie.dto.MovieDto;
import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.repository.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

public class MoviesListViewModel extends ViewModel {

    private LiveData<List<MovieDto>> moviesList;
    private MoviesRepository moviesRepository;

    @Inject
    public MoviesListViewModel() {
        this.moviesRepository = new MoviesRepository(ServiceFactory.GET.getMovieService());
        moviesList = MovieDatabase.getInstance(null).getMovieDao().getAll();
    }

    public LiveData<List<MovieDto>> getMoviesList(int page) {
        moviesRepository.getMovies(page);
        return moviesList;
    }


}
