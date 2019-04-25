package com.lk.openmaterialmovie.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.databinding.FragmentMoviesByGenreBinding;
import com.lk.openmaterialmovie.dto.MovieListResponse;
import com.lk.openmaterialmovie.enums.DecoratorType;
import com.lk.openmaterialmovie.enums.PlaceHolderType;
import com.lk.openmaterialmovie.helpers.Provider;
import com.lk.openmaterialmovie.navigator.Navigate;
import com.lk.openmaterialmovie.ui.adapters.GenericAdapter;
import com.lk.openmaterialmovie.ui.viewholders.MoviesViewHolder;

import java.util.List;


public class FragmentMoviesByGenre extends BaseFragment {

    private FragmentMoviesByGenreViewModel viewModel;
    private FragmentMoviesByGenreBinding binding;
    private GenericAdapter<MovieListResponse.MovieListResponseResults, MoviesViewHolder> adapter;
    private List<MovieListResponse.MovieListResponseResults> movieListResponseResults;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo check
        if (savedInstanceState == null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO: 2019-04-24 Check kill
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_by_genre, container, false);
        binding.recyclerMovies.setOnLoadMore(this::getMovies);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FragmentMoviesByGenreViewModel.class);
        getMovies(0);
    }

    // TODO: 2019-04-25 Do i need to know about paging, from architecture point of view ?...
    private void getMovies(int page) {
        viewModel.getMoviesByGenre(page).onChangeOnce(this, movies -> {
            if (page == 0) {
                MovieListResponse movieListDto = (MovieListResponse) movies.getData();
                movieListResponseResults = movieListDto.getResults();
                adapter = binding.recyclerMovies.initList(MoviesViewHolder.class, movieListResponseResults, holderCreate -> {
                    holderCreate.itemView.setOnClickListener(v -> Navigate.toFragment(this, Provider
                            .getFragmentMovieDetails(adapter.getItems()
                                    .get(holderCreate.getAdapterPosition()).getId())));
                }, (holderBind, item) -> {
                    holderBind.b.txtTitle.setText(item.getTitle());
                    holderBind.b.imgCover.load(item.getPoster_path(), PlaceHolderType.MOVIE);
                }, null, DecoratorType.NO_TOP);
            } else {
                //paging
                List<MovieListResponse.MovieListResponseResults> results = ((MovieListResponse) movies.getData()).getResults();
                movieListResponseResults.addAll(results);
                adapter.notifyItemRangeInserted(adapter.getItems().size(), results.size() - 1);
            }
        });
    }
}
