package com.alokomkar.rxmoviedb.moviedetails.model;

import java.util.List;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonObject
public class ReviewResponse {

@JsonField(name="id")

private Integer id;
@JsonField(name="page")

private Integer page;
@JsonField(name="results")

private List<ReviewResult> results = null;
@JsonField(name="total_pages")

private Integer totalPages;
@JsonField(name="total_results")

private Integer totalResults;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getPage() {
return page;
}

public void setPage(Integer page) {
this.page = page;
}

public List<ReviewResult> getResults() {
return results;
}

public void setResults(List<ReviewResult> results) {
this.results = results;
}

public Integer getTotalPages() {
return totalPages;
}

public void setTotalPages(Integer totalPages) {
this.totalPages = totalPages;
}

public Integer getTotalResults() {
return totalResults;
}

public void setTotalResults(Integer totalResults) {
this.totalResults = totalResults;
}

}