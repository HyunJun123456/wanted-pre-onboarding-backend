package com.wanted.backend.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.backend.dto.response.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomResponseUtil {

	public static void success(HttpServletResponse response, Object dto) {
		try {
			ObjectMapper om = new ObjectMapper();
			ResponseDto<?> responseDto = new ResponseDto<>(1, "로그인 성공", dto);
			String responseBody = om.writeValueAsString(responseDto);
			response.setContentType("application/json; charset=utf-8");
			response.setStatus(200);
			response.getWriter().println(responseBody);
		} catch (IOException e) {
			log.error("서버 파싱 에러");
		}
	}

	public static void fail(HttpServletResponse response, String msg, HttpStatus httpStatus) {
		try {
			ObjectMapper om = new ObjectMapper();
			ResponseDto<?> responseDto = new ResponseDto<>(-1, msg, null);
			String responseBody = om.writeValueAsString(responseDto);
			response.setContentType("application/json; charset=utf-8");
			response.setStatus(httpStatus.value());
			response.getWriter().println(responseBody);
		} catch (IOException e) {
			log.error("서버 파싱 에러");
		}
	}
}
