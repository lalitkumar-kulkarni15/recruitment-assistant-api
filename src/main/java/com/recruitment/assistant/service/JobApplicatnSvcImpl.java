package com.recruitment.assistant.service;

import com.recruitment.assistant.entity.JobApplicationEntity;
import com.recruitment.assistant.entity.JobOfferEntity;
import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.NotificationException;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.model.JobOffer;
import com.recruitment.assistant.repository.IJobApplicationRepository;
import com.recruitment.assistant.repository.IJobOfferRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DateTimeException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobApplicatnSvcImpl implements IJobApplicatnSvc {

    @Autowired
    private IJobApplicationRepository jobApplnRepo;

    @Autowired
    private IJobOfferRepository jobOfferRepo;

    @Autowired
    private INotifClientSvc notificationSvc;

    @Override
    public long submitJobApplicatn(final JobApplication jobApplication) throws DataNotFoundException,RecAsstntTechnicalException {

        Optional<JobApplication> jobApplicationOp =  Optional.ofNullable(jobApplication);
        if(jobApplicationOp.isPresent()) {
            JobApplicationEntity jobApplicatnEntity = new JobApplicationEntity();
            BeanUtils.copyProperties(jobApplication, jobApplicatnEntity);

            JobOfferEntity jobOfferEntity = this.jobOfferRepo.findById(jobApplication.getJobId())
                    .orElseThrow(() -> new DataNotFoundException
                            ("Job offer not found for the given job id : " + jobApplication.getJobId()));

            jobApplicatnEntity.setJobOffer(jobOfferEntity);
            long appId = this.jobApplnRepo.save(jobApplicatnEntity).getApplicationId();
            return appId;
        } else {
            throw new RecAsstntTechnicalException("JobApplication object is null");
        }

    }

        public JobApplication getAllApps(Long appId) throws DataNotFoundException {

            JobApplicationEntity jobApplicationEntity = this.jobApplnRepo.findJobApplicationByOfferId(appId)
                    .orElseThrow(() -> new DateTimeException(""));
            JobApplication jobAppli = new JobApplication();
            BeanUtils.copyProperties(jobApplicationEntity,jobAppli);

            JobOffer jobOffer = new JobOffer(jobApplicationEntity.getJobOffer().getJobId(),
                    jobApplicationEntity.getJobOffer().getJobTitle(),
                    jobApplicationEntity.getJobOffer().getJobDesc(),
                    jobApplicationEntity.getJobOffer().getContactPerson(),
                    jobApplicationEntity.getJobOffer().getCreatedDate(),
                    jobApplicationEntity.getJobOffer().getModifiedDate(),
                    jobApplicationEntity.getJobOffer().getJobOfferStatus(),
                    jobApplicationEntity.getJobOffer().getNoOfApplication()
                    );

            jobAppli.setRelatedJobOffer(jobOffer);
            return jobAppli;
        }

        @Override
        public JobApplication updateJobApplnStatus(JobApplicationStatus applnStatus,long appId)
            throws DataNotFoundException {

            JobApplicationEntity jobAppToBeUpdtd = this.jobApplnRepo.findById(appId).orElseThrow(
                    () -> new DataNotFoundException(""));

            jobAppToBeUpdtd.setApplicationStatus(applnStatus);
            JobApplicationEntity updtdJobEntity = this.jobApplnRepo.save(jobAppToBeUpdtd);
            JobApplication updatedJobApplication = new JobApplication();
            BeanUtils.copyProperties(updtdJobEntity,updatedJobApplication);

            JobOffer jobOffer = new JobOffer(updtdJobEntity.getJobOffer().getJobId(),
                    updtdJobEntity.getJobOffer().getJobTitle(),updtdJobEntity.getJobOffer().getJobDesc(),
                    updtdJobEntity.getJobOffer().getContactPerson(),updtdJobEntity.getJobOffer().getCreatedDate(),
                    updtdJobEntity.getJobOffer().getModifiedDate(),updtdJobEntity.getJobOffer().getJobOfferStatus(),
                    updtdJobEntity.getJobOffer().getNoOfApplication());


            updatedJobApplication.setRelatedJobOffer(jobOffer);

            try {
                notificationSvc.processNotification(updatedJobApplication);
            } catch(NotificationException notifException){
                // Log this exception.
            }

            return updatedJobApplication;
        }

        public List<JobApplication> getApplicationsByJobId(long jobId){

            List<JobApplicationEntity> jobApplicationList  = this.jobApplnRepo.findJobApplicationByOferId(jobId);

            List<JobApplication> jobOfferDtoList = jobApplicationList.stream().map(
                    jbAppEntity -> new JobApplication(jbAppEntity.getApplicationId(),
                            jbAppEntity.getCandidateEmail(),
                            jbAppEntity.getResumeTxt(),
                            jbAppEntity.getApplicationStatus(),
                            new JobOffer(jbAppEntity.getJobOffer().getJobId(),
                                    jbAppEntity.getJobOffer().getJobTitle(),
                                    jbAppEntity.getJobOffer().getJobDesc(),
                                    jbAppEntity.getJobOffer().getContactPerson(),
                                    jbAppEntity.getJobOffer().getCreatedDate(),
                                    jbAppEntity.getJobOffer().getModifiedDate(),
                                    jbAppEntity.getJobOffer().getJobOfferStatus(),
                                    jbAppEntity.getJobOffer().getNoOfApplication()
                                   )
                            )
            ).collect(Collectors.toList());

            return jobOfferDtoList;
        }
    }
