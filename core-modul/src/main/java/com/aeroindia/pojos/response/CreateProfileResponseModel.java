package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by SUNAINA on 21-11-2018.
 */

public class CreateProfileResponseModel extends GenericResponse implements Serializable {

private ProfileItem profile;

    public ProfileItem getProfile() {
        return profile;
    }

    public void setProfile(ProfileItem profile) {
        this.profile = profile;
    }

    static public CreateProfileResponseModel create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, CreateProfileResponseModel.class);
    }

    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
