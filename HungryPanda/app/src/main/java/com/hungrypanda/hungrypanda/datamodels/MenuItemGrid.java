package com.hungrypanda.hungrypanda.datamodels;

/**
 * Created by Keji's Lab on 02/01/2018.
 */

public class MenuItemGrid {
    private String iName;
    private String itemCategory;
    private String itemPrice;
    private String itemBannerUrl;
    private String itemKey;
    private String itemCode;
    private Boolean itemPublic;

    public String getItemCode(){
        return itemCode;
    }
    public Boolean getItemPublic(){
        return itemPublic;
    }

    public String getiName(){
        return iName;
    }
    public String getItemCategory(){
        return itemCategory;
    }
    public String getItemPrice(){
        return itemPrice;
    }
    public String getItemBannerUrl(){
        return itemBannerUrl;
    }
    public String getItemKey(){
        return itemKey;
    }
    public void setiName(String name){
        this.iName = name;
    }
    public void setItemCategory(String category){
        this.itemCategory = category;
    }
    public void setItemBannerUrl(String bUrl){
        this.itemBannerUrl = bUrl;
    }
    public void setItemPrice(String price){
        this.itemPrice = price;
    }
    public void setItemKey(String key){
        this.itemKey = key;
    }
    public void setItemCode(String code){
        this.itemCode = code;
    }
    public void setItemPublic(Boolean itemPublic){
        this.itemPublic = itemPublic;
    }
}
