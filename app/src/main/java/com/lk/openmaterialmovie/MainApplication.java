/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie;

import android.content.Context;

import com.lk.openmaterialmovie.di.AppComponent;
import com.lk.openmaterialmovie.di.DaggerAppComponent;
import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.network.ApiClient;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.lk.openmaterialmovie.network.ApiClient.HEADER_AUTHORIZATION;

public class MainApplication extends DaggerApplication {

    private static Picasso picasso;

    private static void initImageLoader(Context context) {
        if (picasso == null) {
            getPicassoImageLoader(context);
        }
    }

    private static void getPicassoImageLoader(Context context) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder().addHeader(HEADER_AUTHORIZATION, ApiClient.getAuthHeaderValue()).build();
            Response response = chain.proceed(newRequest);
            return response;
        }).build();
        picasso = new Picasso.Builder(context).downloader(new OkHttp3Downloader(client)).build();
        Picasso.setSingletonInstance(picasso);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceFactory.GET.init();
        Ui.setApplication(this);
        initImageLoader(this);
    }
}
