package com.example.wanted.apply.domain;

import com.example.wanted.jobopening.domain.JobOpening;
import com.example.wanted.member.domain.Member;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Apply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "member_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@JoinColumn(name = "job_opening_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private JobOpening jobOpening;

	@Builder
	public Apply(Member member, JobOpening jobOpening) {
		this.member = member;
		this.jobOpening = jobOpening;
	}
}
