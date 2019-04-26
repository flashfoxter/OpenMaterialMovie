/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.di;

import android.app.Application;
import android.content.Context;

import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.navigator.Navigator;
import com.lk.openmaterialmovie.navigator.NavigatorImpl;
import com.lk.openmaterialmovie.repository.MoviesRepository;
import com.lk.openmaterialmovie.service.MovieService;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Provides
    static Navigator provideNavigator() {
        return new NavigatorImpl();
    }

    @Provides
    static MovieService provideMovieService() {
        return ServiceFactory.GET.getMovieService();
    }

    @Provides
    static MoviesRepository provideMoviesRepository() {
        return new MoviesRepository(ServiceFactory.GET.getMovieService());
    }

    @Binds
    public abstract Context bindContext(Application application);

}