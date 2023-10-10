package com.example.wanted.jobopening.repository;

import com.example.wanted.jobopening.domain.UsingStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsingStackRepository extends JpaRepository<UsingStack, Long> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("delete from UsingStack u where u.jobOpening.id = :jobOpeningId")
	void deleteByJobOpeningId(@Param("jobOpeningId") Long jobOpeningId);
}
