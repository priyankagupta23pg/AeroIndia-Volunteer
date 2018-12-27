package com.aeroindia.pojos.request;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by SUNAINA on 21-11-2018.
 */

public class CreateProfileRequestModel implements Serializable {
    private long exhibitorId;
    private String aboutMe;

    public long getExhibitorId() {
        return exhibitorId;
    }

    public void setExhibitorId(long exhibitorId) {
        this.exhibitorId = exhibitorId;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    static public CreateProfileRequestModel create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, CreateProfileRequestModel.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
