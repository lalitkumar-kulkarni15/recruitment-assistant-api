package com.recruitment.assistant.service;

import com.recruitment.assistant.data.IJobOfferRepository;
import com.recruitment.assistant.entity.JobOfferEntity;
import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobOfferSvcImpl implements IJobOfferSvc {

    @Autowired
    private IJobOfferRepository jobOfferRepository;

    /**
     * This method accepts the job offer input object {@link JobOffer} which houses all the
     * required job offer properties that needs to be persisted in the data store.
     *
     * @param jobOffer : This object contains all the job offer related input fields
     *                   that needs to be persisted to the data store.
     * @return long    : This method returns the job id which is generated in the data store
     *                   when the job offer is persisted.
     * @throws RecAsstntTechnicalException : This exception is thrown if there is any exception
     *                   occurred while persisting the job offer data in the datastore.
     */
    @Override
    public long createNewJobOffer(final JobOffer jobOffer) throws RecAsstntTechnicalException {

        JobOfferEntity jobOfferEntity = new JobOfferEntity();
        
        try {
        	BeanUtils.copyProperties(jobOffer,jobOfferEntity);
            final JobOfferEntity jobOfferEntityPersisted = this.jobOfferRepository.save(jobOfferEntity);
            return jobOfferEntityPersisted.getJobId();
        } catch(final Exception exception) {
        	throw new RecAsstntTechnicalException("Exception while persisting the job offer entity to " +
                    "the database",exception);
        }
        
    }

    /**
     * This method fetches all the job offers present in the data store.
     *
     * @return Returns list of job offers that are present inside the data store.
     *         {@link List<JobOffer>}
     */
    @Override
    public List<JobOffer> getAllJobOffers(){

        List<JobOfferEntity> jobOffers = this.jobOfferRepository.findAll();
        List<JobOffer> jobOfferDtoList = jobOffers.stream().map(
                        jbOfrEntity -> new JobOffer(jbOfrEntity.getJobId(),
                        jbOfrEntity.getJobTitle(),
                        jbOfrEntity.getJobDesc(),
                        jbOfrEntity.getContactPerson(),
                        jbOfrEntity.getCreatedDate(),
                        jbOfrEntity.getModifiedDate(),
                        jbOfrEntity.getJobOfferStatus())
        ).collect(Collectors.toList());

        return jobOfferDtoList;
    }

    /**
     * This method fetches the job offer by accepting the job id field as an input.
     *
     * @param  jobId : This is the unique id of the job offer.
     * @return {@link JobOffer} : This is the job offer object which
     *         contains all the required properties of the job offer.
     * @throws DataNotFoundException : This exception is thrown when the entities are not
     *         found in the data store.
     */
    @Override
    public JobOffer findJobOfferByJobId(long jobId)
            throws DataNotFoundException {

        JobOfferEntity jobOfferEntity = this.jobOfferRepository.findById(jobId)
                .orElseThrow(() -> new DataNotFoundException("Job Offer not found for" +
                        " the given job Id :"+jobId));
        JobOffer jobOffer = new JobOffer();
        BeanUtils.copyProperties(jobOfferEntity,jobOffer);
        return jobOffer;
    }

    @Override
    public JobOffer findJobOfferByJobTitle(final String jobTitle)
            throws DataNotFoundException {

        JobOfferEntity jobOfferEntity = this.jobOfferRepository.findByJobTitle(jobTitle)
                .orElseThrow(() -> new DataNotFoundException("Job offer entity not " +
                        "found for the given job title :"+jobTitle));

        JobOffer jobOffer = new JobOffer();
        BeanUtils.copyProperties(jobOfferEntity,jobOffer);
        return jobOffer;

    }
}
