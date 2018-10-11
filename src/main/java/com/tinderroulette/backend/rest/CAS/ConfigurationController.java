package com.tinderroulette.backend.rest.CAS;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {
	@RequestMapping("/index")
	public String index() {
		return this.getAuthUser();
	}

	public static String getAuthUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth);
		if (auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) auth.getPrincipal()).getUsername();
		}
		return "cc";
	}
}