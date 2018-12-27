package com.aeroindia.pojos.response;

/**
 * Created by Dell13 on 29-11-2018.
 */

public class ServiceCategoryItemModel {
    private String categoryName,subCategoryName,latLong,distance;
    private int categoryCode,subCategoryCode;

    public ServiceCategoryItemModel(int categoryCode, String categoryName, int subCategoryCode,String distance) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.subCategoryCode = subCategoryCode;
        this.distance = distance;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSubCategoryCode() {
        return subCategoryCode;
    }

    public void setSubCategoryCode(int subCategoryCode) {
        this.subCategoryCode = subCategoryCode;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
