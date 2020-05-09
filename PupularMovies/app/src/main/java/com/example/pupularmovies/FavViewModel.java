package com.example.pupularmovies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pupularmovies.models.FavoriteMovie;

import java.util.List;

public class FavViewModel extends ViewModel {
    private FavoriteDatabase db;
    private LiveData<List<FavoriteMovie>> list;

    public FavViewModel(FavoriteDatabase db) {
        this.db = db;
    }


            public LiveData<List<FavoriteMovie>> getFavs() {

            if (this.list == null) {
                this.list = db.favoriteDao().getFavoriteList();
                return this.list;
            }
            else
            return this.list;
    }
}
