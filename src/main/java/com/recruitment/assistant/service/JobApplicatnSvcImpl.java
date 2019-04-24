package com.recruitment.assistant.service;

import com.recruitment.assistant.data.IJobApplicationRepository;
import com.recruitment.assistant.data.IJobOfferRepository;
import com.recruitment.assistant.entity.JobApplicationEntity;
import com.recruitment.assistant.entity.JobOfferEntity;
import com.recruitment.assistant.enums.JobApplicationStatus;
import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.exception.NotificationException;
import com.recruitment.assistant.model.JobApplication;
import com.recruitment.assistant.model.JobOffer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class JobApplicatnSvcImpl implements IJobApplicatnSvc {

    @Autowired
    private IJobApplicationRepository jobApplnRepo;

    @Autowired
    private IJobOfferRepository jobOfferRepo;

    @Autowired
    private INotifClientSvc notificationSvc;

    @Override
    public long submitJobApplicatn(final JobApplication jobApplication) throws DataNotFoundException {

        JobApplicationEntity jobApplicatnEntity = new JobApplicationEntity();
        BeanUtils.copyProperties(jobApplication, jobApplicatnEntity);

        JobOfferEntity jobOfferEntity = this.jobOfferRepo.findById(jobApplication.getJobId())
                .orElseThrow(() -> new DataNotFoundException
                        ("Job offer not found for the55555555 given job id : " + jobApplication.getJobId()));

        jobApplicatnEntity.setJobOffer(jobOfferEntity);
        long appId = this.jobApplnRepo.save(jobApplicatnEntity).getApplicationId();
       /* JobApplicationEntity jobApplicatnEntitySaved = this.jobApplnRepo.save(jobApplicatnEntity);
        JobApplication jobApplicatnSaved = new JobApplication();
        BeanUtils.copyProperties(jobApplicatnEntitySaved,jobApplicatnSaved);
        JobOffer relatedJobOffer = new JobOffer();
        relatedJobOffer.setJobTitle(jobOfferEntity.getJobTitle());
        relatedJobOffer.setJobId(jobOfferEntity.getJobId());
        relatedJobOffer.setContactPerson(jobOfferEntity.getContactPerson());
        jobApplicatnSaved.setRelatedJobOffer(relatedJobOffer);*/
        return appId;

    }

    /*private JobApplication fetchAppIdOfPersistedAppl(List<JobApplicationEntity> jobApplList){

        long count = jobApplList.size();
        Stream<JobApplicationEntity> stream = jobApplList.stream();
        JobApplicationEntity jobApplnEntityNewlyAdded = stream.skip(count - 1).findFirst().get();
        JobApplication jobAppln = new JobApplication();
        BeanUtils.copyProperties(jobApplnEntityNewlyAdded,jobAppln);
        return jobAppln;
    }*/

        public JobApplication getAllApps(Long appId) throws DataNotFoundException {

            JobApplicationEntity jobApplicationEntity = this.jobApplnRepo.findJobApplicationByOfferId(appId)
                    .orElseThrow(() -> new DateTimeException(""));
            JobApplication jobAppli = new JobApplication();
            BeanUtils.copyProperties(jobApplicationEntity,jobAppli);
            long jobId = jobApplicationEntity.getJobOffer().getJobId();
            String contactPerson = jobApplicationEntity.getJobOffer().getContactPerson();
            LocalDate createdDate = jobApplicationEntity.getJobOffer().getCreatedDate();
            String jobTitle = jobApplicationEntity.getJobOffer().getJobTitle();

            JobOffer jobOffer = new JobOffer(jobId,jobTitle,contactPerson,createdDate);
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

            try {
                notificationSvc.processNotification(updatedJobApplication);
            } catch(NotificationException notifException){
                // Log this exception.
            }

            return updatedJobApplication;
        }

        public List<JobApplication> getApplicationsByJobId(long jobId){

            List<JobApplicationEntity> jobApplicationList  = this.jobApplnRepo.findJobApplicationByOferId(jobId);
            List<JobApplication> jobApplicationDtoList = jobApplicationList.stream().map(
                    jbOfrEntity -> new JobApplication(jbOfrEntity.getApplicationId(),
                            jbOfrEntity.getCandidateEmail(),jbOfrEntity.getResumeTxt(),
                            jbOfrEntity.getApplicationStatus())
                    ).collect(Collectors.toList());
            return jobApplicationDtoList;
        }



    }
