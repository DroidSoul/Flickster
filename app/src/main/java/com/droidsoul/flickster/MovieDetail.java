package com.droidsoul.flickster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.droidsoul.flickster.helpers.VideoPlayerHelper;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by bear&bear on 9/8/2017.
 */
public class MovieDetail extends YouTubeBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView tvPopularity = findViewById(R.id.tvPopularity);
        TextView tvSynopsis = findViewById(R.id.tvSynopsis);
        TextView tvReleaseDate = findViewById(R.id.tvReleaseDate);
        ImageView ivPosterImage = findViewById(R.id.ivPosterImage);
        YouTubePlayerView playRegularTrailer = findViewById(R.id.playRegularTrailer);
        String id = getIntent().getStringExtra("id");
        ratingBar.setRating((float) getIntent().getDoubleExtra("rating", 0));
        tvPopularity.setText(getIntent().getStringExtra("popularity"));
        tvSynopsis.setText(getIntent().getStringExtra("synopsis"));
        tvReleaseDate.setText(getIntent().getStringExtra("releaseDate"));
        Picasso.with(getBaseContext()).load(getIntent().getStringExtra("posterPath")).placeholder(R.drawable.small_movie_poster).transform(new RoundedCornersTransformation(10, 10)).into(ivPosterImage);
        VideoPlayerHelper videoPlayerHelper = new VideoPlayerHelper();
        videoPlayerHelper.playOrCue(id, "cue", playRegularTrailer);
    }
}
