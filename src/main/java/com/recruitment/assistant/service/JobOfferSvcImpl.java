package com.recruitment.assistant.service;

import com.recruitment.assistant.data.IJobOfferRepository;
import com.recruitment.assistant.entity.JobOfferEntity;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

@Service
public class JobOfferSvcImpl implements IJobOfferSvc {

    @Autowired
    private IJobOfferRepository jobOfferRepository;

    public long createNewJobOffer(final JobOffer jobOffer) throws RecAsstntTechnicalException {

        JobOfferEntity jobOfferEntity = new JobOfferEntity();
        
        try {
        	BeanUtils.copyProperties(jobOffer,jobOfferEntity);
            final JobOfferEntity jobOfferEntityPersisted = this.jobOfferRepository.save(jobOfferEntity);
            return jobOfferEntityPersisted.getJobId();
        } catch(final Exception exception) {
        	throw new RecAsstntTechnicalException("Exception while persisting the job offer entity to the database",exception);
        }
        
    }

}
