package com.lk.openmaterialmovie.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.openmaterialmovie.Constants;
import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.databinding.FragmentMoviesByGenreBinding;
import com.lk.openmaterialmovie.dto.MovieListResponse;
import com.lk.openmaterialmovie.enums.DecoratorType;
import com.lk.openmaterialmovie.helpers.Provider;
import com.lk.openmaterialmovie.navigator.Navigate;
import com.lk.openmaterialmovie.network.ApiClient;
import com.lk.openmaterialmovie.ui.adapters.GenericAdapter;
import com.lk.openmaterialmovie.ui.viewholders.MoviesViewHolder;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;


public class FragmentMoviesByGenre extends BaseFragment {

    private FragmentMoviesByGenreViewModel viewModel;
    private FragmentMoviesByGenreBinding binding;
    private GenericAdapter<MovieListResponse.MovieListResponseResults, MoviesViewHolder> adapter;

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
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FragmentMoviesByGenreViewModel.class);
        viewModel.getMoviesByGenre().onChangeOnce(this, movies -> {
            MovieListResponse movieListDto = (MovieListResponse) movies.getData();
            List<MovieListResponse.MovieListResponseResults> resultList = movieListDto.getResults();
            //noinspection CodeBlock2Expr
            adapter = binding.recyclerMovies.initList(MoviesViewHolder.class, resultList, holderCreate -> {
                holderCreate.itemView.setOnClickListener(v -> {
                    Navigate.toFragment(this, Provider.getFragmentMovieDetails(adapter.getItems().get(holderCreate.getAdapterPosition()).getId()));
                });
            }, (holderBind, item) -> {
                holderBind.b.txtTitle.setText(item.getTitle());
                // TODO: 2019-04-24 Move to base image
                String imageUrl = MessageFormat.format("{0}{1}?api_key={2}", ApiClient.URL_IMAGE, item.getPoster_path(), Constants.KEY_THE_MOVIE_DB);
                Picasso.get().load(imageUrl).into(holderBind.b.imgCover);
                //holderBind.b.imgCover.setImageBitmap(imageUrl);
            }, null, DecoratorType.NO_TOP);

        });
    }
}
