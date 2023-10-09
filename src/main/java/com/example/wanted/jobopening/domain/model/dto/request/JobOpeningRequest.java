package com.example.wanted.jobopening.domain.model.dto.request;

import com.example.wanted.jobopening.domain.model.WorkPlace;
import java.util.List;

public interface JobOpeningRequest {

	List<String> getStackNames();
	WorkPlace getWorkPlace();
	String getPosition();
	Integer getReward();
	String getContent();
}
