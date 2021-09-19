package com.example.movieinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.movieinformation.adapter.MyViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyViewAdapter.PageClickListener {

    TextView loading;
    ArrayList<MovieItem> movies = new ArrayList<>();
    RecyclerView RV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //refers to the appropriate layout
        setContentView(R.layout.activity_main);

        //getting layouts ids
        loading = findViewById(R.id.LoadingText);
        RV = findViewById(R.id.MovieRecyclerView);

        //extracting data from the TMDB api and executing the recycle view
        GetMovieInfo getMovieInfo = new GetMovieInfo();
        getMovieInfo.execute();

    }

    // a function to indicate the actions when clicking a movie item in the main screen
    @Override
    public void onClickPage(int position) {
        MovieItem cur = movies.get(position);
        Intent intent = new Intent(this, MoviePage.class);
        intent.putExtra("Title", cur.getMovieTitle());
        intent.putExtra("Description", cur.getDescription());
        intent.putExtra("Rating", cur.getRating());
        intent.putExtra("Release", cur.getRelease_date());
        intent.putExtra("Image", cur.getImagePath());
        startActivity(intent);
    }


    // an inner class that extracts the data from the TMDB api and preforms the recycle view execution
    class GetMovieInfo extends AsyncTask<String, String, String> {

        //for extracting the data from the TMDB api
        @Override
        protected String doInBackground(String... strings) {
            String dataUrl = "https://api.themoviedb.org/3/discover/movie?api_key=8ea0aef856c98a06de380c838251e401";
            StringBuilder res = new StringBuilder();

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(dataUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
                        res.append((char) data);
                        data = isr.read();
                    }

                    return res.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return res.toString();
        }

        //transfer the extracted data into a movie item list
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject cur = jsonArray.getJSONObject(i);

                    String title = cur.getString("title");
                    String rating = cur.getString("vote_average");
                    String release_date = cur.getString("release_date");
                    String description = cur.getString("overview");
                    String imagePath = cur.getString("poster_path");
                    MovieItem movie = new MovieItem(title, rating, release_date, description, imagePath);
                    movies.add(movie);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RecyclerViewExecution();
        }
    }

    //executing the recycle view using the adapter we have built
    private void RecyclerViewExecution() {
        loading.setText("");
        MyViewAdapter adapter = new MyViewAdapter(movies, this);
        RV.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        RV.setLayoutManager(layoutManager);
    }

}