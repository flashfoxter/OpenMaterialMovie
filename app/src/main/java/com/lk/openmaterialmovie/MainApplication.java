/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie;

import android.content.Context;
import android.os.StrictMode;

import com.lk.openmaterialmovie.factories.ServiceFactory;
import com.lk.openmaterialmovie.helpers.Ui;
import com.lk.openmaterialmovie.network.ApiClient;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.lk.openmaterialmovie.network.ApiClient.HEADER_AUTHORIZATION;

public class MainApplication extends BaseApplication {

    // TODO: 2019-04-25 Remove picasso var
    private Picasso picasso;

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                //.detectAll() //
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog() //
                .penaltyDeath() //
                .build());
    }

    private void initImageLoader(Context context) {
        if (picasso == null) {
            getPicassoImageLoader(context);
        }
    }

    private void getPicassoImageLoader(Context context) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request()
                    .newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, ApiClient.getAuthHeaderValue())
                    .build();
            return chain.proceed(newRequest);
        }).build();
        picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceFactory.GET.init();
        Ui.setApplication(this);
        initImageLoader(this);
        if (BuildConfig.DEBUG) {
            initLeakCanary();
        }
    }

    protected void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        enabledStrictMode();
        refWatcher = LeakCanary.install(this);
    }
}
