package com.wanted.backend.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wanted.backend.domain.User;
import com.wanted.backend.dto.request.JoinReqDto;
import com.wanted.backend.dto.request.LoginReqDto;
import com.wanted.backend.dto.response.JoinResDto;
import com.wanted.backend.dto.response.LoginResDto;
import com.wanted.backend.handler.ex.CustomApiException;
import com.wanted.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public JoinResDto signUp(JoinReqDto joinReqDto) {
        Optional<User> userOP = userRepository.findByEmail(joinReqDto.getEmail());
        if (userOP.isPresent()) {
            throw new CustomApiException("동일한 email이 존재합니다.", HttpStatus.CONFLICT);
        }

        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        return new JoinResDto(userPS);
    }

    public LoginResDto signIn(LoginReqDto loginReqDto) {
        return null;
    }
}
