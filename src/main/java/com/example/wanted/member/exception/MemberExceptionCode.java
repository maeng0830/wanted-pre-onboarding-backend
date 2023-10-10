package com.example.wanted.member.exception;

import com.example.wanted.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionCode implements ExceptionCode {
	NOT_EXIST_MEMBER("존재하지 않는 회원입니다.");

	private final String message;

	@Override
	public String getMessage() {
		return this.message;
	}
}
