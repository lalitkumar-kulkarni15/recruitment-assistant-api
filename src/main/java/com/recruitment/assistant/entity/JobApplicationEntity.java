package com.recruitment.assistant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.model.JobOffer;

import javax.persistence.*;

@Entity(name = "JOB_APPLICATION")
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
    @Column(name = "cand_email",nullable = false)
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
    private JobApplicationStatus applicationStatus;

    public JobOfferEntity getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOfferEntity jobOffer) {
        this.jobOffer = jobOffer;
    }

    /**
     * This is the job offer for which the candidate has submitted the job application.
     */
    /*@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_OFFER",updatable = false)
    private JobOfferEntity relatedJobOffer;*/

    /*@JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "JOB_OFFER",updatable = false)
    private JobOfferEntity relatedJobOffer;*/

    /*@JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "JOB_OFFER_ID")
    private JobOfferEntity jobOffer;*/

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Job_id",updatable = false)
    private JobOfferEntity jobOffer;

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

    public JobApplicationEntity() {
    }
}
