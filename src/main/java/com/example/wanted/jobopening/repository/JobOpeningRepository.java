package com.example.wanted.jobopening.repository;

import com.example.wanted.jobopening.domain.JobOpening;
import com.example.wanted.jobopening.repository.custom.JobOpeningRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOpeningRepository extends JpaRepository<JobOpening, Long>,
		JobOpeningRepositoryCustom {

	List<JobOpening> findByCompany_Id(Long companyId);
}
