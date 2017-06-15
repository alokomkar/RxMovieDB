package com.alokomkar.rxmoviedb.moviedetails;

import com.alokomkar.rxmoviedb.base.BasePresenter;
import com.alokomkar.rxmoviedb.base.BaseView;
import com.alokomkar.rxmoviedb.moviedetails.model.MovieDetailsResponse;

import java.util.List;

/**
 * Created by rahul on 15/06/17.
 */

public interface MovieDetailsContract {

    interface View extends BaseView {

    void showProgress();

    void hideProgress();

    void showMessage();

    void setMovieDetails(MovieDetailsResponse details);
}

    interface  Presenter extends BasePresenter
    {
        void getMovieDetails(int movieId,String APIkEY);
    }

}
