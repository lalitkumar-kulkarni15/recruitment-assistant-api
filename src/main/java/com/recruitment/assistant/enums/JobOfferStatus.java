package com.recruitment.assistant.enums;

public enum JobOfferStatus {

    ACTIVE("A"),
    INACTIVE("I");

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    JobOfferStatus(String sts) {
        this.status = sts;
    }



}





