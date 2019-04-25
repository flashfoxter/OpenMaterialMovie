package com.lk.openmaterialmovie.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Consumer;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.databinding.FragmentMoviesByGenreBinding;
import com.lk.openmaterialmovie.dto.MovieListResponse;
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


public class FragmentMoviesByGenre extends BaseFragment {

    @Getter
    @Setter
    private Consumer<Integer> selectedId;
    private FragmentMoviesByGenreViewModel viewModel;
    private FragmentMoviesByGenreBinding binding;
    private GenericAdapter<MovieListResponse.MovieListResponseResults, MoviesViewHolder> adapter;
    private List<MovieListResponse.MovieListResponseResults> movieListResponseResults;
    private LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_by_genre, container, false);
            linearLayoutManager = new LinearLayoutManager(getContext());
            binding.recyclerMovies.setOnLoadMore(this::getMovies);
        }
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FragmentMoviesByGenreViewModel.class);
        getMovies(0);
    }

    // TODO: 2019-04-25 Do i need to know about paging, from architecture point of view ?...
    private void getMovies(int tempPage) {
        //Kinda hack TheMovieDb begins from 1...
        int page = tempPage + 1;
        viewModel.getPopular(page).onChangeOnce(this, movies -> {
            if (page == 1) {
                MovieListResponse movieListDto = (MovieListResponse) movies.getData();
                movieListResponseResults = movieListDto.getResults();
                adapter = binding.recyclerMovies.initList(MoviesViewHolder.class, movieListResponseResults, holderCreate ->
                        {
                            holderCreate.itemView.setOnClickListener(v -> {
                                // TODO: 2019-04-25 Do not add if use Strategy pattern
                                int id = adapter.getItems().get(holderCreate.getAdapterPosition()).getId();
                                if (!Ui.isTablet()) {
                                    Navigate.toFragment(this, Provider.getFragmentMovieDetails(id));
                                } else {
                                    if (selectedId != null) {
                                        selectedId.accept(id);
                                    }
                                }

                            });
                        },
                        (holderBind, item) -> {
                            holderBind.b.txtTitle.setText(item.getTitle());
                            holderBind.b.imgCover.load(item.getPoster_path(), PlaceHolderType.MOVIE);
                        }, linearLayoutManager, DecoratorType.NO_TOP);
            } else {
                List<MovieListResponse.MovieListResponseResults> results = ((MovieListResponse) movies.getData()).getResults();
                movieListResponseResults.addAll(results);
                adapter.notifyItemRangeInserted(adapter.getItems().size(), results.size() - 1);
            }
        });
    }
}
