package com.droidsoul.flickster.adapters;

import android.content.Context;
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

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    int orientation;

    public enum LayoutTypes{
        movieRegular,
        moviePopular
    }

    private static class ViewHolder {
        ImageView ivMovieImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    private static class ViewHolderPopular {
        ImageView ivMovieImagePopular;
        TextView tvTitlePopular;
        TextView tvOverviewPopular;
    }


    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
        this.orientation = context.getResources().getConfiguration().orientation;
    }

    @Override
    public int getItemViewType(int position){
        Movie movie = getItem(position);
        int type;

        if(movie.getRating() > 2.5){
            type = LayoutTypes.moviePopular.ordinal();
        } else {
            type = LayoutTypes.movieRegular.ordinal();
        }
        return type;
    }

    @Override
    public int getViewTypeCount(){
        return LayoutTypes.values().length;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        ViewHolderPopular viewHolderPopular = new ViewHolderPopular();
        int type = getItemViewType(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            if (type == 0) {
//                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder.ivMovieImage = convertView.findViewById(R.id.ivMovieImage);
                viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
                viewHolder.tvOverview = convertView.findViewById(R.id.tvOverview);
                convertView.setTag(viewHolder);
            }
            else {
//                viewHolderPopular = new ViewHolderPopular();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_movie_popular, parent, false);
                viewHolderPopular.ivMovieImagePopular = convertView.findViewById(R.id.ivMovieImagePopular);
                viewHolderPopular.tvTitlePopular = convertView.findViewById(R.id.tvTitlePopular);
                viewHolderPopular.tvOverviewPopular = convertView.findViewById(R.id.tvOverviewPopular);
                convertView.setTag(viewHolderPopular);
            }
        }else {
            if (type == 0) {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            else {
                viewHolderPopular = (ViewHolderPopular) convertView.getTag();
            }
        }
        if (orientation == 1) {
            if (type == 0) {
                viewHolder.ivMovieImage.setImageResource(0);
                viewHolder.tvTitle.setText(movie.getOriginalTile());
                viewHolder.tvOverview.setText(movie.getOverview());
                Picasso.with(getContext()).load(movie.getPosterPath()).placeholder(R.drawable.small_movie_poster).transform(new RoundedCornersTransformation(10, 10)).into(viewHolder.ivMovieImage);
            }
            else {
                Picasso.with(getContext()).load(movie.getBackdropPath()).placeholder(R.drawable.small_movie_poster).transform(new RoundedCornersTransformation(10, 10)).into(viewHolderPopular.ivMovieImagePopular);
            }
        }
        else {
            if (type == 0) {
                viewHolder.ivMovieImage.setImageResource(0);
                viewHolder.tvTitle.setText(movie.getOriginalTile());
                viewHolder.tvOverview.setText(movie.getOverview());
                Picasso.with(getContext()).load(movie.getBackdropPath()).placeholder(R.drawable.large_movie_poster).transform(new RoundedCornersTransformation(10, 10)).into(viewHolder.ivMovieImage);
            }
            else {
                viewHolderPopular.ivMovieImagePopular.setImageResource(0);
                viewHolderPopular.tvTitlePopular.setText(movie.getOriginalTile());
                viewHolderPopular.tvOverviewPopular.setText(movie.getOverview());
                Picasso.with(getContext()).load(movie.getBackdropPath()).placeholder(R.drawable.large_movie_poster).transform(new RoundedCornersTransformation(10, 10)).into(viewHolderPopular.ivMovieImagePopular);
            }
        }
        return convertView;
    }
}
