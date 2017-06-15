package com.alokomkar.rxmoviedb.moviedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alokomkar.rxmoviedb.MovieApplication;
import com.alokomkar.rxmoviedb.R;
import com.alokomkar.rxmoviedb.moviedetails.model.MovieDetailsResponse;

/**
 * Created by rahul on 15/06/17.
 */

public class MovieDetailsFragment extends Fragment implements MovieDetailsContract.View{

    private MovieDetailsPresenter movieDetailsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.details_layout,container,false);
        movieDetailsPresenter=new MovieDetailsPresenter(this, MovieApplication.getInstance().getNetModule().getRetrofit(),"11");
        movieDetailsPresenter.start();
        return fragmentView;
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

    }


}
