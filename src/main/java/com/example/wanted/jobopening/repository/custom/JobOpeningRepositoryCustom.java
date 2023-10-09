package com.example.wanted.jobopening.repository.custom;

import com.example.wanted.jobopening.domain.JobOpening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobOpeningRepositoryCustom {

	Page<JobOpening> searchJopOpeningsBySearchText(String searchText, Pageable pageable);
}
