package com.example.pupularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pupularmovies.models.Movie;
import com.example.pupularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("EXTRA_MOVIE");
        setTitle(movie.getTitle());

        tvTitle.setText(movie.getOriginalTitle());

        Picasso.get().load(NetworkUtils.BASE_IMAGE_URL + "original" + movie.getPosterPath()).into(ivPoster);

        tvReleaseDate.setText(movie.getReleaseDate());

        tvVoteAvg.setText("" + movie.getVoteAvg());

        tvOverView.setText(movie.getOverview());

    }
}
