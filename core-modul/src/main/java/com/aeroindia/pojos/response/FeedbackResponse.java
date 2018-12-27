package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell13 on 14-11-2018.
 */

public class FeedbackResponse extends GenericResponse {
    public List<feedbackList> feedbackList = new ArrayList<>();
    public static class feedbackList {
private int id,whoGiven,whatItemCode,serviceType,totalStar;
private String whoGivenName,comment,createdBy;
private long createdOn;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getWhoGiven() {
            return whoGiven;
        }

        public void setWhoGiven(int whoGiven) {
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

        public long getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(long createdOn) {
            this.createdOn = createdOn;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }
    }

    static public FeedbackResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, FeedbackResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
