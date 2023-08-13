package com.wanted.backend.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.backend.config.auth.LoginUser;
import com.wanted.backend.dto.request.LoginReqDto;
import com.wanted.backend.utils.CustomResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		setFilterProcessesUrl("/api/login");
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		ObjectMapper om = new ObjectMapper();
		try {
			LoginReqDto loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					loginReqDto.getEmail(), loginReqDto.getPassword());
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			return authentication;
		} catch (IOException e) {
			throw new InternalAuthenticationServiceException(e.getMessage());
		}
	}

	// 로그인 성공
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.debug("테스트: 나오는중임?");
		LoginUser loginUser = (LoginUser) authResult.getPrincipal();
		String jwtToken = JwtProcess.create(loginUser);
		response.addHeader(JwtVO.HEADER, jwtToken);
		Map<String, String> tokenMap = new HashMap<>();
		jwtToken = jwtToken.replace("Bearer ", "");
		tokenMap.put("token", jwtToken);
		CustomResponseUtil.success(response, tokenMap);
	}

	// 로그인 실패
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		CustomResponseUtil.fail(response, "로그인 실패", HttpStatus.UNAUTHORIZED);
	}

}
