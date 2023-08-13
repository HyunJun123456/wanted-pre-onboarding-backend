package com.wanted.backend.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class LoginReqDto {
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;
	@Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
	private String password;
}
