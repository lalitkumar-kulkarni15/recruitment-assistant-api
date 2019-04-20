package com.recruitment.assistant.model;

public class JobCreationResp {

    private long jobId;

    private String message;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JobCreationResp(long jobId, String message) {
        this.jobId = jobId;
        this.message = message;
    }
}
