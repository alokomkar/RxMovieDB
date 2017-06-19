package com.alokomkar.rxmoviedb.moviedetails.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Result implements Parcelable {

@JsonField(name="id")

private String id;
@JsonField(name="iso_639_1")

private String iso6391;
@JsonField(name="iso_3166_1")

private String iso31661;
@JsonField(name="key")

private String key;
@JsonField(name="name")

private String name;
@JsonField(name="site")

private String site;
@JsonField(name="size")

private Integer size;
@JsonField(name="type")

private String type;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getIso6391() {
return iso6391;
}

public void setIso6391(String iso6391) {
this.iso6391 = iso6391;
}

public String getIso31661() {
return iso31661;
}

public void setIso31661(String iso31661) {
this.iso31661 = iso31661;
}

public String getKey() {
return key;
}

public void setKey(String key) {
this.key = key;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getSite() {
return site;
}

public void setSite(String site) {
this.site = site;
}

public Integer getSize() {
return size;
}

public void setSize(Integer size) {
this.size = size;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.iso6391);
        dest.writeString(this.iso31661);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeValue(this.size);
        dest.writeString(this.type);
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.id = in.readString();
        this.iso6391 = in.readString();
        this.iso31661 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}