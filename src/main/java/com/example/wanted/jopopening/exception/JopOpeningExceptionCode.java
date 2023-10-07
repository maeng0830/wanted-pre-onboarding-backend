package com.example.wanted.jopopening.exception;

import com.example.wanted.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JopOpeningExceptionCode implements ExceptionCode {

	NOT_EXIST_JOP_OPENING("채용 공고가 존재하지 않습니다.");

	private final String message;


	@Override
	public String getMessage() {
		return this.message;
	}
}
