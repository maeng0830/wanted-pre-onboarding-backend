package com.example.wanted.apply.controller;

import com.example.wanted.apply.domain.model.ApplyDto;
import com.example.wanted.apply.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApplyController {

	private final ApplyService applyService;

	// 채용 공고 지원 API
	@PostMapping("/api/apply")
	public ApplyDto apply(@RequestBody ApplyDto applyDto) {
		return applyService.apply(applyDto);
	}
}
