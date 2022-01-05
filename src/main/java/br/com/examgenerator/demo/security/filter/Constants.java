package br.com.examgenerator.demo.security.filter;

import java.util.concurrent.TimeUnit;

public class Constants {
    public static final String SECRET = "secret";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 86400000L; // 1 day
}
