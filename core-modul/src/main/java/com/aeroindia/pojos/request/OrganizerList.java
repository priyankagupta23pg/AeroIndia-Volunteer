package com.aeroindia.pojos.request;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dell1 on 26-09-2018.
 */

public class OrganizerList implements Serializable {
    private ArrayList<OrganizerItem> list;

    public ArrayList<OrganizerItem> getList() {
        return list;
    }

    public void setList(ArrayList<OrganizerItem> list) {
        this.list = list;
    }

    static public OrganizerList create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, OrganizerList.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
