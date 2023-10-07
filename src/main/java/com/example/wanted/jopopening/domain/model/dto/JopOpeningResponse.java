package com.example.wanted.jopopening.domain.model.dto;

import com.example.wanted.jopopening.domain.JopOpening;
import com.example.wanted.jopopening.domain.UsingStack;
import com.example.wanted.jopopening.domain.model.WorkPlace;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JopOpeningResponse {

	private Long jopOpeningId;
	private String companyName;
	private WorkPlace workPlace;
	private String position;
	private Integer reward;
	private String content;
	@Builder.Default
	private List<String> stackNames = new ArrayList<>();

	public static JopOpeningResponse from(JopOpening jopOpening) {
		List<UsingStack> usingStacks = jopOpening.getUsingStacks();

		List<String> list = usingStacks.stream().map(UsingStack::getStackName)
				.collect(Collectors.toList());

		return JopOpeningResponse.builder()
				.jopOpeningId(jopOpening.getId())
				.companyName(jopOpening.getCompany().getCompanyName())
				.workPlace(jopOpening.getWorkPlace())
				.position(jopOpening.getPosition())
				.reward(jopOpening.getReward())
				.content(jopOpening.getContent())
				.stackNames(list)
				.build();
	}
}
