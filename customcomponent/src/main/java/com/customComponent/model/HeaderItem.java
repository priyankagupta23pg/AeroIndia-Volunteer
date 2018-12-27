package com.customComponent.model;

import java.io.Serializable;

/**
 * Created by dell1 on 03-08-2017.
 */
public class HeaderItem implements Serializable {

    private String headername;
    private String headerValue;

    public String getHeadername() {
        return headername;
    }

    public void setHeadername(String headername) {
        this.headername = headername;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }
}
