package com.example.pupularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pupularmovies.models.FavoriteMovie;
import com.example.pupularmovies.models.Movie;
import com.example.pupularmovies.utilities.JsonUtils;
import com.example.pupularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements MoviesAdapter.ListItemClickListener {

    Context context = this;

    private FavoriteDatabase db = null;

    public static int loadedPages = 1;

    @BindView(R.id.rv_movies_list)
    RecyclerView rvMoviesList;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar pbLoadingIndicator;
    @BindView(R.id.tv_error_message)
    TextView tvErrorMessage;
    @BindView(R.id.ll_favs)
    LinearLayout llFavs;

    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        db = FavoriteDatabase.getInstance(context);

//        new Thread() {
//            @Override
//            public void run() {
//                db.favoriteDao().delete();
//            }
//        }.start();


        moviesAdapter = new MoviesAdapter(this);

        rvMoviesList.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));

        rvMoviesList.setHasFixedSize(true);

        rvMoviesList.setAdapter(moviesAdapter);

        loadData("popular");

    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 328;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private boolean sortFlag = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        llFavs.removeAllViews();

        if (id == R.id.action_sort) {
            if (sortFlag) {

                moviesAdapter.setMovies(null);
                loadData("popular");

                moviesAdapter.notifyDataSetChanged();
                item.setTitle(R.string.sort_by_rating);

                sortFlag = false;
            }
            else {
                moviesAdapter.setMovies(null);
                loadData("top_rated");

                moviesAdapter.notifyDataSetChanged();
                item.setTitle(R.string.sort_by_popular);

                sortFlag = true;
            }
        }
        else if (id == R.id.action_load) {
            if (sortFlag)
                loadData("popular");
            else
                loadData("top_rated");
        }
        else if (id == R.id.action_fav) {
            moviesAdapter.setMovies(null);
            moviesAdapter.notifyDataSetChanged();
            new Thread() {
                @Override
                public void run() {
                    List<FavoriteMovie> list = db.favoriteDao().getFavoriteList();
                    for (int i = 0; i < list.size(); i++) {
                        TextView textView = new TextView(context);
                        textView.setText(list.get(i).getName());
                        textView.setPadding(10, 10, 10, 10);
                        textView.setTextSize(20);
                        textView.setTextColor(Color.WHITE);
                        llFavs.addView(textView);
                    }
                }
            }.start();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intentDetails = new Intent(MainActivity.this, DetailsActivity.class);

        intentDetails.putExtra("EXTRA_MOVIE", moviesAdapter.getMovies()[clickedItemIndex]);

        startActivity(intentDetails);
    }

    void loadData(String sort) {
        URL url = NetworkUtils.buildUrl(loadedPages, sort);
        loadedPages++;
        new TMDbQueryTask().execute(url);
    }



    private void showRecyclerView() {
        tvErrorMessage.setVisibility(View.INVISIBLE);

        rvMoviesList.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        rvMoviesList.setVisibility(View.INVISIBLE);
        tvErrorMessage.setVisibility(View.VISIBLE);
    }





    class TMDbQueryTask extends AsyncTask<URL, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(URL... params) {
            URL searchUrl = params[0];
            Movie[] result = null;
            try {
                String response = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                result = JsonUtils.parseMovieJson(response);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Movie[] result) {
            pbLoadingIndicator.setVisibility(View.INVISIBLE);

            if (result != null) {
                showRecyclerView();
                Arrays.sort(result, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie, Movie t1) {
                        return (int) (t1.getPopularity() - movie.getPopularity());
                    }
                });

                if (moviesAdapter.getMovies() == null)
                    moviesAdapter.setMovies(result);
                else {
                    Movie[] movies = Arrays.copyOf(moviesAdapter.getMovies(), moviesAdapter.getMovies().length + result.length);
                    for (int i = moviesAdapter.getMovies().length, j = 0; i < movies.length && j < result.length; i++, j++) {
                        Movie movie = new Movie(
                                result[j].getPopularity(),
                                result[j].getVoteCount(),
                                result[j].getPosterPath(),
                                result[j].getId(),
                                result[j].getOriginalLang(),
                                result[j].getOriginalTitle(),
                                result[j].getTitle(),
                                result[j].getVoteAvg(),
                                result[j].getOverview(),
                                result[j].getReleaseDate()
                        );
                        movies[i] = movie;
                    }
                    moviesAdapter.setMovies(movies);
                }
            } else {
                showErrorMessage();
            }
        }
    }

}
