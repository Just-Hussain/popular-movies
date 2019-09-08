package com.example.pupularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    static final String BASE_URL = "https://api.themoviedb.org/3/movie/popular";

    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";

    public static final String BASE_IMG_SIZE = "w342";

    static final String PARAM_KEY = "api_key";

    static final String PARAM_PAGE = "page";


    public static URL buildUrl(int page) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_KEY, "<<api_key>>")
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
