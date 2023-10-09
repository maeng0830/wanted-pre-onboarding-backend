package com.example.wanted.jobopening.controller;

import com.example.wanted.jobopening.domain.model.dto.request.JobOpeningModify;
import com.example.wanted.jobopening.domain.model.dto.response.JobOpeningDetail;
import com.example.wanted.jobopening.domain.model.dto.response.JobOpeningOutline;
import com.example.wanted.jobopening.domain.model.dto.request.JobOpeningRegister;
import com.example.wanted.jobopening.domain.model.dto.response.JobOpeningResponse;
import com.example.wanted.jobopening.service.JobOpeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JobOpeningController {

	private final JobOpeningService jobOpeningService;

	@PostMapping("/api/job-opening")
	public JobOpeningResponse register(@RequestBody JobOpeningRegister jobOpeningRequest) {
		return jobOpeningService.register(jobOpeningRequest);
	}

	@PatchMapping("/api/job-opening/{jobOpeningId}")
	public JobOpeningResponse modify(@PathVariable() Long jobOpeningId, @RequestBody
	JobOpeningModify jobOpeningModify) {
		return jobOpeningService.modify(jobOpeningId, jobOpeningModify);
	}

	@DeleteMapping("/api/job-opening/{jobOpeningId}")
	public void delete(@PathVariable() Long jobOpeningId) {
		jobOpeningService.delete(jobOpeningId);
	}

	@GetMapping("/api/job-opening")
	public Page<JobOpeningOutline> getJobOpenings(@RequestParam String searchText, Pageable pageable) {
		return jobOpeningService.getJopOpenings(searchText, pageable);
	}

	@GetMapping("/api/job-opening/{jobOpeningId}")
	public JobOpeningDetail getJobOpening(@PathVariable Long jobOpeningId) {
		return jobOpeningService.getJopOpening(jobOpeningId);
	}
}
