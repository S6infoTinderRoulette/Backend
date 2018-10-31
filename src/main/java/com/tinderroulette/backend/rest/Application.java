package com.tinderroulette.backend.rest;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.tinderroulette.backend.rest.CAS.CASCookie;

@Controller
@SpringBootApplication
public class Application
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired
	AuthenticationProvider authenticationProvider;

	@GetMapping(value = "/test/")
	public ResponseEntity test(@CookieValue("auth_user") Cookie userCookie,
			@CookieValue("auth_cred") Cookie credCookie) {
		System.out.println("test");
		CASCookie.decodeLoginCookie(userCookie, credCookie);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String user = "cc";
		if (auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof UserDetails) {
			user = ((UserDetails) auth.getPrincipal()).getUsername();
		}
		System.out.println(auth.getName());
		return new ResponseEntity(auth.getName(), HttpStatus.OK);
	}

    public static void main( String[] args )
    {
    	SpringApplication.run(Application.class, args);
    }
}
