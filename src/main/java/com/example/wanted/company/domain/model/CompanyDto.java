package com.example.wanted.company.domain.model;

import com.example.wanted.company.domain.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyDto {

	private Long id;
	private String companyName;

	@Builder
	public CompanyDto(Long id, String companyName) {
		this.id = id;
		this.companyName = companyName;
	}

	public static CompanyDto from(Company company) {
		return CompanyDto.builder()
				.id(company.getId())
				.companyName(company.getCompanyName())
				.build();
	}
}
