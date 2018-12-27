package com.aeroindia.pojos.request;

/**
 * Created by dell1 on 28-09-2018.
 */

public class MeetingItem {
    private String meetingWith;
    private String location;
    private String time;
    private String date;
    private String contactNum;


    public String getMeetingWith() {
        return meetingWith;
    }

    public void setMeetingWith(String meetingWith) {
        this.meetingWith = meetingWith;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }
}
