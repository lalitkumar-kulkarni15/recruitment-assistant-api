package com.recruitment.assistant.repository;

import com.recruitment.assistant.entity.JobOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IJobOfferRepository extends JpaRepository<JobOfferEntity,Long>{

    public Optional<JobOfferEntity> findByJobTitle(String jobTitle);


}
