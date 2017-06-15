package com.alokomkar.rxmoviedb.movielist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alokomkar.rxmoviedb.MovieApplication;
import com.alokomkar.rxmoviedb.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 15/06/17.
 */

public class MovieListFragment extends Fragment implements MovieListContract.View {

    @BindView(R.id.newTrailerTextView)
    TextView newTrailerTextView;
    @BindView(R.id.nowPlayingRecyclerView)
    RecyclerView nowPlayingRecyclerView;
    @BindView(R.id.popularTextView)
    TextView popularTextView;
    @BindView(R.id.popularMoviesRecyclerView)
    RecyclerView popularMoviesRecyclerView;
    @BindView(R.id.topRatedTextView)
    TextView topRatedTextView;
    @BindView(R.id.topMoviesRecyclerView)
    RecyclerView topMoviesRecyclerView;
    @BindView(R.id.latestTextView)
    TextView latestTextView;
    @BindView(R.id.latestMoviesRecyclerView)
    RecyclerView latestMoviesRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;
    private MovieListPresenter movieListPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        movieListPresenter = new MovieListPresenter(this, MovieApplication.getInstance().getNetModule().getRetrofit());
        movieListPresenter.start();
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    //TODO : To be decided if this is needed
    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void loadTopRatedMovies(List<Movie> movieList) {

    }

    @Override
    public void loadPopularMovies(List<Movie> movieList) {

    }

    @Override
    public void loadLatestMovies(List<Movie> movieList) {

    }

    @Override
    public void loadNowPlayingMovies(List<Movie> movieList) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
