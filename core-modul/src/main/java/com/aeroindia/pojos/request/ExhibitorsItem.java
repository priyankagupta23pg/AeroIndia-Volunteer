package com.aeroindia.pojos.request;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by dell1 on 26-09-2018.
 */

public class ExhibitorsItem implements Serializable {
    private String hallNo;
    private String stalNo;
    private String company;
    private String address,country;
    private String phone;
    private String name;
    private String email;
    private int companyId;
    private String uniqueId;

    public ExhibitorsItem() {
    }

    public ExhibitorsItem(String hallNo, String stalNo, String country, String name) {
        this.hallNo = hallNo;
        this.stalNo = stalNo;
        this.country = country;
        this.name = name;

    }

    public String getHallNo() {
        return hallNo;
    }

    public void setHallNo(String hallNo) {
        this.hallNo = hallNo;
    }

    public String getStalNo() {
        return stalNo;
    }

    public void setStalNo(String stalNo) {
        this.stalNo = stalNo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    static public ExhibitorsItem create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, ExhibitorsItem.class);
    }

    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
