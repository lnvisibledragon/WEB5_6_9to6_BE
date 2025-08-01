package com.grepp.spring.infra.auth.jwt;

import org.springframework.http.ResponseCookie;

public class TokenCookieFactory {
    public static ResponseCookie create(String name, String value, Long expires) {
        return ResponseCookie.from(name, value)
            .maxAge(expires)
            .path("/")
//            .domain(".studium-9to6.vercel.app")
            .httpOnly(true)             // HttpOnly
            .secure(true)
            .sameSite("None")// Secure
            .build();
    }

    public static ResponseCookie createExpiredToken(String name) {
        return ResponseCookie.from(name, "")
            .maxAge(0)
            .path("/")
            .httpOnly(true)             // HttpOnly
            .secure(true)
            .sameSite("None")// // Secure
            .build();
    }
}

