package com.example.wanted;

import com.example.wanted.company.service.CompanyService;
import com.example.wanted.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("dev")
@RequiredArgsConstructor
@Component
public class DevData {

	private final MemberService memberService;
	private final CompanyService companyService;

	@EventListener(ApplicationReadyEvent.class)
	public void devData() {
		// 임시 사용자 데이터 등록

		for (int i = 0; i < 10; i++) {
			memberService.join();
		}

		// 임시 회사 데이터 등록
		String[] companyNames = {"네이버", "카카오", "라인", "쿠팡", "배달의민족", "당근마켓", "토스"};

		for (int i = 0; i < companyNames.length; i++) {
			companyService.register(companyNames[i]);
		}
	}
}
