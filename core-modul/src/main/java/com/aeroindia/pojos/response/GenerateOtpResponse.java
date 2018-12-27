package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by PSQ on 11/4/2017.
 */

public class GenerateOtpResponse extends GenericResponse implements Serializable {
    static public GenerateOtpResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, GenerateOtpResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
