package com.example.wanted.jobopening.domain;

import com.example.wanted.company.domain.Company;
import com.example.wanted.jobopening.domain.model.WorkPlace;
import com.example.wanted.jobopening.domain.model.dto.request.JobOpeningRequest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JobOpening {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "company_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;

	@Embedded
	private WorkPlace workPlace;

	private String position;

	private Integer reward;

	@Lob
	private String content;

	@Builder.Default
	@OneToMany(mappedBy = "jobOpening")
	private List<UsingStack> usingStacks = new ArrayList<>();

	public void modifyJopOpening(JobOpeningRequest jobOpeningRequest, List<UsingStack> usingStacks) {
		this.workPlace = jobOpeningRequest.getWorkPlace();
		this.position = jobOpeningRequest.getPosition();
		this.reward = jobOpeningRequest.getReward();
		this.content = jobOpeningRequest.getContent();
		this.usingStacks = usingStacks;
	}
}
