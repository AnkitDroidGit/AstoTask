package com.ankit.astroankit;

import android.app.Application;
import android.os.Handler;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class AnkitApp extends Application {
    private static AnkitApp Instance;
    public static volatile Handler applicationHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
        applicationHandler = new Handler(getInstance().getMainLooper());


    }

    public static AnkitApp getInstance() {
        return Instance;
    }
}
