package com.recruitment.assistant.data;

import com.recruitment.assistant.entity.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobApplicationRepository extends
        JpaRepository<JobApplicationEntity,Long> {
}
