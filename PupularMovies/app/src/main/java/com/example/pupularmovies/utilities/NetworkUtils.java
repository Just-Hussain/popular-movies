package com.example.pupularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    static final String BASE_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular";
    static final String BASE_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated";

    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";

    public static final String BASE_IMG_SIZE = "w342";

    static final String PARAM_KEY = "api_key";

    static final String PARAM_PAGE = "page";


    public static URL buildUrl(int page, String sort) {
        if (sort == null)
            sort = "popular";

        Uri builtUri = Uri.parse(BASE_URL + sort).buildUpon()
                .appendQueryParameter(PARAM_KEY, "c6c533d22a663fca314c58769bce4e4d")
                .appendQueryParameter(PARAM_PAGE, page + "")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
