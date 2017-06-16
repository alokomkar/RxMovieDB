package com.alokomkar.rxmoviedb.moviedetails.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by rahul on 16/06/17.
 */
@JsonObject
public class ReviewResult {

    @JsonField(name="id")
    
    private String id;
    @JsonField(name="author")
    
    private String author;
    @JsonField(name="content")
    
    private String content;
    @JsonField(name="url")
    
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
