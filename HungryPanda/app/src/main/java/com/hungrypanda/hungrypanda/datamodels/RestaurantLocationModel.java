package com.hungrypanda.hungrypanda.datamodels;

/**
 * Created by Keji's Lab on 22/01/2018.
 */

public class RestaurantLocationModel {
    String key;
    String restaurantAddress;
    double locationLatitude;
    double locationLongitude;

    public String getKey(){
        return key;
    }
    public String getRestaurantAddress(){
        return restaurantAddress;
    }
    public void setKey(String itemKey){
        this.key = itemKey;
    }
    public void setRestaurantAddress(String restoLocation){
        this.restaurantAddress = restoLocation;
    }
    public double getLocationLatitude(){
        return locationLatitude;
    }

    public double getLocationLongitude(){
        return locationLongitude;
    }
    public void setLocationLat(double latitude){
        this.locationLatitude = latitude;
    }
    public void setLocationLongitude(double longitude){
        this.locationLongitude = longitude;
    }
}
