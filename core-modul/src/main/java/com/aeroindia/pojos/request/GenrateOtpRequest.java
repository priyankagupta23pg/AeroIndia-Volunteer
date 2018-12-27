package com.penpencil.core.pojos.request;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by PSQ on 11/4/2017.
 */
/*{
        "client_id":1,
        "country_code":91,
        "number":"8586990906"
        }*/

public class GenrateOtpRequest implements Serializable {
    private  Integer client_id;
    private  Integer country_code;
    private  String number;

    static public GenrateOtpRequest create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, GenrateOtpRequest.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public Integer getCountry_code() {
        return country_code;
    }

    public void setCountry_code(Integer country_code) {
        this.country_code = country_code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
