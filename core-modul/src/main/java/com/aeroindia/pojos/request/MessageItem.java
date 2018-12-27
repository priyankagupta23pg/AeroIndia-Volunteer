package com.aeroindia.pojos.request;

import com.google.gson.Gson;

/**
 * Created by Dell13 on 23-11-2018.
 */

public class MessageItem {
    private long dateTime;
            private String purpose,message,name,commType;


    public MessageItem(long dateTime, String purpose, String message, String name, String commType) {
        this.dateTime = dateTime;
        this.purpose = purpose;
        this.message = message;
        this.name = name;
        this.commType = commType;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    static public MessageItem create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, MessageItem.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
