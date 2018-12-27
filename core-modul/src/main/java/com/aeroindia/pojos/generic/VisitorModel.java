package com.aeroindia.pojos.generic;

/**
 * Created by Dell13 on 14-11-2018.
 */

public class VisitorModel {
    private String visitorNm,visitorAdd;

    public VisitorModel(String visitorNm, String visitorAdd) {
        this.visitorNm = visitorNm;
        this.visitorAdd = visitorAdd;
    }

    public String getVisitorNm() {
        return visitorNm;
    }

    public void setVisitorNm(String visitorNm) {
        this.visitorNm = visitorNm;
    }

    public String getVisitorAdd() {
        return visitorAdd;
    }

    public void setVisitorAdd(String visitorAdd) {
        this.visitorAdd = visitorAdd;
    }
}
