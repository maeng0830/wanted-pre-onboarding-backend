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

	// 채용 공고 등록 API
	@PostMapping("/api/job-opening")
	public JobOpeningResponse register(@RequestBody JobOpeningRegister jobOpeningRequest) {
		return jobOpeningService.register(jobOpeningRequest);
	}

	// 채용 공고 수정 API
	@PatchMapping("/api/job-opening/{jobOpeningId}")
	public JobOpeningResponse modify(@PathVariable() Long jobOpeningId, @RequestBody
	JobOpeningModify jobOpeningModify) {
		return jobOpeningService.modify(jobOpeningId, jobOpeningModify);
	}

	// 채용 공고 삭제 API
	@DeleteMapping("/api/job-opening/{jobOpeningId}")
	public void delete(@PathVariable() Long jobOpeningId) {
		jobOpeningService.delete(jobOpeningId);
	}

	// 채용 공고 목록 조회 API
	// searchText 값이 null이면, 전체 채용 공고 목록 조회
	// searchText 값이 null이 아니면, searchText가 회사명, 포지션, 사용 기술에 포함된 채용 공고 목록을 조회
	@GetMapping("/api/job-opening")
	public Page<JobOpeningOutline> getJobOpenings(@RequestParam String searchText, Pageable pageable) {
		return jobOpeningService.getJopOpenings(searchText, pageable);
	}

	// 특정 채용 공고 상세 조회 API
	@GetMapping("/api/job-opening/{jobOpeningId}")
	public JobOpeningDetail getJobOpening(@PathVariable Long jobOpeningId) {
		return jobOpeningService.getJopOpening(jobOpeningId);
	}
}
