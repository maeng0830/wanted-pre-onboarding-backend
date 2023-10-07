package com.example.wanted.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

	private ExceptionCode code;
	private String message;
}
