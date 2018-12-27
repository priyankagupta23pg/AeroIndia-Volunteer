package com.aeroindia.pojos.request;

import com.google.gson.Gson;

/**
 * Created by Dell13 on 03-12-2018.
 */

public class GetFeedbackRequest {
    private int whatItemCode;

    public GetFeedbackRequest(int whatItemCode) {
        this.whatItemCode = whatItemCode;
    }

    public int getWhatItemCode() {
        return whatItemCode;
    }

    public void setWhatItemCode(int whatItemCode) {
        this.whatItemCode = whatItemCode;
    }
    static public GetFeedbackRequest create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, GetFeedbackRequest.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
