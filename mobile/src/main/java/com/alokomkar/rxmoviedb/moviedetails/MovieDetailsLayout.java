package com.alokomkar.rxmoviedb.moviedetails;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alokomkar.rxmoviedb.HideDetailsTransitionSet;
import com.alokomkar.rxmoviedb.MovieApplication;
import com.alokomkar.rxmoviedb.R;
import com.alokomkar.rxmoviedb.ShowMovieDetailsTransition;
import com.alokomkar.rxmoviedb.moviedetails.model.MovieDetailsResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 15/06/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MovieDetailsLayout extends CoordinatorLayout implements MovieDetailsContract.View {

    private static MovieDetailsPresenter movieDetailsPresenter;
    @BindView(R.id.headerImage)
    public ImageView imageViewPlaceDetails;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.title)
   public TextView title;
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
   public RelativeLayout rootCardView;
    @BindView(R.id.scrolling_container)
    NestedScrollView scrollingContainer;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.details_container)
    MovieDetailsLayout detailsContainer;
    private static Activity attachedActivity;

    String prefixImgUrl = "http://image.tmdb.org/t/p/" + "w342";

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
        setToolbar();
    }

    private void setToolbar() {
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(),android.R.color.black));
        collapsingToolbar.setTitle("Movie Details");
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);

        if (toolbar != null) {
            ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } else {
            // Don't inflate. Tablet is in landscape mode.
        }


        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attachedActivity.onBackPressed();

            }
        });
    }

    public static Scene showScene(Activity activity, final ViewGroup container, final View clickedPictureView, final String transitionName, int movieId) {
        attachedActivity=activity;
        MovieDetailsLayout movieDetailsLayout = (MovieDetailsLayout) activity.getLayoutInflater().inflate(R.layout.details_layout, container, false);
        movieDetailsPresenter = new MovieDetailsPresenter(movieDetailsLayout, MovieApplication.getInstance().getNetModule().getRetrofit(), movieId);
        movieDetailsPresenter.start();
        TransitionSet set = new ShowMovieDetailsTransition(activity, transitionName, clickedPictureView, movieDetailsLayout);
        Scene scene = new Scene(container, (View) movieDetailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }


    public static Scene hideScene(Activity activity,  ViewGroup container,  View sharedView, String transitionName) {
        MovieDetailsLayout detailsLayout = (MovieDetailsLayout) container.findViewById(R.id.details_container);
        TransitionSet set = new HideDetailsTransitionSet(activity, transitionName, sharedView, detailsLayout);
        Scene scene = new Scene(container, (View) detailsLayout);
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
        Log.d("title",details.getTitle()+" "+details.getPosterPath());
        title.setText(details.getTitle());
        Glide.with(getContext()).load(prefixImgUrl+details.getPosterPath())
                .centerCrop()
                .error(R.drawable.app_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewPlaceDetails);

    }
}
