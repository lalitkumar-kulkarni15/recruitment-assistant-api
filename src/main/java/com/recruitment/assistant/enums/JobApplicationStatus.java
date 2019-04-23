package com.recruitment.assistant.enums;

public enum JobApplicationStatus {

    APPLIED("APPLIED"),
    INVITED("INVITED"),
    HIRED("HIRED"),
    REJECTED("REJECTED");

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    JobApplicationStatus(String sts) {
        this.status = sts;
    }
}
