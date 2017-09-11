package com.droidsoul.flickster.adapters;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidsoul.flickster.R;
import com.droidsoul.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by bear&bear on 9/7/2017.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    int orientation;

    private static class ViewHolder {
        ImageView ivMovieImage;
        TextView tvTitle;
        TextView tvOverview;
    }


    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
        this.orientation = context.getResources().getConfiguration().orientation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.ivMovieImage = convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = convertView.findViewById(R.id.tvOverview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivMovieImage.setImageResource(0);
        viewHolder.tvTitle.setText(movie.getOriginalTile());
        viewHolder.tvOverview.setText(movie.getOverview());
        if (orientation == 1) {
            Picasso.with(getContext()).load(movie.getPosterPath()).placeholder(R.drawable.small_movie_poster).transform(new RoundedCornersTransformation(10, 10)).into(viewHolder.ivMovieImage);}
        else {
            Picasso.with(getContext()).load(movie.getBackdropPath()).placeholder(R.drawable.large_movie_poster).transform(new RoundedCornersTransformation(10, 10)).into(viewHolder.ivMovieImage);
        }
        return convertView;
    }
}
