package com.example.pupularmovies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pupularmovies.models.FavoriteMovie;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("Select * from favorite")
    LiveData<List<FavoriteMovie>> getFavoriteList();

    @Insert
    void insertFavorite(FavoriteMovie favoriteMovie);

    @Update
    void updateFavorite(FavoriteMovie favoriteMovie);

    @Delete
    void deleteFavorite(FavoriteMovie favoriteMovie);

    @Query("DELETE FROM favorite")
    void delete();
}
