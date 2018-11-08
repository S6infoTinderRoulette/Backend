package com.tinderroulette.backend.rest.CAS;

import java.util.Base64;

import javax.servlet.http.Cookie;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CASCookie {
    static public Cookie createCookie(String key, String value) {
        return new Cookie(key, new String(Base64.getEncoder().encode(value.getBytes())));
    }

    static public void decodeLoginCookie(Cookie userCookie, Cookie credCookie) {
        String user = new String(Base64.getDecoder().decode(userCookie.getValue().getBytes()));
        String cred = new String(Base64.getDecoder().decode(credCookie.getValue().getBytes()));
        Authentication auth = new UsernamePasswordAuthenticationToken(user, cred);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
