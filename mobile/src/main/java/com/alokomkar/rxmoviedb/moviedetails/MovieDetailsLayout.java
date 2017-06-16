package com.alokomkar.rxmoviedb.moviedetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alokomkar.rxmoviedb.HideDetailsTransitionSet;
import com.alokomkar.rxmoviedb.MainActivity;
import com.alokomkar.rxmoviedb.MovieApplication;
import com.alokomkar.rxmoviedb.R;
import com.alokomkar.rxmoviedb.ShowMovieDetailsTransition;
import com.alokomkar.rxmoviedb.moviedetails.model.MovieDetailsResponse;
import com.alokomkar.rxmoviedb.moviedetails.model.Result;
import com.alokomkar.rxmoviedb.utils.GravitySnapHelper;
import com.alokomkar.rxmoviedb.utils.ItemOffsetDecoration;
import com.alokomkar.rxmoviedb.youtube.FragmentDemoActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 15/06/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MovieDetailsLayout extends CoordinatorLayout implements MovieDetailsContract.View ,TrailerAdapter.OnTrailerClick{

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
    private static String backdropPathCopy;
    private TrailerAdapter mTrailerAdapter;

    static String prefixImgUrl = "http://image.tmdb.org/t/p/" + "w342";
    @BindView(R.id.rating)
    TextView mRating;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

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
        Glide.with(getContext()).load(prefixImgUrl + backdropPathCopy)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewPlaceDetails);
        setToolbar();


    }

    private void setToolbar() {
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(), android.R.color.black));
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

    public static Scene showScene(Activity activity, final ViewGroup container, final View clickedPictureView, final String transitionName, int movieId, String backdropPath) {
        attachedActivity = activity;
        backdropPathCopy = backdropPath;
        MovieDetailsLayout movieDetailsLayout = (MovieDetailsLayout) activity.getLayoutInflater().inflate(R.layout.details_layout, container, false);
        movieDetailsPresenter = new MovieDetailsPresenter(movieDetailsLayout, MovieApplication.getInstance().getNetModule().getRetrofit(), movieId);
        movieDetailsPresenter.start();
        TransitionSet set = new ShowMovieDetailsTransition(activity, transitionName, clickedPictureView, movieDetailsLayout);
        Scene scene = new Scene(container, (View) movieDetailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }


    public static Scene hideScene(Activity activity, ViewGroup container, View sharedView, String transitionName) {
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
        mProgressBar.setVisibility(VISIBLE);

    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(GONE);

    }

    @Override
    public void showMessage() {

    }

    @Override
    public void setMovieDetails(MovieDetailsResponse details) {
        title.setText(details.getOriginalTitle());
        movieLimit.setText("Release Date: " + details.getReleaseDate());
        starCastText.setText("Language: " + details.getOriginalLanguage());
        description.setText(details.getOverview());
        mRating.setText(String.format(getContext().getString(R.string.rating), String.valueOf(details.getVoteAverage())));

        movieDetailsPresenter.getTrailers(details.getId());

    }

    @Override
    public void setTrailers(List<Result> trailers) {
        if(trailers.size()==0)
        {
            trailerRecyclerView.setVisibility(GONE);
            trailerText.setVisibility(GONE);
        }
        else {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            trailerRecyclerView.setLayoutManager(linearLayoutManager);
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
            trailerRecyclerView.addItemDecoration(itemDecoration);
            SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
            trailerRecyclerView.setOnFlingListener(null);
            snapHelper.attachToRecyclerView(trailerRecyclerView);
            mTrailerAdapter = new TrailerAdapter(getContext(), trailers,this);
            trailerRecyclerView.setAdapter(mTrailerAdapter);
        }


        imageViewPlaceDetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FragmentDemoActivity.class);
                intent.putExtra("key",trailers.get(0).getKey());
                getContext().startActivity(intent);

            }
        });


    }



    @Override
    public void onTrailerClick(int position, List<Result> mTrailerResults) {
        Intent intent = new Intent(getContext(), FragmentDemoActivity.class);
        intent.putExtra("key",mTrailerResults.get(position).getKey());
        getContext().startActivity(intent);
    }
}
