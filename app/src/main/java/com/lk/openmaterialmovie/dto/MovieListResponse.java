/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieListResponse {
    private int page;
    private int total_pages;
    private List<MovieListResponseResults> results;
    private int total_results;

    @Data
    @AllArgsConstructor
    public class MovieListResponseResults {
        private String overview;
        private String original_language;
        private String original_title;
        private boolean video;
        private String title;
        private int[] genre_ids;
        private String poster_path;
        private String backdrop_path;
        private String release_date;
        private double vote_average;
        private double popularity;
        private int id;
        private boolean adult;
        private int vote_count;
    }
}
