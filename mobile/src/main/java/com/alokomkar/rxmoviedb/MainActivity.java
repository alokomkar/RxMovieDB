package com.alokomkar.rxmoviedb;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alokomkar.rxmoviedb.movielist.Movie;
import com.alokomkar.rxmoviedb.movielist.MovieListFragment;
import com.alokomkar.rxmoviedb.trailers.TrailerFragment;
import com.alokomkar.rxmoviedb.trailers.TrailerViewPagerAdapter;
import com.alokomkar.rxmoviedb.utils.DepthPageTransformer;
import com.alokomkar.rxmoviedb.youtube.FragmentDemoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationListener {

    @BindView(R.id.movieTrailerViewPager)
    ViewPager movieTrailerViewPager;
    private CollapsingToolbarLayout collapsingToolbar;
    private FragmentTransaction mFragmentTransaction;
    private MovieListFragment movieListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
        collapsingToolbar.setTitle(getString(R.string.app_name));
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loadMovieListFragment();
    }

    private void loadMovieListFragment() {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        movieListFragment = (MovieListFragment) getSupportFragmentManager().findFragmentByTag(MovieListFragment.class.getSimpleName());
        if (movieListFragment == null) {
            movieListFragment = new MovieListFragment();
        }
        mFragmentTransaction.replace(R.id.container, movieListFragment, MovieListFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMoviesLoaded(List<Movie> movies) {
        if( movies != null ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window w = getWindow(); // in Activity's onCreate() for instance
                w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }

            movieTrailerViewPager.setPageTransformer(true, new DepthPageTransformer());
            List<TrailerFragment> trailerFragments = new ArrayList<>();
            for( Movie movie : movies ) {
                TrailerFragment trailerFragment = new TrailerFragment();
                trailerFragment.setMovie(movie);
                trailerFragments.add(trailerFragment);
            }
            movieTrailerViewPager.setAdapter(new TrailerViewPagerAdapter(getSupportFragmentManager(), trailerFragments));
        }
    }

    @Override
    public void playVideo(Movie movie) {
        Intent intent = new Intent(MainActivity.this, FragmentDemoActivity.class);
        startActivity(intent);
    }
}
