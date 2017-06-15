package com.alokomkar.rxmoviedb.moviedetails;

import com.alokomkar.rxmoviedb.moviedetails.model.MovieDetailsResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rahul on 15/06/17.
 */

public interface MovieDetailsAPI {

     @GET("movie/{movie_id}")
     Observable<MovieDetailsResponse> getMovieDetails(@Path("movie_id") String movieid, @Query("api_key") String apikEY );

}
