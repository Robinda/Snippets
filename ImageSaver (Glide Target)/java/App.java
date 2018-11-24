package com.example.robin.demoapp;

import android.app.Application;

public class App extends Application {

    public static App INSTANCE;

    private WallpaperDatabase database;

    public static App get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Code...

        INSTANCE = this;
    }
}
