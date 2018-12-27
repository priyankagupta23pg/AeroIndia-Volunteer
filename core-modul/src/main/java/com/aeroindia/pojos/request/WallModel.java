package com.aeroindia.pojos.request;

/**
 * Created by Dell13 on 27-11-2018.
 */

public class WallModel {
private String feedbackTxt,feedbackBy,dateTimeTxt;
int rating;

    public WallModel(String feedbackTxt, String feedbackBy, int rating,String dateTimeTxt) {
        this.feedbackTxt = feedbackTxt;
        this.feedbackBy = feedbackBy;
        this.rating = rating;
        this.dateTimeTxt = dateTimeTxt;
    }

    public String getFeedbackTxt() {
        return feedbackTxt;
    }

    public void setFeedbackTxt(String feedbackTxt) {
        this.feedbackTxt = feedbackTxt;
    }

    public String getFeedbackBy() {
        return feedbackBy;
    }

    public void setFeedbackBy(String feedbackBy) {
        this.feedbackBy = feedbackBy;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDateTimeTxt() {
        return dateTimeTxt;
    }

    public void setDateTimeTxt(String dateTimeTxt) {
        this.dateTimeTxt = dateTimeTxt;
    }
}
