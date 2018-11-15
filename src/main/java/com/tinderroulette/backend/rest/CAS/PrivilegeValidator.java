package com.tinderroulette.backend.rest.CAS;

import java.util.Arrays;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tinderroulette.backend.rest.Message;
import com.tinderroulette.backend.rest.dao.MembersDao;
import com.tinderroulette.backend.rest.model.Members;

@Service
@Configurable
public class PrivilegeValidator {
    @Autowired
    MembersDao membersDao;

    public String validate(Cookie userCookie, Cookie credCookie, Status... roles) throws Exception {
        CASCookie.decodeLoginCookie(userCookie, credCookie);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cip = "cc";
        if (auth != null && auth.getPrincipal() != null) {
            cip = auth.getPrincipal().toString();
        }
        Members member = membersDao.findByCip(cip);

        if (member == null || !Arrays.asList(roles).stream().anyMatch(o -> o.getId() == member.getIdMemberStatus())) {
            Exception up = new Exception(Message.PRIVILEGE_INSUFFICIENT.toString());
            throw up;
        }
        return cip;

    }
}
