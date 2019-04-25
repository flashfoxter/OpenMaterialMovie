/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.dto;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;

//@Entity
@Data
@AllArgsConstructor
public class MovieDto {
    // TODO: 2019-04-26 Check working with lombock
    //@PrimaryKey
    public int id;
    public String overview;
    public String original_language;
    public String original_title;
    public boolean video;
    public String title;
    public int[] genre_ids;
    public String poster_path;
    public String backdrop_path;
    public String release_date;
    public double vote_average;
    public double popularity;
    public boolean adult;
    public int vote_count;
}
