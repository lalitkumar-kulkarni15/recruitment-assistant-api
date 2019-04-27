package com.recruitment.assistant.model;

import com.recruitment.assistant.entity.JobApplicationEntity;
import com.recruitment.assistant.enums.JobOfferStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This model class contains the all the properties required for the job offer.
 *
 * @since   19-04-2019
 * @author  lalit kulkarni
 * @version 1.0
 */
public class JobOffer {

    // This is the unique job id for uniquely identifying the job offer at the backend.
    private long jobId;

    // This the title of the job offer which is unique.
    @NotNull(message = "Job title field cannot be null in the input request.")
    @Size(max = 50,message = "Job title should not be more than 50 characters in length.")
    private String jobTitle;

    // This field contains the description of the job offer.
    @NotNull(message = "Job description field cannot be null in the input request.")
    @Size(max = 500,message = "Job description field should not be more than 500 characters in length.")
    private String jobDesc;

    // This field holds the name of the concerned person for the mentioned job offer.
    @NotNull(message = "Contact person field cannot be null in the input request.")
    @Size(max = 50,message = "Contact person field should not be more than 50 characters in length.")
    private String contactPerson;

    // This is the creation date of the job offer in the system.
    @NotNull(message = "Created date field cannot be null in the input request.")
    private LocalDate createdDate;

    // This is the date when the job offer is modified in the system.
    @NotNull(message = "Modified date field cannot be null in the input request.")
    private LocalDate modifiedDate;

    /** This is the status of the job offer in the backend system.
        The probable values are 'A' - Active and 'I' - Inactive. */
    private JobOfferStatus jobOfferStatus;

    public JobOffer(long jobId, String jobtitle, String jobDesc, String contactPerson, LocalDate createdDate, LocalDate modifiedDate, JobOfferStatus jobOfferStatus) {
    }

    public int getNoOfApplication() {
        return noOfApplication;
    }

    public void setNoOfApplication(int noOfApplication) {
        this.noOfApplication = noOfApplication;
    }

    private int noOfApplication;

    public Set<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(Set<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    private Set<JobApplication> jobApplications = new HashSet<>();

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public long getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public JobOfferStatus getJobOfferStatus() {
        return jobOfferStatus;
    }

    public void setJobOfferStatus(JobOfferStatus jobOfferStatus) {
        this.jobOfferStatus = jobOfferStatus;
    }



    public JobOffer(long jobId, String jobTitle, String jobDesc, String contactPerson,
                    LocalDate createdDate, LocalDate modifiedDate, JobOfferStatus jobOfferStatus,int noOfApplication) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobDesc = jobDesc;
        this.contactPerson = contactPerson;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.jobOfferStatus = jobOfferStatus;
        this.noOfApplication= noOfApplication;
    }

    public JobOffer(long jobId, String jobTitle, String contactPerson, LocalDate createdDate) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.contactPerson = contactPerson;
        this.createdDate = createdDate;
    }

    public JobOffer() {
    }

}
