package com.aeroindia.pojos.request;

import com.google.gson.Gson;

/**
 * Created by Dell13 on 20-11-2018.
 */

public class RegisterUserRequest {
    public RegisterUserRequest(String email, String name, String country, String mobileNo, String companyName, String category, String companyUniqueId) {
        this.email = email;
        this.name = name;
        this.country = country;
        this.mobileNo = mobileNo;
        this.companyName = companyName;
        this.category = category;
        this.companyUniqueId = companyUniqueId;
    }

//    public RegisterUserRequest(String email, String category) {
//        this.email = email;
//        this.category = category;
//    }
//
//    public RegisterUserRequest(String email, String name, String country, String mobileNo, String category) {
//        this.email = email;
//        this.name = name;
//        this.country = country;
//        this.mobileNo = mobileNo;
//        this.category = category;
//    }

    private String email,name,country,mobileNo,companyName,category,companyUniqueId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompanyUniqueId() {
        return companyUniqueId;
    }

    public void setCompanyUniqueId(String companyUniqueId) {
        this.companyUniqueId = companyUniqueId;
    }

    static public RegisterUserRequest create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, RegisterUserRequest.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
