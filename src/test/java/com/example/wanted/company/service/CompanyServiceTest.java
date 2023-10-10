package com.example.wanted.company.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.wanted.company.domain.model.CompanyDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class CompanyServiceTest {

	@Autowired
	private CompanyService companyService;

	@DisplayName("회사를 등록할 수 있다.")
	@CsvSource(value = {"AAA", "BBB"})
	@ParameterizedTest
	void register(String companyName) {
	    // given

	    // when
		CompanyDto companyDto = companyService.register(companyName);

		// then
		assertThat(companyDto.getId()).isNotNull();
		assertThat(companyDto.getCompanyName()).isEqualTo(companyName);
	}
}