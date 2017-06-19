package com.alokomkar.rxmoviedb.trailers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alokomkar.rxmoviedb.NavigationListener;
import com.alokomkar.rxmoviedb.R;
import com.alokomkar.rxmoviedb.base.Constants;
import com.alokomkar.rxmoviedb.movielist.Movie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 15/06/17.
 */

public class TrailerFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.trailerImageView)
    ImageView trailerImageView;
    Unbinder unbinder;
    @BindView(R.id.movieName)
    TextView mMovieName;
    private Movie movie;
    private NavigationListener navigationListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( savedInstanceState != null ) {
            movie = savedInstanceState.getParcelable(Constants.MOVIE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_trailer, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        if( savedInstanceState == null ) {
            movie = getArguments().getParcelable(Constants.MOVIE);
            setupViews();
        }
        else {
            movie = savedInstanceState.getParcelable(Constants.MOVIE);
            setupViews();
        }
        return fragmentView;
    }

    private void setupViews() {
        String imgUrl = "";
        if (movie != null) {
            imgUrl = "http://image.tmdb.org/t/p/" + "original" + movie.getPosterPath();
        }
        Glide.with(getContext()).load(imgUrl)
                .thumbnail(0.5f)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(trailerImageView);
        //mMovieName.setText(movie.getOriginalTitle());
        trailerImageView.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        navigationListener.playVideo(movie);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationListener) {
            navigationListener = (NavigationListener) context;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.MOVIE, movie);
    }

    @Override
    public void onDetach() {
        navigationListener = null;
        super.onDetach();
    }
}
