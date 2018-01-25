package com.hungrypanda.hungrypanda.utils;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

/**
 * Created by Keji's Lab on 26/12/2017.
 */
public class Utils {
    public static String storeItemCategory = "storeItemCategory";
    public static String storeProfiles = "storeProfiles";
    public static String restaurantItems = "restaurantItems";
    public static String restaurantLocation = "restaurantLocation";
    public static String deviceCurrentLocation = "deviceCurrentLocation";
    public static String testImageUrl ="https://firebasestorage.googleapis.com/v0/b/android-based-pos.appspot.com/o/images%2FrestaurantItems%2FBcUYltmzioggwtH0SCUYlKP9XWr2%2FIMG_20171127_222809.jpgandroid.os.ParcelFileDescriptor%24AutoCloseInputStream%40d2eb373%2FIMG_20171127_222809.jpg?alt=media&token=f3133eed-a3fc-4a66-9b11-8a827a23e1f5";
    public static void toster(Context context, String Text){
        Toast.makeText(context,Text,Toast.LENGTH_LONG).show();
    }
    public static class productItems{
        public static String iName = "iName";
        public static String itemCategory = "itemCategory";
        public static String itemPrice = "itemPrice";
        public static String itemBannerUrl = "itemBannerUrl";
        public static String itemKey = "itemKey";
    }

    public static class storeProfilesItem{
        public static String storeAddress ="storeAddress";
        public static String storeName = "storeName";
        public static String storeBannerUrl = "storeBannerUrl";
        public static String storeProfileUrl = "storeProfileUrl";
        public static String storeContact = "storeContact";
        public static String restaurantID ="restaurantID";

    }

    public Location getDeviceLocation(Context context){

        return null;
    }

}
