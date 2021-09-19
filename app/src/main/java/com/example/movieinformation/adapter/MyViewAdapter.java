package com.example.movieinformation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.movieinformation.MovieItem;
import com.example.movieinformation.R;
import java.util.ArrayList;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.ViewHolder> {

    private Context context;
    private final ArrayList<MovieItem> MovieList;
    private final PageClickListener listener;

    public MyViewAdapter(ArrayList<MovieItem> movies, PageClickListener listener){
        this.listener = listener;
        this.MovieList = movies;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView movieTitle;
        public TextView RatingStar;
        public ImageView movieImage;
        PageClickListener listener;


        public ViewHolder(View itemView, PageClickListener listener){
            super(itemView);
            movieTitle = itemView.findViewById((R.id.MovieItemTitle));
            RatingStar = itemView.findViewById((R.id.MovieItemRatinfStar));
            movieImage =  itemView.findViewById((R.id.MovieItemImage));
            this.listener = listener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listener.onClickPage(getBindingAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        // Inflate the custom layout
        View View = LayoutInflater.from(context)
                .inflate(R.layout.item_movie, parent, false);

        // Return a new holder instance
        return new ViewHolder(View, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieItem movie = MovieList.get(position);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + movie.getImagePath()).into(holder.movieImage);
        holder.movieTitle.setText(movie.getMovieTitle());
        holder.RatingStar.setText(movie.getRating());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return MovieList.size();
    }


    public interface PageClickListener {
        void onClickPage(int position);
    }

}

