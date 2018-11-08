package com.tinderroulette.backend.rest;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.tinderroulette.backend.rest.CAS.CASCookie;
import com.tinderroulette.backend.rest.notification.NotificationData;
import com.tinderroulette.backend.rest.notification.NotificationDispatcher;

@Controller
@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @GetMapping(value = "/test/")
    public ResponseEntity test(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) {
        CASCookie.decodeLoginCookie(userCookie, credCookie);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        NotificationDispatcher.addEvent(auth.getPrincipal().toString(), new NotificationData("Test", "Add test event"));
        return new ResponseEntity(auth.getName(), HttpStatus.OK);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
