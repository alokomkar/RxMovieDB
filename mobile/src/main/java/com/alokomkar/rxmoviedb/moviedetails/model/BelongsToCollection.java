package com.alokomkar.rxmoviedb.moviedetails.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class BelongsToCollection implements Parcelable {

    @JsonField(name="id")
    
    private Integer id;
    @JsonField(name="name")

    private String name;
    @JsonField(name="poster_path")

    private String posterPath;
    @JsonField(name="backdrop_path")

    private String backdropPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
    }

    public BelongsToCollection() {
    }

    protected BelongsToCollection(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
    }

    public static final Parcelable.Creator<BelongsToCollection> CREATOR = new Parcelable.Creator<BelongsToCollection>() {
        @Override
        public BelongsToCollection createFromParcel(Parcel source) {
            return new BelongsToCollection(source);
        }

        @Override
        public BelongsToCollection[] newArray(int size) {
            return new BelongsToCollection[size];
        }
    };
}