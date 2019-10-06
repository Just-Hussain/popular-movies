package com.example.pupularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pupularmovies.models.FavoriteMovie;
import com.example.pupularmovies.models.Movie;
import com.example.pupularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private static Movie[] movies;
    private final ListItemClickListener onClickListener;

    public MoviesAdapter(ListItemClickListener listener) {
        onClickListener = listener;
    }



    public Movie[] getMovies() {
        return movies;
    }


    public void setMovies(Movie[] movies) {
        MoviesAdapter.movies = movies;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int itemLayoutId = R.layout.movies_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(itemLayoutId, parent, false);

        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (movies == null) return 0;
        return movies.length;
    }



    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivMovieItem;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovieItem = itemView.findViewById(R.id.iv_movie_item);

            itemView.setOnClickListener(this);
        }

        void bind(int pos) {
            Picasso.get().load(NetworkUtils.BASE_IMAGE_URL + NetworkUtils.BASE_IMG_SIZE + movies[pos].getPosterPath()).into(ivMovieItem);
        }

        @Override
        public void onClick(View view) {
            int clickedPos = getAdapterPosition();
            onClickListener.onListItemClick(clickedPos);
        }
    }
}
