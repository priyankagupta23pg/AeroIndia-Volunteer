package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by dell1 on 27-09-2018.
 */

public class LoginResponse extends GenericResponse implements Serializable{
    private User user;
    private Company userDetail;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return userDetail;
    }

    public void setCompany(Company company) {
        this.userDetail = company;
    }

    static public LoginResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, LoginResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public class Company {
        private int userDetailId;
        private String name;
        private String country;

        public int getCompanyId() {
            return userDetailId;
        }

        public void setCompanyId(int companyId) {
            this.userDetailId = companyId;
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
    }
    public class User {
            private Long userId;
            private String userName;
            private String companyUniqueId;
            private boolean isFirst;
            private String category;
            private String isActive;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCompanyUniqueId() {
            return companyUniqueId;
        }

        public void setCompanyUniqueId(String companyUniqueId) {
            this.companyUniqueId = companyUniqueId;
        }

        public boolean isFirst() {
            return isFirst;
        }

        public void setFirst(boolean first) {
            isFirst = first;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }
    }
    }

