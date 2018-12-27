package com.aeroindia.pojos.response;

public  class ClaimListModel {
private int id,barcodeId,starring,createdBy,updatedBy;
private String comment,claimList,status,remarks,serviceName;
private long createdOn,updatedOn;

    public ClaimListModel(int id, int barcodeId, int starring, int createdBy, String comment, String claimList, String status, String remarks, long createdOn, long updatedOn,int updatedBy,String serviceName) {
        this.id = id;
        this.barcodeId = barcodeId;
        this.starring = starring;
        this.createdBy = createdBy;
        this.comment = comment;
        this.claimList = claimList;
        this.status = status;
        this.remarks = remarks;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.updatedBy = updatedBy;
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBarcodeId() {
            return barcodeId;
        }

        public void setBarcodeId(int barcodeId) {
            this.barcodeId = barcodeId;
        }

        public int getStarring() {
            return starring;
        }

        public void setStarring(int starring) {
            this.starring = starring;
        }

        public int getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(int createdBy) {
            this.createdBy = createdBy;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getClaimList() {
            return claimList;
        }

        public void setClaimList(String claimList) {
            this.claimList = claimList;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public long getCreatedOn() {
            return createdOn;
        }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setCreatedOn(long createdOn) {
            this.createdOn = createdOn;
        }
    }