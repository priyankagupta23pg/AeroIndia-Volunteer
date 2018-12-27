package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SUNAINA on 19-11-2018.
 */

public class AnnouncemnetResponseModel implements Serializable {
    private ArrayList<AnnouncementItemsModel> AnnouncementItems;

    public ArrayList<AnnouncementItemsModel> getAnnouncementItemsModels() {
        return AnnouncementItems;
    }

    public void setAnnouncementItemsModels(ArrayList<AnnouncementItemsModel> announcementItemsModels) {
        this.AnnouncementItems = announcementItemsModels;
    }

    static public AnnouncemnetResponseModel create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, AnnouncemnetResponseModel.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
