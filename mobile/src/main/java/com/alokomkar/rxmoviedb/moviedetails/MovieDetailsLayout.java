package com.alokomkar.rxmoviedb.moviedetails;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.alokomkar.rxmoviedb.MovieApplication;
import com.alokomkar.rxmoviedb.R;
import com.alokomkar.rxmoviedb.ShowMovieDetailsTransition;
import com.alokomkar.rxmoviedb.moviedetails.model.MovieDetailsResponse;

import butterknife.ButterKnife;

/**
 * Created by rahul on 15/06/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MovieDetailsLayout extends CoordinatorLayout implements MovieDetailsContract.View{

    private static MovieDetailsPresenter  movieDetailsPresenter;

    public MovieDetailsLayout(final Context context) {
        this(context, null);
    }

    public MovieDetailsLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }


    public static Scene showScene(Activity activity, final ViewGroup container, final View clickedPictureView, final String transitionName, int movieId) {

        MovieDetailsLayout movieDetailsLayout = (MovieDetailsLayout) activity.getLayoutInflater().inflate(R.layout.details_layout, container, false);
        movieDetailsPresenter=new MovieDetailsPresenter(movieDetailsLayout, MovieApplication.getInstance().getNetModule().getRetrofit(),movieId);
        movieDetailsPresenter.start();
        TransitionSet set = new ShowMovieDetailsTransition(activity, transitionName, clickedPictureView, movieDetailsLayout);
        Scene scene = new Scene(container, (View) movieDetailsLayout);
        TransitionManager.go(scene, set);
        return scene;
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
