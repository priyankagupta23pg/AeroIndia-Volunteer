package com.aeroindia.pojos.response;

/**
 * Created by Dell13 on 27-11-2018.
 */

public class EventModel {
    private String eventName,eventType,venue,latitude,longitude;
    private long dateTime;
    private int id;

    public EventModel() {
    }

    public EventModel(int id, long dateTime, String eventName, String eventType, String venue, String latitude, String longitude) {
        this.id = id;
        this.dateTime = dateTime;
        this.eventName = eventName;
        this.eventType = eventType;
        this.venue = venue;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }


}
