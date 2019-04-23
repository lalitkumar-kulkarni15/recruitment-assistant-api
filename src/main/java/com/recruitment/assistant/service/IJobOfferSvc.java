package com.recruitment.assistant.service;

import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;

import java.util.List;

public interface IJobOfferSvc {

    JobOffer createNewJobOffer(final JobOffer jobOffer) throws RecAsstntTechnicalException;

    List<JobOffer> getAllJobOffers();

    JobOffer findJobOfferByJobId(long jobId) throws DataNotFoundException;

    JobOffer findJobOfferByJobTitle(String jobTitle) throws DataNotFoundException;
}
