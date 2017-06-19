package com.alokomkar.rxmoviedb.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alokomkar.rxmoviedb.MovieApplication;
import com.alokomkar.rxmoviedb.NavigationListener;
import com.alokomkar.rxmoviedb.R;
import com.alokomkar.rxmoviedb.moviedetails.model.MovieDetailsResponse;
import com.alokomkar.rxmoviedb.moviedetails.model.Result;

import com.alokomkar.rxmoviedb.moviedetails.model.ReviewResult;

import com.alokomkar.rxmoviedb.utils.GravitySnapHelper;
import com.alokomkar.rxmoviedb.utils.ItemOffsetDecoration;
import com.alokomkar.rxmoviedb.youtube.FragmentDemoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Alok on 16/06/17.
 */


public class MovieDetailsLandscapeFragment extends Fragment implements MovieDetailsContract.View,TrailerAdapter.OnTrailerClick,View.OnClickListener {


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
    @BindView(R.id.rating)
    TextView mRating;
    @BindView(R.id.summaryText)
    TextView mSummaryText;
    @BindView(R.id.reviews_label)
    TextView reviews;
    @BindView(R.id.reviews)
    LinearLayout reviewsContainer;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private int movieId;
    private MovieDetailsPresenter movieDetailsPresenter;
    private TrailerAdapter mTrailerAdapter;

    private String TAG = MovieDetailsLandscapeFragment.class.getSimpleName();
    private NavigationListener navigationListener;


    public static MovieDetailsLandscapeFragment getInstance() {
        if (instance == null) {
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
        if( mProgressBar != null )
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if( mProgressBar != null )
            mProgressBar.setVisibility(View.GONE);
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
    }

    @Override
    public void setTrailers(List<Result> trailers) {


        if (trailers.size() == 0) {
            trailerRecyclerView.setVisibility(GONE);
            trailerText.setVisibility(GONE);
        } else {

            trailerRecyclerView.setVisibility(VISIBLE);
            trailerText.setVisibility(VISIBLE);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            trailerRecyclerView.setLayoutManager(linearLayoutManager);
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
            trailerRecyclerView.addItemDecoration(itemDecoration);
            SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
            trailerRecyclerView.setOnFlingListener(null);
            snapHelper.attachToRecyclerView(trailerRecyclerView);
            mTrailerAdapter = new TrailerAdapter(getContext(), trailers, this);
            trailerRecyclerView.setAdapter(mTrailerAdapter);
            if( navigationListener != null ) {
                navigationListener.setCurrentTrailerId(trailers.get(0).getKey());
            }
        }
    }


    @Override
    public void setReviews(List<ReviewResult> reviews) {
        if (reviews.size()==0)
        {
            this.reviews.setVisibility(View.GONE);
            reviewsContainer.setVisibility(View.GONE);
        } else
        {
            this.reviews.setVisibility(View.VISIBLE);
            reviewsContainer.setVisibility(View.VISIBLE);

            reviewsContainer.removeAllViews();
            LayoutInflater inflater = getActivity().getLayoutInflater();
            for (ReviewResult review : reviews)
            {
                ViewGroup reviewContainer = (ViewGroup) inflater.inflate(R.layout.review, reviewsContainer, false);
                TextView reviewAuthor = ButterKnife.findById(reviewContainer, R.id.review_author);
                TextView reviewContent = ButterKnife.findById(reviewContainer, R.id.review_content);
                reviewAuthor.setText(review.getAuthor());
                reviewContent.setText(review.getContent());
                reviewContent.setOnClickListener(this);
                reviewsContainer.addView(reviewContainer);
            }

        }
    }

    @Override
    public void onTrailerClick(int position, List<Result> mTrailerResults) {
        Intent intent = new Intent(getContext(), FragmentDemoActivity.class);
        intent.putExtra("key",mTrailerResults.get(position).getKey());
        getContext().startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof NavigationListener ) {
            navigationListener = (NavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        navigationListener = null;
        super.onDetach();
    }

    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.review_content:
                onReviewClick((TextView) v);
                break;
        }

    }

    private void onReviewClick(TextView view) {
        if (view.getMaxLines() == 5)
        {
            view.setMaxLines(500);
        } else
        {
            view.setMaxLines(5);
        }
    }
}
