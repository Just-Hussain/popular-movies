package com.example.pupularmovies;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pupularmovies.models.FavoriteMovie;

@Database(entities = FavoriteMovie.class, exportSchema = false, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    private static final String DB_NAME = "favorite_db";
    private static FavoriteDatabase instance;

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, FavoriteDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


    public abstract FavoriteDao favoriteDao();
}
