package com.droidsoul.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bear&bear on 9/7/2017.
 */
public class Movie {

    final String posterPre = "https://image.tmdb.org/t/p/w342";
    final String backdropPre = "https://image.tmdb.org/t/p/w780/%s";
    String posterPath;
    String backdropPath;
    String originalTile;
    String overview;
    String releaseDate;
    double rating;
    double popularity;
    String id;

    public String getId() {
        return id;
    }

    public double getRating() {
        return rating / 2.0;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
       // return String.format(posterPre, posterPath);
        return posterPre + posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackdropPath() {
        return String.format(backdropPre, backdropPath);
    }

    public String getOriginalTile() {
        return originalTile;
    }

    public String getOverview() {
        return overview;
    }

    public Movie(JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTile = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.rating = jsonObject.getDouble("vote_average");
        this.popularity = jsonObject.getDouble("popularity");
        this.releaseDate = jsonObject.getString("release_date");
        this.id = jsonObject.getString("id");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> res = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                res.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
    public boolean isPopular() {
        return getRating() > 2.5;
    }
}
