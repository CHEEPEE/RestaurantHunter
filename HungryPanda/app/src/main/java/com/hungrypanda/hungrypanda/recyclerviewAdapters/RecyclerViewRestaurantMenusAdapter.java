package com.hungrypanda.hungrypanda.recyclerviewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hungrypanda.hungrypanda.AppModules.GlideApp;
import com.hungrypanda.hungrypanda.R;
import com.hungrypanda.hungrypanda.datamodels.CategoryModel;
import com.hungrypanda.hungrypanda.datamodels.MenuItemGrid;
import com.hungrypanda.hungrypanda.mapModels.CategoryMapModel;
import com.hungrypanda.hungrypanda.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Keji's Lab on 26/12/2017.
 */

public class RecyclerViewRestaurantMenusAdapter extends RecyclerView.Adapter<RecyclerViewRestaurantMenusAdapter.MyViewHolder> {

    private ArrayList<MenuItemGrid> menuItemGridArrayList;
    private ArrayList<CategoryModel> categoryList = new ArrayList<>();
    private Context context;
    private String restaurantId;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView menuLabel,menuPrice,lblMenuCategory;
        public ImageView menuBanner;
        public MyViewHolder(View view){
            super(view);
            lblMenuCategory = (TextView) view.findViewById(R.id.lblMenuCategory);
            menuLabel = (TextView) view.findViewById(R.id.menuLabel);
            menuPrice = (TextView) view.findViewById(R.id.menuPrice);
            menuBanner = (ImageView) view.findViewById(R.id.menuBanner);


        }
    }

    public RecyclerViewRestaurantMenusAdapter(Context c, ArrayList<MenuItemGrid> menuItemGridArrayList,String restaurantId){
        this.menuItemGridArrayList = menuItemGridArrayList;
        this.context = c;
        this.restaurantId = restaurantId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_menus_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewRestaurantMenusAdapter.MyViewHolder holder, final int position) {
        final MenuItemGrid menuItemGridModel = menuItemGridArrayList.get(position);
        GlideApp.with(context).load(menuItemGridModel.getItemBannerUrl()).placeholder(R.drawable.image_placeholder).centerCrop().into(holder.menuBanner);
        holder.menuLabel.setText(menuItemGridModel.getiName());
        holder.menuPrice.setText("₱ "+menuItemGridModel.getItemPrice());
       // holder.lblMenuCategory.setText(menuItemGridModel.getItemCategory());
        System.out.println(restaurantId);


        FirebaseDatabase.getInstance().getReference().child(Utils.storeItemCategory).child(restaurantId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    CategoryModel categoryModel = new CategoryModel();
                    CategoryMapModel categoryMapModel =dataSnapshot1.getValue(CategoryMapModel.class);
                    categoryModel.setKey(categoryMapModel.key);
                    categoryModel.setCategory(categoryMapModel.category);
                    categoryList.add(categoryModel);

                }
                for (int i = 0;i<categoryList.size();i++){

                    if (menuItemGridModel.getItemCategory().equals(categoryList.get(i).getKey())){
                        holder.lblMenuCategory.setText(categoryList.get(i).getCategory());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return menuItemGridArrayList.size();
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