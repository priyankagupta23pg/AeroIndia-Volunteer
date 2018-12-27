package com.aeroindia.pojos.response;

import com.google.gson.Gson;

/**
 * Created by Dell13 on 20-11-2018.
 */

public class RegisterResponse extends GenericResponse {
    static public RegisterResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, RegisterResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
