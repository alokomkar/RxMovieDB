package com.alokomkar.rxmoviedb.movielist;

/**
 * Created by Alok on 15/06/17.
 */


import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by rajkiran on 12/06/17.
 */
@JsonObject
public class MoviesResponse implements Parcelable {
    @JsonField(name = "page")
    private int page;
    @JsonField(name = "results")
    private List<Movie> results;
    @JsonField(name = "total_results")
    private int totalResults;
    @JsonField(name = "total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeTypedList(this.results);
        dest.writeInt(this.totalResults);
        dest.writeInt(this.totalPages);
    }

    public MoviesResponse() {
    }

    protected MoviesResponse(Parcel in) {
        this.page = in.readInt();
        this.results = in.createTypedArrayList(Movie.CREATOR);
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
    }

    public static final Parcelable.Creator<MoviesResponse> CREATOR = new Parcelable.Creator<MoviesResponse>() {
        @Override
        public MoviesResponse createFromParcel(Parcel source) {
            return new MoviesResponse(source);
        }

        @Override
        public MoviesResponse[] newArray(int size) {
            return new MoviesResponse[size];
        }
    };
}
