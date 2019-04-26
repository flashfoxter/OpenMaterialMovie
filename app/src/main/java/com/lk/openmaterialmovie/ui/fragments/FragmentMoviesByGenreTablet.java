/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.ui.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.openmaterialmovie.R;
import com.lk.openmaterialmovie.dto.MovieDto;
import com.lk.openmaterialmovie.helpers.Provider;

import static com.lk.openmaterialmovie.navigator.Navigate.replaceChildFragment;

public class FragmentMoviesByGenreTablet extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_by_genre_tablet, container, false);
        initByGenres();
        return binding.getRoot();
    }

    private void initByGenres() {
        FragmentMoviesList fragmentMoviesList = new FragmentMoviesList();
        // TODO: 2019-04-25 Implement default selection
        fragmentMoviesList.setOnSelected(this::initDetails);
        replaceChildFragment(R.id.container_list, this, fragmentMoviesList);
    }

    private void initDetails(MovieDto movieDto) {
        FragmentMovieDetails fragmentMovieDetails = (FragmentMovieDetails) Provider.getFragmentMovieDetails(movieDto);
        replaceChildFragment(R.id.container_details, this, fragmentMovieDetails);
    }
}
