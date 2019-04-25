/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.service;

import com.lk.openmaterialmovie.dto.MovieListResponse;
import com.lk.openmaterialmovie.dto.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {


    @GET("movie/popular")
    Call<MovieListResponse> getPopular(@Query("api_key") String apiKey, @Query("page") Integer page);

    //Default 28
    @GET("discover/movie")
    Call<MovieListResponse> getMoviesByGenres(@Query("with_genres") Integer genreId, @Query("api_key") String apiKey, @Query("page") Integer page);

    @GET("discover/movie")
    Call<MovieListResponse> getMoviesByPopularity(@Query("sort_by") String sortBy, @Query("api_key") String apiKey);

    //V4 MoviesList
    @GET("list/{page}")
    Call<MovieListResponse> getMovies(@Path("page") Integer page, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailersResponse> getTrailers(@Path("id") Integer id, @Query("api_key") String apiKey);

}
