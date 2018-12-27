package com.aeroindia.pojos.request;

import com.google.gson.Gson;

/**
 * Created by Dell13 on 10-12-2018.
 */

public class UpdateReportProviderVolunteerRequest {
    private String status,remarks,qrcodeNumber;
    private int barcodeId,id;
    private long userId;
    private int claimId;

    public UpdateReportProviderVolunteerRequest() {
    }

    public UpdateReportProviderVolunteerRequest(String status, String remarks, int barcodeId, int id, long userId) {
        this.status = status;
        this.remarks = remarks;
        this.barcodeId = barcodeId;
        this.id = id;
        this.userId = userId;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public String getQrcodeNumber() {
        return qrcodeNumber;
    }

    public void setQrcodeNumber(String qrcodeNumber) {
        this.qrcodeNumber = qrcodeNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(int barcodeId) {
        this.barcodeId = barcodeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    static public UpdateReportProviderVolunteerRequest create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, UpdateReportProviderVolunteerRequest.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
