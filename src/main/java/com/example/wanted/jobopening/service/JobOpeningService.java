package com.example.wanted.jobopening.service;

import static com.example.wanted.jobopening.exception.JobOpeningExceptionCode.*;

import com.example.wanted.common.exception.ApiException;
import com.example.wanted.company.domain.Company;
import com.example.wanted.company.exception.CompanyExceptionCode;
import com.example.wanted.company.repository.CompanyRepository;
import com.example.wanted.jobopening.domain.JobOpening;
import com.example.wanted.jobopening.domain.UsingStack;
import com.example.wanted.jobopening.domain.model.dto.request.JobOpeningModify;
import com.example.wanted.jobopening.domain.model.dto.response.JobOpeningDetail;
import com.example.wanted.jobopening.domain.model.dto.response.JobOpeningOutline;
import com.example.wanted.jobopening.domain.model.dto.request.JobOpeningRegister;
import com.example.wanted.jobopening.domain.model.dto.request.JobOpeningRequest;
import com.example.wanted.jobopening.domain.model.dto.response.JobOpeningResponse;
import com.example.wanted.jobopening.repository.JobOpeningRepository;
import com.example.wanted.jobopening.repository.UsingStackRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JobOpeningService {

	private final JobOpeningRepository jobOpeningRepository;
	private final UsingStackRepository usingStackRepository;
	private final CompanyRepository companyRepository;

	@Transactional
	public JobOpeningResponse register(JobOpeningRegister jobOpeningRegister) {
		// 회사 조회
		Company company = companyRepository.findById(jobOpeningRegister.getCompanyId())
				.orElseThrow(() -> new ApiException(CompanyExceptionCode.NOT_EXIST_COMPANY));

		JobOpening jobOpening = JobOpening.builder()
				.company(company)
				.workPlace(jobOpeningRegister.getWorkPlace())
				.position(jobOpeningRegister.getPosition())
				.reward(jobOpeningRegister.getReward())
				.content(jobOpeningRegister.getContent())
				.build();

		jobOpeningRepository.save(jobOpening);

		// usingStack 데이터 생성을 포함한 jopOpening 데이터 수정
		modifyJopOpeningWithUsingStack(jobOpening, jobOpeningRegister);

		return JobOpeningResponse.from(jobOpening);
	}

	@Transactional
	public JobOpeningResponse modify(Long jopOpeningId, JobOpeningModify jobOpeningModify) {
		// 채용공고 조회
		JobOpening jobOpening = jobOpeningRepository.findById(jopOpeningId)
				.orElseThrow(() -> new ApiException(
						NOT_EXIST_JOB_OPENING));

		// 해당 채용공고의 usingStack 데이터 삭제
		usingStackRepository.deleteByJobOpeningId(jopOpeningId);

		// usingStack 데이터 생성을 포함한 jopOpening 데이터 수정
		modifyJopOpeningWithUsingStack(jobOpening, jobOpeningModify);

		return JobOpeningResponse.from(jobOpening);
	}

	@Transactional
	public void delete(Long jopOpeningId) {
		usingStackRepository.deleteByJobOpeningId(jopOpeningId);
		jobOpeningRepository.deleteById(jopOpeningId);
	}

	public Page<JobOpeningOutline> getJopOpenings(String searchText, Pageable pageable) {
		Page<JobOpening> jopOpenings = jobOpeningRepository.searchJopOpeningsBySearchText(
				searchText, pageable);

		return jopOpenings.map(JobOpeningOutline::from);
	}

	public JobOpeningDetail getJopOpening(Long jopOpeningId) {
		JobOpening jobOpening = jobOpeningRepository.findById(jopOpeningId)
				.orElseThrow(() -> new ApiException(
						NOT_EXIST_JOB_OPENING));

		List<JobOpening> otherJobOpenings = jobOpeningRepository.findByCompany_Id(jobOpening.getCompany()
				.getId());

		return JobOpeningDetail.from(jobOpening, otherJobOpenings);
	}

	private List<UsingStack> createUsingStacks(JobOpening jobOpening, List<String> stackNames) {
		List<UsingStack> usingStacks = new ArrayList<>();

		// 새로운 usingStack 데이터 생성
		for (int i = 0; i < stackNames.size(); i++) {
			UsingStack usingStack = UsingStack.builder()
					.jobOpening(jobOpening)
					.stackName(stackNames.get(i))
					.build();

			usingStackRepository.save(usingStack);

			usingStacks.add(usingStack);
		}

		return usingStacks;
	}

	private void modifyJopOpeningWithUsingStack(JobOpening jobOpening, JobOpeningRequest jobOpeningRequest) {
		List<UsingStack> usingStacks = createUsingStacks(jobOpening,
				jobOpeningRequest.getStackNames());

		jobOpening.modifyJopOpening(jobOpeningRequest, usingStacks);
	}
}
