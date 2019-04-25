/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.lk.openmaterialmovie.dto.MovieDto;

//@Database(entities = { MovieDto.class }, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    // TODO: 2019-04-26 Check room and lombock annotation works
  /*  private static final String DB_NAME = "movieDatabase.db";
    private static volatile MovieDatabase instance;

    static synchronized MovieDatabase getInstance(Context context) {
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

    public abstract MovieDao getRepoDao();*/
}