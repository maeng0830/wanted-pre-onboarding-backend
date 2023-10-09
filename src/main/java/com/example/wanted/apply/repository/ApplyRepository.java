package com.example.wanted.apply.repository;

import com.example.wanted.apply.domain.Apply;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

	Optional<Apply> findByMember_IdAndJobOpening_Id(Long memberId, Long jobOpeningId);
}
