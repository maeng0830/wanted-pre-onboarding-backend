package com.example.wanted.member.service;

import com.example.wanted.member.domain.Member;
import com.example.wanted.member.domain.model.MemberDto;
import com.example.wanted.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	// 회원 데이터 등록 기능
	public MemberDto join() {
		return MemberDto.from(memberRepository.save(new Member()));
	}
}
