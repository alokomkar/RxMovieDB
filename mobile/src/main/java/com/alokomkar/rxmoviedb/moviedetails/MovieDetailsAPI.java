package com.alokomkar.rxmoviedb.moviedetails;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rahul on 15/06/17.
 */

public interface MovieDetailsAPI {

     @GET("movie/{id}")
     Observable<MovieDetailsResponse> getMovieDetails(@Path("id") int movieid, @Query("api_key") String apikEY );

}
