package com.example.wanted.common.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(ApiException.class)
	public ExceptionResponse ApiExceptionHandler(ApiException e) {
		return new ExceptionResponse(e.getCode(), e.getMessage());
	}
}
