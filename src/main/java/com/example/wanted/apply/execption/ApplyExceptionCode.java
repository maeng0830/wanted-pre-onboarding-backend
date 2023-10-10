package com.example.wanted.apply.execption;

import com.example.wanted.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplyExceptionCode implements ExceptionCode {
	ALREADY_APPLY("이미 해당 채용공고에 지원했습니다.");

	private final String message;

	@Override
	public String getMessage() {
		return this.message;
	}
}
