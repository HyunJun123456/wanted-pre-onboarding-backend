package com.wanted.backend.config.jwt;

public interface JwtVO {
    public static final String SECRET = "원티드";
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 일주일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";

}
