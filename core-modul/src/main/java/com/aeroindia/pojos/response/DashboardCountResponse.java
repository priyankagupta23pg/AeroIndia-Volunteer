package com.aeroindia.pojos.response;

import com.google.gson.Gson;

/**
 * Created by Dell13 on 10-12-2018.
 */

public class DashboardCountResponse extends GenericResponse {
private int pendingCount,attendedCount,resolveCount,noattendedCount;

    public int getNoattendedCount() {
        return noattendedCount;
    }

    public void setNoattendedCount(int noattendedCount) {
        this.noattendedCount = noattendedCount;
    }

    public int getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(int pendingCount) {
        this.pendingCount = pendingCount;
    }

    public int getAttendedCount() {
        return attendedCount;
    }

    public void setAttendedCount(int attendedCount) {
        this.attendedCount = attendedCount;
    }

    public int getResolveCount() {
        return resolveCount;
    }

    public void setResolveCount(int resolveCount) {
        this.resolveCount = resolveCount;
    }
    static public DashboardCountResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, DashboardCountResponse.class);
    }

    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
