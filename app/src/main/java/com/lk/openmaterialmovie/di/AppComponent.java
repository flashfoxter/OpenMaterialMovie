/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.di;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityContributorModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}