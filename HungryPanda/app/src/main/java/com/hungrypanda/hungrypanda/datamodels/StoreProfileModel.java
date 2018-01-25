package com.hungrypanda.hungrypanda.datamodels;

/**
 * Created by Keji's Lab on 26/12/2017.
 */
public class StoreProfileModel {
    private String storeProfileUrl;
    private String storeBannerUrl;
    private String storeName;
    private String storeAddress;
    private String storeContact;
    private String restaurantID;

    public String getStoreProfileUrl(){
        return  storeProfileUrl;
    }
    public String getRestaurantID(){
        return restaurantID;
    }
    public String getStoreBannerUrl(){
        return storeBannerUrl;
    }
    public String getStoreName(){
        return storeName;
    }
    public String getStoreAddress(){
        return storeAddress;
    }
    public String getStoreContact(){
        return storeContact;
    }
    public void setStoreProfileUrl(String profileUrl){
        this.storeProfileUrl = profileUrl;
    }
    public void setStoreBannerUrl(String bannerUrl){
        this.storeBannerUrl = bannerUrl;
    }
    public void setStoreName(String name){
        this.storeName = name;
    }
    public void setStoreAddress(String address){
        this.storeAddress = address;
    }
    public void setStoreContact(String contact){
        this.storeContact = contact;
    }
    public void setRestaurantID(String id){
        this.restaurantID = id;
    }
}