package com.hungrypanda.hungrypanda.recyclerviewAdapters;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hungrypanda.hungrypanda.AppModules.GlideApp;
import com.hungrypanda.hungrypanda.R;
import com.hungrypanda.hungrypanda.activities.ScrollingActivity;
import com.hungrypanda.hungrypanda.datamodels.RestaurantLocationModel;
import com.hungrypanda.hungrypanda.datamodels.StoreProfileModel;
import com.hungrypanda.hungrypanda.mapModels.DeviceCurrentLocationMap;
import com.hungrypanda.hungrypanda.mapModels.RestaurantLocationMapModel;
import com.hungrypanda.hungrypanda.utils.Utils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Keji's Lab on 26/12/2017.
 */

public class RecycleStoreProfilesAdapter extends RecyclerView.Adapter<RecycleStoreProfilesAdapter.MyViewHolder> {

    private ArrayList<StoreProfileModel> storeProfile;
    private Context context;
    private Location deviceLocation = new Location("");
    private Location location1 = new Location("");
    private Location location2 = new Location("");


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView lblStoreName,lnlStoreLocation,lblRestaurantRange;
        public ImageView restoImageBanner;
        public ImageView restoIcon;

        public MyViewHolder(View view){

            super(view);
            restoIcon = (ImageView) view.findViewById(R.id.restoIcon);
          //restoImageBanner = (ImageView) view.findViewById(R.id.restoImageBanner);
            lblStoreName = (TextView) view.findViewById(R.id.lblRestaurantName);
            lnlStoreLocation = (TextView) view.findViewById(R.id.lblRestaurantLocation);
            lblRestaurantRange = (TextView) view.findViewById(R.id.restaurantRange);

        }
    }

    public RecycleStoreProfilesAdapter(Context c, ArrayList<StoreProfileModel> storeProfile, Location deviceLocation){
        this.storeProfile = storeProfile;
        this.context = c;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restor_list_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleStoreProfilesAdapter.MyViewHolder holder, final int position) {
        final StoreProfileModel storeProfileModel = storeProfile.get(position);
        final Location storeLocation = new Location("");
        holder.lblStoreName.setText(storeProfileModel.getStoreName());
        holder.lnlStoreLocation.setText(storeProfileModel.getStoreAddress());
        holder.lblStoreName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ScrollingActivity.class);
                i.putExtra("key", storeProfileModel.getRestaurantID());
                context.startActivity(i);
            }
        });
        holder.restoIcon.clearColorFilter();
        //GlideApp.with(context).load(storeProfileModel.getStoreBannerUrl()).into(holder.restoImageBanner);
        GlideApp.with(context).load(storeProfileModel.getStoreProfileUrl()).placeholder(R.drawable.image_placeholder).centerCrop().into(holder.restoIcon);
        FirebaseDatabase.getInstance().getReference().child(Utils.restaurantLocation).child(storeProfileModel.getRestaurantID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                RestaurantLocationModel restaurantLocationModel = new RestaurantLocationModel();
                RestaurantLocationMapModel restaurantLocationMapModel = dataSnapshot.getValue(RestaurantLocationMapModel.class);
                storeLocation.setLatitude(restaurantLocationMapModel.locationLatitude);
                storeLocation.setLongitude(restaurantLocationMapModel.locationLongitude);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child(Utils.deviceCurrentLocation).child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DeviceCurrentLocationMap deviceCurrentLocationMap = dataSnapshot.getValue(DeviceCurrentLocationMap.class);

                  deviceLocation.setLatitude(deviceCurrentLocationMap.Latitude);
                  deviceLocation.setLongitude(deviceCurrentLocationMap.Longitude);
                  if (deviceLocation.distanceTo(storeLocation)>1000) {
                      String range = ((deviceLocation.distanceTo(storeLocation) / 1000)+"").substring(0,((deviceLocation.distanceTo(storeLocation) / 1000)+"").indexOf(".")+3);
                      holder.lblRestaurantRange.setText(range + " Km");
                  }else {
                      String range = ((deviceLocation.distanceTo(storeLocation))+"").substring(0,((deviceLocation.distanceTo(storeLocation))+"").indexOf(".")+3);
                      holder.lblRestaurantRange.setText(range+"meters");
                  }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return storeProfile.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int posistion);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickListener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private OnItemLongClickListener monItemLongClickListener;

    public void setonItemLongClickListener(OnItemLongClickListener monItemLongClickListener){
        this.monItemLongClickListener = monItemLongClickListener;
    }


}