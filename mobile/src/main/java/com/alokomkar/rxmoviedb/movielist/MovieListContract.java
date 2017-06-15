package com.alokomkar.rxmoviedb.movielist;

import com.alokomkar.rxmoviedb.base.BasePresenter;
import com.alokomkar.rxmoviedb.base.BaseView;

import java.util.List;

/**
 * Created by Alok on 15/06/17.
 */

public interface MovieListContract {

    interface View extends BaseView {

        void loadTopRatedMovies(List<Movie> movieList);

        void loadPopularMovies(List<Movie> movieList);

        void loadLatestMovies(List<Movie> movieList);

        void loadNowPlayingMovies(List<Movie> movieList);

        void showProgress();

        void hideProgress();

        void onFailure(String msg);

    }

    interface Presenter extends BasePresenter {

        void getTopRatedMovie(String APIKey);

        void getPopularMovies(String APIKey);

        void getLatestMovies(String APIKey);

        void getNowPlayingMovies(String APIKey);

    }

}
