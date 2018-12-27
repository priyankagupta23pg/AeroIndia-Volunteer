package com.aeroindia.pojos.request;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dell1 on 28-09-2018.
 */

public class PersonaliseItem implements Serializable {

    private ArrayList<MeetingItem> meetings;
    private PossessionItem possesions;


    public PossessionItem getPossesions() {
        return possesions;
    }

    public void setPossesions(PossessionItem possesions) {
        this.possesions = possesions;
    }

    static public PersonaliseItem create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, PersonaliseItem.class);
    }
    public ArrayList<MeetingItem> getMeetings() {
        return meetings;
    }

    public void setMeetings(ArrayList<MeetingItem> meetings) {
        this.meetings = meetings;
    }

    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
