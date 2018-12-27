package com.aeroindia.pojos.request;

/**
 * Created by Dell13 on 22-11-2018.
 */

public class SendFCMRequest {
    private int userId,userType;
    private String fcmToken;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
