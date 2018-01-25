package com.hungrypanda.hungrypanda.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hungrypanda.hungrypanda.R;
import com.hungrypanda.hungrypanda.datamodels.StoreProfileModel;

import java.util.ArrayList;

/**
 * Created by Keji's Lab on 26/12/2017.
 */

public class RestaurantMenusFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_restaurant_menu, container, false);



        return rootView;
    }
}
