package com.example.wanted.company.exception;

import com.example.wanted.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyExceptionCode implements ExceptionCode {

	NOT_EXIST_COMPANY("회사가 존재하지 않습니다.");

	private final String message;

	@Override
	public String getMessage() {
		return this.message;
	}
}
