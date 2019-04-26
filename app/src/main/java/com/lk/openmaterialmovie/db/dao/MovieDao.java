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

import com.lk.openmaterialmovie.dto.Movie;

import java.util.List;

@Dao
public abstract class MovieDao extends BaseDao<Movie> {

    @Query("SELECT * FROM Movie")
    public abstract LiveData<List<Movie>> getAll();

    @Query("SELECT * FROM Movie")
    public abstract DataSource.Factory<Integer, Movie> getAllPaged();

    @Insert
    public abstract void insert(Movie... items);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertCollection(List<Movie> items);

    @Update
    public abstract void update(Movie... items);

    @Delete
    public abstract void delete(Movie... items);

}
