package com.recruitment.assistant.model;

import com.recruitment.assistant.enums.JobApplnStatus;

import java.time.LocalDate;

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
    private String jobTitle;

    // This field contains the description of the job offer.
    private String jobDesc;

    // This field holds the name of the concerned person for the mentioned job offer.
    private String contactPerson;

    // This is the creation date of the job offer in the system.
    private LocalDate createdDate;

    // This is the date when the job offer is modified in the system.
    private LocalDate modifiedDate;

    /** This is the status of the job offer in the backend system.
        The probable values are 'A' - Active and 'I' - Inactive. */
    private JobApplnStatus jobOfferStatus;

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

    public JobApplnStatus getJobOfferStatus() {
        return jobOfferStatus;
    }

    public void setJobOfferStatus(JobApplnStatus jobOfferStatus) {
        this.jobOfferStatus = jobOfferStatus;
    }



    public JobOffer(long jobId, String jobTitle, String jobDesc, String contactPerson,
                    LocalDate createdDate, LocalDate modifiedDate, JobApplnStatus jobOfferStatus) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobDesc = jobDesc;
        this.contactPerson = contactPerson;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.jobOfferStatus = jobOfferStatus;
    }

    @Override
    public String toString() {
        return "JobOffer{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", jobOfferStatus=" + jobOfferStatus +
                '}';
    }

}
