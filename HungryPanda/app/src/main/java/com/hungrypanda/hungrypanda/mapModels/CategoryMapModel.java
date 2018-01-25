package com.hungrypanda.hungrypanda.mapModels;

/**
 * Created by Keji's Lab on 22/01/2018.
 */

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keji's Lab on 26/11/2017.
 */

public class CategoryMapModel {
    public String key;
    public String category;

    public CategoryMapModel(){

    }
    public CategoryMapModel (String getKey,String ItemCategory){
        this.category = ItemCategory;
        this.key = getKey;
    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("key",key);
        result.put("category",category);

        return result;
    }
}
