package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell13 on 04-12-2018.
 */

public class SubServicesResponse extends GenericResponse {
    public List<subServices> subServices = new ArrayList<>();
    public class subServices {
       private int id,serviceCode,subServiceCode;
       private String subServceName,latitude,longitude;
       private String distance;

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

        public int getSubServiceCode() {
            return subServiceCode;
        }

        public void setSubServiceCode(int subServiceCode) {
            this.subServiceCode = subServiceCode;
        }

        public String getSubServceName() {
            return subServceName;
        }

        public void setSubServceName(String subServceName) {
            this.subServceName = subServceName;
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
    static public SubServicesResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, SubServicesResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
