package com.felipeshiba.lab.nytimes.articles;

import android.app.Application;

import io.reactivex.plugins.RxJavaPlugins;

public class VntApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);
    }
}
