package com.example.movieinformation;

// A class to define a movie item, which will be used for the movie cell in the main screen layout
public class MovieItem {
    String movie_title;
    String rating;
    String release_date;
    String description;
    String image_path;

    public MovieItem(String movie_title, String rating, String release_date, String description, String image_path){
        this.movie_title = movie_title;
        this.rating = rating;
        this.release_date = release_date;
        this.description = description;
        this.image_path = image_path;
    }

    public String getMovieTitle() {
        return movie_title;
    }

    public String getRating() {
        return rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return image_path;
    }
}
