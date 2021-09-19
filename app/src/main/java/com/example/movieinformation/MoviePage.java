package com.example.movieinformation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

// A class to define a movie page, which will be used when the movie page layout
public class MoviePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //refers to the appropriate layout
        setContentView(R.layout.page_movie);

        //getting layout ids
        TextView movieTitle = findViewById(R.id.MoviePageTitle);
        TextView movieRating = findViewById(R.id.MoviePagelRating);
        TextView movieRelease = findViewById(R.id.ReleaseDate);
        TextView movieDesc = findViewById(R.id.Description);
        ImageView movieImage = findViewById(R.id.MoviePagemageView);
        Button backButton = findViewById(R.id.BackButton);

        //getting data from the bundle that was created when we clicked the movie cell button
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            movieTitle.setText(bundle.getString("Title"));
            movieRating.setText("Rating: "+bundle.getString("Rating") );
            movieRelease.setText("Released: " + bundle.getString("Release"));
            Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + bundle.getString("Image")).into(movieImage);
            movieDesc.setText(bundle.getString("Description"));
        }

        //setting the data we gathered in the last stage using a listener we define inplace
        backButton.setOnClickListener(view -> MoviePage.super.onBackPressed());
    }


}