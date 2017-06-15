package com.alokomkar.rxmoviedb.movielist;

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

    private String filterType;

    public MovieListPresenter(MovieListContract.View view, Retrofit retrofit) {
        this.view = view;
        this.retrofit = retrofit;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        compositeDisposable.clear();
    }

    @Override
    public void getTopRatedMovie(String APIKey) {
        filterType = "topRated";
        getMovies(APIKey);
    }

    @Override
    public void getPopularMovies(String APIKey) {
        filterType = "popular";
        getMovies(APIKey);
    }

    @Override
    public void getLatestMovies(String APIKey) {
        filterType = "latest";
        getMovies(APIKey);
    }

    @Override
    public void getNowPlayingMovies(String APIKey) {
        filterType = "nowPlaying";
        getMovies(APIKey);
    }

    private void getMovies(String apiKey) {
        view.showProgress();
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
