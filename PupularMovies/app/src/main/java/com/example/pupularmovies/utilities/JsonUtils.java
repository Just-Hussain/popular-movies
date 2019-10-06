package com.example.pupularmovies.utilities;

import com.example.pupularmovies.models.Movie;
import com.example.pupularmovies.models.Review;
import com.example.pupularmovies.models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonUtils {

    public static Movie[] parseMovieJson(String json) {

        Movie[] results = null;

        try {
            JSONArray jsonResults = new JSONObject(json).getJSONArray("results");
            results = new Movie[jsonResults.length()];

            for (int i = 0; i < jsonResults.length(); i++) {
                JSONObject jsonMovie = jsonResults.getJSONObject(i);
                Movie movie = new Movie(
                        jsonMovie.getInt("popularity"),
                        jsonMovie.getInt("vote_count"),
                        jsonMovie.getString("poster_path"),
                        jsonMovie.getInt("id"),
                        jsonMovie.getString("original_language"),
                        jsonMovie.getString("original_title"),
                        jsonMovie.getString("title"),
                        jsonMovie.getInt("vote_average"),
                        jsonMovie.getString("overview"),
                        jsonMovie.getString("release_date" )
                );

                results[i] = movie;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static Review[] parseReviewJson(String json) {
        Review[] results = null;

        try {
            JSONArray jsonResults = new JSONObject(json).getJSONArray("results");
            results = new Review[jsonResults.length()];

            for (int i = 0; i < jsonResults.length(); i++) {
                JSONObject jsonReview = jsonResults.getJSONObject(i);
                Review review = new Review(
                        jsonReview.getString("author"),
                        jsonReview.getString("content"),
                        jsonReview.getString("id"),
                        jsonReview.getString("url")
                );

                results[i] = review;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static Video[] parseVideoJson(String json) {
        Video[] results = null;

        try {
            JSONArray jsonResults = new JSONObject(json).getJSONArray("results");
            results = new Video[jsonResults.length()];

            for (int i = 0; i < jsonResults.length(); i++) {
                JSONObject jsonVideo = jsonResults.getJSONObject(i);
                Video video = new Video(
                        jsonVideo.getString("id"),
                        jsonVideo.getString("key"),
                        jsonVideo.getString("name"),
                        jsonVideo.getString("site")
                );

                results[i] = video;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

}
