package com.example.wanted.apply.service;

import static com.example.wanted.apply.execption.ApplyExceptionCode.ALREADY_APPLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.wanted.apply.domain.Apply;
import com.example.wanted.apply.domain.model.ApplyDto;
import com.example.wanted.apply.repository.ApplyRepository;
import com.example.wanted.common.exception.ApiException;
import com.example.wanted.jobopening.domain.JobOpening;
import com.example.wanted.jobopening.repository.JobOpeningRepository;
import com.example.wanted.member.domain.Member;
import com.example.wanted.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class ApplyServiceTest {

	@Autowired
	private ApplyService applyService;
	@Autowired
	private ApplyRepository applyRepository;
	@Autowired
	private JobOpeningRepository jobOpeningRepository;
	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("채용공고에 지원할 수 있습니다.")
	@Test
	void apply() {
		// given
		// 채용공고 데이터
		JobOpening jobOpening = JobOpening.builder()
				.build();
		jobOpeningRepository.save(jobOpening);

		// 회원 데이터
		Member member = Member.builder()
				.build();
		memberRepository.save(member);

		// 채용 지원 데이터
		ApplyDto applyDto = ApplyDto.builder()
				.memberId(member.getId())
				.jobOpeningId(jobOpening.getId())
				.build();

		// when
		ApplyDto result = applyService.apply(applyDto);

		// then
		assertThat(result)
				.extracting("memberId", "jobOpeningId")
				.contains(member.getId(), jobOpening.getId());
	}

	@DisplayName("사용자는 특정 채용공고에 중복 지원할 수 없습니다.")
	@Test
	void apply_alreadyApply() {
		// given
		// 채용공고 데이터
		JobOpening jobOpening = JobOpening.builder()
				.build();
		jobOpeningRepository.save(jobOpening);

		// 회원 데이터
		Member member = Member.builder()
				.build();
		memberRepository.save(member);

		// 채용 지원 데이터
		Apply apply = Apply.builder()
				.member(member)
				.jobOpening(jobOpening)
				.build();
		applyRepository.save(apply);

		// 중복 채용 지원 데이터
		ApplyDto applyDto = ApplyDto.builder()
				.memberId(member.getId())
				.jobOpeningId(jobOpening.getId())
				.build();

		// when
		assertThatThrownBy(() -> applyService.apply(applyDto))
				.isInstanceOf(ApiException.class)
				.hasMessage(ALREADY_APPLY.getMessage());

		// then
	}
}