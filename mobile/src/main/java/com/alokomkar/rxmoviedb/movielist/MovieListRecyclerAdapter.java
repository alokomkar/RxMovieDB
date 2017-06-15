package com.alokomkar.rxmoviedb.movielist;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alokomkar.rxmoviedb.R;
import com.alokomkar.rxmoviedb.TransitionUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alok on 15/06/17.
 */

public class MovieListRecyclerAdapter extends RecyclerView.Adapter<MovieListRecyclerAdapter.ViewHolder> {


    private Context context;
    private List<Movie> movieList;
    private ItemClickListener itemClickListener;


    public interface ItemClickListener {
        void onItemClick(View sharedView, String transitionName, final int position);
    }

    public MovieListRecyclerAdapter(Context context, List<Movie> movieList, ItemClickListener itemClickListener) {
        this.context = context;
        this.movieList = movieList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder customViewHolder, int i) {

        Movie movie = movieList.get(i);

        String imgUrl = "http://image.tmdb.org/t/p/" + "w342" + movieList.get(i).getBackdropPath();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            customViewHolder.movieImageView.setClipToOutline(true);
        }

        Glide.with(context).load(imgUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(customViewHolder.movieImageView);
        customViewHolder.movieNameTextView.setText(movie.getOriginalTitle());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movieImageView)
        ImageView movieImageView;
        @BindView(R.id.movieNameTextView)
        TextView movieNameTextView;
        @BindView(R.id.rootId)
        RelativeLayout rootId;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener.onItemClick(view,TransitionUtils.getRecyclerViewTransitionName(position),position);
            }
        }
    }
}
