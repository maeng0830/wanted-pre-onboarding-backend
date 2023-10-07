package com.example.wanted.jopopening.service;

import com.example.wanted.common.exception.ApiException;
import com.example.wanted.company.domain.Company;
import com.example.wanted.company.exception.CompanyExceptionCode;
import com.example.wanted.company.repository.CompanyRepository;
import com.example.wanted.jopopening.domain.JopOpening;
import com.example.wanted.jopopening.domain.UsingStack;
import com.example.wanted.jopopening.domain.model.dto.JopOpeningRequest;
import com.example.wanted.jopopening.domain.model.dto.JopOpeningResponse;
import com.example.wanted.jopopening.repository.JopOpeningRepository;
import com.example.wanted.jopopening.repository.UsingStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JopOpeningService {

	private final JopOpeningRepository jopOpeningRepository;
	private final UsingStackRepository usingStackRepository;
	private final CompanyRepository companyRepository;

	@Transactional
	public JopOpeningResponse register(JopOpeningRequest jopOpeningRequest) {
		// 회사 조회
		Company company = companyRepository.findById(jopOpeningRequest.getCompanyId())
				.orElseThrow(() -> new ApiException(CompanyExceptionCode.NOT_EXIST_COMPANY));

		JopOpening jopOpening = JopOpening.builder()
				.company(company)
				.workPlace(jopOpeningRequest.getWorkPlace())
				.position(jopOpeningRequest.getPosition())
				.reward(jopOpeningRequest.getReward())
				.content(jopOpeningRequest.getContent())
				.build();

		jopOpeningRepository.save(jopOpening);

		for (int i = 0; i < jopOpeningRequest.getStackNames().size(); i++) {
			UsingStack usingStack = UsingStack.builder()
					.stackName(jopOpeningRequest.getStackNames().get(i))
					.build();

			usingStack.mappingJopOpening(jopOpening);

			usingStackRepository.save(usingStack);
		}

		return JopOpeningResponse.from(jopOpening);
	}
}
