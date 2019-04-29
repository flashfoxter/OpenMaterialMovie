package com.lk.openmaterialmovie.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Consumer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.databinding.FragmentMoviesListBinding;
import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.helpers.Provider;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.navigator.Navigate;
import com.lk.openmaterialmovie.ui.adapters.GenericAdapter;
import com.lk.openmaterialmovie.ui.adapters.MoviePagingAdapter;
import com.lk.openmaterialmovie.ui.viewholders.MoviesViewHolder;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class FragmentMoviesList extends BaseFragment {

    @Getter
    @Setter
    private Consumer<Movie> onSelected;
    private MoviesListViewModel viewModel;
    private FragmentMoviesListBinding binding;
    private GenericAdapter<Movie, MoviesViewHolder> adapterGeneric;
    private GridLayoutManager layoutManager;
    private List<Movie> movieList;
    private RecyclerView recyclerView;
    private MoviePagingAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_list, container, false);
            layoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView = binding.recyclerMovies;
            binding.swipeToRefresh.setEnabled(false); //implement - data source invalidate
        }
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideBack();
        viewModel = ViewModelProviders.of(this).get(MoviesListViewModel.class);
        setTitle(R.string.filter_popular);
        adapter = new MoviePagingAdapter(this);
        observeNetworkState();
        observerMovies();
    }

    public void onMovieSelected(MoviesViewHolder holderCreate) {
        Movie selectedMovie = adapter.getItems().get(holderCreate.getAdapterPosition());
        if (!Ui.isTablet()) {
            Navigate.toFragment(this, Provider.getFragmentMovieDetails(selectedMovie));
        } else {
            if (onSelected != null) {
                onSelected.accept(selectedMovie);
            }
        }
    }

    private void observeNetworkState() {
        viewModel.getNetworkState().observe(this, networkState -> {
            adapter.setNetworkState(networkState);
        });
    }

    private void observerMovies() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewModel.getPagedMovies().observe(this, movies -> {
            //noinspection StatementWithEmptyBody
            if (movies.isEmpty()) {
                // TODO: 2019-04-29 Show empty state
            } else {

            }
            adapter.submitList(movies);
        });
    }
}
