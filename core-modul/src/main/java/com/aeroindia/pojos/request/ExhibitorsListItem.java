package com.aeroindia.pojos.request;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dell1 on 26-09-2018.
 */

public class ExhibitorsListItem implements Serializable {
    private ArrayList<ExhibitorsItem> list;

    public ArrayList<ExhibitorsItem> getList() {
        return list;
    }

    public void setList(ArrayList<ExhibitorsItem> list) {
        this.list = list;
    }

    static public ExhibitorsListItem create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, ExhibitorsListItem.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
