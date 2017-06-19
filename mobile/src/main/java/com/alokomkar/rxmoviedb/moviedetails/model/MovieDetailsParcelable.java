package com.alokomkar.rxmoviedb.moviedetails.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Alok on 19/06/17.
 */

public class MovieDetailsParcelable implements Parcelable {

    private List<Result> movieTrailers;
    private MovieDetailsResponse movieDetailsResponse;

    public MovieDetailsParcelable(List<Result> movieTrailers, MovieDetailsResponse movieDetailsResponse) {
        this.movieTrailers = movieTrailers;
        this.movieDetailsResponse = movieDetailsResponse;
    }

    public List<Result> getMovieTrailers() {
        return movieTrailers;
    }

    public void setMovieTrailers(List<Result> movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    public MovieDetailsResponse getMovieDetailsResponse() {
        return movieDetailsResponse;
    }

    public void setMovieDetailsResponse(MovieDetailsResponse movieDetailsResponse) {
        this.movieDetailsResponse = movieDetailsResponse;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.movieTrailers);
        dest.writeParcelable(this.movieDetailsResponse, flags);
    }

    protected MovieDetailsParcelable(Parcel in) {
        this.movieTrailers = in.createTypedArrayList(Result.CREATOR);
        this.movieDetailsResponse = in.readParcelable(MovieDetailsResponse.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieDetailsParcelable> CREATOR = new Parcelable.Creator<MovieDetailsParcelable>() {
        @Override
        public MovieDetailsParcelable createFromParcel(Parcel source) {
            return new MovieDetailsParcelable(source);
        }

        @Override
        public MovieDetailsParcelable[] newArray(int size) {
            return new MovieDetailsParcelable[size];
        }
    };
}
