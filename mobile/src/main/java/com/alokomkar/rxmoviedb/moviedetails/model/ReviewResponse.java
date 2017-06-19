package com.alokomkar.rxmoviedb.moviedetails.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class ReviewResponse implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.page);
        dest.writeTypedList(this.results);
        dest.writeValue(this.totalPages);
        dest.writeValue(this.totalResults);
    }

    public ReviewResponse() {
    }

    protected ReviewResponse(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(ReviewResult.CREATOR);
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalResults = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReviewResponse> CREATOR = new Parcelable.Creator<ReviewResponse>() {
        @Override
        public ReviewResponse createFromParcel(Parcel source) {
            return new ReviewResponse(source);
        }

        @Override
        public ReviewResponse[] newArray(int size) {
            return new ReviewResponse[size];
        }
    };
}