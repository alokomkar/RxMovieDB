package com.alokomkar.rxmoviedb.movielist;

import com.alokomkar.rxmoviedb.base.Constants;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Alok on 15/06/17.
 */

public class MovieListPresenter implements MovieListContract.Presenter {

    private MovieListContract.View view;
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    private String API_KEY;

    public MovieListPresenter(MovieListContract.View view, Retrofit retrofit) {
        this.view = view;
        this.retrofit = retrofit;
        this.compositeDisposable = new CompositeDisposable();
        this.API_KEY = Constants.API_KEY;
    }

    @Override
    public void start() {
        view.showProgress();
        getLatestMovies(API_KEY);
        getPopularMovies(API_KEY);
        getNowPlayingMovies(API_KEY);
        getTopRatedMovie(API_KEY);
    }

    @Override
    public void stop() {
        compositeDisposable.clear();
    }

    @Override
    public void getTopRatedMovie(String APIKey) {
        getMovies(APIKey, "topRated");
    }

    @Override
    public void getPopularMovies(String APIKey) {
        getMovies(APIKey, "popular");
    }

    @Override
    public void getLatestMovies(String APIKey) {
        getMovies(APIKey, "latest");
    }

    @Override
    public void getNowPlayingMovies(String APIKey) {
        getMovies(APIKey, "nowPlaying");
    }

    private void getMovies(String apiKey, String filterType) {

        MovieListAPI movieListAPI = retrofit.create(MovieListAPI.class);
        Observable<MoviesResponse> moviesResponseObservable = null;
        switch ( filterType ) {
            case "topRated" :
                moviesResponseObservable = movieListAPI.getTopRated(apiKey);
                break;
            case "popular" :
                moviesResponseObservable = movieListAPI.getPopularMovies(apiKey);
                break;
            case "latest" :
                moviesResponseObservable = movieListAPI.getLatest(apiKey);
                break;
            case "nowPlaying" :
                moviesResponseObservable = movieListAPI.getRunningMovies(apiKey);
                break;
        }
        if( moviesResponseObservable != null ) {
            moviesResponseObservable.subscribeOn(Schedulers.io())
                    .map(MoviesResponse::getResults)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Movie>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(List<Movie> movies) {
                            switch ( filterType ) {
                                case "topRated" :
                                    view.loadTopRatedMovies(movies);
                                    break;
                                case "popular" :
                                    view.loadPopularMovies(movies);
                                    break;
                                case "latest" :
                                    view.loadLatestMovies(movies);
                                    break;
                                case "nowPlaying" :
                                    view.loadNowPlayingMovies(movies);
                                    break;
                            }
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
    }

}
