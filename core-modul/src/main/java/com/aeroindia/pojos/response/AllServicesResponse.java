package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell13 on 04-12-2018.
 */

public class AllServicesResponse extends GenericResponse {
    public List<services> services = new ArrayList<>();
    public static class services {
        private int id,serviceCode;
        private String serviceName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(int serviceCode) {
            this.serviceCode = serviceCode;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }
    }
    static public AllServicesResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, AllServicesResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
