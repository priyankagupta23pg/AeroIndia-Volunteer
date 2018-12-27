package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell13 on 05-12-2018.
 */

public class UpcomingEventsResponse extends GenericResponse {
    private List<EventModel> eventList = new ArrayList<>();

    public List<EventModel> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventModel> eventList) {
        this.eventList = eventList;
    }
    static public UpcomingEventsResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, UpcomingEventsResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
