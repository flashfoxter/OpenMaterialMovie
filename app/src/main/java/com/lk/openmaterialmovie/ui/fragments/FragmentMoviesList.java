package com.lk.openmaterialmovie.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Consumer;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.databinding.FragmentMoviesListBinding;
import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.enums.DecoratorType;
import com.lk.openmaterialmovie.enums.PlaceHolderType;
import com.lk.openmaterialmovie.helpers.Provider;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.navigator.Navigate;
import com.lk.openmaterialmovie.ui.adapters.GenericAdapter;
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
    private GenericAdapter<Movie, MoviesViewHolder> adapter;
    private GridLayoutManager layoutManager;
    private List<Movie> movieList;


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
            binding.recyclerMovies.setOnLoadMore(this::getMovies);
        }
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MoviesListViewModel.class);
        getMovies(0);
    }

    private void getMovies(int tempPage) {
        //Kinda hack TheMovieDb begins from 1...
        int page = tempPage + 1;
        viewModel.getMoviesList(page).observe(this, movies -> {
            if (page == 1) {
                movieList = movies;
                adapter = binding.recyclerMovies.initList(MoviesViewHolder.class, movieList, holderCreate ->
                                holderCreate.itemView.setOnClickListener(v -> {
                                    // TODO: 2019-04-25 Do not add if, use Strategy pattern
                                    Movie selectedMovie = adapter.getSelected(holderCreate);
                                    if (!Ui.isTablet()) {
                                        Navigate.toFragment(this, Provider.getFragmentMovieDetails(selectedMovie));
                                    } else {
                                        if (onSelected != null) {
                                            onSelected.accept(selectedMovie);
                                        }
                                    }
                                }),
                        (holderBind, item) -> {
                            holderBind.b.txtTitle.setText(item.getTitle());
                            holderBind.b.txtReleaseDate.setText(item.getRelease_date());
                            holderBind.b.imgCover.load(item.getPoster_path(), PlaceHolderType.MOVIE);
                        }, layoutManager, DecoratorType.NO_TOP);
            } else {
                if (movies != null) {
                    movieList.addAll(movies);
                    adapter.notifyItemRangeInserted(adapter.getItems().size(), movies.size() - 1);
                }
            }
        });
    }
}
