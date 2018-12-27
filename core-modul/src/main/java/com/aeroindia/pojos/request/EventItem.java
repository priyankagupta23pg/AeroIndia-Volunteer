package com.aeroindia.pojos.request;

import java.io.Serializable;

/**
 * Created by dell1 on 20-09-2018.
 */

public class EventItem implements Serializable {
    private String day;
    private String url;
    private String desc;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
