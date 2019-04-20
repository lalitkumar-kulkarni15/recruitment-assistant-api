package com.recruitment.assistant.service;

import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;

public interface IJobOfferSvc {

    public long createNewJobOffer(final JobOffer jobOffer) throws RecAsstntTechnicalException;
}
