/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.lk.openmaterialmovie.data.db.dao.MovieDao;
import com.lk.openmaterialmovie.dto.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DB_NAME = "db5";
    private static volatile MovieDatabase instance;

    public static synchronized MovieDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static MovieDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                MovieDatabase.class,
                DB_NAME).build();
    }

    public abstract MovieDao getMovieDao();
}