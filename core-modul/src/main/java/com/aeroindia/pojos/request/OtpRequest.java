package com.aeroindia.pojos.request;

import com.google.gson.Gson;

/**
 * Created by Dell13 on 20-11-2018.
 */

public class OtpRequest {
    private String otp,email;

    public OtpRequest(String otp, String email) {
        this.otp = otp;
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    static public OtpRequest create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, OtpRequest.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
