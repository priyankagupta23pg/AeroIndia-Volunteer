package com.aeroindia.pojos.generic;

/**
 * Created by Dell13 on 27-11-2018.
 */

public class ServiceModel {
    private String serviceName;
private int serviceCode,id;
    public ServiceModel(int id,int serviceCode, String serviceName) {
        this.id = id;
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
