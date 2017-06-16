package com.alokomkar.rxmoviedb.moviedetails;

import com.alokomkar.rxmoviedb.base.Constants;
import com.alokomkar.rxmoviedb.moviedetails.model.MovieDetailsResponse;
import com.alokomkar.rxmoviedb.moviedetails.model.VideoResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by rahul on 15/06/17.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private final int movieId;
    private MovieDetailsContract.View view;
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;
    private String API_KEY;


    public MovieDetailsPresenter(MovieDetailsContract.View view, Retrofit retrofit , int movieId) {
        this.view = view;
        this.retrofit = retrofit;
        this.compositeDisposable = new CompositeDisposable();
        this.movieId=movieId;
        this.API_KEY= Constants.API_KEY;
    }

    @Override
    public void start() {
         view.showProgress();
         getMovieDetails(movieId,API_KEY);
    }

    @Override
    public void stop() {
        compositeDisposable.clear();
    }

    @Override
    public void getMovieDetails(int movieId, String apiKey) {

        MovieDetailsAPI movieDetailsAPI = retrofit.create(MovieDetailsAPI.class);
        Observable<MovieDetailsResponse> moviesResponseObservable = null;
        moviesResponseObservable = movieDetailsAPI.getMovieDetails(movieId, apiKey);
        moviesResponseObservable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetailsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(MovieDetailsResponse movieDetailsResponse) {
                          view.setMovieDetails(movieDetailsResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        view.hideProgress();
                    }
                });
    }

    @Override
    public void getTrailers(int movieId) {
        view.showProgress();
        MovieDetailsAPI movieDetailsAPI = retrofit.create(MovieDetailsAPI.class);
        Observable<VideoResponse> moviesTrailerResponseObservable = null;
        moviesTrailerResponseObservable = movieDetailsAPI.getTrailers(movieId,API_KEY);
        moviesTrailerResponseObservable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                     compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull VideoResponse videoResponse) {
                        view.setTrailers(videoResponse.getResults());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                          e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        view.hideProgress();

                    }
                });

    }
}
