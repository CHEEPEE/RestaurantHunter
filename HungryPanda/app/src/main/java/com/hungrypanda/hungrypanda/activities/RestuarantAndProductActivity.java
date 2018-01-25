package com.hungrypanda.hungrypanda.activities;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hungrypanda.hungrypanda.R;
import com.hungrypanda.hungrypanda.fragments.RestaurantFragment;
import com.hungrypanda.hungrypanda.fragments.RestaurantMenusFragment;
import com.hungrypanda.hungrypanda.mapModels.DeviceCurrentLocationMap;
import com.hungrypanda.hungrypanda.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RestuarantAndProductActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 14;
    private boolean mLocationPermissionGranted = false;
    private GeoDataClient mGeoDataClient;
    private Location mLastKnownLocation;
    public PlaceDetectionClient mPlaceDetectionClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private  TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_and_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the applicationActivty.
        getDeviceLocation();

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationPermission();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_tabed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent applicationActivty in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
/*    public static class PlaceholderFragment extends Fragment {
        *//**
     * The fragment argument representing the section number for this
     * fragment.
     *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        *//**
     * Returns a new instance of this fragment for the given section
     * number.
     *//*
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_test_tabed, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    final RestaurantFragment restaurantFragment = new RestaurantFragment();

                    return restaurantFragment;

                case 1:
                    RestaurantMenusFragment restaurantMenusFragment = new RestaurantMenusFragment();
                    return restaurantMenusFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }

    private void getDeviceLocation() {


        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            LatLng latLng = null;
                            if (mLastKnownLocation!=null){
                                latLng =  new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude());
                            }
                            System.out.println(mLastKnownLocation.getLatitude()+" "+mLastKnownLocation.getLongitude());


                          //  restaurantFragment.getFragmentManager().beginTransaction().detach(restaurantFragment).attach(restaurantFragment).commit();
                          /*  RestaurantFragment fragment = (RestaurantFragment) getSupportFragmentManager().findFragmentByTag("RestaurantFragment");
                            getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();*/

                            DeviceCurrentLocationMap deviceCurrentLocationMap = new DeviceCurrentLocationMap(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
                            Map<String,Object> postValue = deviceCurrentLocationMap.toMap();
                            Map<String,Object> childupdates = new HashMap<>();
                            childupdates.put(FirebaseAuth.getInstance().getUid(),postValue);
                            FirebaseDatabase.getInstance().getReference().child("deviceCurrentLocation").setValue(childupdates);




                            try {

                                Geocoder geo = new Geocoder(RestuarantAndProductActivity.this, Locale.getDefault());
                                List<Address> addresses = geo.getFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1);
                                if (addresses.isEmpty()) {

                                    //   Log.i("System out", "onMarkerDragEnd... waiting for location name"+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);
                                }
                                else {
                                    if (addresses.size() > 0) {
                                        for (int i = 0;i<addresses.size();i++){
                             /*  Log.i("Location",addresses.get(i).getAddressLine(0)+", "+addresses.get(i).getFeatureName() + ", " + addresses.get(i).getLocality() +", " + addresses.get(i).getAdminArea()+ ", " + addresses.get(i).getFeatureName() + ", " + addresses.get(i).getCountryName());*/
                                            Log.i("Location",addresses.get(i).getLocality()+", "+addresses.get(i).getSubAdminArea()+", "+addresses.get(i).getAdminArea()+ ", " + addresses.get(i).getCountryName());
                                           /* lblLocation.setText(addresses.get(i).getLocality()+", "+addresses.get(i).getSubAdminArea()+", "+addresses.get(i).getAdminArea()+ ", " + addresses.get(i).getCountryName());
                                            restoLocationLatitude = mLastKnownLocation.getLatitude();
                                            restoLocationLongitude = mLastKnownLocation.getLongitude();*/
                                        }
                                    }
                                }
                            }catch (IOException e){
                                Log.i("System out error", e.toString());
                                Utils.toster(RestuarantAndProductActivity.this,"Please Check Internet Connection");

                            }


                        } else {
                            Log.d("Location Error", "Current location is null. Using defaults.");
                            Log.e("Loction Error", "Exception: %s", task.getException());

                        }

                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }



    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
//        updateLocationUI();
    }

    public Location getLastKnownLocation(){
        return mLastKnownLocation;
    }
}
