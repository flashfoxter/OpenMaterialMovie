/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.network;

import android.support.annotation.NonNull;

import java.text.MessageFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// TODO: 2019-04-24 refactor
@SuppressWarnings("ALL")
public class ApiClient {

    public static final int REST_API_TIMEOUT = 20;
    public static final String PROTOCOL = "https://";
    public static final String HOST = "api.themoviedb.org/3";
    public static final String PORT = "";
    public static final String URL = MessageFormat.format("{0}{1}{2}", PROTOCOL, HOST, PORT);

    public static final String HEADER_X_REQUESTED_WITH = "X-Requested-With";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String HOST_IMAGE = "image.tmdb.org/t/p/w92";
    public static final String URL_IMAGE = MessageFormat.format("{0}{1}", PROTOCOL, HOST_IMAGE);

    @Getter
    @Setter
    private static String accessToken;

    @NonNull
    private static <T> T getServiceUtil(Class<T> c, boolean refresh) {
        String baseUrl = URL + "/";
/*
        if (!baseUrl.endsWith("/"))
            baseUrl = baseUrl + "/";
*/
        return getRetrofitBuilder(baseUrl, refresh).build().create(c);
    }

    public static <T> T getService(Class<T> c) {
        return getServiceUtil(c, false);
    }

    public static <T> T getRefreshService(Class<T> c) {
        return getServiceUtil(c, true);
    }

    public static OkHttpClient.Builder getHttpBuilder(boolean refresh) {
        return new OkHttpClient.Builder()
                //.authenticator(new TokenAuthenticator())
                //.addInterceptor(new CurlInterceptor(Logg::d))
                .addInterceptor(chain -> {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.headers(getHeaders(refresh));
                    return chain.proceed(builder.build());
                })
                .connectTimeout(REST_API_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REST_API_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REST_API_TIMEOUT, TimeUnit.SECONDS);
    }

    public static Headers getHeaders(boolean refresh) {
        Headers.Builder xmlHttpRequest = new Headers.Builder()
                .add(HEADER_X_REQUESTED_WITH, "XMLHttpRequest")
                .add(HEADER_CONTENT_TYPE, "application/json");
        //Do not add bearer for refresh or login(accessToken == null)
        if (accessToken != null && !refresh) {
            xmlHttpRequest.add(HEADER_AUTHORIZATION, getAuthHeaderValue());
        }
        return xmlHttpRequest.build();
    }

    private static OkHttpClient getHttpClient(boolean auth) {
        return getHttpBuilder(auth).build();
    }

    @NonNull
    public static String getAuthHeaderValue() {
        return MessageFormat.format("Bearer {0}", accessToken);
    }

    private static Retrofit.Builder getRetrofitBuilder(String path, boolean refresh) {
        return new Retrofit.Builder()
                .baseUrl(path)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient(refresh));
    }
}
