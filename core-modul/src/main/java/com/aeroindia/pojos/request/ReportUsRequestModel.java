package com.aeroindia.pojos.request;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by SUNAINA on 04-12-2018.
 */

public class ReportUsRequestModel implements Serializable {
    private long createdBy;
    private String qrcodeNumber;
    private String serviceCode;
    private String comment;
    private long rating;
    private long dateTime;
    private String scanStatus;
    private int status;
    private int complaintId;
    private String serviceIssue;

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public String getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getServiceIssue() {
        return serviceIssue;
    }

    public void setServiceIssue(String serviceIssue) {
        this.serviceIssue = serviceIssue;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public String getQrcodeNumber() {
        return qrcodeNumber;
    }

    public void setQrcodeNumber(String qrcodeNumber) {
        this.qrcodeNumber = qrcodeNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    static public ReportUsRequestModel create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, ReportUsRequestModel.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
