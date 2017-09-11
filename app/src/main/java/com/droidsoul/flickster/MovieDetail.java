package com.droidsoul.flickster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by bear&bear on 9/8/2017.
 */
public class MovieDetail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        TextView tvPopularity = (TextView) findViewById(R.id.tvPopularity);
        TextView tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        ratingBar.setRating((float) getIntent().getDoubleExtra("rating", 0));
        tvPopularity.setText(getIntent().getStringExtra("popularity"));
        tvSynopsis.setText(getIntent().getStringExtra("synopsis"));
    }
}
