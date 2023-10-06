package com.example.wanted.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.wanted.member.domain.model.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@DisplayName("사용자를 등록한다.")
	@Test
	void join() {
	    // given

	    // when
		MemberDto memberDto = memberService.join();

		// then
		assertThat(memberDto.getId()).isNotNull();
	}
}