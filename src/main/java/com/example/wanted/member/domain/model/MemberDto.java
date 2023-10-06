package com.example.wanted.member.domain.model;

import com.example.wanted.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {

	private Long id;

	@Builder
	public MemberDto(Long id) {
		this.id = id;
	}

	public static MemberDto from(Member member) {
		return MemberDto.builder()
				.id(member.getId())
				.build();
	}
}
