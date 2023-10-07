package com.example.wanted.jopopening.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UsingStack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "jop_opening_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private JopOpening jopOpening;

	private String stackName;

	public void mappingJopOpening(JopOpening jopOpening) {
		this.jopOpening = jopOpening;
		jopOpening.getUsingStacks().add(this);
	}
}
