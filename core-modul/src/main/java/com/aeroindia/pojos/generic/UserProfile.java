package com.penpencil.core.pojos.generic;

import java.io.Serializable;

/**
 * Created by PSQ on 11/5/2017.
 */

public class UserProfile implements Serializable {
    private Integer User_id;
    private String phone_number;
    private String password;
    private String board;
    private String lang;
    private String classes;
    private String first_name;
    private String last_name;
    private Integer client_client_id;
    private Integer country_code;

    public Integer getUser_id() {
        return User_id;
    }

    public void setUser_id(Integer user_id) {
        User_id = user_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getClient_client_id() {
        return client_client_id;
    }

    public void setClient_client_id(Integer client_client_id) {
        this.client_client_id = client_client_id;
    }

    public Integer getCountry_code() {
        return country_code;
    }

    public void setCountry_code(Integer country_code) {
        this.country_code = country_code;
    }

}
