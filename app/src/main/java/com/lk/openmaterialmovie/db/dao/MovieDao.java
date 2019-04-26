/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.lk.openmaterialmovie.dto.MovieDto;

import java.util.List;

@Dao
public abstract class MovieDao extends BaseDao<MovieDto> {

    @Query("SELECT * FROM moviedto")
    public abstract LiveData<List<MovieDto>> getAll();

    @Query("SELECT * FROM moviedto")
    public abstract DataSource.Factory<Integer, MovieDto> getAllPaged();

    @Insert
    public abstract void insert(MovieDto... items);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertCollection(List<MovieDto> items);

    @Update
    public abstract void update(MovieDto... items);

    @Delete
    public abstract void delete(MovieDto... items);

}
