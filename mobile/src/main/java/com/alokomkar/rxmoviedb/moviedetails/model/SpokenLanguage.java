package com.alokomkar.rxmoviedb.moviedetails.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class SpokenLanguage implements Parcelable {

    @JsonField(name="iso_639_1")
    
    private String iso6391;
    @JsonField(name="name")
    
    private String name;

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iso6391);
        dest.writeString(this.name);
    }

    public SpokenLanguage() {
    }

    protected SpokenLanguage(Parcel in) {
        this.iso6391 = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<SpokenLanguage> CREATOR = new Parcelable.Creator<SpokenLanguage>() {
        @Override
        public SpokenLanguage createFromParcel(Parcel source) {
            return new SpokenLanguage(source);
        }

        @Override
        public SpokenLanguage[] newArray(int size) {
            return new SpokenLanguage[size];
        }
    };
}