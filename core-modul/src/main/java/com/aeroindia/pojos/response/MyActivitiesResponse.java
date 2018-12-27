package com.aeroindia.pojos.response;

import com.aeroindia.pojos.request.MessageItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell13 on 26-11-2018.
 */

public class MyActivitiesResponse extends GenericResponse {
    public List<MessageItem> youContacted = new ArrayList<>();
    public List<MessageItem> contactedBy = new ArrayList<>();

    static public MyActivitiesResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, MyActivitiesResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
