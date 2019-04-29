package com.lk.openmaterialmovie.ui.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.lk.openmaterialmovie.arch.SingleLiveEvent;
import com.lk.openmaterialmovie.data.NetworkState;
import com.lk.openmaterialmovie.data.db.MovieDatabase;
import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.repository.MoviesRepository;

import java.util.List;

import javax.inject.Inject;

public class MoviesListViewModel extends ViewModel {

    public final SingleLiveEvent taskNavigation = new SingleLiveEvent();
    private LiveData<PagedList<Movie>> pagedMovies;
    private MoviesRepository moviesRepository;
    private LiveData<List<Movie>> moviesList;
    private LiveData<NetworkState> networkState;

    @Inject
    public MoviesListViewModel() {
        this.moviesRepository = new MoviesRepository(ServiceFactory.GET.getMovieService());
        moviesList = MovieDatabase.getInstance(null).getMovieDao().getAll();
        pagedMovies = moviesRepository.getPagedMovies();
        networkState = moviesRepository.getNetworkState();
    }

    public LiveData<List<Movie>> getMoviesList(int page) {
        moviesRepository.getMovies(page);
        return moviesList;
    }

    public LiveData<PagedList<Movie>> getPagedMovies() {
        return pagedMovies;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
