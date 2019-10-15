package com.example.pupularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pupularmovies.models.FavoriteMovie;
import com.example.pupularmovies.models.Movie;
import com.example.pupularmovies.models.Review;
import com.example.pupularmovies.models.Video;
import com.example.pupularmovies.utilities.JsonUtils;
import com.example.pupularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    Context context = this;

    private FavoriteDatabase db = null;

    @BindView(R.id.rv_reviews_list)
    RecyclerView rvReviewsList;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_vote_avg)
    TextView tvVoteAvg;
    @BindView(R.id.tv_overview)
    TextView tvOverView;
    @BindView(R.id.ll_trailers)
    LinearLayout llTrailers;
    @BindView(R.id.btn_fav_add)
    Button btnFavAdd;
    @BindView(R.id.btn_fav_remove)
    Button btnFavRemove;

    private ReviewsAdapter reviewsAdapter;
    private Movie movie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        db = FavoriteDatabase.getInstance(context);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("EXTRA_MOVIE");
        setTitle(movie.getTitle());

        tvTitle.setText(movie.getOriginalTitle());

        Picasso.get().load(NetworkUtils.BASE_IMAGE_URL + "original" + movie.getPosterPath()).into(ivPoster);

        tvReleaseDate.setText(movie.getReleaseDate());

        tvVoteAvg.setText("" + movie.getVoteAvg());

        tvOverView.setText(movie.getOverview());

        reviewsAdapter = new ReviewsAdapter();

        rvReviewsList.setLayoutManager(new LinearLayoutManager(this));

        rvReviewsList.setHasFixedSize(true);

        rvReviewsList.setAdapter(reviewsAdapter);

        loadData();
    }


    public void addFavHandler(View view) {
        new Thread() {
            @Override
            public void run() {
                db.favoriteDao().insertFavorite(new FavoriteMovie(movie.getTitle(), movie.getId()));
                btnFavAdd.setClickable(false);
                btnFavRemove.setClickable(true);
            }
        }.start();
    }

    public void removeFavHandler(View view) {
        new Thread() {
            @Override
            public void run() {
                db.favoriteDao().deleteFavorite(new FavoriteMovie(movie.getTitle(), movie.getId()));
                btnFavRemove.setClickable(false);
                btnFavAdd.setClickable(true);
            }
        }.start();
    }
    void loadData() {
        URL urlReviews = NetworkUtils.buildReviewsUrl(movie.getId());
        new ReviewsQueryTask().execute(urlReviews);
        URL urlVideos = NetworkUtils.buildVideoUrl(movie.getId());
        new VideosQueryTask().execute(urlVideos);
    }

    class VideosQueryTask extends AsyncTask<URL, Void, Video[]> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Video[] doInBackground(URL... urls) {
            URL url = urls[0];
            Video[] result = null;

            try {
                String response = NetworkUtils.getResponseFromHttpUrl(url);
                result = JsonUtils.parseVideoJson(response);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Video[] videos) {
            super.onPostExecute(videos);

            for (int i = 0; i < videos.length; i++) {
                Button button = new Button(context);
                button.setText(videos[i].getName());
                button.setHint(videos[i].getKey());
                button.setOnClickListener(onButtonClickListener);
                llTrailers.addView(button);
            }

        }
    }

    View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + ((Button) view).getHint());

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            if (intent.resolveActivity((getPackageManager())) != null)
                startActivity(intent);
        }
    };

    class ReviewsQueryTask extends AsyncTask<URL, Void, Review[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Review[] doInBackground(URL... urls) {
            URL url = urls[0];
            Review[] result = null;

            try {
                String response = NetworkUtils.getResponseFromHttpUrl(url);
                result = JsonUtils.parseReviewJson(response);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Review[] reviews) {
            super.onPostExecute(reviews);

            if (reviews != null) {
                reviewsAdapter.setReviews(reviews);
            }
        }
    }
}
