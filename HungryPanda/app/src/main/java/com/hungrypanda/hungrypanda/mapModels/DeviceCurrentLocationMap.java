package com.hungrypanda.hungrypanda.mapModels;

/**
 * Created by Keji's Lab on 25/01/2018.
 */


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keji's Lab on 26/11/2017.
 */

public class DeviceCurrentLocationMap {
   public double Latitude;
   public double Longitude;
    public DeviceCurrentLocationMap(double lat, double longi){
        this.Latitude = lat;
        this.Longitude = longi;

    }
    public DeviceCurrentLocationMap(){

    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("Latitude",Latitude);
        result.put("Longitude",Longitude);



        return result;
    }
}
