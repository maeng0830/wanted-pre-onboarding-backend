package com.example.wanted.jobopening.domain.model.dto.request;

import com.example.wanted.jobopening.domain.model.WorkPlace;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobOpeningModify implements JobOpeningRequest {

	private WorkPlace workPlace;
	private String position;
	private Integer reward;
	private String content;
	@Builder.Default
	private List<String> stackNames = new ArrayList<>();
}
