package com.hungrypanda.hungrypanda.AppModules;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Keji's Lab on 25/12/2017.
 */


public class FirebaseApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}