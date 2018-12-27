package com.aeroindia.pojos.request;

import com.google.gson.Gson;

import java.io.Serializable;
/*Request Body
    {
        "userName": "rgera@gov.in",
            "password": "1234"
    }
 */
public class LoginRequest implements Serializable {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    static public LoginRequest create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, LoginRequest.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
