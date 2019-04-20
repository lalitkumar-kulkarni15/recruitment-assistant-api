package com.recruitment.assistant.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;
import com.recruitment.assistant.service.IJobOfferSvc;

@RestController
@RequestMapping("/job-offer/v1")
public class JobOfferController {
	
	@Autowired
	private IJobOfferSvc jobOfferSvc;

	@PostMapping(path = "/newJobOffer/v1", consumes = "application/json")
	public long createNewJobOffer(@RequestBody JobOffer jobOffer) throws RecAsstntTechnicalException {
	
		return this.jobOfferSvc.createNewJobOffer(jobOffer);
  }



}
