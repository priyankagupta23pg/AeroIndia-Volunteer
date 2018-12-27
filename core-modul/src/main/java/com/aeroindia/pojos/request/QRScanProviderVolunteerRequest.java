package com.aeroindia.pojos.request;

import com.google.gson.Gson;

/**
 * Created by Dell13 on 10-12-2018.
 */

public class QRScanProviderVolunteerRequest {
    public QRScanProviderVolunteerRequest(String qrcodeNumber, long userId,String userType) {
        this.qrcodeNumber = qrcodeNumber;
        this.userId = userId;
        this.userType = userType;
    }

    private String qrcodeNumber,userType;
    private long userId;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getQrcodeNumber() {
        return qrcodeNumber;
    }

    public void setQrcodeNumber(String qrcodeNumber) {
        this.qrcodeNumber = qrcodeNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    static public QRScanProviderVolunteerRequest create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, QRScanProviderVolunteerRequest.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
