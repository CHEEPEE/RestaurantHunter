package com.hungrypanda.hungrypanda.fragments;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hungrypanda.hungrypanda.R;
import com.hungrypanda.hungrypanda.activities.RestuarantAndProductActivity;
import com.hungrypanda.hungrypanda.datamodels.StoreProfileModel;
import com.hungrypanda.hungrypanda.mapModels.DeviceCurrentLocationMap;
import com.hungrypanda.hungrypanda.mapModels.StoreProfileInformationMap;
import com.hungrypanda.hungrypanda.recyclerviewAdapters.RecycleStoreProfilesAdapter;
import com.hungrypanda.hungrypanda.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Keji's Lab on 26/12/2017.
 */

public class RestaurantFragment extends Fragment {
    TextView lblRestaurantName,lblRestaurantAddress;
    ArrayList<StoreProfileModel> storeProfileModels = new ArrayList<>();
    RecyclerView rvRestaurantList;
    DatabaseReference mDatabase;
    RecycleStoreProfilesAdapter recycleStoreProfilesAdapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 14;
    private boolean mLocationPermissionGranted = false;
    private GeoDataClient mGeoDataClient;
    private Location mLastKnownLocation = new Location("");
    public PlaceDetectionClient mPlaceDetectionClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleApiClient googleApiClient;
    protected LocationManager locationManager;
    protected LocationListener locationListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_restaurant, container, false);
        lblRestaurantName = (TextView)rootView.findViewById(R.id.lblRestaurantName);
        RestuarantAndProductActivity restuarantAndProductActivity = (RestuarantAndProductActivity) getActivity() ;
        lblRestaurantAddress = (TextView) rootView.findViewById(R.id.lblRestaurantLocation);
        rvRestaurantList = (RecyclerView) rootView.findViewById(R.id.rvRestuarantsList);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        context = getActivity();
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvRestaurantList.setLayoutManager(layoutManager);
        recycleStoreProfilesAdapter = new RecycleStoreProfilesAdapter(getContext(),storeProfileModels,mLastKnownLocation);
        rvRestaurantList.setAdapter(recycleStoreProfilesAdapter);

        mGeoDataClient = Places.getGeoDataClient(getContext(), null);


        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getContext(), null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());





        mDatabase.child(Utils.storeProfiles).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storeProfileModels.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    StoreProfileModel storeProfileModel = new StoreProfileModel();
                    StoreProfileInformationMap storeProfileInformationMap = dataSnapshot1.getValue(StoreProfileInformationMap.class);
                    storeProfileModel.setStoreName(storeProfileInformationMap.storeName);
                    storeProfileModel.setStoreBannerUrl(storeProfileInformationMap.storeBannerUrl);
                    storeProfileModel.setStoreProfileUrl(storeProfileInformationMap.storeProfileUrl);
                    storeProfileModel.setStoreAddress(storeProfileInformationMap.storeAddress);
                    storeProfileModel.setStoreContact(storeProfileInformationMap.storeContact);
                    storeProfileModel.setRestaurantID(storeProfileInformationMap.restaurantID);
                    storeProfileModels.add(storeProfileModel);

                }
                recycleStoreProfilesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }

}
