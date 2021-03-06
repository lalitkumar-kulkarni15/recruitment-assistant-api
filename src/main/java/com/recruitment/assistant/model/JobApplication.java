package com.recruitment.assistant.model;

import com.recruitment.assistant.entity.JobOfferEntity;
import com.recruitment.assistant.enums.JobApplicationStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class JobApplication {

    @NotNull(message = "Job id field cannot be null in the input request.")
    private long jobId;

    /**
     *  This is the unique application id of the job application.
     */
    @NotNull(message = "App id field cannot be null in the input request.")
    private long applicationId;

    /**
     *  This is the unique email id of the candidate who is applying
     *  for the job offer.
     */
    @NotNull(message = "Email id field cannot be null in the input request.")
    @Email(message= "Please send the email id in appropriate email format.")
    private String candidateEmail;

    /**
     * This is the resume text of the candidate applying for the job offer.
     */
    @NotNull(message = "Resume text field cannot be null in the input request.")
    private String resumeTxt;

    /**
     * This is the status of the candidate job application. Its possible values are :-
     * a) APPLIED
     * b) INVITED
     * c) REJECTED
     * D) HIRED.
     *
     */
    @NotNull(message = "Job application status field cannot be null in the input request.")
    private JobApplicationStatus applicationStatus;

    /**
     * This is the job offer for which the candidate has submitted the job application.
     */
    private JobOffer relatedJobOffer;

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    public String getResumeTxt() {
        return resumeTxt;
    }

    public void setResumeTxt(String resumeTxt) {
        this.resumeTxt = resumeTxt;
    }

    public JobApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(JobApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;

    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public JobOffer getRelatedJobOffer() {
        return relatedJobOffer;
    }

    public void setRelatedJobOffer(JobOffer relatedJobOffer) {
        this.relatedJobOffer = relatedJobOffer;
    }

    public JobApplication(long applicationId, String candidateEmail, String resumeTxt,
                                JobApplicationStatus applicationStatus,
                                JobOffer relatedJobOffer) {

        this.applicationId = applicationId;
        this.candidateEmail = candidateEmail;
        this.resumeTxt = resumeTxt;
        this.applicationStatus = applicationStatus;
        this.relatedJobOffer = relatedJobOffer;
    }

    public JobApplication(long applicationId, String candidateEmail, String resumeTxt, JobApplicationStatus applicationStatus) {
        this.jobId = jobId;
        this.applicationId = applicationId;
        this.candidateEmail = candidateEmail;
        this.resumeTxt = resumeTxt;
        this.applicationStatus = applicationStatus;
    }

    public JobApplication(long jobId,String candidateEmail,JobApplicationStatus status,String resumeTxt){
        this.jobId = jobId;
        this.candidateEmail=candidateEmail;
        this.resumeTxt=resumeTxt;
        this.applicationStatus=status;

    }

    public JobApplication() {
    }

}
