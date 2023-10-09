package com.example.wanted.jobopening.domain.model.dto.response;

import com.example.wanted.jobopening.domain.JobOpening;
import com.example.wanted.jobopening.domain.UsingStack;
import com.example.wanted.jobopening.domain.model.WorkPlace;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobOpeningDetail {

	private Long jobOpeningId;
	private String companyName;
	private WorkPlace workPlace;
	private String position;
	private Integer reward;
	private String content;
	@Builder.Default
	private List<String> stackNames = new ArrayList<>();
	@Builder.Default
	private List<Long> otherJobOpeningIds = new ArrayList<>();

	public static JobOpeningDetail from(JobOpening jobOpening, List<JobOpening> otherJobOpenings) {
		List<UsingStack> usingStacks = jobOpening.getUsingStacks();

		List<String> usingStackNames = usingStacks.stream().map(UsingStack::getStackName)
				.collect(Collectors.toList());

		List<Long> jobOpeningIds = otherJobOpenings.stream()
				.map(JobOpening::getId)
				.filter(id -> !Objects.equals(id, jobOpening.getId()))
				.collect(Collectors.toList());

		return JobOpeningDetail.builder()
				.jobOpeningId(jobOpening.getId())
				.companyName(jobOpening.getCompany().getCompanyName())
				.workPlace(jobOpening.getWorkPlace())
				.position(jobOpening.getPosition())
				.reward(jobOpening.getReward())
				.content(jobOpening.getContent())
				.stackNames(usingStackNames)
				.otherJobOpeningIds(jobOpeningIds)
				.build();
	}
}
