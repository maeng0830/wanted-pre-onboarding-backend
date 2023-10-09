package com.example.wanted.jobopening.domain.model.dto.response;

import com.example.wanted.jobopening.domain.JobOpening;
import com.example.wanted.jobopening.domain.UsingStack;
import com.example.wanted.jobopening.domain.model.WorkPlace;
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
public class JobOpeningOutline {

	private Long jobOpeningId;
	private String companyName;
	private WorkPlace workPlace;
	private String position;
	private Integer reward;
	@Builder.Default
	private List<String> stackNames = new ArrayList<>();

	public static JobOpeningOutline from(JobOpening jobOpening) {
		List<UsingStack> usingStacks = jobOpening.getUsingStacks();

		List<String> list = usingStacks.stream().map(UsingStack::getStackName)
				.collect(Collectors.toList());

		return JobOpeningOutline.builder()
				.jobOpeningId(jobOpening.getId())
				.companyName(jobOpening.getCompany().getCompanyName())
				.workPlace(jobOpening.getWorkPlace())
				.position(jobOpening.getPosition())
				.reward(jobOpening.getReward())
				.stackNames(list)
				.build();
	}
}
