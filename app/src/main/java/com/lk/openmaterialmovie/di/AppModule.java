/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.di;

import android.app.Application;
import android.content.Context;

import com.lk.openmaterialmovie.navigator.Navigator;
import com.lk.openmaterialmovie.navigator.NavigatorImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Provides
    static Navigator provideNavigator() {
        return new NavigatorImpl();
    }

    @Binds
    public abstract Context bindContext(Application application);

}