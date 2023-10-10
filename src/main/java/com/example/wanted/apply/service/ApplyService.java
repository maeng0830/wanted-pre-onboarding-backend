package com.example.wanted.apply.service;

import static com.example.wanted.apply.execption.ApplyExceptionCode.ALREADY_APPLY;
import static com.example.wanted.jobopening.exception.JobOpeningExceptionCode.NOT_EXIST_JOB_OPENING;
import static com.example.wanted.member.exception.MemberExceptionCode.NOT_EXIST_MEMBER;

import com.example.wanted.apply.domain.Apply;
import com.example.wanted.apply.domain.model.ApplyDto;
import com.example.wanted.apply.repository.ApplyRepository;
import com.example.wanted.common.exception.ApiException;
import com.example.wanted.jobopening.domain.JobOpening;
import com.example.wanted.jobopening.repository.JobOpeningRepository;
import com.example.wanted.member.domain.Member;
import com.example.wanted.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplyService {

	private final ApplyRepository applyRepository;
	private final MemberRepository memberRepository;
	private final JobOpeningRepository jobOpeningRepository;

	// 채용 공고 지원 기능
	// 중복 지원 시 예외 발생
	public ApplyDto apply(ApplyDto applyDto) {
		// 중복 지원 체크
		applyRepository.findByMember_IdAndJobOpening_Id(applyDto.getMemberId(), applyDto.getJobOpeningId())
				.ifPresent(apply -> {
					throw new ApiException(ALREADY_APPLY);
				});

		// 회원 조회
		Member member = memberRepository.findById(applyDto.getMemberId())
				.orElseThrow(() -> new ApiException(NOT_EXIST_MEMBER));

		// 채용 공고 조회
		JobOpening jobOpening = jobOpeningRepository.findById(applyDto.getJobOpeningId())
				.orElseThrow(() -> new ApiException(NOT_EXIST_JOB_OPENING));

		// 채용 지원 데이터 생성 및 저장
		Apply apply = Apply.builder()
				.member(member)
				.jobOpening(jobOpening)
				.build();
		applyRepository.save(apply);

		return ApplyDto.from(apply);
	}
}
