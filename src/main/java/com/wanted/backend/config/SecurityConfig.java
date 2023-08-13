package com.wanted.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.wanted.backend.config.jwt.JwtAuthenticationFilter;
import com.wanted.backend.config.jwt.JwtAuthorizationFilter;
import com.wanted.backend.enums.RoleType;
import com.wanted.backend.utils.CustomResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
		@Override
		public void configure(HttpSecurity builder) throws Exception {
			AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
			builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
			builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
			super.configure(builder);
		}
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.apply(new CustomSecurityFilterManager());

		http.exceptionHandling(handling -> handling.accessDeniedHandler((request, response, e) -> {
			CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
		}));

		http.headers(headers -> headers.frameOptions().disable())
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(configurationSource()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// .formLogin(formLogin -> formLogin.loginProcessingUrl("/api/login"))
				.exceptionHandling(exceptionHandling -> exceptionHandling
						.authenticationEntryPoint((request, response, authException) -> {
							CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED);
						}))
				.httpBasic(httpBasic -> httpBasic.disable())
				.authorizeRequests(authorizeRequests -> authorizeRequests
						.antMatchers("/api/s/**").authenticated()
						.antMatchers("/api/admin/**").hasRole("" + RoleType.ADMIN.name())
						.anyRequest().permitAll());

		return http.build();
	}

	public CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedOriginPattern("*");
		configuration.setAllowCredentials(true);
		configuration.addExposedHeader("Authorization");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
