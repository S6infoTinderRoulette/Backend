package com.tinderroulette.backend.rest;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.tinderroulette.backend.rest.CAS.PrivilegeValidator;
import com.tinderroulette.backend.rest.CAS.Status;

@Controller
@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    PrivilegeValidator validator;

    @GetMapping(value = "/test/")
    public ResponseEntity test(@CookieValue("auth_user") Cookie userCookie,
            @CookieValue("auth_cred") Cookie credCookie) {
        String auth;
        try {
            auth = validator.validate(userCookie, credCookie, Status.Teacher, Status.Admin);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage().toString(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(auth, HttpStatus.OK);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
