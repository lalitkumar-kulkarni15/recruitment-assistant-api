package com.recruitment.assistant.endpoints;

import com.recruitment.assistant.exception.DataNotFoundException;
import com.recruitment.assistant.model.JobCreationResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.recruitment.assistant.exception.RecAsstntTechnicalException;
import com.recruitment.assistant.model.JobOffer;
import com.recruitment.assistant.service.IJobOfferSvc;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/job-offer/v1")
public class JobOfferController {
	
	@Autowired
	private IJobOfferSvc jobOfferSvc;

	@PostMapping(path = "/newJobOffer/v1", consumes = "application/json")
	public ResponseEntity<JobOffer> createNewJobOffer(@RequestBody JobOffer jobOffer)
			throws RecAsstntTechnicalException {
	
		final JobOffer jobOfferSaved = this.jobOfferSvc.createNewJobOffer(jobOffer);

		final URI uri =
				MvcUriComponentsBuilder.fromController(getClass())
						.path("/{id}")
						.buildAndExpand(jobOfferSaved.getJobId())
						.toUri();

		return ResponseEntity.created(uri).body(jobOfferSaved);
  }

  	@GetMapping(value = "/getAllJobOffers/v1",consumes = "application/json")
  	public ResponseEntity<List<JobOffer>> getAllJobOffers(){

		final List<JobOffer> jobOffers = this.jobOfferSvc.getAllJobOffers();
		return ResponseEntity.ok().body(jobOffers);
	}

	@GetMapping("/getJobOfferByJobId/v1/{jobId}")
	public ResponseEntity<JobOffer> getJobOfferByJobId(@PathVariable long jobId)
			throws DataNotFoundException {

		JobOffer jobOffer = this.jobOfferSvc.findJobOfferByJobId(jobId);
		return ResponseEntity.ok().body(jobOffer);
	}

	@GetMapping("/getJobOfferByJobTitle/v1/{jobTitle}")
	public ResponseEntity<JobOffer> getJobOfferByJobTitle(@PathVariable String jobTitle)
			throws DataNotFoundException {

		JobOffer jobOffer = this.jobOfferSvc.findJobOfferByJobTitle(jobTitle);
		return ResponseEntity.ok().body(jobOffer);
	}

}
