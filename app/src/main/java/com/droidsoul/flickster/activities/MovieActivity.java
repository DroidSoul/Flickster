package com.droidsoul.flickster.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.droidsoul.flickster.R;
import com.droidsoul.flickster.adapters.MovieArrayAdapter;
import com.droidsoul.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    ListView lvMovies;
    MovieArrayAdapter movieAdapter;
    TextView tvReleaseDate, tvRating, tvPopularity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        tvRating = (TextView) findViewById(R.id.tvRating);
        tvPopularity = (TextView) findViewById(R.id.tvPopularity);*/
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvMovies.setAdapter(movieAdapter);
        getMovies();
        setupListViewListener();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.iReleaseDate) {
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return -o1.getReleaseDate().compareTo(o2.getReleaseDate());
                }
            });
            movieAdapter.notifyDataSetChanged();
            return true;
        }
        else if (id == R.id.iRating) {
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    if (o1.getRating() <= o2.getRating()) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                }
            });
            movieAdapter.notifyDataSetChanged();
            return true;
        }
        else if(id == R.id.iPopularity) {
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    if (o1.getPopularity() <= o2.getPopularity()) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                }
            });
            movieAdapter.notifyDataSetChanged();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void setupListViewListener() {
        lvMovies.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Movie movie = movies.get(position);
                        if (movie.isPopular()) {
                            Intent i = new Intent(MovieActivity.this, PlayPopularActivity.class);
                            i.putExtra("id", movie.getId());
                            startActivity(i);
                        }
                        else {
                            Intent i = new Intent(MovieActivity.this, MovieDetail.class);
                            i.putExtra("rating", movie.getRating());
                            i.putExtra("popularity", String.valueOf(movie.getPopularity()));
                            i.putExtra("synopsis", movie.getOverview());
                            i.putExtra("id", movie.getId());
                            i.putExtra("releaseDate", movie.getReleaseDate());
                            i.putExtra("posterPath", movie.getPosterPath());
                            i.putExtra("title", movie.getTitle());
                            startActivity(i);
                        }
                    }
                }
        );
    }
    //sort list based on due date
    public void sortRating(View view) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                if (o1.getRating() <= o2.getRating()) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        });
        movieAdapter.notifyDataSetChanged();
        tvRating.setTypeface(null, Typeface.BOLD_ITALIC);
        tvPopularity.setTypeface(null, Typeface.NORMAL);
        tvReleaseDate.setTypeface(null, Typeface.NORMAL);
    }
    //sort list based on priority
    public void sortPopularity(View view) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                if (o1.getPopularity() <= o2.getPopularity()) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        });
        movieAdapter.notifyDataSetChanged();
        tvRating.setTypeface(null, Typeface.NORMAL);
        tvPopularity.setTypeface(null, Typeface.BOLD_ITALIC);
        tvReleaseDate.setTypeface(null, Typeface.NORMAL);
    }
    public void sortReleaseDate(View view) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
               return -o1.getReleaseDate().compareTo(o2.getReleaseDate());
            }
        });
        movieAdapter.notifyDataSetChanged();
        tvRating.setTypeface(null, Typeface.NORMAL);
        tvPopularity.setTypeface(null, Typeface.NORMAL);
        tvReleaseDate.setTypeface(null, Typeface.BOLD_ITALIC);
    }
    private void getMovies() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJSONResults = null;
                try {
                    movieJSONResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJSONResults));
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
