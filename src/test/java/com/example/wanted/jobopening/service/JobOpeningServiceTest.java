package com.example.wanted.jobopening.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.example.wanted.company.domain.Company;
import com.example.wanted.company.repository.CompanyRepository;
import com.example.wanted.jobopening.domain.JobOpening;
import com.example.wanted.jobopening.domain.UsingStack;
import com.example.wanted.jobopening.domain.model.WorkPlace;
import com.example.wanted.jobopening.domain.model.dto.request.JobOpeningModify;
import com.example.wanted.jobopening.domain.model.dto.response.JobOpeningDetail;
import com.example.wanted.jobopening.domain.model.dto.response.JobOpeningOutline;
import com.example.wanted.jobopening.domain.model.dto.request.JobOpeningRegister;
import com.example.wanted.jobopening.domain.model.dto.response.JobOpeningResponse;
import com.example.wanted.jobopening.repository.JobOpeningRepository;
import com.example.wanted.jobopening.repository.UsingStackRepository;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class JobOpeningServiceTest {

	@Autowired
	private JobOpeningService jobOpeningService;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private JobOpeningRepository jobOpeningRepository;
	@Autowired
	private UsingStackRepository usingStackRepository;
	@Autowired
	private EntityManager em;

	@DisplayName("채용 공고를 등록할 수 있다.")
	@Test
	void register() {
		// given
		//회사 데이터
		Company company = Company.builder()
				.companyName("AAA")
				.build();
		companyRepository.save(company);

		// 채용 공고 등록 데이터
		JobOpeningRegister jobOpeningRequest = JobOpeningRegister.builder()
				.companyId(company.getId())
				.workPlace(new WorkPlace("대한민국", "서울"))
				.position("백엔드 개발자")
				.reward(1000000)
				.content("백엔드 개발자를 채용합니다.")
				.stackNames(List.of("Java", "Spring Boot", "MySql"))
				.build();

		// when
		JobOpeningResponse jobOpeningResponse = jobOpeningService.register(jobOpeningRequest);

		// then
		assertThat(jobOpeningResponse)
				.hasFieldOrPropertyWithValue("companyName", company.getCompanyName())
				.hasFieldOrPropertyWithValue("workPlace", jobOpeningRequest.getWorkPlace())
				.hasFieldOrPropertyWithValue("position", jobOpeningRequest.getPosition())
				.hasFieldOrPropertyWithValue("reward", jobOpeningRequest.getReward())
				.hasFieldOrPropertyWithValue("content", jobOpeningRequest.getContent())
				.hasFieldOrPropertyWithValue("stackNames", jobOpeningRequest.getStackNames());
	}

	@DisplayName("채용 공고를 수정할 수 있다.")
	@Test
	void modify() {
		// given
		//회사 데이터
		Company company = Company.builder()
				.companyName("AAA")
				.build();
		companyRepository.save(company);

		// 채용 공고 데이터
		JobOpening jobOpening = JobOpening.builder()
				.company(company)
				.workPlace(new WorkPlace("일본", "도쿄"))
				.position("프론트엔드 개발자")
				.reward(500000)
				.content("프론트엔드 개발자를 채용합니다.")
				.build();
		jobOpeningRepository.save(jobOpening);

		// 사용 기술 데이터
		UsingStack usingStack1 = UsingStack.builder()
				.jobOpening(jobOpening)
				.stackName("html")
				.build();
		UsingStack usingStack2 = UsingStack.builder()
				.jobOpening(jobOpening)
				.stackName("css")
				.build();
		UsingStack usingStack3 = UsingStack.builder()
				.jobOpening(jobOpening)
				.stackName("javaScript")
				.build();
		usingStackRepository.saveAll(List.of(usingStack1, usingStack2, usingStack3));

		// 채용공고 수정 데이터
		JobOpeningModify jobOpeningModify = JobOpeningModify.builder()
				.workPlace(new WorkPlace("대한민국", "서울"))
				.position("백엔드 개발자")
				.reward(1000000)
				.content("백엔드 개발자를 채용합니다.")
				.stackNames(List.of("Java", "Spring Boot", "MySql", "Git"))
				.build();

		// when
		JobOpeningResponse jobOpeningResponse = jobOpeningService.modify(jobOpening.getId(),
				jobOpeningModify);

		// then
		assertThat(jobOpeningResponse)
				.hasFieldOrPropertyWithValue("companyName", company.getCompanyName())
				.hasFieldOrPropertyWithValue("workPlace", jobOpeningModify.getWorkPlace())
				.hasFieldOrPropertyWithValue("position", jobOpeningModify.getPosition())
				.hasFieldOrPropertyWithValue("reward", jobOpeningModify.getReward())
				.hasFieldOrPropertyWithValue("content", jobOpeningModify.getContent());
		assertThat(jobOpeningResponse.getStackNames()).hasSize(4)
				.containsExactlyInAnyOrder("Java", "Spring Boot", "MySql", "Git");
	}

	@DisplayName("searchText에 따른 채용공고 목록을 조회할 수 있다.")
	@MethodSource("argumentsForSearchJobOpeningsBySearchText")
	@ParameterizedTest
	void getJopOpenings(String searchText, List<String> properties) {
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
				.workPlace(new WorkPlace("대한민국", "서울"))
				.reward(5000)
				.position("프론트엔드 개발자")
				.build();
		JobOpening jobOpening2 = JobOpening.builder()
				.company(company2)
				.workPlace(new WorkPlace("대한민국", "춘천"))
				.reward(10000)
				.position("백엔드 개발자")
				.build();
		JobOpening jobOpening3 = JobOpening.builder()
				.company(company3)
				.workPlace(new WorkPlace("대한민국", "부산"))
				.reward(15000)
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
		Page<JobOpeningOutline> jobOpeningOutlines = jobOpeningService.getJopOpenings(searchText,
				pageRequest);

		// then
		assertThat(jobOpeningOutlines.getContent()).hasSize(1)
				.extracting("companyName", "workPlace.city", "position", "reward")
				.containsExactlyInAnyOrder(
						tuple(properties.get(0), properties.get(4), properties.get(1),
								Integer.parseInt(properties.get(5))));
		assertThat(jobOpeningOutlines.getContent().get(0).getStackNames()).hasSize(1)
				.containsExactlyInAnyOrder(properties.get(2));
	}

	@DisplayName("특정 채용공고의 상세 정보를 조회할 수 있다.")
	@Test
	void getJopOpening() {
		// given
		Company company = Company.builder()
				.companyName("네이버")
				.build();
		companyRepository.save(company);

		JobOpening jobOpening1 = JobOpening.builder()
				.company(company)
				.workPlace(new WorkPlace("대한민국", "서울"))
				.position("백엔드 개발자")
				.reward(10000)
				.content("백엔드 개발자 채용합니다.")
				.build();
		JobOpening jobOpening2 = JobOpening.builder()
				.company(company)
				.content("프론트엔드 개발자 채용합니다.")
				.build();
		JobOpening jobOpening3 = JobOpening.builder()
				.company(company)
				.content("기획자 채용합니다.")
				.build();
		jobOpeningRepository.saveAll(List.of(jobOpening1, jobOpening2, jobOpening3));

		UsingStack usingStack = UsingStack.builder()
				.jobOpening(jobOpening1)
				.stackName("Spring")
				.build();
		usingStackRepository.save(usingStack);

		// when
		JobOpeningDetail result = jobOpeningService.getJopOpening(jobOpening1.getId());

		// then
		assertThat(result)
				.extracting("jobOpeningId", "companyName", "workPlace", "position", "reward",
						"content")
				.contains(jobOpening1.getId(), company.getCompanyName(), jobOpening1.getWorkPlace(),
						jobOpening1.getPosition(), jobOpening1.getReward(),
						jobOpening1.getContent());
		assertThat(result.getOtherJobOpeningIds()).hasSize(2)
				.containsExactlyInAnyOrder(jobOpening2.getId(), jobOpening3.getId());
	}

	private static Stream<Arguments> argumentsForSearchJobOpeningsBySearchText() {
		return Stream.of(
				Arguments.of("네이버", List.of("네이버", "프론트엔드 개발자", "JS", "대한민국", "서울", "5000")),
				Arguments.of("프론트엔드 개발자", List.of("네이버", "프론트엔드 개발자", "JS", "대한민국", "서울", "5000")),
				Arguments.of("JS", List.of("네이버", "프론트엔드 개발자", "JS", "대한민국", "서울", "5000")),
				Arguments.of("카카오", List.of("카카오", "백엔드 개발자", "Java", "대한민국", "춘천", "10000")),
				Arguments.of("백엔드 개발자", List.of("카카오", "백엔드 개발자", "Java", "대한민국", "춘천", "10000")),
				Arguments.of("Java", List.of("카카오", "백엔드 개발자", "Java", "대한민국", "춘천", "10000")),
				Arguments.of("라인", List.of("라인", "기획자", "커뮤니케이션", "대한민국", "부산", "15000")),
				Arguments.of("기획자", List.of("라인", "기획자", "커뮤니케이션", "대한민국", "부산", "15000")),
				Arguments.of("커뮤니케이션", List.of("라인", "기획자", "커뮤니케이션", "대한민국", "부산", "15000"))
		);
	}
}