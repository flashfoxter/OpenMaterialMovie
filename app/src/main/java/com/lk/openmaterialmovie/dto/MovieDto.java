/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
//@AllArgsConstructor(onConstructor = @__({@Ignore}))
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //Room annotation after lombok only
public class MovieDto {
    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "title")
    public String title;

    @Ignore
    public String overview;

    @Ignore
    public String original_language;

    @Ignore
    public String original_title;

    @Ignore
    public boolean video;

    @Ignore
    public int[] genre_ids;

    @Ignore
    public String poster_path;

    @Ignore
    public String backdrop_path;

    @Ignore
    public String release_date;

    @Ignore
    public double vote_average;

    @Ignore
    public double popularity;

    @Ignore
    public boolean adult;

    @Ignore
    public int vote_count;
}

