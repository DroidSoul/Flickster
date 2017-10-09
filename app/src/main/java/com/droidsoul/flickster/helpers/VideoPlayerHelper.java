package com.droidsoul.flickster.helpers;

import android.util.Log;

import com.droidsoul.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class VideoPlayerHelper {

    String source = null;

    public void playOrCue(String id, final String playCue, final YouTubePlayerView youTubePlayerView)
    {
        String url = "https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                JSONArray videoJSONResults = null;
                try {
                    JSONArray videoJSONResults = response.getJSONArray("results");
                    for (int i = 0; i < videoJSONResults.length(); i++) {
                        source = videoJSONResults.getJSONObject(i).getString("key");
                        if (source != null) {
                            break;
                        }
                    }
                    if (source != null) {
                        youTubePlayerView.initialize("AIzaSyCdDuf6g8QRAqGZ9_RVtvVpHO2lUUygxRQ",
                                new YouTubePlayer.OnInitializedListener() {
                                    @Override
                                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                        YouTubePlayer youTubePlayer, boolean b) {
                                        // do any work here to cue video, play video, etc.
                                        //youTubePlayer.cueVideo("5xVh-7ywKpE");
                                        if(playCue.equals("cue")){
                                            youTubePlayer.cueVideo(source);
                                        } else {
                                            youTubePlayer.setFullscreen(true);
                                            youTubePlayer.loadVideo(source);
                                        }

                                    }
                                    @Override
                                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                        YouTubeInitializationResult youTubeInitializationResult) {
                                    }
                                });
                    }
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
