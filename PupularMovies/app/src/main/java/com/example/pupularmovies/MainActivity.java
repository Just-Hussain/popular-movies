package com.example.pupularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pupularmovies.models.Movie;
import com.example.pupularmovies.utilities.JsonUtils;
import com.example.pupularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements MoviesAdapter.ListItemClickListener {


    public static int loadedPages = 1;

    @BindView(R.id.rv_movies_list)
    RecyclerView rvMoviesList;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar pbLoadingIndicator;
    @BindView(R.id.tv_error_message)
    TextView tvErrorMessage;

    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        moviesAdapter = new MoviesAdapter(this);

        rvMoviesList.setLayoutManager(new GridLayoutManager(this, 3));

        rvMoviesList.setHasFixedSize(true);

        rvMoviesList.setAdapter(moviesAdapter);

        loadData();
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

        if (id == R.id.action_sort) {
            if (sortFlag) {
                Arrays.sort(moviesAdapter.getMovies(), new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie, Movie t1) {
                        return (int) (t1.getPopularity() - movie.getPopularity());
                    }
                });

                moviesAdapter.notifyDataSetChanged();
                item.setTitle(R.string.sort_by_rating);

                sortFlag = false;
            }
            else {
                Arrays.sort(moviesAdapter.getMovies(), new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie, Movie t1) {
                        return (int) (t1.getVoteAvg() - movie.getVoteAvg());
                    }
                });
                moviesAdapter.notifyDataSetChanged();
                item.setTitle(R.string.sort_by_popular);

                sortFlag = true;
            }
        }
        else if (id == R.id.action_load) {
            loadData();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intentDetails = new Intent(MainActivity.this, DetailsActivity.class);

        intentDetails.putExtra("EXTRA_MOVIE", moviesAdapter.getMovies()[clickedItemIndex]);

        startActivity(intentDetails);
    }

    void loadData() {
        URL url = NetworkUtils.buildUrl(loadedPages);
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
