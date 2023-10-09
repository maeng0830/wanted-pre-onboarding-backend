package com.example.wanted.jobopening.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.wanted.jobopening.domain.JobOpening;
import com.example.wanted.jobopening.domain.UsingStack;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class UsingStackRepositoryTest {

	@Autowired
	private JobOpeningRepository jobOpeningRepository;
	@Autowired
	private UsingStackRepository usingStackRepository;

	@DisplayName("주어진 JopOpeningId에 해당하는 UsingStack 데이터를 삭제한다.")
	@Test
	void deleteByJobOpeningId() {
		// given
		// 채용 공고 데이터
		JobOpening jobOpening1 = JobOpening.builder()
				.content("채용공고1")
				.build();
		JobOpening jobOpening2 = JobOpening.builder()
				.content("채용공고2")
				.build();

		jobOpeningRepository.saveAll(List.of(jobOpening1, jobOpening2));

		// 사용 기술 데이터
		List<UsingStack> usingStacks = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			UsingStack usingStack;
			if (i % 2 == 0) {
				usingStack = UsingStack.builder()
						.jobOpening(jobOpening1)
						.build();
			} else {
				usingStack = UsingStack.builder()
						.jobOpening(jobOpening2)
						.build();
			}

			usingStacks.add(usingStack);
		}

		usingStackRepository.saveAll(usingStacks);

		// when
		usingStackRepository.deleteByJobOpeningId(jobOpening1.getId());
		List<UsingStack> result = usingStackRepository.findAll();

		// then
		assertThat(result).hasSize(5)
				.extracting("jobOpening.content")
				.containsExactlyInAnyOrder(jobOpening2.getContent(), jobOpening2.getContent(),
						jobOpening2.getContent(), jobOpening2.getContent(),
						jobOpening2.getContent());
	}
}