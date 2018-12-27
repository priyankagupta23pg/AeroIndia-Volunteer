package com.aeroindia.pojos.response;

/**
 * Created by Dell13 on 27-11-2018.
 */

public class AnnouncementModel {
    private String title,dateTime,venue,latLong;

    public AnnouncementModel(String title, String dateTime, String venue) {
        this.title = title;
        this.dateTime = dateTime;
        this.venue = venue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }
}
