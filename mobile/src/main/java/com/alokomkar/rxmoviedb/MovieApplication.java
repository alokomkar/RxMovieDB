package com.alokomkar.rxmoviedb;

import android.app.Application;

import com.alokomkar.rxmoviedb.base.NetModule;

/**
 * Created by Alok on 15/06/17.
 */

public class MovieApplication extends Application {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static MovieApplication instance;
    private NetModule netModule;

    public static MovieApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        netModule = getNetModule();
    }

    public NetModule getNetModule() {
        if( netModule == null ) {
            netModule = new NetModule(BASE_URL, this);
        }
        return netModule;
    }
}
