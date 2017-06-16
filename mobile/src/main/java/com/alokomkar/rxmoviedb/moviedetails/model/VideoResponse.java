package com.alokomkar.rxmoviedb.moviedetails.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class VideoResponse {

@JsonField(name="id")

private Integer id;
@JsonField(name="results")

private List<Result> results = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public List<Result> getResults() {
return results;
}

public void setResults(List<Result> results) {
this.results = results;
}

}