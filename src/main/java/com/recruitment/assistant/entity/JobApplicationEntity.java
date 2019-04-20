package com.recruitment.assistant.entity;

import javax.persistence.*;

@Entity
@Table(name = "JOB_APPLICATION")
public class JobApplicationEntity {

    /**
     *  This is the unique application id of the job application.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private long applicationId;

    /**
     *  This is the unique email id of the candidate who is applying
     *  for the job offer.
     */
    @Column(name = "cand_email",unique = true,nullable = false)
    private String candidateEmail;

    /**
     * This is the resume text of the candidate applying for the job offer.
     */
    @Column(name = "resume_txt",nullable = false)
    private String resumeTxt;

    /**
     * This is the status of the candidate job application. Its possible values are :-
     * a) APPLIED
     * b) INVITED
     * c) REJECTED
     * D) HIRED.
     *
     */
    @Column(name="app_sts",nullable = false)
    private String applicationStatus;

    /**
     * This is the job offer for which the candidate has submitted the job application.
     */
    @ManyToOne()
    @JoinColumn(name = "Job_id")
    private JobOfferEntity relatedJobOffer;

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

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public JobOfferEntity getRelatedJobOffer() {
        return relatedJobOffer;
    }

    public void setRelatedJobOffer(JobOfferEntity relatedJobOffer) {
        this.relatedJobOffer = relatedJobOffer;
    }

    public JobApplicationEntity(long applicationId, String candidateEmail, String resumeTxt, String applicationStatus, JobOfferEntity relatedJobOffer) {
        this.applicationId = applicationId;
        this.candidateEmail = candidateEmail;
        this.resumeTxt = resumeTxt;
        this.applicationStatus = applicationStatus;
        this.relatedJobOffer = relatedJobOffer;
    }

    public JobApplicationEntity() {
    }
}
