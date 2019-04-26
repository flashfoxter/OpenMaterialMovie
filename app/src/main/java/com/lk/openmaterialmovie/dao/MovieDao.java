/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.lk.openmaterialmovie.dto.MovieDto;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM moviedto")
    List<MovieDto> getAllMovies();

    @Insert
    void insert(MovieDto... repos);

    @Update
    void update(MovieDto... repos);

    @Delete
    void delete(MovieDto... repos);

}
