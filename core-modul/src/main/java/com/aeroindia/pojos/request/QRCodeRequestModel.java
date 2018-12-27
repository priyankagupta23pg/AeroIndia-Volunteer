package com.aeroindia.pojos.request;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by SUNAINA on 04-12-2018.
 */

public class QRCodeRequestModel implements Serializable {
    private String qrcodeNumber;
    private String latitude;
    private String longitude;
    private long userId;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getQrcodeNumber() {
        return qrcodeNumber;
    }

    public void setQrcodeNumber(String qrcodeNumber) {
        this.qrcodeNumber = qrcodeNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    static public QRCodeRequestModel create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, QRCodeRequestModel.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
