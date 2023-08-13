package com.wanted.backend.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.backend.dto.request.JoinReqDto;
import com.wanted.backend.dto.response.JoinResDto;
import com.wanted.backend.dto.response.ResponseDto;
import com.wanted.backend.service.UserService;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinReqDto joinReqDto, BindingResult bindingResult) {
        JoinResDto joinResDto = userService.signUp(joinReqDto);
        // 회원 가입 로직
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinResDto), HttpStatus.CREATED);
    }

}
