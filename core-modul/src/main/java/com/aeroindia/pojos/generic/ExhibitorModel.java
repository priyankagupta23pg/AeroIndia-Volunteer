package com.aeroindia.pojos.generic;

/**
 * Created by Dell13 on 14-11-2018.
 */

public class ExhibitorModel {
    private String cmpnyNm,hallNm,stallNo,address,cntry;

    public ExhibitorModel(String cmpnyNm, String hallNm, String stallNo,String address, String cntry) {
        this.cmpnyNm = cmpnyNm;
        this.hallNm = hallNm;
        this.stallNo = stallNo;
        this.address = address;
        this.cntry = cntry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCntry() {
        return cntry;
    }

    public void setCntry(String cntry) {
        this.cntry = cntry;
    }

    public String getCmpnyNm() {
        return cmpnyNm;
    }

    public void setCmpnyNm(String cmpnyNm) {
        this.cmpnyNm = cmpnyNm;
    }

    public String getHallNm() {
        return hallNm;
    }

    public void setHallNm(String hallNm) {
        this.hallNm = hallNm;
    }

    public String getStallNo() {
        return stallNo;
    }

    public void setStallNo(String stallNo) {
        this.stallNo = stallNo;
    }
}
