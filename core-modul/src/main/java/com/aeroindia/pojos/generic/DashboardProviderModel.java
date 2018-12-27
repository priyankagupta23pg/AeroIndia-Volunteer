package com.aeroindia.pojos.generic;

/**
 * Created by Dell13 on 05-12-2018.
 */

public class DashboardProviderModel {
    private String label;
    private int count;

    public DashboardProviderModel(String label, int count) {
        this.label = label;
        this.count = count;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
