/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.dto;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor(onConstructor = @__({@Ignore}))
@NoArgsConstructor
public class MovieListResponse {
    private int page;
    private int total_pages;
    private List<Movie> results;
    private int total_results;

}
