package com.example.wanted.jobopening.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.example.wanted.company.domain.Company;
import com.example.wanted.company.repository.CompanyRepository;
import com.example.wanted.jobopening.domain.JobOpening;
import com.example.wanted.jobopening.domain.UsingStack;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class JobOpeningRepositoryTest {

	@Autowired
	private JobOpeningRepository jobOpeningRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UsingStackRepository usingStackRepository;
	@Autowired
	private EntityManager em;

	@DisplayName("searchText에 따른 채용 공고를 조회할 수 있다.")
	@MethodSource("argumentsForSearchJopOpeningsBySearchText")
	@ParameterizedTest
	void searchJopOpeningsBySearchText(String searchText, String companyName, String position, String stackName) {
	    // given
	    // 회사 데이터
		Company company1 = Company.builder()
				.companyName("네이버")
				.build();
		Company company2 = Company.builder()
				.companyName("카카오")
				.build();
		Company company3 = Company.builder()
				.companyName("라인")
				.build();
		companyRepository.saveAll(List.of(company1, company2, company3));

		// 채용 공고 데이터
		JobOpening jobOpening1 = JobOpening.builder()
				.company(company1)
				.position("프론트엔드 개발자")
				.build();
		JobOpening jobOpening2 = JobOpening.builder()
				.company(company2)
				.position("백엔드 개발자")
				.build();
		JobOpening jobOpening3 = JobOpening.builder()
				.company(company3)
				.position("기획자")
				.build();
		jobOpeningRepository.saveAll(List.of(jobOpening1, jobOpening2, jobOpening3));

		// 사용 기술 데이터
		UsingStack usingStack1 = UsingStack.builder()
				.jobOpening(jobOpening1)
				.stackName("JS")
				.build();
		UsingStack usingStack2 = UsingStack.builder()
				.jobOpening(jobOpening2)
				.stackName("Java")
				.build();
		UsingStack usingStack3 = UsingStack.builder()
				.jobOpening(jobOpening3)
				.stackName("커뮤니케이션")
				.build();
		usingStackRepository.saveAll(List.of(usingStack1, usingStack2, usingStack3));

		// DB 반영 및 영속성 컨텍스트 초기화
		em.flush();
		em.clear();

		// 페이징
		PageRequest pageRequest = PageRequest.of(0, 20);

		// when
		Page<JobOpening> jopOpenings = jobOpeningRepository.searchJopOpeningsBySearchText(
				searchText, pageRequest);

		System.out.println(jopOpenings.getContent().get(0).getUsingStacks().size());

		// then
		assertThat(jopOpenings.getContent()).hasSize(1)
				.extracting("company.companyName", "position")
				.containsExactlyInAnyOrder(tuple(companyName, position));
		assertThat(jopOpenings.getContent().get(0).getUsingStacks()).hasSize(1)
				.extracting("stackName")
				.containsExactlyInAnyOrder(stackName);
	}

	private static Stream<Arguments> argumentsForSearchJopOpeningsBySearchText() {
		return Stream.of(
				Arguments.of("네이버", "네이버", "프론트엔드 개발자", "JS"),
				Arguments.of("프론트엔드 개발자", "네이버", "프론트엔드 개발자", "JS"),
				Arguments.of("JS", "네이버", "프론트엔드 개발자", "JS"),
				Arguments.of("카카오", "카카오", "백엔드 개발자", "Java"),
				Arguments.of("백엔드 개발자", "카카오", "백엔드 개발자", "Java"),
				Arguments.of("Java", "카카오", "백엔드 개발자", "Java"),
				Arguments.of("라인", "라인", "기획자", "커뮤니케이션"),
				Arguments.of("기획자", "라인", "기획자", "커뮤니케이션"),
				Arguments.of("커뮤니케이션", "라인", "기획자", "커뮤니케이션")
		);
	}
}