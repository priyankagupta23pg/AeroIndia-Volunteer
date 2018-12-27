package com.aeroindia.pojos.request;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dell1 on 20-09-2018.
 */

public class EventListItem implements Serializable {
    private ArrayList<EventItem> eventList;

    public ArrayList<EventItem> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<EventItem> eventList) {
        this.eventList = eventList;
    }
    static public EventListItem create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, EventListItem.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
