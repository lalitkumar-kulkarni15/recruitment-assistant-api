package com.recruitment.assistant.data;

import com.recruitment.assistant.entity.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface IJobApplicationRepository extends
        JpaRepository<JobApplicationEntity,Long> {

   /* @Query("SELECT a FROM JOB_APPLICATION a WHERE JOB_OFFER_ID = ?1")
    List<JobApplicationEntity> findJobApplicationByOfferId(Long offerId);*/

    @Query("SELECT a FROM JOB_APPLICATION a WHERE app_id = ?1")
    Optional<JobApplicationEntity> findJobApplicationByOfferId(Long appId);


}
