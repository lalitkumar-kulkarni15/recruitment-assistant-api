package com.recruitment.assistant.data;

import com.recruitment.assistant.entity.JobOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobOfferRepository extends JpaRepository<JobOfferEntity,Long>{

}
