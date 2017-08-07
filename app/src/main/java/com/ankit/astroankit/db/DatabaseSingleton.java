package com.ankit.astroankit.db;

import android.content.Context;

import com.ankit.astroankit.AnkitApp;

/**
 * @author by Ankit Kumar (ankitdroiddeveloper@gmail.com) on 8/5/17
 **/

public class DatabaseSingleton {
    private static DatabaseSingleton instance;
    public DatabaseHelper helper;

    private DatabaseSingleton(Context context) {
        helper = new DatabaseHelper(context);
    }

    public static DatabaseSingleton getInstance() {
        if (instance == null) {
            instance = new DatabaseSingleton(AnkitApp.getInstance());
        }
        return instance;
    }

    public DatabaseHelper getHelper() {
        return helper;
    }
}
