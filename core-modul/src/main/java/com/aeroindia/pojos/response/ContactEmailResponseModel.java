package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by SUNAINA on 21-11-2018.
 */

public class ContactEmailResponseModel extends GenericResponse implements Serializable {

    static public ContactEmailResponseModel create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, ContactEmailResponseModel.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
