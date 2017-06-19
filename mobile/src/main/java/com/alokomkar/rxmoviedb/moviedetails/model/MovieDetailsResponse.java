package com.alokomkar.rxmoviedb.moviedetails.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by rahul on 15/06/17.
 */

@JsonObject
public class MovieDetailsResponse implements Parcelable {

    @JsonField(name = "adult")

    private Boolean adult;
    @JsonField(name = "backdrop_path")

    private String backdropPath;
    @JsonField(name = "belongs_to_collection")

    private BelongsToCollection belongsToCollection;
    @JsonField(name = "budget")

    private Integer budget;
    @JsonField(name = "genres")

    private List<Genre> genres = null;
    @JsonField(name = "homepage")

    private String homepage;
    @JsonField(name = "id")

    private Integer id;
    @JsonField(name = "imdb_id")

    private String imdbId;
    @JsonField(name = "original_language")

    private String originalLanguage;
    @JsonField(name = "original_title")

    private String originalTitle;
    @JsonField(name = "overview")

    private String overview;
    @JsonField(name = "popularity")

    private Double popularity;
    @JsonField(name = "poster_path")

    private String posterPath;
    @JsonField(name = "production_companies")

    private List<ProductionCompany> productionCompanies = null;
    @JsonField(name = "production_countries")

    private List<ProductionCountry> productionCountries = null;
    @JsonField(name = "release_date")

    private String releaseDate;
    @JsonField(name = "revenue")

    private Integer revenue;
    @JsonField(name = "runtime")

    private Integer runtime;
    @JsonField(name = "spoken_languages")

    private List<SpokenLanguage> spokenLanguages = null;
    @JsonField(name = "status")

    private String status;
    @JsonField(name = "tagline")

    private String tagline;
    @JsonField(name = "title")

    private String title;
    @JsonField(name = "video")

    private Boolean video;
    @JsonField(name = "vote_average")

    private Double voteAverage;
    @JsonField(name = "vote_count")

    private Integer voteCount;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public BelongsToCollection getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.adult);
        dest.writeString(this.backdropPath);
        dest.writeParcelable(this.belongsToCollection, flags);
        dest.writeValue(this.budget);
        dest.writeTypedList(this.genres);
        dest.writeString(this.homepage);
        dest.writeValue(this.id);
        dest.writeString(this.imdbId);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeValue(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeTypedList(this.productionCompanies);
        dest.writeTypedList(this.productionCountries);
        dest.writeString(this.releaseDate);
        dest.writeValue(this.revenue);
        dest.writeValue(this.runtime);
        dest.writeTypedList(this.spokenLanguages);
        dest.writeString(this.status);
        dest.writeString(this.tagline);
        dest.writeString(this.title);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.voteCount);
    }

    public MovieDetailsResponse() {
    }

    protected MovieDetailsResponse(Parcel in) {
        this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.backdropPath = in.readString();
        this.belongsToCollection = in.readParcelable(BelongsToCollection.class.getClassLoader());
        this.budget = (Integer) in.readValue(Integer.class.getClassLoader());
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.homepage = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.imdbId = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.posterPath = in.readString();
        this.productionCompanies = in.createTypedArrayList(ProductionCompany.CREATOR);
        this.productionCountries = in.createTypedArrayList(ProductionCountry.CREATOR);
        this.releaseDate = in.readString();
        this.revenue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.runtime = (Integer) in.readValue(Integer.class.getClassLoader());
        this.spokenLanguages = in.createTypedArrayList(SpokenLanguage.CREATOR);
        this.status = in.readString();
        this.tagline = in.readString();
        this.title = in.readString();
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieDetailsResponse> CREATOR = new Parcelable.Creator<MovieDetailsResponse>() {
        @Override
        public MovieDetailsResponse createFromParcel(Parcel source) {
            return new MovieDetailsResponse(source);
        }

        @Override
        public MovieDetailsResponse[] newArray(int size) {
            return new MovieDetailsResponse[size];
        }
    };
}