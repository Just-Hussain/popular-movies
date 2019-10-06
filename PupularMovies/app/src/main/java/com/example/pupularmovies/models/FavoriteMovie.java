package com.example.pupularmovies.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite")
public class FavoriteMovie {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public FavoriteMovie(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "FavoriteMovie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
