package com.example.wanted.company.service;

import com.example.wanted.company.domain.Company;
import com.example.wanted.company.domain.model.CompanyDto;
import com.example.wanted.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyService {

	private final CompanyRepository companyRepository;

	// 회사 등록 기능
	public CompanyDto register(String companyName) {
		Company company = Company.builder()
				.companyName(companyName)
				.build();

		companyRepository.save(company);

		return CompanyDto.from(company);
	}
}
