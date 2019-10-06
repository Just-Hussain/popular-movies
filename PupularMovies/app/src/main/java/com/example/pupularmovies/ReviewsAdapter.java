package com.example.pupularmovies;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pupularmovies.models.Review;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {


    private static Review[] reviews;

    public Review[] getReviews() {
        return reviews;
    }

    public void setReviews(Review[] reviews) {
        ReviewsAdapter.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int itemLayoutId = R.layout.reviews_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(itemLayoutId, parent, false);

        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(view);

        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (reviews == null) return 0;
        return reviews.length;
    }



    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView tvReviewItem;
        TextView tvAuthor;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            tvReviewItem = itemView.findViewById(R.id.tv_review_item);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }

        void bind(int pos) {
            tvAuthor.setText(R.string.author);
            tvAuthor.append(" " + reviews[pos].getAuthor());
            tvReviewItem.setText(reviews[pos].getContent());
        }
    }
}
