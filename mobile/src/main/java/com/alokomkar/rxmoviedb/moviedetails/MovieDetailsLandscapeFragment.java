package com.alokomkar.rxmoviedb.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alokomkar.rxmoviedb.MovieApplication;
import com.alokomkar.rxmoviedb.R;
import com.alokomkar.rxmoviedb.moviedetails.model.MovieDetailsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 16/06/17.
 */

public class MovieDetailsLandscapeFragment extends Fragment implements MovieDetailsContract.View {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.movieLimit)
    TextView movieLimit;
    @BindView(R.id.starCastText)
    TextView starCastText;
    @BindView(R.id.starCast)
    TextView starCast;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.trailerText)
    TextView trailerText;
    @BindView(R.id.trailerRecyclerView)
    RecyclerView trailerRecyclerView;
    @BindView(R.id.rootCardView)
    RelativeLayout rootCardView;
    @BindView(R.id.scrolling_container)
    NestedScrollView scrollingContainer;
    Unbinder unbinder;

    private static MovieDetailsLandscapeFragment instance;
    private int movieId;
    private MovieDetailsPresenter movieDetailsPresenter;

    public static MovieDetailsLandscapeFragment getInstance() {
        if( instance == null ) {
            instance = new MovieDetailsLandscapeFragment();
        }
        return instance;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
        movieDetailsPresenter = new MovieDetailsPresenter(this, MovieApplication.getInstance().getNetModule().getRetrofit(), movieId);
        movieDetailsPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage() {

    }

    @Override
    public void setMovieDetails(MovieDetailsResponse details) {
        title.setText(details.getOriginalTitle());
        movieLimit.setText(details.getReleaseDate());
        starCastText.setText(details.getOriginalLanguage());
        description.setText(details.getOverview());
    }
}
