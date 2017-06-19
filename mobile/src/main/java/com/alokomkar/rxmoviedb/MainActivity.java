package com.alokomkar.rxmoviedb;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.alokomkar.rxmoviedb.base.Constants;
import com.alokomkar.rxmoviedb.moviedetails.MovieDetailsLandscapeFragment;
import com.alokomkar.rxmoviedb.movielist.Movie;
import com.alokomkar.rxmoviedb.movielist.MovieListContract;
import com.alokomkar.rxmoviedb.movielist.MovieListFragment;
import com.alokomkar.rxmoviedb.movielist.MovieListPresenter;
import com.alokomkar.rxmoviedb.trailers.TrailerFragment;
import com.alokomkar.rxmoviedb.trailers.TrailerViewPagerAdapter;
import com.alokomkar.rxmoviedb.utils.DepthPageTransformer;
import com.alokomkar.rxmoviedb.youtube.FragmentDemoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationListener, MovieListContract.View {


    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private ViewPager movieTrailerViewPager;
    private CollapsingToolbarLayout collapsingToolbar;
    private FragmentTransaction mFragmentTransaction;
    private MovieListFragment movieListFragment;
    private FrameLayout progressLayout;
    private MovieListPresenter movieListPresenter;
    private List<Movie> viewPagerMovies;
    private String currentTrailerId = null;
    private FloatingActionButton fab;
    private AppBarLayout.Behavior behavior;
    private CoordinatorLayout mainLayout;
    private AppBarLayout appBarLayout;
    Toolbar toolbar;
    @Override
    public void onBackPressed() {
        if (movieListFragment != null && movieListFragment.movieDetailsScene != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                movieListFragment.onBackPressedWithScene();
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //landscape mode
        if (findViewById(R.id.movieTrailerViewPager) != null) {
            setUpNavigationView();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            progressLayout = (FrameLayout) findViewById(R.id.progressLayout);
            movieTrailerViewPager = (ViewPager) findViewById(R.id.movieTrailerViewPager);
            mainLayout = (CoordinatorLayout) findViewById(R.id.mainLayout);
            appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.bringToFront();
            collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setContentScrimColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
            collapsingToolbar.setTitle(getString(R.string.app_name));
            collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
            collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
            collapsingToolbar.setTitleEnabled(true);
            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(view -> collapseToolbar());
            if( savedInstanceState == null ) {
                movieListPresenter = new MovieListPresenter(this, MovieApplication.getInstance().getNetModule().getRetrofit());
                movieListPresenter.start();
            }
            else {
                viewPagerMovies = savedInstanceState.getParcelableArrayList(Constants.MOVIES);
                currentTrailerId = savedInstanceState.getString(Constants.TRAILER_ID, null);
            }

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            loadMovieListFragment();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    private void setUpNavigationView() {

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        break;
                    case R.id.nav_photos:
                        break;
                    case R.id.nav_movies:
                        break;
                    case R.id.nav_notifications:
                        break;

                    default:
                        break;
                }


                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    public void collapseToolbar() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null) {
            behavior.onNestedFling(mainLayout, appBarLayout, null, 0, 10000, true);
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (viewPagerMovies != null && viewPagerMovies.size() > 0) {
                loadMovieDetailsFragment(viewPagerMovies.get(position));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void loadMovieDetailsFragment(Movie movie) {
        currentTrailerId = null;
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        MovieDetailsLandscapeFragment movieDetailsLandscapeFragment = MovieDetailsLandscapeFragment.getInstance();
        movieDetailsLandscapeFragment.setMovieId(movie.getId());
        mFragmentTransaction.replace(R.id.container, movieDetailsLandscapeFragment, MovieDetailsLandscapeFragment.class.getSimpleName());
        mFragmentTransaction.commit();
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
    public void onMoviesLoaded(List<Movie> movies) {
        if (movieTrailerViewPager != null && movies != null) {
            viewPagerMovies = movies;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window w = getWindow(); // in Activity's onCreate() for instance
                w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }

            movieTrailerViewPager.setPageTransformer(true, new DepthPageTransformer());
            List<TrailerFragment> trailerFragments = new ArrayList<>();
            for (Movie movie : movies) {
                TrailerFragment trailerFragment = new TrailerFragment();
                trailerFragment.setMovie(movie);
                trailerFragments.add(trailerFragment);
            }
            movieTrailerViewPager.setAdapter(new TrailerViewPagerAdapter(getSupportFragmentManager(), trailerFragments));
            movieTrailerViewPager.addOnPageChangeListener(pageChangeListener);
            if (viewPagerMovies != null && viewPagerMovies.size() > 0) {
                loadMovieDetailsFragment(viewPagerMovies.get(0));
            }

        }
    }

    @Override
    public void playVideo(Movie movie) {
        if (currentTrailerId == null) {
            Snackbar.make(findViewById(android.R.id.content), "Trailer not ready", Snackbar.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(MainActivity.this, FragmentDemoActivity.class);
        intent.putExtra("key", currentTrailerId);
        startActivity(intent);
    }

    @Override
    public void setCurrentTrailerId(String currentTrailerId) {
        this.currentTrailerId = currentTrailerId;
    }

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
        onMoviesLoaded(movieList);
    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if( viewPagerMovies != null ) {
            outState.putParcelableArrayList(Constants.MOVIES, new ArrayList<>(viewPagerMovies));
        }
        outState.putString(Constants.TRAILER_ID, currentTrailerId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPagerMovies = savedInstanceState.getParcelableArrayList(Constants.MOVIES);
        currentTrailerId = savedInstanceState.getString(Constants.TRAILER_ID, null);
    }
}
