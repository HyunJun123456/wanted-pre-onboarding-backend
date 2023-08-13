package com.wanted.backend.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wanted.backend.domain.User;
import com.wanted.backend.enums.RoleType;

import lombok.Getter;

@Getter
public class JoinReqDto {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    public User toEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(RoleType.USER)
                .build();
    }
}
