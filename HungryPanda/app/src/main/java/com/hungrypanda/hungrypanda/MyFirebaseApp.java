package com.hungrypanda.hungrypanda;

/**
 * Created by Keji's Lab on 22/01/2018.
 */

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Keji's Lab on 09/12/2017.
 */

public class MyFirebaseApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
    /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
