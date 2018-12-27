package com.aeroindia.pojos.request;

import com.google.gson.Gson;

/**
 * Created by Priyanka Gupta on 21-03-2017.
 */
public class EmptyRequest {

    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
