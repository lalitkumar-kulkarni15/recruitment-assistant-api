package com.recruitment.assistant.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="JOB_OFFER")
public class JobOfferEntity {

    public JobOfferEntity() {
    }

    public JobOfferEntity(long jobId, String jobTitle, String jobDesc, String contactPerson,
    		LocalDate createdDate, LocalDate modifiedDate, char jobOfferStatus) {

        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobDesc = jobDesc;
        this.contactPerson = contactPerson;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.jobOfferStatus = jobOfferStatus;
    }

    /** This is the unique job id for uniquely identifying the job offer at
     * the backend.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Job_id")
    private long jobId;

    /**
     * This the title of the job offer which is unique.
     */
    @Column(name = "job_title",unique = true,length = 100)
    private String jobTitle;

    /**
     * This field contains the description of the job offer.
     */
    @Column(name = "job_desc")
    private String jobDesc;

    /**
     * This field holds the name of the concerned person for the
     * mentioned job offer.
     */
    @Column(name = "cnt_persn")
    private String contactPerson;

    /**
     * This is the creation date of the job offer in the system.
     */
    @Column(name = "crtd_dt")
    private LocalDate createdDate;

    /**
     * This is the date when the job offer is modified in the system.
     */
    @Column(name = "mod_dt")
    private LocalDate modifiedDate;

    /** This is the status of the job offer in the backend system.
     The probable values are 'A' - Active and 'I' - Inactive. */
    @Column(name = "status")
    private char jobOfferStatus;

    /**
     *  This is the list of job applications corresponding to this job offer.
     */
    @OneToMany
    @JoinColumn(name = "Job_id",nullable = true)
    private List<JobApplicationEntity> jobApplications = new ArrayList<>();

    public List<JobApplicationEntity> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(List<JobApplicationEntity> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public char getJobOfferStatus() {
        return jobOfferStatus;
    }

    public void setJobOfferStatus(char jobOfferStatus) {
        this.jobOfferStatus = jobOfferStatus;
    }
}
