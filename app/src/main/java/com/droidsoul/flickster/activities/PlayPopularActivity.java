package com.droidsoul.flickster.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.droidsoul.flickster.R;
import com.droidsoul.flickster.helpers.VideoPlayerHelper;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayPopularActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_popular);
        YouTubePlayerView playPopularTrailer = findViewById(R.id.playPopularTrailer);
        String id = getIntent().getStringExtra("id");
        VideoPlayerHelper videoPlayerHelper = new VideoPlayerHelper();
        videoPlayerHelper.playOrCue(id, "play", playPopularTrailer);
    }
}
