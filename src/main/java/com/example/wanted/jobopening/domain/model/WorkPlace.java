package com.example.wanted.jobopening.domain.model;

import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class WorkPlace {

	private String country;
	private String city;

	@Builder
	public WorkPlace(String country, String city) {
		this.country = country;
		this.city = city;
	}
}
