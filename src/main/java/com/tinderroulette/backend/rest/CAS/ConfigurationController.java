package com.tinderroulette.backend.rest.CAS;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {
    @GetMapping(value = "/")
    @ResponseBody
    public void index(HttpServletResponse httpServletResponse) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = "cc";
        if (auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof UserDetails) {
            user = ((UserDetails) auth.getPrincipal()).getUsername();
        }

        httpServletResponse.addCookie(CASCookie.createCookie("auth_user", auth.getName()));
        httpServletResponse.addCookie(CASCookie.createCookie("auth_cred", auth.getCredentials().toString()));
        httpServletResponse.sendRedirect("http://localhost:8080");
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
