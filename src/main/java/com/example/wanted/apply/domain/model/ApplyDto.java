package com.example.wanted.apply.domain.model;

import com.example.wanted.apply.domain.Apply;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyDto {

	private Long id;
	private Long memberId;
	private Long jobOpeningId;

	@Builder
	public ApplyDto(Long id, Long memberId, Long jobOpeningId) {
		this.id = id;
		this.memberId = memberId;
		this.jobOpeningId = jobOpeningId;
	}

	public static ApplyDto from(Apply apply) {
		return ApplyDto.builder()
				.id(apply.getId())
				.memberId(apply.getMember().getId())
				.jobOpeningId(apply.getJobOpening().getId())
				.build();
	}
}
