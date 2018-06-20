package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utils.LoadMovies;
import com.example.android.popularmovies.utils.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private ArrayList<Movie> movies;
    private MovieAdapter movieAdapter;
    private String title;
    private double rating;
    private String date;
    private String description;
    private String poster;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            System.out.println("SAVED INSTANCE STATE " + savedInstanceState.toString() + "\n");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridView);
        if (savedInstanceState != null && savedInstanceState.containsKey("movieList")) {
            movies = savedInstanceState.getParcelableArrayList("movieList");
        } else {
            movies = new ArrayList<>();
        }
        movieAdapter = new MovieAdapter(this, movies);
        gridView.setAdapter(movieAdapter);
        helperOnItemClick();
        // asynctask
        new MovieASyncTask().execute(LoadMovies.URLParsing.popular);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.top_rated_menu) {
            new MovieASyncTask().execute(LoadMovies.URLParsing.toprated);
        } else {
            new MovieASyncTask().execute(LoadMovies.URLParsing.popular);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void helperOnItemClick() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
                Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                title = movieAdapter.getItem(position).getTitle();
                intent.putExtra("title", title);
                rating = movieAdapter.getItem(position).getRating();
                intent.putExtra("rating", rating);
                date = movieAdapter.getItem(position).getDate();
                intent.putExtra("date", date);
                description = movieAdapter.getItem(position).getOverview();
                intent.putExtra("description", description);
                poster = movieAdapter.getItem(position).getPicture();
                intent.putExtra("poster", poster);
                id = movieAdapter.getItem(position).getId();
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movieList", movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        movies = savedInstanceState.getParcelableArrayList("movieList");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private class MovieASyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null) {
                return null;
            }
            return LoadMovies.getMovieList(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            movieAdapter.clear();
            if (movies != null && !movies.isEmpty()) {
                movieAdapter.addAll(movies);
            }
        }
    }


}