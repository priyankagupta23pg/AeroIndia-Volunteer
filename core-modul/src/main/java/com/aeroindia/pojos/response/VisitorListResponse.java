package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell13 on 15-11-2018.
 */

public class VisitorListResponse extends GenericResponse {

    public List<businessVisitorList> businessVisitorList = new ArrayList<>();
    public class businessVisitorList {
private int businessVisitorId;
private String name,addresss,country;

        public int getBusinessVisitorId() {
            return businessVisitorId;
        }

        public void setBusinessVisitorId(int businessVisitorId) {
            this.businessVisitorId = businessVisitorId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddresss() {
            return addresss;
        }

        public void setAddresss(String addresss) {
            this.addresss = addresss;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    static public VisitorListResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, VisitorListResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
