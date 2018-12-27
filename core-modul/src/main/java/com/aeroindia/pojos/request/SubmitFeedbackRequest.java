package com.aeroindia.pojos.request;

import com.google.gson.Gson;

/**
 * Created by Dell13 on 03-12-2018.
 */

public class SubmitFeedbackRequest {
    private int feedbackId;
private int whatItemCode,serviceType,totalStar,status;
private String whoGivenName,comment;
private long whoGiven;

    public SubmitFeedbackRequest(long whoGiven, int whatItemCode, int serviceType, int totalStar, String whoGivenName, String comment) {
        this.whoGiven = whoGiven;
        this.whatItemCode = whatItemCode;
        this.serviceType = serviceType;
        this.totalStar = totalStar;
        this.whoGivenName = whoGivenName;
        this.comment = comment;
    }

    public SubmitFeedbackRequest() {
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getWhoGiven() {
        return whoGiven;
    }

    public void setWhoGiven(long whoGiven) {
        this.whoGiven = whoGiven;
    }

    public int getWhatItemCode() {
        return whatItemCode;
    }

    public void setWhatItemCode(int whatItemCode) {
        this.whatItemCode = whatItemCode;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getTotalStar() {
        return totalStar;
    }

    public void setTotalStar(int totalStar) {
        this.totalStar = totalStar;
    }

    public String getWhoGivenName() {
        return whoGivenName;
    }

    public void setWhoGivenName(String whoGivenName) {
        this.whoGivenName = whoGivenName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    static public SubmitFeedbackRequest create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, SubmitFeedbackRequest.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
