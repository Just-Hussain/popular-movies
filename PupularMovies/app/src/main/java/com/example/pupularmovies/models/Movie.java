package com.example.pupularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Movie implements Parcelable {
    private double popularity;
    private int voteCount;
    private String posterPath;
    private int id;
    private String originalLang;
    private String originalTitle;
    private String title;
    private double voteAvg;
    private String overview;
    private String releaseDate;


    @NonNull
    @Override
    public String toString() {
        return title + "  ";
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalLang() {
        return originalLang;
    }

    public void setOriginalLang(String originalLang) {
        this.originalLang = originalLang;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(popularity);
        parcel.writeInt(voteCount);
        parcel.writeString(posterPath);
        parcel.writeInt(id);
        parcel.writeString(originalLang);
        parcel.writeString(originalTitle);
        parcel.writeString(title);
        parcel.writeDouble(voteAvg);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(
                    parcel.readDouble(),
                    parcel.readInt(),
                    parcel.readString(),
                    parcel.readInt(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readDouble(),
                    parcel.readString(),
                    parcel.readString()
            );
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public Movie(double popularity, int voteCount, String posterPath,int id,String originalLang, String originalTitle, String title, double voteAvg, String overview, String releaseDate) {
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.id = id;
        this.originalLang = originalLang;
        this.originalTitle = originalTitle;
        this.title = title;
        this.voteAvg = voteAvg;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Movie() {
    }
}
