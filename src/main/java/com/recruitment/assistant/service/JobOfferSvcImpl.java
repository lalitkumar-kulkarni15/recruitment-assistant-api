package com.recruitment.assistant.service;

import com.recruitment.assistant.entity.JobApplicationEntity;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.repository.IJobOfferRepository;
import com.recruitment.assistant.entity.JobOfferEntity;
import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobOfferSvcImpl implements IJobOfferSvc {

    @Autowired
    private IJobOfferRepository jobOfferRepository;

    /**
     * This method accepts the job offer input object {@link JobOffer} which houses all the
     * required job offer properties that needs to be persisted in the repository store.
     *
     * @param jobOffer : This object contains all the job offer related input fields
     *                   that needs to be persisted to the repository store.
     * @return long    : This method returns the job id which is generated in the repository store
     *                   when the job offer is persisted.
     * @throws RecAsstntTechnicalException : This exception is thrown if there is any exception
     *                   occurred while persisting the job offer repository in the datastore.
     */
    @Override
    public JobOffer createNewJobOffer(final JobOffer jobOffer) throws RecAsstntTechnicalException {

        JobOfferEntity jobOfferEntity = new JobOfferEntity();
        
        try {
        	BeanUtils.copyProperties(jobOffer,jobOfferEntity);
            final JobOfferEntity jobOfferEntityPersisted = this.jobOfferRepository.save(jobOfferEntity);
            JobOffer jobOfferSaved = new JobOffer();
            BeanUtils.copyProperties(jobOfferEntityPersisted,jobOfferSaved);
            return jobOfferSaved;

        } catch(DataIntegrityViolationException dataIntVioEx){
            throw new DataIntegrityViolationException("Duplicate job title inserted");
        }

        catch(final Exception exception) {
        	throw new RecAsstntTechnicalException("Exception while persisting the job offer entity to " +
                    "the database",exception);
        }
        
    }

    /**
     * This method fetches all the job offers present in the repository store.
     *
     * @return Returns list of job offers that are present inside the repository store.
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
                        jbOfrEntity.getJobOfferStatus(),
                                jbOfrEntity.getNoOfApplication())
        ).collect(Collectors.toList());

        return jobOfferDtoList;
    }

    /**
     * This method fetches the job offer by accepting the job id field as an input.
     *
     * @param  jobId :                 This is the unique id of the job offer.
     * @return {@link JobOffer} :      This is the job offer object which
     *                                 contains all the required properties of the job offer.
     * @throws DataNotFoundException : This exception is thrown when the entities are not
     *                                 found in the repository store.
     */
    @Transactional(readOnly = true)
    @Override
    public JobOffer findJobOfferByJobId(long jobId)
            throws DataNotFoundException {

        Optional<JobOfferEntity> jobOfferEntity = this.jobOfferRepository.findById(jobId);

        JobApplication jobApplicationList = new JobApplication();
        Set<JobApplicationEntity> jobApplicationEntity = jobOfferEntity.get().getJobApplications();

        Set<JobApplication> jobApplicationDtoList = jobApplicationEntity.stream().map(
                jbAplEntity -> new JobApplication(jbAplEntity.getApplicationId(),
                        jbAplEntity.getCandidateEmail(),
                        jbAplEntity.getResumeTxt(),
                        jbAplEntity.getApplicationStatus())
                        ).collect(Collectors.toSet());

        JobOffer jobOffer = new JobOffer();
        BeanUtils.copyProperties(jobOfferEntity.get(),jobOffer);
        jobOffer.setJobApplications(jobApplicationDtoList);
        return jobOffer;
    }


    /**
     * This method finds the job offer in the repository store by the job title.
     *
     * @param jobTitle :               This is the title of the job offer, by which the
     *                                 job offer is to be searched in the repository store.
     * @return @{@link JobOffer}       This object contains all the fields related
     *                                 to the job offer.
     * @throws DataNotFoundException : This exception is thrown when the job offer
     *                                 entity is not found in the repository store for the mentioned job
     *                                 offer in the input.
     */
    @Override
    public JobOffer findJobOfferByJobTitle(final String jobTitle)
            throws DataNotFoundException {

        final JobOfferEntity jobOfferEntity = this.jobOfferRepository.findByJobTitle(jobTitle)
                .orElseThrow(() -> new DataNotFoundException("Job offer entity not " +
                        "found for the given job title :"+jobTitle));

        JobOffer jobOffer = new JobOffer();
        BeanUtils.copyProperties(jobOfferEntity,jobOffer);
        return jobOffer;

    }
}
