package com.example.wanted.jopopening.controller;

import com.example.wanted.jopopening.domain.model.dto.JopOpeningRequest;
import com.example.wanted.jopopening.domain.model.dto.JopOpeningResponse;
import com.example.wanted.jopopening.service.JopOpeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JopOpeningController {

	private final JopOpeningService jopOpeningService;

	@PostMapping("/api/jop-opening")
	public JopOpeningResponse register(@RequestBody JopOpeningRequest jopOpeningRequest) {
		return jopOpeningService.register(jopOpeningRequest);
	}
}
