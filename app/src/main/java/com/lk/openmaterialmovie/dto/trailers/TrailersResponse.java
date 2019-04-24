/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.dto.trailers;

public class TrailersResponse {
    private int id;
    private TrailersResponseResults[] results;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TrailersResponseResults[] getResults() {
        return this.results;
    }

    public void setResults(TrailersResponseResults[] results) {
        this.results = results;
    }
}
