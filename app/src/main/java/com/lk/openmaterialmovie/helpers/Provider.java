/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.helpers;


import com.lk.openmaterialmovie.dto.Movie;
import com.lk.openmaterialmovie.log.Logger;
import com.lk.openmaterialmovie.ui.fragments.BaseFragment;
import com.lk.openmaterialmovie.ui.fragments.FragmentMovieDetails;
import com.lk.openmaterialmovie.ui.fragments.FragmentMoviesByGenreTablet;
import com.lk.openmaterialmovie.ui.fragments.FragmentMoviesList;
import com.lk.openmaterialmovie.ui.fragments.MovieDetailsViewModel;

import java.lang.reflect.Method;


public class Provider {

    public static final String INIT_VIEW_MODEL_METHOD_NAME = "initViewModel";
    @SuppressWarnings("WeakerAccess")
    public static final String VIEW_MODEL_FIELD_NAME = "viewModel";
    private static final String SET_VIEW_MODEL_METHOD_NAME = "setViewModel";
    private static final String VIEW_MODEL_CLASS_NAME_POSTFIX = "ViewModel";

    public static <T extends BaseFragment> T getFragment(Class<? extends T> c) {
        T fragment = null;
        try {
            fragment = c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // Separate creating fragment and viewModel to prevent exception ClassNotFoundException for viewModel (see log for this)
        for (Method m : c.getMethods()) {
            if (m.getName().equals(INIT_VIEW_MODEL_METHOD_NAME)) {
                try {
                    m.invoke(fragment);
                    return fragment;
                } catch (Exception e) {
                    Logger.e(e.getMessage());
                    throw new IllegalStateException(e);
                }
            }
        }
        // TODO: 2019-04-24 use AAC with model provider ?
   /*     boolean hasViewModel = false;
        for (Field f : c.getDeclaredFields()) {
            if (f.getName().equals(VIEW_MODEL_FIELD_NAME)) {
                hasViewModel = true;
                break;
            }
        }
        if (hasViewModel) {
            try {
                Class<?> viewModelClass = Class.forName(c.getName() + VIEW_MODEL_CLASS_NAME_POSTFIX);
                if (viewModelClass != null) {
                    c.getDeclaredMethod(SET_VIEW_MODEL_METHOD_NAME, viewModelClass).invoke(fragment, viewModelClass.newInstance());
                }

            } catch (Exception e) {
                Logger.e(e.getMessage());
                throw new IllegalStateException(e);
            }
        }*/
        return fragment;
    }

    public static BaseFragment getFragmentMoviesByGenre() {
        if (Ui.isTablet()) {
            return new FragmentMoviesByGenreTablet();
        } else {
            return new FragmentMoviesList();
        }
    }

    public static BaseFragment getFragmentMovieDetails(Movie movie) {
        FragmentMovieDetails fragment = new FragmentMovieDetails();
        MovieDetailsViewModel.ViewModelDetailsFactory viewModelDetailsFactory = new MovieDetailsViewModel.ViewModelDetailsFactory(movie);
        fragment.setViewModel(viewModelDetailsFactory.create(MovieDetailsViewModel.class));
        return fragment;
    }
}
