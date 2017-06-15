package com.alokomkar.rxmoviedb;

import com.alokomkar.rxmoviedb.movielist.Movie;

import java.util.List;

/**
 * Created by Alok on 15/06/17.
 */

public interface NavigationListener {
    void onMoviesLoaded(List<Movie> movies);
    void playVideo(Movie movie);
}
