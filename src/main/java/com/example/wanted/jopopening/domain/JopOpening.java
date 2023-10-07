package com.example.wanted.jopopening.domain;

import com.example.wanted.company.domain.Company;
import com.example.wanted.jopopening.domain.model.WorkPlace;
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
public class JopOpening {

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
	@OneToMany(mappedBy = "jopOpening")
	private List<UsingStack> usingStacks = new ArrayList<>();
}
