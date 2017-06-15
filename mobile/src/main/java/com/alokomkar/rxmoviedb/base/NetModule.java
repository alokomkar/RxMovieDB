package com.alokomkar.rxmoviedb.base;

import android.app.Application;
import android.util.Log;

import com.alokomkar.rxmoviedb.BuildConfig;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.github.simonpercic.oklog3.OkLogInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Alok on 15/06/17.
 */

public class NetModule {

    private String mBaseUrl;
    private Application application;

    private Cache cache;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;


    // Constructor needs one parameter to instantiate.
    public NetModule(String baseUrl, Application application) {
        Log.d("baseUrl", baseUrl);
        this.mBaseUrl = baseUrl;
        this.application = application;
    }

    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    OkHttpClient provideOkHttpClient(Cache cache) {
        // create an instance of OkLogInterceptor using a builder()
        OkLogInterceptor okLogInterceptor =
                OkLogInterceptor.builder()
                        .setLogInterceptor(url -> {
                            return true;
                        }).withAllLogData().build();
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(mChain -> {
            Request original = mChain.request();
            Request.Builder builder = original.newBuilder().header("Content-Type", "application/json");
            Request request = builder.build();
            return mChain.proceed(request);
        });
        if (BuildConfig.DEBUG)
            client
                    .addInterceptor(new ChuckInterceptor(application.getApplicationContext()))
                    .addInterceptor(okLogInterceptor)// add OkLogInterceptor to OkHttpClient's application interceptors
                    .cache(cache);
        else
            client
                    .cache(cache);

        return client.build();
    }

    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }

    private Cache getCache() {
        if( cache == null ) {
            cache = provideOkHttpCache(application);
        }
        return cache;
    }

    private OkHttpClient getOkHttpClient() {
        if( okHttpClient == null ) {
            okHttpClient = provideOkHttpClient(getCache());
        }
        return okHttpClient;
    }

    public Retrofit getRetrofit() {
        if( retrofit == null ) {
            retrofit = provideRetrofit(getOkHttpClient());
        }
        return retrofit;
    }

}
