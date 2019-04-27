package com.recruitment.assistant.service;

import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.NotificationException;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobApplication;

import javax.transaction.Transactional;
import java.util.List;

public interface IJobApplicatnSvc {

    long submitJobApplicatn(final JobApplication jobApplication) throws DataNotFoundException, RecAsstntTechnicalException;

    JobApplication getAllApps(Long appId) throws DataNotFoundException;

    JobApplication updateJobApplnStatus(JobApplicationStatus applnStatus, long appId)
            throws DataNotFoundException, RecAsstntTechnicalException;

    public List<JobApplication> getApplicationsByJobId(long jobId) throws RecAsstntTechnicalException;
}
