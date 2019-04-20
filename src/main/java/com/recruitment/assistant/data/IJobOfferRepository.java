package com.recruitment.assistant.data;

import com.recruitment.assistant.entity.JobOfferEntity;
import com.recruitment.assistant.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IJobOfferRepository extends JpaRepository<JobOfferEntity,Long>{

    public Optional<JobOfferEntity> findByJobTitle(String jobTitle);

}
