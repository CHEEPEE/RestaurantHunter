package com.hungrypanda.hungrypanda.datamodels;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Keji's Lab on 22/01/2018.
 */

public class CurrentLocationModel {
    double Latitude;
    double Logitude;

    public double getLatitude(){
        return Latitude;
    }
    public double getLogitude(){
        return Logitude;
    }
    public void setLatitude(double Lat){
        this.Latitude = Lat;
    }
    public void setLogitude(double Lon){
        this.Logitude = Lon;
    }
}

