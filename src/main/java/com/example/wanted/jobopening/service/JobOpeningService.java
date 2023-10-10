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

	// 채용 공고 등록 기능
	@Transactional
	public JobOpeningResponse register(JobOpeningRegister jobOpeningRegister) {
		// 회사 조회
		Company company = companyRepository.findById(jobOpeningRegister.getCompanyId())
				.orElseThrow(() -> new ApiException(CompanyExceptionCode.NOT_EXIST_COMPANY));

		// 채용 공고 데이터 생성 및 저장
		JobOpening jobOpening = JobOpening.builder()
				.company(company)
				.workPlace(jobOpeningRegister.getWorkPlace())
				.position(jobOpeningRegister.getPosition())
				.reward(jobOpeningRegister.getReward())
				.content(jobOpeningRegister.getContent())
				.build();

		jobOpeningRepository.save(jobOpening);

		// usingStack 데이터 생성 및 jopOpening 데이터와 매핑
		modifyJopOpeningWithUsingStack(jobOpening, jobOpeningRegister);

		return JobOpeningResponse.from(jobOpening);
	}

	// 채용 공고 수정 기능
	@Transactional
	public JobOpeningResponse modify(Long jopOpeningId, JobOpeningModify jobOpeningModify) {
		// 채용공고 조회
		JobOpening jobOpening = jobOpeningRepository.findById(jopOpeningId)
				.orElseThrow(() -> new ApiException(
						NOT_EXIST_JOB_OPENING));

		// 해당 채용공고와 매핑된 기존 usingStack 데이터 삭제
		usingStackRepository.deleteByJobOpeningId(jopOpeningId);

		// usingStack 데이터 생성 및 jopOpening 데이터와 매핑
		modifyJopOpeningWithUsingStack(jobOpening, jobOpeningModify);

		return JobOpeningResponse.from(jobOpening);
	}

	// 채용 공고 삭제 기능
	@Transactional
	public void delete(Long jopOpeningId) {
		// 해당 채용공고와 매핑된 usingStack 데이터 삭제
		usingStackRepository.deleteByJobOpeningId(jopOpeningId);
		// 채용공고 데이터 삭제
		jobOpeningRepository.deleteById(jopOpeningId);
	}

	// 채용 공고 목록 조회 기능
	// searchText가 null이면, 모든 채용 공고 목록 조회
	// searchText가 null이 아니면, searchText가 회사명, 포지션, 사용 기술에 포함된 채용 공고 목록 조회
	public Page<JobOpeningOutline> getJopOpenings(String searchText, Pageable pageable) {
		// searchText에 따른 채용공고 데이터 조회
		Page<JobOpening> jopOpenings = jobOpeningRepository.searchJopOpeningsBySearchText(
				searchText, pageable);

		return jopOpenings.map(JobOpeningOutline::from);
	}

	// 특정 채용공고 상세 조회 기능
	public JobOpeningDetail getJopOpening(Long jopOpeningId) {
		// 특정 채용공고 데이터 조회
		JobOpening jobOpening = jobOpeningRepository.findById(jopOpeningId)
				.orElseThrow(() -> new ApiException(
						NOT_EXIST_JOB_OPENING));

		// 해당 채용공고를 등록한 회사의 다른 채용공고 목록 조회
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
