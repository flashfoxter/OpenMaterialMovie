/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.di;

import com.lk.openmaterialmovie.MainActivity;
import com.lk.openmaterialmovie.repository.MoviesRepository;
import com.lk.openmaterialmovie.service.MovieService;
import com.lk.openmaterialmovie.ui.activities.BaseActivity;
import com.lk.openmaterialmovie.ui.fragments.MoviesListViewModel;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityContributorModule {

    @ContributesAndroidInjector
    abstract BaseActivity contributeBaseActivity();

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    // TODO: 2019-04-24 refactor to modules
    @ContributesAndroidInjector
    abstract MoviesListViewModel contributeMoviesListViewModel();

    @ContributesAndroidInjector
    abstract MoviesRepository contributeMoviesRepository();

    @ContributesAndroidInjector
    abstract MovieService contributeMovieService();
}
