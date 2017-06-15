package com.alokomkar.rxmoviedb.movielist;

/**
 * Created by Alok on 15/06/17.
 */


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by rajkiran on 12/06/17.
 */
@JsonObject
public class MoviesResponse {
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
}
