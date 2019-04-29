/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;


@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(T entity);

    @Update
    abstract void update(T entity);

    @Delete
    abstract void delete(T entity);
}


