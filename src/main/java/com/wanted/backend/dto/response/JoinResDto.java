package com.wanted.backend.dto.response;

import com.wanted.backend.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinResDto {
    private Long id;
    private String email;

    public JoinResDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

}
