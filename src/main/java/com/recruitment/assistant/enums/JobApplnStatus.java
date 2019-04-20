package com.recruitment.assistant.enums;

public enum JobApplnStatus {

    ACTIVE("A"),
    INACTIVE("I");

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    JobApplnStatus(String sts) {
        this.status = sts;
    }



}





