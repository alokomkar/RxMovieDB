package com.alokomkar.rxmoviedb.movielist;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Alok on 15/06/17.
 */

public interface MovieListAPI {

    @GET("movie/top_rated")
    Observable<MoviesResponse> getTopRated(@Query("api_key") String api);

    @GET("movie/latest")
    Observable<MoviesResponse> getLatest(@Query("api_key") String api);

    @GET("movie/now_playing")
    Observable<MoviesResponse> getRunningMovies(@Query("api_key") String api);

    @GET("movie/popular")
    Observable<MoviesResponse> getPopularMovies(@Query("api_key") String api);

    @GET("movie/{id}")
    Observable<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String api);

}
