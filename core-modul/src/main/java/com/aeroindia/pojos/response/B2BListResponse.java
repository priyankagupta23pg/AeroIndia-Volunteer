package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell13 on 14-11-2018.
 */

public class B2BListResponse extends GenericResponse {
    public List<companyHallList> companyHallList = new ArrayList<>();
    public class companyHallList {
        public company company;
        public hall hall;


    }
    public class company
    {
        private String name;
        private String country,address;
        private int companyId;
        private String uniqueId,contactEmailId,mobileNumber;

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getContactEmailId() {
            return contactEmailId;
        }

        public void setContactEmailId(String contactEmailId) {
            this.contactEmailId = contactEmailId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }
    }
    public class hall
    {
        private int hallId;
        private String companyUniqueId,name,section,stall;
        private double latitude,longitude;

        public int getHallId() {
            return hallId;
        }

        public void setHallId(int hallId) {
            this.hallId = hallId;
        }

        public String getCompanyUniqueId() {
            return companyUniqueId;
        }

        public void setCompanyUniqueId(String companyUniqueId) {
            this.companyUniqueId = companyUniqueId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getStall() {
            return stall;
        }

        public void setStall(String stall) {
            this.stall = stall;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
    static public B2BListResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, B2BListResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
