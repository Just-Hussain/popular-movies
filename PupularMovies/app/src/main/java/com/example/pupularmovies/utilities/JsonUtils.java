package com.example.pupularmovies.utilities;

import com.example.pupularmovies.models.Movie;

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

}
