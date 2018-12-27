package com.aeroindia.pojos.request;

/**
 * Created by Dell13 on 27-11-2018.
 */

public class DashboardModel {
    private String labelTxt;
    private int img;

    public String getLabelTxt() {
        return labelTxt;
    }

    public void setLabelTxt(String labelTxt) {
        this.labelTxt = labelTxt;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public DashboardModel(String labelTxt, int img) {
        this.labelTxt = labelTxt;
        this.img = img;
    }
}
