package com.example.wanted.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiException extends RuntimeException {

	private ExceptionCode code;

	public ApiException(ExceptionCode code) {
		super(code.getMessage());
		this.code = code;
	}

	public ApiException(ExceptionCode code, Throwable cause) {
		super(code.getMessage(), cause);
		this.code = code;
	}
}
