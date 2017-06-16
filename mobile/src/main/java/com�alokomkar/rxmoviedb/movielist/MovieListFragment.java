package com.alokomkar.rxmoviedb.movielist;


import android.os.Build;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.transition.Scene;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alokomkar.rxmoviedb.MovieApplication;
import com.alokomkar.rxmoviedb.NavigationListener;
import com.alokomkar.rxmoviedb.R;

import com.alokomkar.rxmoviedb.TransitionUtils;
import com.alokomkar.rxmoviedb.moviedetails.MovieDetailsLayout;

import com.alokomkar.rxmoviedb.utils.GravitySnapHelper;
import com.alokomkar.rxmoviedb.utils.ItemOffsetDecoration;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alok on 15/06/17.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MovieListFragment extends Fragment implements MovieListContract.View, MovieListRecyclerAdapter.ItemClickListener {

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
    @BindView(R.id.rootContainer)
    RelativeLayout rootContainer;
    @BindView(R.id.firstChild)
    ScrollView firstChild;
    private MovieListPresenter movieListPresenter;
    public static Scene movieDetailsScene;
    private int offset;

    private NavigationListener navigationListener;
    private String currentTransitionName;
    private MovieListRecyclerAdapter movieListRecyclerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        movieListPresenter = new MovieListPresenter(this, MovieApplication.getInstance().getNetModule().getRetrofit());
        movieListPresenter.start();
        return fragmentView;
    }

    //TODO : To be decided if this is needed
    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void loadTopRatedMovies(List<Movie> movieList) {
        setupRecyclerView(topMoviesRecyclerView, movieList);
    }


    @Override
    public void loadPopularMovies(List<Movie> movieList) {
        setupRecyclerView(popularMoviesRecyclerView, movieList);
    }

    @Override
    public void loadLatestMovies(List<Movie> movieList) {
        setupRecyclerView(latestMoviesRecyclerView, movieList);
    }

    @Override
    public void loadNowPlayingMovies(List<Movie> movieList) {

        setupRecyclerView( nowPlayingRecyclerView, movieList );
        navigationListener.onMoviesLoaded( movieList );

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private RecyclerView selectedRecyclerView;

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemClicked, String transitionName, int position, Movie movie) {
        currentTransitionName=transitionName;
        offset=firstChild.getScrollY();
        selectedRecyclerView = recyclerView;
        movieDetailsScene= MovieDetailsLayout.showScene(getActivity(), rootContainer, itemClicked, transitionName, movie.getId());
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Movie> movieList) {


        movieListRecyclerAdapter = new MovieListRecyclerAdapter(recyclerView, getContext(), movieList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        recyclerView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(movieListRecyclerAdapter);

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

    public void onBackPressedWithScene() {
        int childPosition = TransitionUtils.getItemPositionFromTransition(currentTransitionName);
        MovieDetailsLayout.hideScene(getActivity(),rootContainer,getSharedViewByPosition(childPosition),currentTransitionName);
        notifyLayoutAfterBackPress(childPosition);
        movieDetailsScene = null;

    }

    private void notifyLayoutAfterBackPress(int childPosition) {
        rootContainer.removeAllViews();
        rootContainer.addView(firstChild);
        selectedRecyclerView.requestLayout();

        firstChild.post(new Runnable() {
            @Override
            public void run() {
                firstChild.scrollTo(0, offset);
            }
        });
        movieListRecyclerAdapter.notifyItemChanged(childPosition);

    }

    private View getSharedViewByPosition(int childPosition) {
        for (int i = 0; i < selectedRecyclerView.getChildCount(); i++) {
            if (childPosition == selectedRecyclerView.getChildAdapterPosition(selectedRecyclerView.getChildAt(i))) {
                return selectedRecyclerView.getChildAt(i);
            }
        }
        return null;
    }
}
