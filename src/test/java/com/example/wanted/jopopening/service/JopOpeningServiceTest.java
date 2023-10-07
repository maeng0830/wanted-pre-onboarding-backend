package com.example.wanted.jopopening.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.wanted.company.domain.Company;
import com.example.wanted.company.repository.CompanyRepository;
import com.example.wanted.jopopening.domain.model.WorkPlace;
import com.example.wanted.jopopening.domain.model.dto.JopOpeningRequest;
import com.example.wanted.jopopening.domain.model.dto.JopOpeningResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class JopOpeningServiceTest {

	@Autowired
	private JopOpeningService jopOpeningService;
	@Autowired
	private CompanyRepository companyRepository;

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
		JopOpeningRequest jopOpeningRequest = JopOpeningRequest.builder()
				.companyId(company.getId())
				.workPlace(new WorkPlace("대한민국", "서울"))
				.position("백엔드 개발자")
				.reward(1000000)
				.content("백엔드 개발자를 채용합니다.")
				.stackNames(List.of("Java", "Spring Boot", "MySql"))
				.build();

		// when
		JopOpeningResponse jopOpeningResponse = jopOpeningService.register(jopOpeningRequest);

		// then
		assertThat(jopOpeningResponse)
				.hasFieldOrPropertyWithValue("companyName", company.getCompanyName())
				.hasFieldOrPropertyWithValue("workPlace", jopOpeningRequest.getWorkPlace())
				.hasFieldOrPropertyWithValue("position", jopOpeningRequest.getPosition())
				.hasFieldOrPropertyWithValue("reward", jopOpeningRequest.getReward())
				.hasFieldOrPropertyWithValue("content", jopOpeningRequest.getContent())
				.hasFieldOrPropertyWithValue("stackNames", jopOpeningRequest.getStackNames());
	}
}